package com.vteba.cache.hibernate.memcached.concurrent;

/**
 * @author Alex Snaps
 */
public interface CacheLockProvider {

    /**
     * Gets the Sync Stripe to use for a given key.
     * <p/>
     * This lookup must always return the same Sync for a given key.
     * <p/>
     * @param key the key
     * @return one of a limited number of Sync's.
     */
    Sync getSyncForKey(Object key);
}
