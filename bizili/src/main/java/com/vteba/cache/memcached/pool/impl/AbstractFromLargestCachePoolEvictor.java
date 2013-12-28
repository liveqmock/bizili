package com.vteba.cache.memcached.pool.impl;

import com.vteba.cache.memcached.pool.PoolEvictor;
import com.vteba.cache.memcached.pool.PoolableStore;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Abstract pool evictor which always evicts from the store consuming the most resources.
 *
 * @author Ludovic Orban
 */
public abstract class AbstractFromLargestCachePoolEvictor implements PoolEvictor<PoolableStore> {

    /**
     * {@inheritDoc}
     */
    public boolean freeSpace(Collection<PoolableStore> from, long bytes) {
        if (from == null || from.isEmpty()) {
            return false;
        }

        long remainingSizeInBytes = bytes;
        Collection<PoolableStore> tried = new ArrayList<PoolableStore>();

        while (tried.size() != from.size()) {
            PoolableStore largestPoolableStore = findUntriedLargestPoolableStore(from, tried);

            long beforeEvictionSize = getSizeInBytes(largestPoolableStore);
            if (!evict(1, bytes, largestPoolableStore)) {
                tried.add(largestPoolableStore);
                continue;
            }
            long afterEvictionSize = getSizeInBytes(largestPoolableStore);

            remainingSizeInBytes -= (beforeEvictionSize - afterEvictionSize);
            if (remainingSizeInBytes <= 0L) {
                return true;
            }
        }

        return false;
    }

    /**
     * Evict from a store for a chosen resource
     *
     * @param count the element count
     * @param bytes the bytes count
     * @param poolableStore the store
     * @return true if eviction succeeded, ie: if there was enough evictable resource held by the store
     */
    protected abstract boolean evict(int count, long bytes, PoolableStore poolableStore);

    /**
     * Get a store size in bytes for a chosen resource
     *
     * @param poolableStore the store
     * @return the size in bytes
     */
    protected abstract long getSizeInBytes(PoolableStore poolableStore);

    private PoolableStore findUntriedLargestPoolableStore(Collection<PoolableStore> from, Collection<PoolableStore> tried) {
        PoolableStore largestPoolableStore = null;
        for (PoolableStore poolableStore : from) {
            if (alreadyTried(tried, poolableStore)) {
                continue;
            }

            if (largestPoolableStore == null || getSizeInBytes(poolableStore) > getSizeInBytes(largestPoolableStore)) {
                largestPoolableStore = poolableStore;
            }
        }
        return largestPoolableStore;
    }

    private boolean alreadyTried(Collection<PoolableStore> tried, PoolableStore from) {
        for (PoolableStore poolableStore : tried) {
            if (poolableStore == from) {
                return true;
            }
        }
        return false;
    }

}
