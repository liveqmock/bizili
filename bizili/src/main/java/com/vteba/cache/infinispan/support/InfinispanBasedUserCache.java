package com.vteba.cache.infinispan.support;

import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;

import com.vteba.cache.infinispan.InfinispanCache;

/**
 * 基于InfinispanCache的Spring Security的用户缓存实现。
 * @author yinlei
 * date 2013-7-7 上午12:22:35
 */
public class InfinispanBasedUserCache implements UserCache {
	
	private InfinispanCache<String, UserDetails> infinispanUserCache;
	
	@Override
	public UserDetails getUserFromCache(String username) {
		return infinispanUserCache.get(username);
	}

	@Override
	public void putUserInCache(UserDetails user) {
		infinispanUserCache.put(user.getUsername(), user);
	}

	@Override
	public void removeUserFromCache(String username) {
		infinispanUserCache.remove(username);
	}

	/**
	 * @return the infinispanUserCache
	 */
	public InfinispanCache<String, UserDetails> getInfinispanUserCache() {
		return infinispanUserCache;
	}

	/**
	 * @param infinispanUserCache the infinispanUserCache to set
	 */
	public void setInfinispanUserCache(
			InfinispanCache<String, UserDetails> infinispanUserCache) {
		this.infinispanUserCache = infinispanUserCache;
	}

}
