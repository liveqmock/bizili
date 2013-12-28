package com.vteba.cache.memcached.transaction.local;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.transaction.RollbackException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vteba.cache.hibernate.CacheException;
import com.vteba.cache.memcached.Element;
import com.vteba.cache.memcached.MemcacheClientDelegate;
import com.vteba.cache.memcached.Store;
import com.vteba.cache.memcached.store.ElementValueComparator;
import com.vteba.cache.memcached.store.compound.ReadWriteCopyStrategy;
import com.vteba.cache.memcached.transaction.AbstractTransactionStore;
import com.vteba.cache.memcached.transaction.TransactionController;
import com.vteba.cache.memcached.transaction.TransactionException;
import com.vteba.cache.memcached.transaction.TransactionID;
import com.vteba.cache.memcached.transaction.manager.TransactionManagerLookup;
import com.vteba.cache.memcached.transaction.xa.EhcacheXAResource;
import com.vteba.cache.memcached.transaction.xa.XAExecutionListener;
import com.vteba.cache.memcached.transaction.xa.XATransactionContext;
import com.vteba.lang.concurrent.VicariousThreadLocal;

/**
 * A Store implementation with support for local transactions driven by a JTA transaction manager
 * 支持jta事务管理器驱动的本地事务的存储实现
 * @author Ludovic Orban
 */
public class JtaLocalTransactionStore extends AbstractTransactionStore {

    private static final Logger LOG = LoggerFactory.getLogger(JtaLocalTransactionStore.class.getName());
    private static final String ALTERNATIVE_TERMINATION_MODE_SYS_PROPERTY_NAME = "net.sf.ehcache.transaction.xa.alternativeTerminationMode";
    private static final AtomicBoolean ATOMIKOS_WARNING_ISSUED = new AtomicBoolean(false);

    private static final VicariousThreadLocal<Transaction> BOUND_JTA_TRANSACTIONS = new VicariousThreadLocal<Transaction>();

    private final TransactionManagerLookup transactionManagerLookup;
    private final TransactionController transactionController;
    private final TransactionManager transactionManager;

    /**
     * Create a new JtaLocalTransactionStore instance
     * @param localTxStore the LocalTransactionStore
     * @param memoryStore 基于map实现的内存存储
     * @param transactionManagerLookup the TransactionManagerLookup 这个参数可以传null，可以直接从Memcache中获得
     * @param transactionController the TransactionController
     */
    public JtaLocalTransactionStore(LocalTransactionStore localTxStore, Store memoryStore, TransactionManagerLookup transactionManagerLookup,
                                    TransactionController transactionController, ReadWriteCopyStrategy<Element> copyStrategy) {
        super(memoryStore, localTxStore, copyStrategy);
        this.transactionManagerLookup = transactionManagerLookup;
        this.transactionController = transactionController;
        this.transactionManager = transactionManagerLookup.getTransactionManager();
        if (this.transactionManager == null) {
            throw new TransactionException("no JTA transaction manager could be located");
        }
        if (transactionManager.getClass().getName().contains("atomikos")) {
            System.setProperty(ALTERNATIVE_TERMINATION_MODE_SYS_PROPERTY_NAME, "true");
            if (ATOMIKOS_WARNING_ISSUED.compareAndSet(false, true)) {
                LOG.warn("Atomikos transaction manager detected, make sure you configured com.atomikos.icatch.threaded_2pc=false");
            }
        }
    }

