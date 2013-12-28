package com.vteba.cache.infinispan.support;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.vteba.cache.infinispan.InfinispanCache;
import com.vteba.cache.infinispan.InfinispanCacheManager;

/**
 * InfinispanCache FactoryBean，产生InfinispanCache单例。
 * @author yinlei
 * date 2013-7-7 下午12:04:35
 * @param <K> InfinispanCache key类型
 * @param <V> InfinispanCache value类型
 */
public class InfinispanCacheFactoryBean<K, V> implements InitializingBean, FactoryBean<InfinispanCache<K, V>> {
	private InfinispanCacheManager infinispanCacheManager;
	private InfinispanCache<K, V> infinispanCache;
	private String cacheName;
	
	@Override
	public InfinispanCache<K, V> getObject() throws Exception {
		return infinispanCache;
	}

	@Override
	public Class<?> getObjectType() {
		return infinispanCache == null ? null : infinispanCache.getClass();
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(infinispanCacheManager, "InfinispanCacheManager不能为空。");
		Assert.notNull(cacheName, "cacheName不能为空。");
		infinispanCache = infinispanCacheManager.getCache(cacheName);
	}

	/**
	 * @return the infinispanCacheManager
	 */
	public InfinispanCacheManager getInfinispanCacheManager() {
		return infinispanCacheManager;
	}

	/**
	 * @param infinispanCacheManager the infinispanCacheManager to set
	 */
	public void setInfinispanCacheManager(
			InfinispanCacheManager infinispanCacheManager) {
		this.infinispanCacheManager = infinispanCacheManager;
	}

	/**
	 * @return the cacheName
	 */
	public String getCacheName() {
		return cacheName;
	}

	/**
	 * @param cacheName the cacheName to set
	 */
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

}
