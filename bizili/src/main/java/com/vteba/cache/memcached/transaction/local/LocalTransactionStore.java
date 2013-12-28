package com.vteba.cache.memcached.transaction.local;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vteba.cache.hibernate.CacheException;
import com.vteba.cache.memcached.Element;
import com.vteba.cache.memcached.MemcacheClientDelegate;
import com.vteba.cache.memcached.Store;
import com.vteba.cache.memcached.store.ElementValueComparator;
import com.vteba.cache.memcached.store.compound.ReadWriteCopyStrategy;
import com.vteba.cache.memcached.transaction.AbstractTransactionStore;
import com.vteba.cache.memcached.transaction.DeadLockException;
import com.vteba.cache.memcached.transaction.SoftLock;
import com.vteba.cache.memcached.transaction.SoftLockID;
import com.vteba.cache.memcached.transaction.SoftLockManager;
import com.vteba.cache.memcached.transaction.TransactionController;
import com.vteba.cache.memcached.transaction.TransactionException;
import com.vteba.cache.memcached.transaction.TransactionID;
import com.vteba.cache.memcached.transaction.TransactionIDFactory;
import com.vteba.cache.memcached.transaction.TransactionInterruptedException;
import com.vteba.cache.memcached.transaction.TransactionTimeoutException;

/**
 * A Store implementation with support for local transactions
 * 支持本地事务的存储实现
 * @author Ludovic Orban
 */
public class LocalTransactionStore extends AbstractTransactionStore {

    private static final Logger LOG = LoggerFactory.getLogger(LocalTransactionStore.class.getName());
    /**
     * 事务控制器
     */
    private final TransactionController transactionController;
    /**
     * 事务ID工厂
     */
    private final TransactionIDFactory transactionIdFactory;
    /**
     * 软锁定管理器
     */
    private final SoftLockManager softLockManager;
    /**
     * 缓存名字
     */
    private final String cacheName;
    
    /**
     * 元素值比较器
     */
    private final ElementValueComparator comparator;
    
    /**
     * Memcache实例
     */
    //private final Memcache cache;

    /**
     * Create a new LocalTransactionStore instance
     * @param transactionController 事务控制器
     * @param softLockManager 软锁定管理器
     * @param cache Memcache缓存实例
     * @param underlyingStore 基于memcache的持久化存储
     * @param memoryStore 基于map的内存存储
     * @param copyStrategy 元素读写复制策略
     */
    public LocalTransactionStore(TransactionController transactionController, TransactionIDFactory transactionIdFactory,
            SoftLockManager softLockManager, String cacheName, Store underlyingStore, Store memoryStore, 
            ReadWriteCopyStrategy<Element> copyStrategy, ElementValueComparator comparator) {
        super(memoryStore, underlyingStore, copyStrategy);
        this.transactionController = transactionController;
        this.transactionIdFactory = transactionIdFactory;
        this.softLockManager = softLockManager;
        //this.cache = cache;
        this.cacheName = cacheName;
        this.comparator = comparator;
        transactionController.getRecoveryManager().register(this);
    }

    /**
     * 获得当前的事务上下文
     * @author yinlei
     * date 2012-11-25 下午9:53:51
     */
    private LocalTransactionContext getCurrentTransactionContext() {
        LocalTransactionContext currentTransactionContext = transactionController.getCurrentTransactionContext();
        if (currentTransactionContext == null) {
            throw new TransactionException("transaction not started");
        }
        return currentTransactionContext;
    }

    /**
     * 验证没有超时或者线程没有中断，否则抛出异常
     * @author yinlei
     * date 2012-11-25 下午5:11:05
     */
    private void assertNotTimedOut(Object key, boolean wasPinned) {
        if (getCurrentTransactionContext().timedOut()) {
            throw new TransactionTimeoutException("transaction [" + getCurrentTransactionContext().getTransactionId() + "] timed out");
        }
        if (Thread.interrupted()) {
            throw new TransactionInterruptedException("transaction [" + getCurrentTransactionContext().getTransactionId() + "] interrupted");
        }
    }

    /**
     * 断言没有超时
     * @author yinlei
     * date 2012-12-15 下午10:27:56
     */
    private void assertNotTimedOut() {
        assertNotTimedOut(null, true);
    }

    /**
     * 还有多少时间，事务上下文将过期
     * @author yinlei
     * date 2012-12-15 下午10:29:01
     */
    private long timeBeforeTimeout() {
        return getCurrentTransactionContext().timeBeforeTimeout();
    }

