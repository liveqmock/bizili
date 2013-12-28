package com.vteba.cache.spring.memcached.xmemcached;

import net.rubyeye.xmemcached.MemcachedClient;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;

import com.vteba.cache.memcached.manager.XMemcachedManager;
import com.vteba.service.context.spring.ApplicationContextHolder;

/**
 * 创建xmemcached MemcachedClient客户端缓存的工厂bean
 * @author yinlei
 * date 2012-8-23 下午3:53:50
 */
public class MemCacheFactoryBean implements FactoryBean<MemcachedClient>, BeanNameAware, InitializingBean{
	private XMemcachedManager cacheManager;
	private String cacheName;
	private String beanName;
	private MemcachedClient cache;
	
	public void setCacheManager(XMemcachedManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (this.cacheName == null) {
			this.cacheName = this.beanName;
		}
		
		if (cacheManager == null) {
			ApplicationContext ctx = ApplicationContextHolder.getApplicationContext();
			cacheManager = ctx.getBean(cacheName, XMemcachedManager.class);
		}
		
		if (cache == null) {
			cache = XMemcachedManager.getMemcachedClientInstance(cacheName);
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
