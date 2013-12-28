package com.vteba.cache.hibernate.memcached.concurrent;

/**
 * LockType tells the locking API what kind of lock should be acquired or released
 * @author Alex Snaps
 */
public enum LockType {
    /**
     * Read Lock
     */
    READ,
    /**
     * Write Lock
     */
    WRITE
}
