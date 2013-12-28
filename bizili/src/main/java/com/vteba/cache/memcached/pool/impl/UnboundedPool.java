package com.vteba.cache.memcached.pool.impl;

import java.util.Collection;
import java.util.Collections;

import com.vteba.cache.memcached.pool.Pool;
import com.vteba.cache.memcached.pool.PoolAccessor;
import com.vteba.cache.memcached.pool.PoolEvictor;
import com.vteba.cache.memcached.pool.PoolableStore;
import com.vteba.cache.memcached.pool.SizeOfEngine;

/**
 * A no-op pool which does not enforce any resource consumption limit.
 *
 * @author Ludovic Orban
 */
public class UnboundedPool implements Pool<PoolableStore> {

    /**
     * Create an UnboundedPool instance
     */
    public UnboundedPool() {
    }

    /**
     * {@inheritDoc}
     */
    public long getSize() {
        return -1L;
    }

    /**
     * {@inheritDoc}
     */
    public long getMaxSize() {
        return -1L;
    }

    /**
     * {@inheritDoc}
     */
    public void setMaxSize(long newSize) {
    }

    /**
     * {@inheritDoc}
     */
    public PoolAccessor<PoolableStore> createPoolAccessor(PoolableStore store, int maxDepth, boolean abortWhenMaxDepthExceeded) {
        return new UnboundedPoolAccessor();
    }

    /**
     * {@inheritDoc}
     */
    public PoolAccessor<PoolableStore> createPoolAccessor(PoolableStore store, SizeOfEngine sizeOfEngine) {
        return new UnboundedPoolAccessor();
    }

    /**
     * {@inheritDoc}
     */
    public void registerPoolAccessor(PoolAccessor<? extends PoolableStore> accessor) {
        //no-op
    }

    /**
     * {@inheritDoc}
     */
    public void removePoolAccessor(PoolAccessor<?> accessor) {
        //no-op
    }

    /**
     * {@inheritDoc}
     */
    public Collection<PoolableStore> getPoolableStores() {
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     */
    public PoolEvictor<PoolableStore> getEvictor() {
        throw new UnsupportedOperationException();
    }

    /**
     * The PoolAccessor class of the UnboundedPool
     */
    private final class UnboundedPoolAccessor implements PoolAccessor<PoolableStore> {

        private UnboundedPoolAccessor() {
        }

        /**
         * {@inheritDoc}
         */
        public long add(Object key, Object value, Object container, boolean force) {
            return 0L;
        }

        /**
         * {@inheritDoc}
         */
        public boolean canAddWithoutEvicting(Object key, Object value, Object container) {
            return true;
        }

        /**
         * {@inheritDoc}
         */
        public long delete(long sizeOf) {
            return 0L;
        }

        /**
         * {@inheritDoc}
         */
        public long replace(long currentSize, Object key, Object value, Object container, boolean force) {
            return 0L;
        }

        /**
         * {@inheritDoc}
         */
        public long getSize() {
            return -1L;
        }

        /**
         * {@inheritDoc}
         */
        public void unlink() {
        }

        /**
         * {@inheritDoc}
         */
        public void clear() {
        }

        /**
         * {@inheritDoc}
         */
        public PoolableStore getStore() {
            throw new UnsupportedOperationException();
        }

        /**
         * {@inheritDoc}
         */
        public void setMaxSize(final long newValue) {
        }

        /**
         * {@inheritDoc}
         */
        public boolean hasAbortedSizeOf() {
            return false;
        }
    }
}
