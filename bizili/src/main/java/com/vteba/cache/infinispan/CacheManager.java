package com.vteba.cache.infinispan;

import com.vteba.persister.hibernate.QueryStatement;

/**
 * Infinispan缓存管理器工具类。
 * @author yinlei
 * date 2013-6-15 上午11:58:31
 */
@Deprecated
public class CacheManager {
	private InfinispanCacheManager cacheManager;
	
	public CacheManager() {

	}
	
	/**
	 * @return the cacheManager 缓存管理器
	 */
	public InfinispanCacheManager getCacheManager() {
		return cacheManager;
	}

	/**
	 * @param cacheManager the cacheManager to set
	 */
	public void setCacheManager(InfinispanCacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	/**
	 * 根据缓存名获取InfinispanCache，没有将尝试创建使用DefaultCache。
	 * @param cacheName 缓存名
	 * @return InfinispanCache&ltK, V&gt实例
	 * @author yinlei
	 * date 2013-6-24 下午10:29:48
	 */
	public <K, V> InfinispanCache<K, V> getCache(String cacheName) {
		return cacheManager.getCache(cacheName);
	}
	
	/**
	 * 获得查询语句QueryStatement缓存InfinispanCache实例。
	 * @author yinlei
	 * date 2013-6-25 下午10:34:33
	 */
	public InfinispanCache<String, QueryStatement> getQueryStatementCache() {
		return cacheManager.getCache("query-statement");
	}
	
}