    /**
     * 创建一个值为该key的软锁定的元素，将其放入临时缓存，在commit或rollback时进行提交。
     * @param key 元素key
     * @param softLockId 该元素的软锁定
     * @param isPinned 是否固定
     * @return 新元素
     * @author yinlei
     * date 2012-12-7 下午10:21:56
     */
    private Element createElement(String key, SoftLockID softLockId, boolean isPinned) {
        Element element = new Element(key, softLockId);
        element.setEternal(true);
        return element;
    }
    /**
     * 清理过期的软锁定
     * @param oldElement
     * @param softLockId
     * @return 如果该元素的软锁定过期了，返回true，否则false
     * @author yinlei
     * date 2012-11-10 下午10:49:04
     */
    private boolean cleanupExpiredSoftLock(Element oldElement, SoftLockID softLockId) {
        SoftLock softLock = softLockManager.findSoftLockById(softLockId);
        if (softLock == null || !softLock.isExpired()) {
            return false;
        } else {
            softLock.lock();
            softLock.freeze();
            try {
                Element frozenElement;
                if (transactionIdFactory.isDecisionCommit(softLockId.getTransactionID())) {
                    frozenElement = softLockId.getNewElement();
                } else {
                    frozenElement = softLockId.getOldElement();
                }
                if (frozenElement != null) {
                    memoryStore.replace(oldElement, frozenElement, comparator);
                } else {
                    memoryStore.removeElement(oldElement, comparator);
                }
            } finally {
                softLock.unfreeze();
                softLock.unlock();
            }
            return true;
        }
    }

    /**
     * Recover and resolve all known soft locks。恢复、处理所有已知的软锁定。
     * @return 被恢复的事务ID的集合
     */
    public Set<TransactionID> recover() {
        Set<TransactionID> allOurTransactionIDs = transactionIdFactory.getAllTransactionIDs();

        Set<TransactionID> recoveredIds = new HashSet<TransactionID>(allOurTransactionIDs);
        Iterator<TransactionID> iterator = recoveredIds.iterator();
        while (iterator.hasNext()) {
            TransactionID transactionId = iterator.next();
            if (!transactionIdFactory.isExpired(transactionId)) {
                iterator.remove();
            }
        }

        LOG.debug("recover: {} dead transactions are going to be recovered", recoveredIds.size());
        for (TransactionID transactionId : recoveredIds) {
            Set<SoftLock> softLocks = new HashSet<SoftLock>(softLockManager.collectAllSoftLocksForTransactionID(transactionId));
            Iterator<SoftLock> softLockIterator = softLocks.iterator();
            while (softLockIterator.hasNext()) {
                SoftLock softLock = softLockIterator.next();
                Element element = memoryStore.get(softLock.getKey());
                if (element.getObjectValue() instanceof SoftLockID) {
                    SoftLockID softLockId = (SoftLockID)element.getObjectValue();
                    cleanupExpiredSoftLock(element, softLockId);
                } else {
                    softLockIterator.remove();
                }
            }
            LOG.debug("recover: recovered {} soft locks from dead transaction with ID [{}]", softLocks.size(), transactionId);
        }

        return recoveredIds;
    }

    /* 以下是事务性的方法 */

