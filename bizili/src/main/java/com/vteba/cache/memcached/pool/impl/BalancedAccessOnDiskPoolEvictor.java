package com.vteba.cache.memcached.pool.impl;

import com.vteba.cache.memcached.pool.PoolableStore;

/**
 * Balanced access evictor that makes on-disk eviction decisions.
 *
 * @author Chris Dennis
 */
@Deprecated
public class BalancedAccessOnDiskPoolEvictor extends AbstractBalancedAccessEvictor<PoolableStore> {

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean evict(PoolableStore store, int count, long size) {
        return false;//store.evictFromOnDisk(count, size);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected long countSize(PoolableStore store) {
        return 1L;//store.getApproximateDiskCountSize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected long byteSize(PoolableStore store) {
        return 1L;//store.getApproximateDiskByteSize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected float hitRate(PoolableStore store) {
        return 1F;//store.getApproximateDiskHitRate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected float missRate(PoolableStore store) {
        return 1F;//store.getApproximateDiskMissRate();
    }
}
