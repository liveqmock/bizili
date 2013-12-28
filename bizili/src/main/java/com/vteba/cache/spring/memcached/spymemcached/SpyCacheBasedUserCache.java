package com.vteba.cache.spring.memcached.spymemcached;

import net.spy.memcached.MemcachedClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

/**
 * 基于spymemcahce实现spring security user缓存
 * @author yinlei
 * date 2012-9-24 下午9:52:30
 */
public class SpyCacheBasedUserCache implements InitializingBean, UserCache {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	private MemcachedClient memcachedClient;
	
	@Override
	public UserDetails getUserFromCache(String username) {
		return (UserDetails) memcachedClient.get(username);
	}

	@Override
	public void putUserInCache(UserDetails user) {
		memcachedClient.add(user.getUsername(), 0, user);
		logger.debug("put user into cache, username is : " + user.getUsername());
	}

	@Override
	public void removeUserFromCache(String username) {
		memcachedClient.delete(username);
		logger.debug("delete user from cache, username is : " + username);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(memcachedClient, "cache can't be null");
	}

}
