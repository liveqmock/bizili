package com.vteba.cache.memcached.store;

import com.vteba.cache.hibernate.memcached.concurrent.StripedReadWriteLock;

/**
 * Provider for StripedReadWriteLock that need to match a certain spreading function
 * @author Alex Snaps
 */
public interface StripedReadWriteLockProvider {

    /**
     * Will create a StripedReadWriteLock always using the same spreading function
     * @return a newly created StripedReadWriteLock
     */
    StripedReadWriteLock createStripedReadWriteLock();
}
