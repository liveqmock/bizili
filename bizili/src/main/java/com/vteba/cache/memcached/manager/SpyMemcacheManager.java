package com.vteba.cache.memcached.manager;

import net.spy.memcached.MemcachedClient;

/**
 * spy memcahced客户端缓存封装，操作memcachedclient
 * @author yinlei
 * date 2012-9-24 下午9:57:49
 */
public class SpyMemcacheManager {
	private static MemcachedClient cacheClient;
	private String name;
	
	public static MemcachedClient getInstance(String name) {
		return cacheClient;
	}
	
	public String[] getCacheNames() {
		//String cacheName = cacheClient.getName();
		String[] caches = {name};
		return caches;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		//cacheClient.setName(name);
	}

	public void setCacheClient(MemcachedClient cacheClient) {
		SpyMemcacheManager.cacheClient = cacheClient;
	}
	
}