    public boolean put(Element element) throws CacheException {
        if (element == null) {
            return true;
        }
        final String key = element.getKey();
        while (true) {
            final boolean isPinned = false;
            assertNotTimedOut(key, isPinned);//事务没有超时，继续执行

            Element oldElement = memoryStore.get(key);///这里应该是内存存储
            if (oldElement == null) {//从存储中未获得元素，说明是新缓存元素
            	TransactionID txnId = getCurrentTransactionContext().getTransactionId();
                SoftLockID softLockId = softLockManager.createSoftLockID(txnId, key, element, null, isPinned);
                SoftLock softLock = softLockManager.findSoftLockById(softLockId);
                softLock.lock();
                Element newElement = createElement(key, softLockId, isPinned);
                
                oldElement = memoryStore.putIfAbsent(newElement);
                if (oldElement == null) {//以前该key没有值，软锁定插入成功，在存储上下文注册该软锁定
                    // CAS succeeded, soft lock is in store, job done.
                    getCurrentTransactionContext().registerSoftLock(cacheName, this, softLock);
                    LOG.debug("put: cache [{}] key [{}] was not in, soft lock inserted", cacheName, key);
                    return true;
                } else {//以前该key没有值，软锁定插入失败，解锁，并将再次尝试
                    // CAS failed, something with that key may now be in store, restart.
                    softLock.unlock();
                    LOG.debug("put: cache [{}] key [{}] was not in, soft lock insertion failed, retrying...", cacheName, key);
                    continue;
                }
            } else {//从存储中得到了元素，以前曾经缓存过
                Object value = oldElement.getObjectValue();
                
                //如果元素的值是软锁定，说明当前事务还未曾提交，还在当前事务上下文中。或者软锁定过期，事务超时等。
                //或者说，还没有存储持久化存储中
                if (value instanceof SoftLockID) {
                    SoftLockID softLockId = (SoftLockID) value;

                    if (cleanupExpiredSoftLock(oldElement, softLockId)) {//是过期的软锁定，清理软锁定，并将再次尝试
                        LOG.debug("put: cache [{}] key [{}] guarded by expired soft lock, cleaned up {}",
                                new Object[] {cacheName, key, softLockId});
                        continue;
                    }

                    //元素key已经被软锁定在当前事务中，为该key创建新的软锁定并存储
                    if (softLockId.getTransactionID().equals(getCurrentTransactionContext().getTransactionId())) {
                        SoftLockID newSoftLockId = softLockManager.createSoftLockID(getCurrentTransactionContext().getTransactionId(), softLockId
                            .getKey(), element, softLockId.getOldElement(), softLockId.wasPinned());
                        Element newElement = createElement(newSoftLockId.getKey(), newSoftLockId, newSoftLockId.wasPinned());
                        memoryStore.put(newElement);
                        LOG.debug("put: cache [{}] key [{}] soft locked in current transaction, replaced old value with new one under" +
                                " soft lock", cacheName, key);
                        // replaced old value with new one under soft lock, job done.
                        return false;
                    } else {//元素key被软锁定在以前的事务中，超时或者其他，软锁定死亡。将尝试清理软锁定，然后再次尝试
                        SoftLock softLock = softLockManager.findSoftLockById(softLockId);
                        if (softLock != null) {
                            LOG.debug("put: cache [{}] key [{}] soft locked in foreign transaction, waiting {}ms for soft lock to die...",
                                    new Object[] {cacheName, key, timeBeforeTimeout()});
                            try {
                                if (!softLock.tryLock(timeBeforeTimeout())) {//尝试锁定软锁定
                                    LOG.debug("put: cache [{}] key [{}] soft locked in foreign transaction and not released before" +
                                            " current transaction timeout", cacheName, key);
                                    if (getCurrentTransactionContext().hasLockedAnything()) {
                                        throw new DeadLockException("deadlock detected in cache [" + cacheName + "] on key [" + key + "]" +
                                                " between current transaction [" + getCurrentTransactionContext().getTransactionId() + "]" +
                                                " and foreign transaction [" + softLockId.getTransactionID() + "]");
                                    } else {
                                        continue;
                                    }
                                } else {//尝试锁定成功，解锁之
                                    softLock.clearTryLock();
                                }
                            } catch (InterruptedException ie) {
                                Thread.currentThread().interrupt();
                            }
                        }

                        LOG.debug("put: cache [{}] key [{}] soft locked in foreign transaction, soft lock died, retrying...",
                                cacheName, key);
                        // once the soft lock got unlocked we don't know what's in the store anymore, restart.
                        continue;
                    }
                } else {//元素key已经在缓存中，使用软锁定替换之
                    SoftLockID softLockId = softLockManager.createSoftLockID(getCurrentTransactionContext().getTransactionId(), key,
                        element, oldElement, isPinned);
                    SoftLock softLock = softLockManager.findSoftLockById(softLockId);
                    softLock.lock();
                    Element newElement = createElement(key, softLockId, isPinned);
                    boolean replaced = memoryStore.replace(oldElement, newElement, comparator);
                    if (replaced) {//替换成功，在事务上下文中注册该软锁定
                        // CAS succeeded, value replaced with soft lock, job done.
                        getCurrentTransactionContext().registerSoftLock(cacheName, this, softLock);
                        LOG.debug("put: cache [{}] key [{}] was in, replaced with soft lock", cacheName, key);
                        return false;
                    } else {//替换失败，解锁
                        // CAS failed, something else with that key is now in store or the key disappeared, restart.
                        softLock.unlock();
                        LOG.debug("put: cache [{}] key [{}] was in, replacement by soft lock failed, retrying... ", cacheName, key);
                        continue;
                    }
                }
            }

        } // while
    }

