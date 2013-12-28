package com.vteba.cache.memcached.pool.impl;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.vteba.cache.memcached.pool.Pool;
import com.vteba.cache.memcached.pool.PoolableStore;
import com.vteba.cache.memcached.pool.SizeOfEngine;

/**
 * The PoolAccessor class of the StrictlyBoundedPool
 *
 * @author Ludovic Orban
 */
final class LockedPoolAccessor extends AbstractPoolAccessor<PoolableStore> {

    private long size;
    private final Lock lock = new ReentrantLock();

    /**
     * Creates a locked pool accessor with the specified properties.
     *
     * @param pool pool to be accessed
     * @param store accessing store
     * @param sizeOfEngine engine used to size objects
     * @param currentSize initial size of the store
     */
    LockedPoolAccessor(Pool<PoolableStore> pool, PoolableStore store, SizeOfEngine sizeOfEngine, long currentSize) {
        super(pool, store, sizeOfEngine);
        this.size = currentSize;
    }

    /**
     * {@inheritDoc}
     */
    protected long add(long sizeOf, boolean force) {
        lock.lock();
        try {
            while (true) {
                long newSize = getPool().getSize() + sizeOf;

                if (newSize <= getPool().getMaxSize()) {
                    // there is enough room => add & approve
                    size += sizeOf;
                    return sizeOf;
                } else {
                    // check that the element isn't too big
                    if (!force && sizeOf > getPool().getMaxSize()) {
                        // this is too big to fit in the pool
                        return -1;
                    }

                    // if there is not enough room => evict
                    long missingSize = newSize - getPool().getMaxSize();

                    // eviction must be done outside the lock to avoid deadlocks as it may evict from other pools
                    lock.unlock();
                    try {
                        boolean successful = getPool().getEvictor().freeSpace(getPool().getPoolableStores(), missingSize);
                        if (!force && !successful) {
                            // cannot free enough bytes
                            return -1;
                        }
                    } finally {
                        lock.lock();
                    }

                    // check that the freed space was not 'stolen' by another thread while
                    // eviction was running out of the lock
                    if (!force && getPool().getSize() + sizeOf > getPool().getMaxSize()) {
                        continue;
                    }

                    size += sizeOf;
                    return sizeOf;
                }
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    protected boolean canAddWithoutEvicting(long sizeOf) {
        lock.lock();
        try {
            long newSize = getPool().getSize() + sizeOf;
            return newSize <= getPool().getMaxSize();
        } finally {
            lock.unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    public long delete(long sizeOf) {
        checkLinked();

        // synchronized makes the size update MT-safe but slow
        lock.lock();
        try {
            size -= sizeOf;
        } finally {
            lock.unlock();
        }

        return sizeOf;
    }

    /**
     * {@inheritDoc}
     */
    public long getSize() {
        // locking makes the size update MT-safe but slow
        lock.lock();
        try {
            return size;
        } finally {
            lock.unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    protected void doClear() {
        // locking makes the size update MT-safe but slow
        lock.lock();
        try {
            size = 0L;
        } finally {
            lock.unlock();
        }
    }
}
