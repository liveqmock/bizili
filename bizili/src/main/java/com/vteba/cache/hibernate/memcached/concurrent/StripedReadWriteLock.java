package com.vteba.cache.hibernate.memcached.concurrent;

import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * @author Alex Snaps
 */
public interface StripedReadWriteLock extends CacheLockProvider {

    /**
     * Returns a ReadWriteLock for a particular key
     * @param key the key
     * @return the lock
     */
    ReadWriteLock getLockForKey(Object key);

    /**
     * Returns all Syncs
     * @return
     */
    List<ReadWriteLockSync> getAllSyncs();

}
