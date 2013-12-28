package com.vteba.cache.memcached.transaction;

/**
 * A factory of soft-locks supporting a specific isolation level.
 *
 * @author Chris Dennis
 */
public interface SoftLockFactory {

    /**
     * Construct a new softlock to be managed by the given manager for a specific key.
     *
     * @param manager soft lock manager
     * @param key key to generate against
     * @return a new soft lock
     */
    SoftLock newSoftLock(SoftLockManager manager, Object key);

}
