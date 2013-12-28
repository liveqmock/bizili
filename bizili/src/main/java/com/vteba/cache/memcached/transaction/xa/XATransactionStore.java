package com.vteba.cache.memcached.transaction.xa;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.transaction.RollbackException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.xa.XAException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vteba.cache.hibernate.CacheException;
import com.vteba.cache.memcached.Element;
import com.vteba.cache.memcached.MemcacheClientDelegate;
import com.vteba.cache.memcached.Store;
import com.vteba.cache.memcached.store.ElementValueComparator;
import com.vteba.cache.memcached.store.compound.ReadWriteCopyStrategy;
import com.vteba.cache.memcached.transaction.AbstractTransactionStore;
import com.vteba.cache.memcached.transaction.SoftLock;
import com.vteba.cache.memcached.transaction.SoftLockID;
import com.vteba.cache.memcached.transaction.SoftLockManager;
import com.vteba.cache.memcached.transaction.TransactionException;
import com.vteba.cache.memcached.transaction.TransactionIDFactory;
import com.vteba.cache.memcached.transaction.TransactionInterruptedException;
import com.vteba.cache.memcached.transaction.TransactionTimeoutException;
import com.vteba.cache.memcached.transaction.manager.TransactionManagerLookup;
import com.vteba.cache.memcached.transaction.xa.commands.StorePutCommand;
import com.vteba.cache.memcached.transaction.xa.commands.StoreRemoveCommand;

public class XATransactionStore extends AbstractTransactionStore {

    private static final Logger LOG = LoggerFactory.getLogger(XATransactionStore.class.getName());

    private final TransactionManagerLookup transactionManagerLookup;
    private final TransactionIDFactory transactionIdFactory;
    private final SoftLockManager softLockManager;
    //private final Memcache cache;

    private final ConcurrentHashMap<Transaction, EhcacheXAResource> transactionToXAResourceMap =
            new ConcurrentHashMap<Transaction, EhcacheXAResource>();
    private final ConcurrentHashMap<Transaction, Long> transactionToTimeoutMap = new ConcurrentHashMap<Transaction, Long>();

    /**
     * Constructor
     * @param transactionManagerLookup the transaction manager lookup implementation
     * @param softLockManager the soft lock manager
     * @param transactionIdFactory the transaction ID factory
     * @param cache the cache
     * @param store the underlying store
     * @param copyStrategy the original copy strategy
     */
    public XATransactionStore(TransactionManagerLookup transactionManagerLookup, SoftLockManager softLockManager,
                              TransactionIDFactory transactionIdFactory, Store underlyingStore,
                              Store memoryStore, ReadWriteCopyStrategy<Element> copyStrategy) {
        super(memoryStore, underlyingStore, copyStrategy);
        this.transactionManagerLookup = transactionManagerLookup;
        this.transactionIdFactory = transactionIdFactory;
        if (transactionManagerLookup.getTransactionManager() == null) {
            throw new TransactionException("no JTA transaction manager could be located, cannot bind twopc cache with JTA");
        }
        this.softLockManager = softLockManager;
        //this.cache = cache;
    }

    private Transaction getCurrentTransaction() throws SystemException {
        Transaction transaction = transactionManagerLookup.getTransactionManager().getTransaction();
        if (transaction == null) {
            throw new TransactionException("JTA transaction not started");
        }
        return transaction;
    }

    /**
     * Get or create the XAResource of this XA store
     * @return the EhcacheXAResource of this store
     * @throws SystemException when something goes wrong with the transaction manager
     */
    public EhcacheXAResourceImpl getOrCreateXAResource() throws SystemException {
        Transaction transaction = getCurrentTransaction();
        EhcacheXAResourceImpl xaResource = (EhcacheXAResourceImpl) transactionToXAResourceMap.get(transaction);
        if (xaResource == null) {
            LOG.debug("creating new XAResource");
            xaResource = new EhcacheXAResourceImpl(memcache, memoryStore, transactionManagerLookup,
                    softLockManager, transactionIdFactory);//, copyStrategy);
            transactionToXAResourceMap.put(transaction, xaResource);
            xaResource.addTwoPcExecutionListener(new CleanupXAResource(getCurrentTransaction()));
        }
        return xaResource;
    }

