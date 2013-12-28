package com.vteba.cache.memcached.transaction.local;

import com.vteba.cache.memcached.transaction.SoftLock;
import com.vteba.cache.memcached.transaction.TransactionException;
import com.vteba.cache.memcached.transaction.TransactionID;
import com.vteba.cache.memcached.transaction.TransactionIDFactory;
import com.vteba.cache.memcached.transaction.TransactionTimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * A local transaction's thread context
 * 本地事务的线程上下文
 * @author Ludovic Orban
 */
public class LocalTransactionContext {

    private static final Logger LOG = LoggerFactory.getLogger(LocalTransactionContext.class.getName());

    private boolean rollbackOnly;
    /**
     * 过期时间戳
     */
    private final long expirationTimestamp;
    private final TransactionIDFactory transactionIdFactory;
    private final TransactionID transactionId;
    /**
     * 软锁定map
     */
    private final Map<String, List<SoftLock>> softLockMap = new HashMap<String, List<SoftLock>>();
    /**
     * LocalTransactionStore Map
     */
    private final Map<String, LocalTransactionStore> storeMap = new HashMap<String, LocalTransactionStore>();
    
    /**
     * 事务中断，回滚监听器
     */
    private final List<TransactionListener> listeners = new ArrayList<TransactionListener>();

