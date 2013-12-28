package com.vteba.cache.infinispan.support;

import org.infinispan.manager.EmbeddedCacheManager;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.vteba.cache.infinispan.InfinispanCacheManager;
import com.vteba.cache.infinispan.InfinispanCacheManagerImpl;
import com.vteba.common.exception.BaseException;

/**
 * InfinispanCacheManager FactoryBean，产生InfinispanCacheManager实例。
 * @author yinlei
 * date 2013-7-7 上午12:30:03
 */
public class InfinispanCacheManagerFactoryBean implements InitializingBean, FactoryBean<InfinispanCacheManager> {
	private InfinispanCacheManager infinispanCacheManager;
	private EmbeddedCacheManager embeddedCacheManager;
	private String configFileLocation;
	
	@Override
	public InfinispanCacheManager getObject() throws Exception {
		return infinispanCacheManager;
	}

	@Override
	public Class<?> getObjectType() {
		return infinispanCacheManager == null ? null : infinispanCacheManager.getClass();
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (embeddedCacheManager != null) {
			infinispanCacheManager = new InfinispanCacheManagerImpl(embeddedCacheManager);
		} else {
			if (configFileLocation == null) {
				throw new BaseException("配置文件configFileLocation属性没有指定。");
			}
			infinispanCacheManager = new InfinispanCacheManagerImpl(configFileLocation);
		}
	}

	/**
	 * @return the configFileLocation
	 */
	public String getConfigFileLocation() {
		return configFileLocation;
	}

	/**
	 * @param configFileLocation the configFileLocation to set
	 */
	public void setConfigFileLocation(String configFileLocation) {
		this.configFileLocation = configFileLocation;
	}

	/**
	 * @return the embeddedCacheManager
	 */
	public EmbeddedCacheManager getEmbeddedCacheManager() {
		return embeddedCacheManager;
	}

	/**
	 * @param embeddedCacheManager the embeddedCacheManager to set
	 */
	public void setEmbeddedCacheManager(EmbeddedCacheManager embeddedCacheManager) {
		this.embeddedCacheManager = embeddedCacheManager;
	}

}
