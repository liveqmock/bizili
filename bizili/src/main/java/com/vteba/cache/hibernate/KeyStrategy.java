package com.vteba.cache.hibernate;

import com.vteba.cache.hibernate.memcached.Memcache;

/**
 * Strategy interface for parsing the parts used by {@link Memcache} to generate cache keys.
 */
public interface KeyStrategy {

    String toKey(String regionName, long clearIndex, Object key);
}
