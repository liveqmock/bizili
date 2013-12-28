package com.vteba.cache.memcached.pool.impl;

import com.vteba.cache.memcached.pool.PoolAccessor;
import com.vteba.cache.memcached.pool.PoolEvictor;
import com.vteba.cache.memcached.pool.PoolableStore;
import com.vteba.cache.memcached.pool.SizeOfEngine;

/**
 * A pool which loosely obeys to its bound: it can allow the accessors to consume more bytes than what
 * has been configured if that helps concurrency.

 * @author Ludovic Orban
 * @author Chris Dennis
 */
public class BoundedPool extends AbstractPool<PoolableStore> {

    /**
     * Create a BoundedPool instance
     *
     * @param maximumPoolSize the maximum size of the pool, in bytes.
     * @param evictor the pool evictor, for cross-store eviction.
     * @param defaultSizeOfEngine the default SizeOf engine used by the accessors.
     */
    public BoundedPool(long maximumPoolSize, PoolEvictor<PoolableStore> evictor, SizeOfEngine defaultSizeOfEngine) {
        super(maximumPoolSize, evictor, defaultSizeOfEngine);
    }

    /**
     * {@inheritDoc}
     */
    public PoolAccessor<PoolableStore> createPoolAccessor(PoolableStore store, SizeOfEngine sizeOfEngine) {
        AtomicPoolAccessor accessor = new AtomicPoolAccessor(this, store, sizeOfEngine, 0);
        registerPoolAccessor(accessor);
        return accessor;
    }
}
