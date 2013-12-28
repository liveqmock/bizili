package com.vteba.cache.spring.memcached.xmemcached;

import java.util.Collection;
import java.util.LinkedHashSet;

import net.rubyeye.xmemcached.MemcachedClient;

import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;

import com.vteba.cache.memcached.manager.XMemcachedManager;

/**
 * spring缓存管理器，暂时使用XMemcachedManager实现
 * @author yinlei
 * date 2012-8-23 下午3:52:43
 */
public class MemCacheManager extends AbstractCacheManager {
	private XMemcachedManager cacheManager;
	
	public XMemcachedManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(XMemcachedManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@Override
	protected Collection<Cache> loadCaches() {
		String[] names = this.cacheManager.getCacheNames();
		Collection<Cache> caches = new LinkedHashSet<Cache>(names.length);
		for (String name : names) {
			caches.add(new MemCache(XMemcachedManager.getMemcachedClientInstance(name)));
		}
		return caches;
	}
	
	public Cache getCache(String cacheName) {
		Cache cache = super.getCache(cacheName);
		if (cache == null) {
			MemcachedClient client = XMemcachedManager.getMemcachedClientInstance(cacheName);
			cache = new MemCache(client);
		}
		return cache;
	}
}
