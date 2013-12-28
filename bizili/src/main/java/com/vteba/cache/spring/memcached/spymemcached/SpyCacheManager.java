package com.vteba.cache.spring.memcached.spymemcached;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;

import com.vteba.cache.memcached.manager.SpyMemcacheManager;

/**
 * spring缓存管理器，暂时使用SpyMemcacheManager实现
 * @author yinlei
 * date 2012-9-24 下午9:54:26
 */
public class SpyCacheManager extends AbstractCacheManager {
	private SpyMemcacheManager cacheManager;
	
	public SpyMemcacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(SpyMemcacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@Override
	protected Collection<Cache> loadCaches() {
		String[] names = this.cacheManager.getCacheNames();
		Collection<Cache> caches = new LinkedHashSet<Cache>(names.length);
		for (String name : names) {
			caches.add(new SpyCache(SpyMemcacheManager.getInstance(name)));
		}
		return caches;
	}

}
