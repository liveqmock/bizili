package com.vteba.cache.spring.memcached.xmemcached;

import net.rubyeye.xmemcached.MemcachedClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

/**
 * spring security user cache, based xmemcached implementation
 * @author yinlei
 * date 2012-9-23 下午i6:27:29
 */
public class XMemcachedBasedUserCache implements InitializingBean, UserCache {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	private MemcachedClient memcached;
	
	public XMemcachedBasedUserCache() {
	}

	@Override
	public UserDetails getUserFromCache(String username) {
		try {
			UserDetails user = memcached.get(username);
			return user;
		} catch (Exception e) {
			logger.info("can't find user from cache, username is : " + username);
		}
		return null;
	}

	@Override
	public void putUserInCache(UserDetails userdetails) {
		try {
			memcached.set(userdetails.getUsername(), 0, userdetails);
		} catch (Exception e) {
			logger.info("put user into cache error username is : " + userdetails.getUsername());
		}
	}

	@Override
	public void removeUserFromCache(String username) {
		try {
			memcached.delete(username);
		} catch (Exception e) {
			logger.info("delete user form cache error,username is :" + username);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(memcached, "cache can't be null");
	}

	public void setMemcached(MemcachedClient memcached) {
		this.memcached = memcached;
	}

	public MemcachedClient getMemcached() {
		return memcached;
	}
	
}
