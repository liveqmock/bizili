package com.vteba.cache.memcached.pool.impl;

import com.vteba.cache.memcached.pool.PoolAccessor;
import com.vteba.cache.memcached.pool.PoolEvictor;
import com.vteba.cache.memcached.pool.PoolableStore;
import com.vteba.cache.memcached.pool.SizeOfEngine;

/**
 * A pool which strictly obeys to its bound: it will never allow the accessors to consume more bytes than what
 * has been configured.
 *
 * @author Ludovic Orban
 */
public class StrictlyBoundedPool extends AbstractPool<PoolableStore> {

    /**
     * Create a StrictlyBoundedPool instance
     *
     * @param maximumPoolSize the maximum size of the pool, in bytes.
     * @param evictor the pool evictor, for cross-store eviction.
     * @param defaultSizeOfEngine the default SizeOf engine used by the accessors.
     */
    public StrictlyBoundedPool(long maximumPoolSize, PoolEvictor<PoolableStore> evictor, SizeOfEngine defaultSizeOfEngine) {
        super(maximumPoolSize, evictor, defaultSizeOfEngine);
    }

    /**
     * {@inheritDoc}
     */
    public PoolAccessor<PoolableStore> createPoolAccessor(PoolableStore store, SizeOfEngine sizeOfEngine) {
        LockedPoolAccessor accessor = new LockedPoolAccessor(this, store, sizeOfEngine, 0);
        registerPoolAccessor(accessor);
        return accessor;
    }
}
