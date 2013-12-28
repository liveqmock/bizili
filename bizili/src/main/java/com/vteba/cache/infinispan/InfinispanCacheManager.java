package com.vteba.cache.infinispan;

import org.infinispan.configuration.cache.Configuration;
import org.infinispan.manager.EmbeddedCacheManager;

/**
 * 对Infinispan缓存接口的封装。隔离Infinispan API。
 * @author yinlei
 * date 2013-6-23 下午7:00:52
 */
public interface InfinispanCacheManager extends EmbeddedCacheManager {
	
	/**
	 * Retrieves a named cache from the system in the same way that
	 * {@link #getCache(String)} does except that if offers the possibility for
	 * the named cache not to be retrieved if it has not yet been started, or if
	 * it's been removed after being started.
	 * 
	 * @param cacheName
	 *            name of cache to retrieve
	 * @param createIfAbsent
	 *            if <tt>false</tt>, the named cache will not be retrieved if it
	 *            hasn't been retrieved previously or if it's been removed. If
	 *            <tt>true</tt>, this methods works just like
	 *            {@link #getCache(String)}
	 * @return null if no named cache exists as per rules set above, otherwise
	 *         returns a cache instance identified by cacheName
	 */
	<K, V> InfinispanCache<K, V> getCache(String cacheName, boolean createIfAbsent);
	
	/**
	 * Retrieves the default cache associated with this cache manager. Note that
	 * the default cache does not need to be explicitly created with
	 * {@link #createCache(String)} since it is automatically created lazily
	 * when first used.
	 * <p/>
	 * As such, this method is always guaranteed to return the default cache.
	 * 
	 * @return the default cache.
	 */
	<K, V> InfinispanCache<K, V> getCache();

	/**
	 * Retrieves a named cache from the system. If the cache has been previously
	 * created with the same name, the running cache instance is returned.
	 * Otherwise, this method attempts to create the cache first.
	 * <p/>
	 * When creating a new cache, this method will use the configuration passed
	 * in to the CacheManager on construction, as a template, and then
	 * optionally apply any overrides previously defined for the named cache
	 * using the {@link #defineConfiguration(String, Configuration)} or
	 * {@link #defineConfiguration(String, String, Configuration)} methods, or
	 * declared in the configuration file.
	 * 
	 * @param cacheName
	 *            name of cache to retrieve
	 * 
	 * @return a cache instance identified by cacheName
	 */
	<K, V> InfinispanCache<K, V> getCache(String cacheName);
}
