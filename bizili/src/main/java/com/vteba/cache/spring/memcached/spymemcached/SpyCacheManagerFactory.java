package com.vteba.cache.spring.memcached.spymemcached;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;

import com.vteba.cache.memcached.manager.SpyMemcacheManager;
import com.vteba.service.context.spring.ApplicationContextHolder;

/**
 * 创建SpyMemcacheManager的工厂bean
 * @author yinlei
 * date 2012-9-24 下午9:54:19
 */
public class SpyCacheManagerFactory implements FactoryBean<SpyMemcacheManager>, InitializingBean, DisposableBean{
	private String cacheManagerName;
	private SpyMemcacheManager cacheManager;
	
	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (cacheManager == null) {
			ApplicationContext ctx = ApplicationContextHolder.getApplicationContext();
			cacheManager = ctx.getBean(cacheManagerName, SpyMemcacheManager.class);
		}
		if (this.cacheManagerName != null) {
			this.cacheManager.setName(cacheManagerName);
		}
	}

	@Override
	public SpyMemcacheManager getObject() throws Exception {
		return cacheManager;
	}

	@Override
	public Class<? extends SpyMemcacheManager> getObjectType() {
		return (this.cacheManager != null ? this.cacheManager.getClass() : SpyMemcacheManager.class);
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public void setCacheManagerName(String cacheManagerName) {
		this.cacheManagerName = cacheManagerName;
	}

	public void setCacheManager(SpyMemcacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

}
