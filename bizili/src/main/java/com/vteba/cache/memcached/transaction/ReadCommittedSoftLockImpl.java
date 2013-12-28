package com.vteba.cache.memcached.transaction;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.vteba.cache.memcached.Element;

/**
 * A SoftLock implementation with Read-Committed isolation level
 *
 * @author Ludovic Orban
 */
public class ReadCommittedSoftLockImpl implements SoftLock {
    //private static final int PRIME = 31;

    private final SoftLockManager manager;
    private final ReentrantLock lock;
    private final ReentrantReadWriteLock freezeLock;

    private final Object key;
    private volatile boolean expired;

    /**
     * Create a new ReadCommittedSoftLockImpl instance
     * @param manager the creating manager
     * @param key the element's key this soft lock is going to protect
     */
    ReadCommittedSoftLockImpl(SoftLockManager manager, Object key) {
        this.manager = manager;
        this.key = key;
        this.lock = new ReentrantLock();
        this.freezeLock = new ReentrantReadWriteLock();
    }

    /**
     * {@inheritDoc}
     */
    public String getKey() {
        return key.toString();
    }

    /**
     * {@inheritDoc}
     */
    public Element getElement(TransactionID currentTransactionId, SoftLockID softLockId) {
        freezeLock.readLock().lock();
        try {
            if (softLockId.getTransactionID().equals(currentTransactionId)) {
                return softLockId.getNewElement();
            } else {
                return softLockId.getOldElement();
            }
        } finally {
            freezeLock.readLock().unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    public void lock() {
        lock.lock();
    }

    /**
     * {@inheritDoc}
     */
    public boolean tryLock(long ms) throws InterruptedException {
        return lock.tryLock(ms, TimeUnit.MILLISECONDS);
    }

    /**
     * {@inheritDoc}
     */
    public void clearTryLock() {
        lock.unlock();
    }

    /**
     * {@inheritDoc}
     */
    public void unlock() {
        lock.unlock();
        clear();
    }

    private boolean isLocked() {
        return lock.isLocked();
    }

    /**
     * {@inheritDoc}
     */
    public void freeze() {
        if (!isLocked()) {
            throw new IllegalStateException("cannot freeze an unlocked soft lock");
        }
        freezeLock.writeLock().lock();
    }

    /**
     * {@inheritDoc}
     */
    public void unfreeze() {
        freezeLock.writeLock().unlock();
    }

    private boolean isFrozen() {
        return freezeLock.isWriteLocked();
    }

    /**
     * {@inheritDoc}
     */
    public boolean isExpired() {
        if (!expired) {
            expired = !isFrozen() && !isLocked();
        }
        return expired;
    }

    private void clear() {
        manager.clearSoftLock(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Soft Lock [clustered: false, isolation: rc, key: " + key + "]";
    }

}
