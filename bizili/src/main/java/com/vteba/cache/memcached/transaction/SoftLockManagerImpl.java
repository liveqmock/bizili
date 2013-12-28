package com.vteba.cache.memcached.transaction;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * A SoftLockFactory implementation which creates soft locks with Read-Committed isolation level
 *
 * @author Ludovic Orban
 */
public class SoftLockManagerImpl extends AbstractSoftLockManager {

    // actually all we need would be a ConcurrentSet...
    private final ConcurrentMap<SoftLockID, Boolean> newKeyLocks = new ConcurrentHashMap<SoftLockID, Boolean>();

    private final ConcurrentMap<SoftLockID, SoftLock> allLocks = new ConcurrentHashMap<SoftLockID, SoftLock>();

    /**
     * Create a new ReadCommittedSoftLockFactoryImpl instance for a cache
     * @param cacheName the name of the cache
     */
    public SoftLockManagerImpl(String cacheName, SoftLockFactory lockFactory) {
        super(cacheName, lockFactory);
    }

    @Override
    protected ConcurrentMap<SoftLockID, SoftLock> getAllLocks() {
        return allLocks;
    }

    @Override
    protected ConcurrentMap<SoftLockID, Boolean> getNewKeyLocks() {
        return newKeyLocks;
    }
}
