package com.vteba.cache.memcached.pool.impl;

import com.vteba.cache.memcached.pool.PoolableStore;

/**
 * Pool evictor which always evicts from the store consuming the most disk resources.
 *
 * @author Ludovic Orban
 */
@Deprecated
public class FromLargestCacheOnDiskPoolEvictor extends AbstractFromLargestCachePoolEvictor {

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean evict(int count, long bytes, PoolableStore largestPoolableStore) {
        return false;//largestPoolableStore.evictFromOnDisk(count, bytes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected long getSizeInBytes(PoolableStore largestPoolableStore) {
        return 1L;//largestPoolableStore.getOnDiskSizeInBytes();
    }

}
