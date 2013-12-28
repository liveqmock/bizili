package com.vteba.cache.spring.memcached.xmemcached;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;

import com.vteba.cache.memcached.manager.XMemcachedManager;
import com.vteba.service.context.spring.ApplicationContextHolder;

/**
 * 创建xmemcached缓存管理器的工厂bean
 * @author yinlei
 * date 2012-8-23 下午3:54:23
 */
public class MemCacheManagerFactoryBean implements FactoryBean<XMemcachedManager>, InitializingBean, DisposableBean{
	private String cacheManagerName;
	private XMemcachedManager cacheManager;
	
	@Override
	public void destroy() throws Exception {
		// Auto-generated method stub
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (cacheManager == null) {
			ApplicationContext ctx = ApplicationContextHolder.getApplicationContext();
			cacheManager = ctx.getBean(cacheManagerName, XMemcachedManager.class);
		}
		//if (this.cacheManagerName != null) {
		//	this.cacheManager.setCacheName(cacheManagerName);
		//}
	}

	@Override
	public XMemcachedManager getObject() throws Exception {
		return cacheManager;
	}

	@Override
	public Class<? extends XMemcachedManager> getObjectType() {
		return (this.cacheManager != null ? this.cacheManager.getClass() : XMemcachedManager.class);
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public void setCacheManagerName(String cacheManagerName) {
		this.cacheManagerName = cacheManagerName;
	}

	public void setCacheManager(XMemcachedManager cacheManager) {
		this.cacheManager = cacheManager;
	}

}
