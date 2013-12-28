package com.vteba.cache.memcached.pool.impl;

import com.vteba.cache.memcached.pool.PoolableStore;

/**
 * Balanced access evictor that makes on-heap eviction decisions.
 *
 * @author Chris Dennis
 */
public class BalancedAccessOnHeapPoolEvictor extends AbstractBalancedAccessEvictor<PoolableStore> {

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean evict(PoolableStore store, int count, long size) {
        return store.evictFromOnHeap(count, size);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected long countSize(PoolableStore store) {
        return store.getApproximateHeapCountSize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected long byteSize(PoolableStore store) {
        return store.getApproximateHeapByteSize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected float hitRate(PoolableStore store) {
        return store.getApproximateHeapHitRate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected float missRate(PoolableStore store) {
        return store.getApproximateHeapMissRate();
    }
}