    public Element get(String key) {
        if (key == null) {
            return null;
        }
        while (true) {
            assertNotTimedOut();

            Element oldElement = memoryStore.get(key);//首先从内存存储中查询
            if (oldElement == null) {
                oldElement = memcacheStore.get(key);//如果内存存储中没有，从持久化存储中查询
            }
            
            if (oldElement == null) {//如果持久化存储中也没有，该key没有对应的缓存元素
            	LOG.debug("get: cache [{}] key [{}] is not present", cacheName, key);
                return null;
            }
            
            Object value = oldElement.getObjectValue();
            if (value instanceof SoftLockID) {//还在当前事务上下文中，元素key被软锁定着
                SoftLockID softLockId = (SoftLockID) value;
                if (cleanupExpiredSoftLock(oldElement, softLockId)) {//元素key被过期的软锁定保护着，清理它
                    LOG.debug("get: cache [{}] key [{}] guarded by expired soft lock, cleaned up {}",
                            new Object[] {cacheName, key, softLockId});
                    continue;
                }

                LOG.debug("get: cache [{}] key [{}] soft locked, returning soft locked element", cacheName, key);
                SoftLock softLock = softLockManager.findSoftLockById(softLockId);
                if (softLock == null) {
                    LOG.debug("get: cache [{}] key [{}] soft locked in foreign transaction, soft lock died, retrying...", cacheName, key);
                    continue;
                } else {//元素key被软锁定着，返回被软锁定的元素
                    return copyElementForRead(softLock.getElement(getCurrentTransactionContext().getTransactionId(), softLockId));
                }
            } else {//元素key没有被软锁定，返回底层存储的元素
                LOG.debug("get: cache [{}] key [{}] not soft locked, returning underlying element", cacheName, key);
                return oldElement;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public Element remove(String key) {
        if (key == null) {
            return null;
        }

        while (true) {
            final boolean isPinned = false;
            assertNotTimedOut(key, isPinned);

            Element oldElement = memoryStore.get(key);
            if (oldElement == null) {//当前事务上下文中没有该key对应的元素，创建一个将在事务提交的时候删除
                SoftLockID softLockId = softLockManager.createSoftLockID(getCurrentTransactionContext().getTransactionId(), key,
                    null, null, isPinned);
                SoftLock softLock = softLockManager.findSoftLockById(softLockId);
                softLock.lock();
                Element newElement = createElement(key, softLockId, isPinned);
                oldElement = memoryStore.putIfAbsent(newElement);
                if (oldElement == null) {//软锁定插入成功，将在事务上下文中，注册该软锁定
                    // CAS succeeded, value is in store, job done.
                    getCurrentTransactionContext().registerSoftLock(cacheName, this, softLock);
                    LOG.debug("remove: cache [{}] key [{}] was not in, soft lock inserted", cacheName, key);
                    return null;
                } else {
                    // CAS failed, something with that key may now be in store, restart.
                    softLock.unlock();
                    LOG.debug("remove: cache [{}] key [{}] was not in, soft lock insertion failed, retrying...", cacheName, key);
                    continue;
                }
            } else {
                Object value = oldElement.getObjectValue();
                if (value instanceof SoftLockID) {//将要删除的元素，被过期的软锁定保护着，清理它
                    SoftLockID softLockId = (SoftLockID) value;

                    if (cleanupExpiredSoftLock(oldElement, softLockId)) {
                        LOG.debug("remove: cache [{}] key [{}] guarded by expired soft lock, cleaned up {}",
                                new Object[] {cacheName, key, softLockId});
                        continue;
                    }

                    //元素key被锁定在当前的事务上下文中，使用新的软锁定取代它
                    if (softLockId.getTransactionID().equals(getCurrentTransactionContext().getTransactionId())) {

                        SoftLockID newSoftLockId = softLockManager.createSoftLockID(getCurrentTransactionContext().getTransactionId(), softLockId
                            .getKey(), null, softLockId.getOldElement(), softLockId.wasPinned());
                        Element newElement = createElement(newSoftLockId.getKey(), newSoftLockId, newSoftLockId.wasPinned());

                        memoryStore.put(newElement);

                        // replaced old value with new one under soft lock, job done.
                        LOG.debug("remove: cache [{}] key [{}] soft locked in current transaction, replaced old value with new one under" +
                                " soft lock", cacheName, key);
                        return softLockId.getNewElement();
                    } else {//元素key被锁定在以前的事务上下文中，清理软锁定
                        SoftLock softLock = softLockManager.findSoftLockById(softLockId);
                        if (softLock != null) {
                            LOG.debug("remove: cache [{}] key [{}] soft locked in foreign transaction, waiting {}ms for soft lock to" +
                                    " die...", new Object[] {cacheName, key, timeBeforeTimeout()});
                            try {
                                if (softLock.tryLock(timeBeforeTimeout())) {
                                    softLock.clearTryLock();
                                } else {
                                    LOG.debug("remove: cache [{}] key [{}] soft locked in foreign transaction and not released before" +
                                            " current transaction timeout", cacheName, key);
                                    if (getCurrentTransactionContext().hasLockedAnything()) {
                                        throw new DeadLockException("deadlock detected in cache [" + cacheName + "] on key [" + key + "]" +
                                                " between current transaction [" + getCurrentTransactionContext().getTransactionId() + "]" +
                                                " and foreign transaction [" + softLockId.getTransactionID() + "]");
                                    } else {
                                        continue;
                                    }
                                }
                            } catch (InterruptedException ie) {
                                Thread.currentThread().interrupt();
                            }
                        }

                        // once the soft lock got unlocked we don't know what's in the store anymore, restart.
                        LOG.debug("remove: cache [{}] key [{}] soft locked in foreign transaction, soft lock died, retrying...",
                                cacheName, key);
                        continue;
                    }
                } else {//元素key已经在缓存中，用软锁定取代它
                    SoftLockID softLockId = softLockManager.createSoftLockID(getCurrentTransactionContext().getTransactionId(), key,
                        null, oldElement, isPinned);
                    SoftLock softLock = softLockManager.findSoftLockById(softLockId);
                    softLock.lock();
                    Element newElement = createElement(key, softLockId, isPinned);
                    boolean replaced = memoryStore.replace(oldElement, newElement, comparator);
                    if (replaced) {//替换成功，在事务上下文中注册该软锁定
                        // CAS succeeded, value replaced with soft lock, job done.
                        getCurrentTransactionContext().registerSoftLock(cacheName, this, softLock);
                        LOG.debug("remove: cache [{}] key [{}] was in, replaced with soft lock", cacheName, key);
                        return copyElementForRead(oldElement);
                    } else {//替换失败，解锁之
                        // CAS failed, something else with that key is now in store or the key disappeared, restart.
                        softLock.unlock();
                        LOG.debug("remove: cache [{}] key [{}] was in, replacement by soft lock failed, retrying...", cacheName, key);
                        continue;
                    }
                }
            }

        } // while
    }

    /**
     * {@inheritDoc}
     */
    public Element putIfAbsent(Element e) throws NullPointerException {
        if (e == null) {
            throw new NullPointerException("element cannot be null");
        }
        if (e.getKey() == null) {
            throw new NullPointerException("element key cannot be null");
        }

        final Element element = copyElementForWrite(e);
        final String key = element.getKey();
        while (true) {
            final boolean isPinned = false;//underlyingStore.isPinned(key);
            assertNotTimedOut(key, isPinned);

            Element oldElement = memoryStore.get(key);
            if (oldElement == null) {//元素key不在当前事务上下文中，将创建软锁定，插入内存存储中
                SoftLockID softLockId = softLockManager.createSoftLockID(getCurrentTransactionContext().getTransactionId(), key,
                    element, oldElement, isPinned);
                SoftLock softLock = softLockManager.findSoftLockById(softLockId);
                softLock.lock();
                Element newElement = createElement(key, softLockId, isPinned);
                oldElement = memoryStore.putIfAbsent(newElement);
                if (oldElement == null) {//软锁定插入成功，在当前事务上下文中，注册该软锁定。
                    // CAS succeeded, soft lock is in store, job done.
                    getCurrentTransactionContext().registerSoftLock(cacheName, this, softLock);
                    LOG.debug("putIfAbsent: cache [{}] key [{}] was not in, soft lock inserted", cacheName, key);
                    return null;
                } else {//软锁定插入失败，将尝试
                    // CAS failed, something with that key may now be in store, lets retry the operation.
                    softLock.unlock();
                    LOG.debug("putIfAbsent: cache [{}] key [{}] was not in, soft lock insertion failed, retrying", cacheName, key);
                    continue;
                }
            } else if (oldElement.getObjectValue() instanceof SoftLockID) {//元素key已经被锁定在过期的软锁定中
                SoftLockID softLockId = (SoftLockID) oldElement.getObjectValue();

                if (cleanupExpiredSoftLock(oldElement, softLockId)) {//如果是过期的软锁定，清理之，并继续
                    LOG.debug("putIfAbsent: cache [{}] key [{}] guarded by expired soft lock, cleaned up {}",
                            new Object[] {cacheName, key, softLockId});
                    continue;
                }
                //元素key被锁定在当前的事务上下文中
                if (softLockId.getTransactionID().equals(getCurrentTransactionContext().getTransactionId())) {
                    SoftLock softLock = softLockManager.findSoftLockById(softLockId);
                    Element currentElement = softLock.getElement(getCurrentTransactionContext().getTransactionId(), softLockId);
                    if (currentElement == null) {//当前事务上下文，无法看到锁定的元素值，使用新软锁定取代它
                        SoftLockID newSoftLockId = softLockManager.createSoftLockID(getCurrentTransactionContext().getTransactionId(),
                            softLockId.getKey(), element, softLockId.getOldElement(), softLockId.wasPinned());
                        Element newElement = createElement(newSoftLockId.getKey(), newSoftLockId, newSoftLockId.wasPinned());
                        memoryStore.put(newElement);

                        LOG.debug("putIfAbsent: cache [{}] key [{}] soft locked in current transaction, replaced null with new element" +
                                " under soft lock", cacheName, key);
                        // replaced null with new one under soft lock, job done.
                        return null;
                    } else {//当前事务上下文能够看到被锁定的元素值，直接返回
                        LOG.debug("putIfAbsent: cache [{}] key [{}] soft locked in current transaction, old element is not null",
                                cacheName, key);
                        // not replaced old value with new one, job done.
                        return copyElementForRead(currentElement);
                    }
                } else {//元素key被锁定以前的事务上下文中
                    SoftLock softLock = softLockManager.findSoftLockById(softLockId);
                    if (softLock != null) {//软锁定不为空，尝试清理软锁定
                        LOG.debug("putIfAbsent: cache [{}] key [{}] soft locked in foreign transaction, waiting {}ms for soft lock to die...",
                                new Object[] {cacheName, key, timeBeforeTimeout()});
                        try {
                            if (softLock.tryLock(timeBeforeTimeout())) {
                                softLock.clearTryLock();
                            } else {
                                LOG.debug("putIfAbsent: cache [{}] key [{}] soft locked in foreign transaction and not released before" +
                                        " current transaction timeout", cacheName, key);
                                if (getCurrentTransactionContext().hasLockedAnything()) {
                                    throw new DeadLockException("deadlock detected in cache [" + cacheName + "] on key [" + key + "]" +
                                            " between current transaction [" + getCurrentTransactionContext().getTransactionId() + "]" +
                                            " and foreign transaction [" + softLockId.getTransactionID() + "]");
                                } else {
                                    continue;
                                }
                            }
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                        }
                    }

                    LOG.debug("putIfAbsent: cache [{}] key [{}] soft locked in foreign transaction, soft lock died, retrying...",
                            cacheName, key);
                    // once the soft lock got unlocked we don't know what's in the store anymore, restart.
                    continue;
                }
            } else {//元素key没有被锁定，返回copy副本
                return copyElementForRead(oldElement);
            }
        } // while
    }

    /**
     * {@inheritDoc}
     */
    public Element removeElement(Element e, ElementValueComparator comparator) throws NullPointerException {
        if (e == null) {
            throw new NullPointerException("element cannot be null");
        }
        if (e.getKey() == null) {
            throw new NullPointerException("element key cannot be null");
        }
        final Element element = copyElementForWrite(e);
        final String key = element.getKey();
        while (true) {
            final boolean isPinned = false;//underlyingStore.isPinned(key);
            assertNotTimedOut(key, isPinned);

            Element oldElement = memoryStore.get(key);
            if (oldElement == null) {
                LOG.debug("removeElement: cache [{}] key [{}] was not in, nothing removed", cacheName, key);
                return null;
            } else {
                Object value = oldElement.getObjectValue();
                if (value instanceof SoftLockID) {
                    SoftLockID softLockId = (SoftLockID) value;

                    if (cleanupExpiredSoftLock(oldElement, softLockId)) {
                        LOG.debug("removeElement: cache [{}] key [{}] guarded by expired soft lock, cleaned up {}",
                                new Object[] {cacheName, key, softLockId});
                        continue;
                    }

                    if (softLockId.getTransactionID().equals(getCurrentTransactionContext().getTransactionId())) {
                        SoftLock softLock = softLockManager.findSoftLockById(softLockId);
                        Element currentElement = softLock.getElement(getCurrentTransactionContext().getTransactionId(), softLockId);
                        if (comparator.equals(element, currentElement)) {
                        //if (element.getKey().equals(currentElement.getKey())) {
                            SoftLockID newSoftLockId = softLockManager.createSoftLockID(getCurrentTransactionContext().getTransactionId(), softLockId
                                .getKey(), element, softLockId.getOldElement(), softLockId.wasPinned());
                            Element newElement = createElement(newSoftLockId.getKey(), newSoftLockId, newSoftLockId.wasPinned());
                            memoryStore.put(newElement);

                            // replaced old element with null under soft lock, job done.
                            LOG.debug("removeElement: cache [{}] key [{}] soft locked in current transaction, replaced old element" +
                                    " with null under soft lock", cacheName, key);
                            return copyElementForRead(softLockId.getNewElement());
                        } else {
                            // old element is not equals to element to remove, job done.
                            LOG.debug("removeElement: cache [{}] key [{}] soft locked in current transaction, old element did not" +
                                    " match element to remove", cacheName, key);
                            return null;
                        }
                    } else {
                        SoftLock softLock = softLockManager.findSoftLockById(softLockId);
                        if (softLock != null) {
                            LOG.debug("removeElement: cache [{}] key [{}] soft locked in foreign transaction, waiting {}ms for soft" +
                                    " lock to die...", new Object[] {cacheName, key, timeBeforeTimeout()});
                            try {
                                if (softLock.tryLock(timeBeforeTimeout())) {
                                    softLock.clearTryLock();
                                } else {
                                    LOG.debug("removeElement: cache [{}] key [{}] soft locked in foreign transaction and not released" +
                                            " before current transaction timeout", cacheName, key);
                                    if (getCurrentTransactionContext().hasLockedAnything()) {
                                        throw new DeadLockException("deadlock detected in cache [" + cacheName + "] on key [" + key + "]" +
                                                " between current transaction [" + getCurrentTransactionContext().getTransactionId() + "]" +
                                                " and foreign transaction [" + softLockId.getTransactionID() + "]");
                                    } else {
                                        continue;
                                    }
                                }
                            } catch (InterruptedException ie) {
                                Thread.currentThread().interrupt();
                            }
                        }

                        // once the soft lock got unlocked we don't know what's in the store anymore, restart.
                        LOG.debug("removeElement: cache [{}] key [{}] soft locked in foreign transaction, soft lock died, retrying...",
                                cacheName, key);
                        continue;
                    }
                } else {
                    SoftLockID softLockId = softLockManager.createSoftLockID(getCurrentTransactionContext().getTransactionId(), key,
                        null, oldElement, isPinned);
                    SoftLock softLock = softLockManager.findSoftLockById(softLockId);
                    softLock.lock();
                    Element newElement = createElement(key, softLockId, isPinned);

                    boolean replaced = memoryStore.replace(oldElement, newElement, comparator);
                    if (replaced) {
                        // CAS succeeded, value replaced with soft lock, job done.
                        getCurrentTransactionContext().registerSoftLock(cacheName, this, softLock);
                        LOG.debug("removeElement: cache [{}] key [{}] was in, replaced with soft lock", cacheName, key);
                        return copyElementForRead(oldElement);
                    } else {
                        // CAS failed, something else with that key is now in store or the key disappeared, job done.
                        softLock.unlock();
                        LOG.debug("removeElement: cache [{}] key [{}] was in, replacement by soft lock failed", cacheName, key);
                        return null;
                    }
                }
            }

        } // while
    }

    /**
     * {@inheritDoc}
     */
    public boolean replace(Element oe, Element ne, ElementValueComparator comparator)
            throws NullPointerException, IllegalArgumentException {
        if (oe == null) {
            throw new NullPointerException("old cannot be null");
        }
        if (oe.getKey() == null) {
            throw new NullPointerException("old key cannot be null");
        }
        if (ne == null) {
            throw new NullPointerException("element cannot be null");
        }
        if (ne.getKey() == null) {
            throw new NullPointerException("element key cannot be null");
        }
        if (!oe.getKey().equals(ne.getKey())) {
            throw new IllegalArgumentException("old and element keys are not equal");
        }

        final Element old = copyElementForWrite(oe);
        final Element element = copyElementForWrite(ne);
        final String key = element.getKey();
        while (true) {
            final boolean isPinned = false;///underlyingStore.isPinned(key);
            assertNotTimedOut(key, isPinned);

            Element oldElement = memoryStore.get(key);
            if (oldElement == null) {
                LOG.debug("replace2: cache [{}] key [{}] was not in, nothing replaced", cacheName, key);
                return false;
            } else {
                Object value = oldElement.getObjectValue();
                if (value instanceof SoftLockID) {
                    SoftLockID softLockId = (SoftLockID) value;

                    if (cleanupExpiredSoftLock(oldElement, softLockId)) {
                        LOG.debug("replace2: cache [{}] key [{}] guarded by expired soft lock, cleaned up {}",
                                new Object[] {cacheName, key, softLockId});
                        continue;
                    }

                    if (softLockId.getTransactionID().equals(getCurrentTransactionContext().getTransactionId())) {
                        SoftLock softLock = softLockManager.findSoftLockById(softLockId);
                        Element currentElement = softLock.getElement(getCurrentTransactionContext().getTransactionId(), softLockId);
                        if (comparator.equals(old, currentElement)) {
                        //if (old.getKey().equals(currentElement)) {
                            SoftLockID newSoftLockId = softLockManager.createSoftLockID(getCurrentTransactionContext().getTransactionId(),
                                softLockId.getKey(), element, softLockId.getOldElement(), softLockId.wasPinned());
                            Element newElement = createElement(newSoftLockId.getKey(), newSoftLockId, newSoftLockId.wasPinned());
                            memoryStore.put(newElement);

                            // replaced old element with new one under soft lock, job done.
                            LOG.debug("replace2: cache [{}] key [{}] soft locked in current transaction, replaced old element with" +
                                    " new one under soft lock", cacheName, key);
                            return true;
                        } else {
                            // old element is not equals to element to remove, job done.
                            LOG.debug("replace2: cache [{}] key [{}] soft locked in current transaction, old element did not match" +
                                    " element to replace", cacheName, key);
                            return false;
                        }
                    } else {
                        SoftLock softLock = softLockManager.findSoftLockById(softLockId);
                        if (softLock != null) {
                            LOG.debug("replace2: cache [{}] key [{}] soft locked in foreign transaction, waiting {}ms for" +
                                    " soft lock to die...", new Object[] {cacheName, key, timeBeforeTimeout()});
                            try {
                                if (softLock.tryLock(timeBeforeTimeout())) {
                                    softLock.clearTryLock();
                                } else {
                                    LOG.debug("replace2: cache [{}] key [{}] soft locked in foreign transaction and not released before" +
                                            " current transaction timeout", cacheName, key);
                                    if (getCurrentTransactionContext().hasLockedAnything()) {
                                        throw new DeadLockException("deadlock detected in cache [" + cacheName + "] on key [" + key + "]" +
                                                " between current transaction [" + getCurrentTransactionContext().getTransactionId() + "]" +
                                                " and foreign transaction [" + softLockId.getTransactionID() + "]");
                                    } else {
                                        continue;
                                    }
                                }
                            } catch (InterruptedException ie) {
                                Thread.currentThread().interrupt();
                            }
                        }

                        // once the soft lock got unlocked we don't know what's in the store anymore, restart.
                        LOG.debug("replace2: cache [{}] key [{}] soft locked in foreign transaction, soft lock died, retrying...",
                                cacheName, key);
                        continue;
                    }
                } else {
                    SoftLockID softLockId = softLockManager.createSoftLockID(getCurrentTransactionContext().getTransactionId(), key,
                        element, oldElement, isPinned);
                    SoftLock softLock = softLockManager.findSoftLockById(softLockId);
                    softLock.lock();
                    Element newElement = createElement(key, softLockId, isPinned);

                    boolean replaced = memoryStore.replace(oldElement, newElement, comparator);
                    if (replaced) {
                        // CAS succeeded, value replaced with soft lock, job done.
                        getCurrentTransactionContext().registerSoftLock(cacheName, this, softLock);
                        LOG.debug("replace2: cache [{}] key [{}] was in, replaced with soft lock", cacheName, key);
                        return true;
                    } else {
                        // CAS failed, something else with that key is now in store or the key disappeared, job done.
                        softLock.unlock();
                        LOG.debug("replace2: cache [{}] key [{}] was in, replacement by soft lock failed", cacheName, key);
                        return false;
                    }
                }
            }

        } // while
    }

    /**
     * Commit work of the specified soft locks。提交指定软锁定的工作。
     * @param softLocks 即将被提交的软锁定
     */
    void commit(List<SoftLock> softLocks) {
        LOG.debug("committing {} soft lock(s) in cache {}", softLocks.size(), cacheName);
        for (SoftLock softLock : softLocks) {
            Element e = memoryStore.get(softLock.getKey());
            if (e == null) {
                // the element can be null if it was manually unpinned, see DEV-8308
                continue;
            }
            SoftLockID softLockId = (SoftLockID)e.getObjectValue();

            Element element = softLockId.getNewElement();///事务提交了，将新值放到存储中
            if (element != null) {
                //memoryStore.put(element);
            	memcacheStore.put(element);//将新值存缓存到持久化存储中
            } else {
                ////memoryStore.remove(softLock.getKey());
                
                //对于提交操作，getNewElement()==null，说明是删除操作，所以从持久化存储中删除之
                memcacheStore.remove(softLock.getKey());
            }
            memoryStore.remove(softLock.getKey());//操作成功后，都要从内存存储中(当前事务中)删除它
        }
    }

    /**
     * Rollback work of the specified soft locks。回滚指定软锁定的工作。
     * @param softLocks 将要被回滚的软锁定
     */
    void rollback(List<SoftLock> softLocks) {
        LOG.debug("rolling back {} soft lock(s) in cache {}", softLocks.size(), cacheName);
        for (SoftLock softLock : softLocks) {
            Element e = memoryStore.get(softLock.getKey());///从当前事务上下文去取
            if (e == null) {
              // the element can be null if it was manually unpinned, see DEV-8308
              continue;
            }
            SoftLockID softLockId = (SoftLockID)e.getObjectValue();

            Element element = softLockId.getOldElement();//事务回滚了，将旧值放到存储中
            if (element != null) {
                //memoryStore.put(element);
            	
            	//？该操作是否可以不用做，因为根本没有从持久化存储中删除该元素
            	memcacheStore.put(element);//将旧值存到持久化存储中。
            } //else {
                //memoryStore.remove(softLock.getKey());//从当前事务上下文中，删除该key对应的元素
                //memcacheStore.remove(softLock.getKey());
            //}
            //getOldElement()==null是新添加元素(只是在内存存储中，所以回滚时只需要在内存存储中删除即可)
            //或者是第一次锁定删除元素(也只是在内存存储中，所以回滚时只需要在内存存储中删除即可)
            
            memoryStore.remove(softLock.getKey());//操作成功后，都要从内存存储中(当前事务中)删除它
        }
    }

	@Override
	public void putAll(Collection<Element> elements) throws CacheException {
		for (Element ele : elements) {
			put(ele);
		}
	}

	@Override
	public void removeAll(Collection<String> keys) {
		for (String key : keys) {
			remove(key);
		}
	}

	@Override
	public Map<String, Element> getAll(Collection<String> keys) {
		Map<String, Element> maps = new HashMap<String, Element>();
		for (String key : keys) {
			maps.put(key, get(key));
		}
		return maps;
	}

	/**
     * {@inheritDoc}
     */
	public List<String> getKeys() {
		return memoryStore.getKeys();
    }

    /**
     * {@inheritDoc}
     */
    public boolean containsKey(String key) {
        assertNotTimedOut();
        return getKeys().contains(key);
    }

//    public void removeAll() throws CacheException {
//        assertNotTimedOut();
//        List<String> keys = getKeys();
//        for (String key : keys) {
//            remove(key);
//        }
//    }

	@Override
	public MemcacheClientDelegate getMemcacheClientDelegate() {
		return memcacheStore.getMemcacheClientDelegate();
	}
	
	public TransactionController getTransactionController() {
		return transactionController;
	}
}
