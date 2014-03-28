package com.vteba.cache.hibernate.infinispan;

import java.util.Properties;

import org.hibernate.cache.infinispan.InfinispanRegionFactory;
import org.infinispan.manager.EmbeddedCacheManager;

import com.vteba.service.context.spring.ApplicationContextHolder;

/**
 * Infinispan缓存区域工厂，重写创建CacheManager的方法。
 * @author yinlei
 * date 2013-6-30 上午11:54:27
 */
public class InfinispanCacheRegionFactory extends InfinispanRegionFactory {
	private static final long serialVersionUID = 812967435530068562L;
	
	protected EmbeddedCacheManager createCacheManager(Properties properties) {
		return ApplicationContextHolder.getBean("nativeEmbeddedCacheManager", EmbeddedCacheManager.class);
	}
}
