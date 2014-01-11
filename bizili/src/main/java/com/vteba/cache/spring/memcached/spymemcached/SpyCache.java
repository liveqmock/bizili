package com.vteba.cache.spring.memcached.spymemcached;

import net.spy.memcached.MemcachedClient;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import com.vteba.util.cryption.CryptionUtils;

/**
 * spring缓存，使用SpyMemcache实现，整合spring的缓存抽象
 * @author yinlei
 * date 2012-9-24 下午9:54:45
 */
public class SpyCache implements Cache {
	
	private MemcachedClient cache;
	
	public SpyCache(MemcachedClient cache) {
		super();
		this.cache = cache;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MemcachedClient getNativeCache() {
		return cache;
	}

	@Override
	public ValueWrapper get(Object key) {
		String keys = CryptionUtils.toHexString(key);
		Object object = cache.get(keys);
		if (object != null) {
			return new SimpleValueWrapper(object);
		}
		return null;
	}

	@Override
	public void put(Object key, Object value) {
		String k = CryptionUtils.toHexString(key);
		cache.add(k, 0, value);
	}

	@Override
	public void evict(Object key) {
		String k = CryptionUtils.toHexString(key);
		cache.delete(k);
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
	}

	@Override
	public <T> T get(Object key, Class<T> type) {
		// TODO Auto-generated method stub
		return null;
	}

}
