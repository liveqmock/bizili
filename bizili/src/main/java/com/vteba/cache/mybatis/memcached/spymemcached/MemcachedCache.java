package com.vteba.cache.mybatis.memcached.spymemcached;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;

/**
 * The Memcached-based Cache implementation.
 *
 * @version $Id: MemcachedCache.java 5217 2012-04-23 19:59:07Z simonetripodi $
 */
public final class MemcachedCache implements Cache {

    private static final MemcachedClientWrapper MEMCACHED_CLIENT = new MemcachedClientWrapper();

    /**
     * The {@link ReadWriteLock}.
     */
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * The cache id.
     */
    private final String id;

    /**
     * Builds a new Memcached-based Cache.
     *
     * @param id the Mapper id.
     */
    public MemcachedCache(final String id) {
        this.id = id;
    }

    /**
     * {@inheritDoc}
     */
    public void clear() {
        MEMCACHED_CLIENT.removeGroup(this.id);
    }

    /**
     * {@inheritDoc}
     */
    public String getId() {
        return this.id;
    }

    /**
     * {@inheritDoc}
     */
    public Object getObject(Object key) {
        return MEMCACHED_CLIENT.getObject(key);
    }

    /**
     * {@inheritDoc}
     */
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }

    /**
     * {@inheritDoc}
     */
    public int getSize() {
        return Integer.MAX_VALUE;
    }

    /**
     * {@inheritDoc}
     */
    public void putObject(Object key, Object value) {
        MEMCACHED_CLIENT.putObject(key, value, this.id);
    }

    /**
     * {@inheritDoc}
     */
    public Object removeObject(Object key) {
        return MEMCACHED_CLIENT.removeObject(key);
    }

}