    private void registerInJtaContext() {
        try {
            if (transactionController.getCurrentTransactionContext() != null) {
                // already started local TX and registered in JTA

                // make sure the JTA transaction hasn't changed (happens when TM.suspend() is called)
                Transaction tx = transactionManager.getTransaction();
                if (!BOUND_JTA_TRANSACTIONS.get().equals(tx)) {
                    throw new TransactionException("Invalid JTA transaction context, cache was first used in transaction ["
                            + BOUND_JTA_TRANSACTIONS.get() + "]" +
                            " but is now used in transaction [" + tx + "].");
                }
            } else {
                Transaction tx = transactionManager.getTransaction();
                if (tx == null) {
                    throw new TransactionException("no JTA transaction context started, xa caches cannot be used outside of" +
                            " JTA transactions");
                }
                BOUND_JTA_TRANSACTIONS.set(tx);

                transactionController.begin();

                // DEV-5376
                if (Boolean.getBoolean(ALTERNATIVE_TERMINATION_MODE_SYS_PROPERTY_NAME)) {

                    JtaLocalEhcacheXAResource xaRes = new JtaLocalEhcacheXAResource(transactionController,
                            transactionController.getCurrentTransactionContext().getTransactionId(), transactionManagerLookup);
                    transactionManagerLookup.register(xaRes, false);
                    tx.enlistResource(xaRes);
                } else {
                    tx.registerSynchronization(new JtaLocalEhcacheSynchronization(transactionController,
                            transactionController.getCurrentTransactionContext().getTransactionId()));
                }
            }
        } catch (SystemException e) {
            throw new TransactionException("internal JTA transaction manager error, cannot bind xa cache with it", e);
        } catch (RollbackException e) {
            throw new TransactionException("JTA transaction rolled back, cannot bind xa cache with it", e);
        }
    }

    /**
     * A Synchronization used to terminate the local transaction and clean it up
     */
    private static final class JtaLocalEhcacheSynchronization implements Synchronization {
        private final TransactionController transactionController;
        private final TransactionID transactionId;

        private JtaLocalEhcacheSynchronization(TransactionController transactionController, TransactionID transactionId) {
            this.transactionController = transactionController;
            this.transactionId = transactionId;
        }

        public void beforeCompletion() {
            //
        }

        public void afterCompletion(int status) {
            JtaLocalTransactionStore.BOUND_JTA_TRANSACTIONS.remove();
            if (status == javax.transaction.Status.STATUS_COMMITTED) {
                transactionController.commit(true);
            } else if (status == javax.transaction.Status.STATUS_ROLLEDBACK) {
                transactionController.rollback();
            } else {
                transactionController.rollback();
                LOG.warn("The transaction manager reported UNKNOWN transaction status upon termination." +
                        " The ehcache transaction has been rolled back!");
            }
        }

        @Override
        public String toString() {
            return "JtaLocalEhcacheSynchronization of transaction [" + transactionId + "]";
        }
    }

    /**
     * A XAResource implementation used to terminate the local transaction and clean it up.
     *
     * It should only be used with transaction managers providing a defective Synchronization
     * mechanism with which rollback/commit cannot be reliably differentiated as it relies
     * on the fact that the same thread is used to call Ehcache methods as well as XAResource
     * which isn't guaranteed by the specification.
     * This mechanism also has a slight performance impact as there is one extra resource
     * participating in the 2PC.
     */
    private static final class JtaLocalEhcacheXAResource implements EhcacheXAResource {
        private final TransactionController transactionController;
        private final TransactionID transactionId;
        private final TransactionManagerLookup transactionManagerLookup;

        private JtaLocalEhcacheXAResource(TransactionController transactionController, TransactionID transactionId,
                                          TransactionManagerLookup transactionManagerLookup) {
            this.transactionController = transactionController;
            this.transactionId = transactionId;
            this.transactionManagerLookup = transactionManagerLookup;
        }

        public void commit(Xid xid, boolean onePhase) throws XAException {
            transactionController.commit(true);
            JtaLocalTransactionStore.BOUND_JTA_TRANSACTIONS.remove();
            transactionManagerLookup.unregister(this, false);
        }

        public void end(Xid xid, int flag) throws XAException {
            //
        }

        public void forget(Xid xid) throws XAException {
            //
        }

        public int getTransactionTimeout() throws XAException {
            return 0;
        }

        public boolean isSameRM(XAResource xaResource) throws XAException {
            return xaResource == this;
        }

