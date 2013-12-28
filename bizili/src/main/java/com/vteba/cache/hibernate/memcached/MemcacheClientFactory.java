package com.vteba.cache.hibernate.memcached;

import com.vteba.cache.memcached.Store;

/**
 * Simple interface used to abstract the creation of the MemcachedClient
 * All implementers must have a constructor that takes an instance of
 * {@link com.vteba.cache.hibernate.PropertiesHelper}.
 */
public interface MemcacheClientFactory {
	
	/**
	 * 创建Memcache实例
	 * @throws Exception
	 * @author yinlei
	 * date 2012-9-19 下午9:18:27
	 */
    //Memcache createMemcacheClient() throws Exception;
    
    /**
     * 创建Store实例
     * @throws Exception
     * @author yinlei
     * date 2012-11-19 下午9:18:31
     */
    Store createMemcacheStore() throws Exception;

}
