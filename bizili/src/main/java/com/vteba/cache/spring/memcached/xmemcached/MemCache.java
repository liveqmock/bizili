package com.vteba.cache.spring.memcached.xmemcached;

import net.rubyeye.xmemcached.MemcachedClient;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import com.vteba.utils.cryption.CryptionUtils;

/**
 * Spring缓存，使用xmemcache实现，整合spring的缓存抽象
 * @author yinlei
 * date 2012-8-23 下午3:51:43
 */
public class MemCache implements Cache {
	private MemcachedClient cache;
	
	public MemCache(MemcachedClient cache) {
		super();
		this.cache = cache;
	}

	@Override
	public String getName() {
		return cache.getName();
	}

	@Override
	public MemcachedClient getNativeCache() {
		return cache;
	}

	@Override
	public ValueWrapper get(Object key) {
		Object object;
		try {
			object = cache.get(CryptionUtils.toHexString(key));
			if (object != null) {
				return new SimpleValueWrapper(object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void put(Object key, Object value) {
		try {
			String k = CryptionUtils.toHexString(key);
			cache.add(k, 0, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void evict(Object key) {
		try {
			String k = CryptionUtils.toHexString(key);
			cache.delete(k);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
