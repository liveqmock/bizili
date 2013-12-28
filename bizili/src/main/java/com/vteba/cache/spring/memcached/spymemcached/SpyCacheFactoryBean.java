package com.vteba.cache.spring.memcached.spymemcached;

import net.spy.memcached.MemcachedClient;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;

import com.vteba.cache.memcached.manager.SpyMemcacheManager;
import com.vteba.service.context.spring.ApplicationContextHolder;

/**
 * 创建spy MemcachedClient客户端缓存的工厂bean
 * @author yinlei
 * date 2012-9-24 下午9:54:34
 */
public class SpyCacheFactoryBean implements FactoryBean<MemcachedClient>, BeanNameAware, InitializingBean{
	
	private MemcachedClient cache;
	private String beanName;
	private SpyMemcacheManager cacheManager;
	private String cacheName;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if (this.cacheName == null) {
			this.cacheName = this.beanName;
		}
		
		if (cacheManager == null) {
			ApplicationContext ctx = ApplicationContextHolder.getApplicationContext();
			cacheManager = ctx.getBean(cacheName, SpyMemcacheManager.class);
		}
		
		if (cache == null) {
			cache = SpyMemcacheManager.getInstance(cacheName);
		}
	}

	@Override
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	@Override
	public MemcachedClient getObject() throws Exception {
		return cache;
	}

	@Override
	public Class<? extends MemcachedClient> getObjectType() {
		return (this.cache != null ? this.cache.getClass() : MemcachedClient.class);
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
