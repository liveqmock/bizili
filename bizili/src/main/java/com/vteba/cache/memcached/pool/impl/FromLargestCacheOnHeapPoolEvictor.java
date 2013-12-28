package com.vteba.cache.memcached.pool.impl;

import com.vteba.cache.memcached.pool.PoolableStore;

/**
 * Pool evictor which always evicts from the store consuming the most heap resources.
 *
 * @author Ludovic Orban
 */
public class FromLargestCacheOnHeapPoolEvictor  extends AbstractFromLargestCachePoolEvictor {

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean evict(int count, long bytes, PoolableStore largestPoolableStore) {
        return largestPoolableStore.evictFromOnHeap(count, bytes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected long getSizeInBytes(PoolableStore largestPoolableStore) {
        return largestPoolableStore.getInMemorySizeInBytes();
    }

}