        public int prepare(Xid xid) throws XAException {
            return XA_OK;
        }

        public Xid[] recover(int flags) throws XAException {
            return new Xid[0];
        }

        public void rollback(Xid xid) throws XAException {
            transactionController.rollback();
            JtaLocalTransactionStore.BOUND_JTA_TRANSACTIONS.remove();
            transactionManagerLookup.unregister(this, false);
        }

        public boolean setTransactionTimeout(int timeout) throws XAException {
            return false;
        }

        public void start(Xid xid, int flag) throws XAException {
            //
        }

        public void addTwoPcExecutionListener(XAExecutionListener listener) {
            throw new UnsupportedOperationException();
        }

        public String getCacheName() {
            return transactionId.toString();
        }

        public XATransactionContext createTransactionContext() throws SystemException, RollbackException {
            throw new UnsupportedOperationException();
        }

        public XATransactionContext getCurrentTransactionContext() {
            throw new UnsupportedOperationException();
        }

        @Override
        public String toString() {
            return "JtaLocalEhcacheXAResource of transaction [" + transactionId + "]";
        }
    }

    private void setRollbackOnly() {
        try {
            BOUND_JTA_TRANSACTIONS.get().setRollbackOnly();
            transactionController.setRollbackOnly();
        } catch (SystemException e) {
            LOG.warn("internal JTA transaction manager error", e);
        }
    }

    /* transactional methods */

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean put(Element element) throws CacheException {
        registerInJtaContext();
        try {
            return memcacheStore.put(element);
        } catch (CacheException e) {
            setRollbackOnly();
            throw e;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void putAll(Collection<Element> elements) throws CacheException {
        registerInJtaContext();
        try {
            memcacheStore.putAll(elements);
        } catch (CacheException e) {
            setRollbackOnly();
            throw e;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Element get(String key) {
        registerInJtaContext();
        try {
        	return memcacheStore.get(key);
        } catch (CacheException e) {
            setRollbackOnly();
            throw e;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getKeys() {
        registerInJtaContext();
        try {
            return memcacheStore.getKeys();
        } catch (CacheException e) {
            setRollbackOnly();
            throw e;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Element remove(String key) {
        registerInJtaContext();
        try {
            return memcacheStore.remove(key);
        } catch (CacheException e) {
            setRollbackOnly();
            throw e;
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void removeAll(Collection<String> keys) {
        registerInJtaContext();
        try {
            memcacheStore.removeAll(keys);
        } catch (CacheException e) {
            setRollbackOnly();
            throw e;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Element putIfAbsent(Element element) throws NullPointerException {
        registerInJtaContext();
        try {
            return memcacheStore.putIfAbsent(element);
        } catch (CacheException e) {
            setRollbackOnly();
            throw e;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Element removeElement(Element element, ElementValueComparator comparator) throws NullPointerException {
        registerInJtaContext();
        try {
            return memcacheStore.removeElement(element, comparator);
        } catch (CacheException e) {
            setRollbackOnly();
            throw e;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean replace(Element old, Element element, ElementValueComparator comparator)
            throws NullPointerException, IllegalArgumentException {
        registerInJtaContext();
        try {
            return memcacheStore.replace(old, element, comparator);
        } catch (CacheException e) {
            setRollbackOnly();
            throw e;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsKey(String key) {
        registerInJtaContext();
        try {
            return memcacheStore.containsKey(key);
        } catch (CacheException e) {
            setRollbackOnly();
            throw e;
        }
    }
    
	@Override
	public Map<String, Element> getAll(Collection<String> keys) {
		registerInJtaContext();
        try {
        	return memcacheStore.getAll(keys);
        } catch (CacheException e) {
            setRollbackOnly();
            throw e;
        }
	}

	@Override
	public MemcacheClientDelegate getMemcacheClientDelegate() {
		return memcacheStore.getMemcacheClientDelegate();
	}

}