    /**
     * 创建LocalTransactionContext实例
     * @param transactionTimeout the timeout before the context expires
     * @param transactionIdFactory the transaction ID factory to retrieve a new transaction id from
     */
    public LocalTransactionContext(int transactionTimeout, TransactionIDFactory transactionIdFactory) {
        this.expirationTimestamp = MILLISECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS) +
                MILLISECONDS.convert(transactionTimeout, TimeUnit.SECONDS);
        this.transactionIdFactory = transactionIdFactory;
        this.transactionId = transactionIdFactory.createTransactionID();
    }

    /**
     * 检查上下文是否超时
     * @return true if the context timed out, false otherwise
     */
    public boolean timedOut() {
        return timeBeforeTimeout() <= 0;
    }

    /**
     * Get the time until this context will expire。
     * 获得事务上下文过期时间
     * @return 上下文即将过期的以毫秒为单位的时间
     */
    public long timeBeforeTimeout() {
    	long systemTime = MILLISECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS); 
        return Math.max(0, expirationTimestamp - systemTime);
    }
    
    /**
     * 将上下文标记为回滚
     */
    public void setRollbackOnly() {
        this.rollbackOnly = true;
    }

    /**
     * 在上下文中，注册一个软锁定
     * @param cacheName 软锁定所在的缓存的名字
     * @param store 软锁定所在的LocalTransactionStore
     * @param softLock the soft lock
     */
    public void registerSoftLock(String cacheName, LocalTransactionStore store, SoftLock softLock) {
        List<SoftLock> softLocks = softLockMap.get(cacheName);
        if (softLocks == null) {
            softLocks = new ArrayList<SoftLock>();
            softLockMap.put(cacheName, softLocks);
            storeMap.put(cacheName, store);
        }
        softLocks.add(softLock);
    }

    //todo this method isn't needed if there is no copy on read/write in the underlying store
    /**
     * 更新已经注册在上下文中的软锁定
     * @param cacheName 软锁定所在的缓存的名字
     * @param softLock the soft lock
     */
    public void updateSoftLock(String cacheName, SoftLock softLock) {
        List<SoftLock> softLocks = softLockMap.get(cacheName);
        softLocks.remove(softLock);
        softLocks.add(softLock);
    }

    /**
     * Get all soft locks registered in this context for a specific cache。
     * 获取上下文中指定cache的所有软锁定
     * @param cacheName the name of the cache
     * @return a List of registered soft locks for this cache
     */
    public List<SoftLock> getSoftLocksForCache(String cacheName) {
        List<SoftLock> softLocks = softLockMap.get(cacheName);
        if (softLocks == null) {
            return Collections.emptyList();
        }

        return Collections.unmodifiableList(softLocks);
    }

    /**
     * Check if anything was locked in this transaction's context。
     * 检查是否有些东西被锁定在事务上下文中
     * @return true if at least one soft lock got registered, false otherwise
     */
    public boolean hasLockedAnything() {
        return !softLockMap.isEmpty();
    }

    /**
     * Commit all work done in the context and release all registered soft locks。
     * 提交上下文中所有的工作，并且释放所有已注册的软锁定。
     * @param ignoreTimeout 如果true则提交事务，不管是否超时
     */
    public void commit(boolean ignoreTimeout) {
        if (!ignoreTimeout && timedOut()) {
            rollback();
            throw new TransactionTimeoutException("transaction timed out, rolled back on commit");
        }
        if (rollbackOnly) {
            rollback();
            throw new TransactionException("transaction was marked as rollback only, rolled back on commit");
        }

        try {
            fireBeforeCommitEvent();
            if (LOG.isDebugEnabled()) {
                LOG.debug("{} participating cache(s), committing transaction {}", softLockMap.keySet().size(), transactionId);
            }
            freeze();
            transactionIdFactory.markForCommit(transactionId);

            for (Map.Entry<String, List<SoftLock>> stringListEntry : softLockMap.entrySet()) {
                String cacheName = stringListEntry.getKey();
                LocalTransactionStore store = storeMap.get(cacheName);
                List<SoftLock> softLocks = stringListEntry.getValue();

                LOG.debug("committing soft locked values of cache {}", cacheName);
                store.commit(softLocks);
            }
            LOG.debug("committed transaction {}", transactionId);
        } finally {
            unfreezeAndUnlock();
            softLockMap.clear();
            storeMap.clear();
            fireAfterCommitEvent();
        }
    }

    /**
     * Rollback all work done in the context and release all registered soft locks
     * 回滚在上下文中所有的工作，并且释放所有的已注册的软锁定
     */
    public void rollback() {
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug("{} participating cache(s), rolling back transaction {}", softLockMap.keySet().size(), transactionId);
            }
            freeze();

            for (Map.Entry<String, List<SoftLock>> stringListEntry : softLockMap.entrySet()) {
                String cacheName = stringListEntry.getKey();
                LocalTransactionStore store = storeMap.get(cacheName);
                List<SoftLock> softLocks = stringListEntry.getValue();

                LOG.debug("rolling back soft locked values of cache {}", cacheName);
                store.rollback(softLocks);
            }
            LOG.debug("rolled back transaction {}", transactionId);
        } finally {
            unfreezeAndUnlock();
            softLockMap.clear();
            storeMap.clear();
            fireAfterRollbackEvent();
        }
    }

    /**
     * Get the transaction ID of the context
     * @return the transaction ID
     */
    public TransactionID getTransactionId() {
        return transactionId;
    }

    /**
     * Add a TransactionListener to this context
     * @param listener the listener
     */
    public void addListener(TransactionListener listener) {
        this.listeners.add(listener);
    }

    private void fireBeforeCommitEvent() {
        for (TransactionListener listener : listeners) {
            try {
                listener.beforeCommit();
            } catch (Exception e) {
                LOG.error("beforeCommit error", e);
            }
        }
    }

    private void fireAfterCommitEvent() {
        for (TransactionListener listener : listeners) {
            try {
                listener.afterCommit();
            } catch (Exception e) {
                LOG.error("afterCommit error", e);
            }
        }
    }

    private void fireAfterRollbackEvent() {
        for (TransactionListener listener : listeners) {
            try {
                listener.afterRollback();
            } catch (Exception e) {
                LOG.error("afterRollback error", e);
            }
        }
    }

    private void unfreezeAndUnlock() {
        LOG.debug("unfreezing and unlocking {} soft lock(s)", softLockMap.size());
        for (Map.Entry<String, List<SoftLock>> stringListEntry : softLockMap.entrySet()) {
            List<SoftLock> softLocks = stringListEntry.getValue();

            for (SoftLock softLock : softLocks) {
                try {
                    softLock.unfreeze();
                    LOG.debug("unfroze {}", softLock);
                } catch (Exception e) {
                    LOG.error("error unfreezing " + softLock, e);
                }
                try {
                    softLock.unlock();
                    LOG.debug("unlocked {}", softLock);
                } catch (Exception e) {
                    LOG.error("error unlocking " + softLock, e);
                }
            }
        }
    }

    private void freeze() {
        for (Map.Entry<String, List<SoftLock>> stringListEntry : softLockMap.entrySet()) {
            List<SoftLock> softLocks = stringListEntry.getValue();

            for (SoftLock softLock : softLocks) {
                softLock.freeze();
            }
        }
    }

    /**
     * 返回事务ID的hashcode
     */
    @Override
    public int hashCode() {
        return transactionId.hashCode();
    }

    /**
     * 比较LocalTransactionContext是否相等
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LocalTransactionContext) {
            LocalTransactionContext otherCtx = (LocalTransactionContext) obj;
            return transactionId.equals(otherCtx.transactionId);
        }
        return false;
    }

}
