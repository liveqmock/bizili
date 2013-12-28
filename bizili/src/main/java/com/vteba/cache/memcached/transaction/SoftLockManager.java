package com.vteba.cache.memcached.transaction;

import java.util.Set;

import com.vteba.cache.memcached.Element;
import com.vteba.cache.memcached.Store;
import com.vteba.cache.memcached.transaction.local.LocalTransactionContext;

/**
 * A factory for {@link SoftLock}s
 *
 * @author Ludovic Orban
 */
public interface SoftLockManager {

    /**
     * Create a new soft lock ID and associated soft lock if necessary.
     *
     * @param transactionID the transaction ID under which the soft lock will operate
     * @param key the key of the Element this soft lock is protecting
     * @param newElement the new Element
     * @param oldElement the actual Element
     * @param pinned true if the actual Element is pinned
     * @return the soft lock ID
     */
    SoftLockID createSoftLockID(TransactionID transactionID, Object key, Element newElement, Element oldElement, boolean pinned);

    /**
     * Clear a soft lock
     * @param softLock the lock to clear
     */
    void clearSoftLock(SoftLock softLock);

    /**
     * Find a previously created and still existing soft lock
     * @param softLockId the soft lock's ID
     * @return the soft lock
     */
    SoftLock findSoftLockById(SoftLockID softLockId);

    /**
     * Get a Set of keys protected by soft locks which must not be visible to a transaction context
     * according to the isolation level.
     * @param transactionContext the transaction context
     * @return a Set of keys invisible to the context
     */
    Set<Object> getKeysInvisibleInContext(LocalTransactionContext transactionContext, Store underlyingStore);

    /**
     * Get a the soft locks of the specified transaction ID
     * @param transactionID the transaction ID
     * @return a Set of SoftLocks
     */
    Set<SoftLock> collectAllSoftLocksForTransactionID(TransactionID transactionID);

}