    private XATransactionContext getTransactionContext() {
        try {
            Transaction transaction = getCurrentTransaction();
            EhcacheXAResourceImpl xaResource = (EhcacheXAResourceImpl) transactionToXAResourceMap.get(transaction);
            if (xaResource == null) {
                return null;
            }
            XATransactionContext transactionContext = xaResource.getCurrentTransactionContext();

            if (transactionContext == null) {
                transactionManagerLookup.register(xaResource, false);
                LOG.debug("creating new XA context");
                transactionContext = xaResource.createTransactionContext();
                xaResource.addTwoPcExecutionListener(new UnregisterXAResource());
            } else {
                transactionContext = xaResource.getCurrentTransactionContext();
            }

            LOG.debug("using XA context {}", transactionContext);
            return transactionContext;
        } catch (SystemException e) {
            throw new TransactionException("cannot get the current transaction", e);
        } catch (RollbackException e) {
            throw new TransactionException("transaction rolled back", e);
        }
    }

    private XATransactionContext getOrCreateTransactionContext() {
        try {
            EhcacheXAResourceImpl xaResource = getOrCreateXAResource();
            XATransactionContext transactionContext = xaResource.getCurrentTransactionContext();

            if (transactionContext == null) {
                transactionManagerLookup.register(xaResource, false);
                LOG.debug("creating new XA context");
                transactionContext = xaResource.createTransactionContext();
                xaResource.addTwoPcExecutionListener(new UnregisterXAResource());
            } else {
                transactionContext = xaResource.getCurrentTransactionContext();
            }

            LOG.debug("using XA context {}", transactionContext);
            return transactionContext;
        } catch (SystemException e) {
            throw new TransactionException("cannot get the current transaction", e);
        } catch (RollbackException e) {
            throw new TransactionException("transaction rolled back", e);
        }
    }

    /**
     * This class is used to clean up the transactionToTimeoutMap after a transaction
     * committed or rolled back.
     */
    private final class CleanupTimeout implements Synchronization {
        private final Transaction transaction;

        private CleanupTimeout(final Transaction transaction) {
            this.transaction = transaction;
        }

        public void beforeCompletion() {
        }

        public void afterCompletion(final int status) {
            transactionToTimeoutMap.remove(transaction);
        }
    }

    /**
     * This class is used to clean up the transactionToXAResourceMap after a transaction
     * committed or rolled back.
     */
    private final class CleanupXAResource implements XAExecutionListener {
        private final Transaction transaction;

        private CleanupXAResource(Transaction transaction) {
            this.transaction = transaction;
        }

        public void beforePrepare(EhcacheXAResource xaResource) {
        }

        public void afterCommitOrRollback(EhcacheXAResource xaResource) {
            transactionToXAResourceMap.remove(transaction);
        }
    }

    /**
     * This class is used to unregister the XAResource after a transaction
     * committed or rolled back.
     */
    private final class UnregisterXAResource implements XAExecutionListener {

        public void beforePrepare(EhcacheXAResource xaResource) {
        }

        public void afterCommitOrRollback(EhcacheXAResource xaResource) {
            transactionManagerLookup.unregister(xaResource, false);
        }
    }


    /**
     * @return milliseconds left before timeout
     */
    private long assertNotTimedOut() {
        try {
            if (Thread.interrupted()) {
                throw new TransactionInterruptedException("transaction interrupted");
            }

            Transaction transaction = getCurrentTransaction();
            Long timeoutTimestamp = transactionToTimeoutMap.get(transaction);
            long now = MILLISECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS);
            if (timeoutTimestamp == null) {
                long timeout;
                EhcacheXAResource xaResource = transactionToXAResourceMap.get(transaction);
                if (xaResource != null) {
                    int xaResourceTimeout = xaResource.getTransactionTimeout();
                    timeout = MILLISECONDS.convert(xaResourceTimeout, TimeUnit.SECONDS);
                } else {
                    int defaultTransactionTimeout = memcache.getCacheManager().getTransactionController().getDefaultTransactionTimeout();
                    timeout = MILLISECONDS.convert(defaultTransactionTimeout, TimeUnit.SECONDS);
                }
                timeoutTimestamp = now + timeout;
                transactionToTimeoutMap.put(transaction, timeoutTimestamp);
                try {
                    transaction.registerSynchronization(new CleanupTimeout(transaction));
                } catch (RollbackException e) {
                    throw new TransactionException("transaction has been marked as rollback only", e);
                }
                return timeout;
            } else {
                long timeToExpiry = timeoutTimestamp - now;
                if (timeToExpiry <= 0) {
                    throw new TransactionTimeoutException("transaction timed out");
                } else {
                    return timeToExpiry;
                }
            }
        } catch (SystemException e) {
            throw new TransactionException("cannot get the current transaction", e);
        } catch (XAException e) {
            throw new TransactionException("cannot get the XAResource transaction timeout", e);
        }
    }

    /* transactional methods */

    /**
     * {@inheritDoc}
     */
    public Element get(String key) {
        LOG.debug("cache {} get {}", memcache.getName(), key);
        XATransactionContext context = getTransactionContext();
        Element element;
        if (context == null) {
            element = getFromUnderlyingStore(key);
        } else {
            element = context.get(key);
            if (element == null && !context.isRemoved(key)) {
                element = getFromUnderlyingStore(key);
            }
        }
        //return copyElementForRead(element);
        return element;
    }


    /**
     * {@inheritDoc}
     */
    public Element getQuiet(String key) {
        LOG.debug("cache {} getQuiet {}", memcache.getName(), key);
        XATransactionContext context = getTransactionContext();
        Element element;
        if (context == null) {
            element = getQuietFromUnderlyingStore(key);
        } else {
            element = context.get(key);
            if (element == null && !context.isRemoved(key)) {
                element = getQuietFromUnderlyingStore(key);
            }
        }
        //return copyElementForRead(element);
        return element;
    }

    /**
     * {@inheritDoc}
     */
    public boolean containsKey(String key) {
        LOG.debug("cache {} containsKey", memcache.getName(), key);
        XATransactionContext context = getOrCreateTransactionContext();
        return !context.isRemoved(key) && (context.getAddedKeys().contains(key) || memoryStore.containsKey(key));
    }

    /**
     * {@inheritDoc}
     */
    public List<String> getKeys() {
//        LOG.debug("cache {} getKeys", cache.getName());
//        XATransactionContext context = getOrCreateTransactionContext();
//        Set<Object> keys = new LargeSet<Object>() {
//
//            @Override
//            public int sourceSize() {
//                ///return underlyingStore.getSize();
//            	return 0;
//            }
//
//            @SuppressWarnings("unchecked")
//			@Override
//            public Iterator<Object> sourceIterator() {
//                return underlyingStore.getKeys().iterator();
//            }
//        };
//        keys.addAll(context.getAddedKeys());
//        keys.removeAll(context.getRemovedKeys());
//        return new SetAsList<Object>(keys);
    	return memoryStore.getKeys();
    }


    private Element getFromUnderlyingStore(final String key) {
        while (true) {
            long timeLeft = assertNotTimedOut();
            LOG.debug("cache {} underlying.get key {} not timed out, time left: " + timeLeft, memcache.getName(), key);

            Element element = memoryStore.get(key);
            if (element == null) {
                return null;
            }
            Object value = element.getObjectValue();
            if (value instanceof SoftLockID) {
                SoftLockID softLockId = (SoftLockID) value;
                SoftLock softLock = softLockManager.findSoftLockById(softLockId);
                if (softLock == null) {
                    LOG.debug("cache {} underlying.get key {} soft lock died, retrying...", memcache.getName(), key);
                    continue;
                } else {
                    try {
                        LOG.debug("cache {} key {} soft locked, awaiting unlock...", memcache.getName(), key);
                        if (softLock.tryLock(timeLeft)) {
                            softLock.clearTryLock();
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            } else {
                return element;
            }
        }
    }

    private Element getQuietFromUnderlyingStore(final String key) {
        while (true) {
            long timeLeft = assertNotTimedOut();
            LOG.debug("cache {} underlying.getQuiet key {} not timed out, time left: " + timeLeft, memcache.getName(), key);

            Element element = memoryStore.get(key);
            if (element == null) {
                return null;
            }
            Object value = element.getObjectValue();
            if (value instanceof SoftLockID) {
                SoftLockID softLockId = (SoftLockID) value;
                SoftLock softLock = softLockManager.findSoftLockById(softLockId);
                if (softLock == null) {
                    LOG.debug("cache {} underlying.getQuiet key {} soft lock died, retrying...", memcache.getName(), key);
                    continue;
                } else {
                    try {
                        LOG.debug("cache {} key {} soft locked, awaiting unlock...", memcache.getName(), key);
                        if (softLock.tryLock(timeLeft)) {
                            softLock.clearTryLock();
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            } else {
                return element;
            }
        }
    }

    private Element getCurrentElement(final String key, final XATransactionContext context) {
        Element previous = context.get(key);
        if (previous == null && !context.isRemoved(key)) {
            previous = getQuietFromUnderlyingStore(key);
        }
        return previous;
    }

    /**
     * {@inheritDoc}
     */
    public boolean put(Element element) throws CacheException {
        LOG.debug("cache {} put {}", memcache.getName(), element);
        // this forces enlistment so the XA transaction timeout can be propagated to the XA resource
        getOrCreateTransactionContext();

        Element oldElement = getQuietFromUnderlyingStore(element.getKey());
        return internalPut(new StorePutCommand(oldElement, element));
    }

    private boolean internalPut(final StorePutCommand putCommand) {
        final Element element = putCommand.getElement();
        boolean isNull;
        if (element == null) {
            return true;
        }
        XATransactionContext context = getOrCreateTransactionContext();
        // In case this key is currently being updated...
        isNull = memoryStore.get(element.getKey()) == null;
        if (isNull) {
            isNull = context.get(element.getKey()) == null;
        }
        context.addCommand(putCommand, element);
        return isNull;
    }


    /**
     * {@inheritDoc}
     */
    public Element remove(String key) {
        LOG.debug("cache {} remove {}", memcache.getName(), key);
        // this forces enlistment so the XA transaction timeout can be propagated to the XA resource
        getOrCreateTransactionContext();

        Element oldElement = getQuietFromUnderlyingStore(key);
        return removeInternal(new StoreRemoveCommand(key, oldElement));
    }

    private Element removeInternal(final StoreRemoveCommand command) {
        Element element = command.getEntry();//.getElement();
        getOrCreateTransactionContext().addCommand(command, element);
        //return copyElementForRead(element);
        return element;
    }

    /**
     * {@inheritDoc}
     */
    public void removeAll() throws CacheException {
        LOG.debug("cache {} removeAll", memcache.getName());
        List<String> keys = getKeys();
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * {@inheritDoc}
     */
    public Element putIfAbsent(Element element) throws NullPointerException {
        LOG.debug("cache {} putIfAbsent {}", memcache.getName(), element);
        XATransactionContext context = getOrCreateTransactionContext();
        Element previous = getCurrentElement(element.getKey(), context);

        if (previous == null) {
            Element oldElement = getQuietFromUnderlyingStore(element.getKey());
            Element elementForWrite = element;//copyElementForWrite(element);
            context.addCommand(new StorePutCommand(oldElement, elementForWrite), elementForWrite);
        }

        //return copyElementForRead(previous);
        return previous;
    }

    /**
     * {@inheritDoc}
     */
    public Element removeElement(Element element, ElementValueComparator comparator) throws NullPointerException {
        LOG.debug("cache {} removeElement {}", memcache.getName(), element);
        XATransactionContext context = getOrCreateTransactionContext();
        Element previous = getCurrentElement(element.getKey(), context);

        Element elementForWrite = element;//copyElementForWrite(element);
        if (previous != null && element.getKey().equals(previous.getKey())) {
            Element oldElement = getQuietFromUnderlyingStore(element.getKey());
            context.addCommand(new StoreRemoveCommand(element.getKey(), oldElement), elementForWrite);
            return previous;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public boolean replace(Element old, Element element, ElementValueComparator comparator)
            throws NullPointerException, IllegalArgumentException {
        LOG.debug("cache {} replace2 {}", memcache.getName(), element);
        XATransactionContext context = getOrCreateTransactionContext();
        Element previous = getCurrentElement(element.getKey(), context);

        boolean replaced = false;
        if (previous != null && old.getKey().equals(previous.getKey())) {
            Element oldElement = getQuietFromUnderlyingStore(element.getKey());
            Element elementForWrite = element;//copyElementForWrite(element);
            context.addCommand(new StorePutCommand(oldElement, elementForWrite), elementForWrite);
            replaced = true;
        }
        return replaced;
    }

    /**
     * {@inheritDoc}
     */
    public Element replace(Element element) throws NullPointerException {
        LOG.debug("cache {} replace1 {}", memcache.getName(), element);
        XATransactionContext context = getOrCreateTransactionContext();
        Element previous = getCurrentElement(element.getKey(), context);

        if (previous != null) {
            Element oldElement = getQuietFromUnderlyingStore(element.getKey());
            Element elementForWrite = element;//copyElementForWrite(element);
            context.addCommand(new StorePutCommand(oldElement, elementForWrite), elementForWrite);
        }
        //return copyElementForRead(previous);
        return previous;
    }

	@Override
	public void putAll(Collection<Element> elements) throws CacheException {
		
	}

	@Override
	public void removeAll(Collection<String> keys) {
		
	}

	@Override
	public Map<String, Element> getAll(Collection<String> keys) {
		return null;
	}

	@Override
	public MemcacheClientDelegate getMemcacheClientDelegate() {
		return memcacheStore.getMemcacheClientDelegate();
	}

}
