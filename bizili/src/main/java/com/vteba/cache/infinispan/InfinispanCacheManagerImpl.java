package com.vteba.cache.infinispan;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.lifecycle.ComponentStatus;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.remoting.transport.Address;
import org.infinispan.remoting.transport.Transport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bitronix.tm.recovery.RecoveryException;
import bitronix.tm.resource.ResourceRegistrar;

import com.vteba.common.exception.BasicException;
import com.vteba.tm.bitronix.infinispan.InfinispanXAResourceProducer;

/**
 * 对Infinispan缓存管理器的封装。
 * @author yinlei
 * date 2013-6-23 下午7:03:56
 */
public class InfinispanCacheManagerImpl implements InfinispanCacheManager {
//	private static final String INFINISPAN_CONFIG_FILE = "infinispan-configs.xml";//"application-infinispan-configs.xml";//默认配置文件
	private static Logger logger = LoggerFactory.getLogger(InfinispanCacheManagerImpl.class);
	//private DefaultInfinispanCacheManager defaultCacheManager;
	//private String configFilePath;//配置文件名
	private EmbeddedCacheManager embeddedCacheManager;
	
	
	public InfinispanCacheManagerImpl() {
//		if (configFilePath == null) {
//			configFilePath = INFINISPAN_CONFIG_FILE;
//		}
//		try {
//			defaultCacheManager = new DefaultInfinispanCacheManager(configFilePath);
//		} catch (IOException e) {
//			logger.info("In classpath is not exist file : " + configFilePath + ", will use default configFile : " + INFINISPAN_CONFIG_FILE);
//			try {
//				defaultCacheManager = new DefaultInfinispanCacheManager(INFINISPAN_CONFIG_FILE);
//			} catch (IOException e1) {
//				logger.info("In classpath is not exist file : " + INFINISPAN_CONFIG_FILE);
//			}
//		}
//		SessionFactoryImpl skmbwSessionFactoryImpl = ApplicationContextHolder.getApplicationContext().getBean("skmbwSessionFactory", SessionFactoryImpl.class);
//		InfinispanRegionFactory infinispanRegionFactory = (InfinispanRegionFactory) skmbwSessionFactoryImpl.getSettings().getRegionFactory();
//		embeddedCacheManager = infinispanRegionFactory.getCacheManager();
		
		//defaultCacheManager = this;
	}
	
	public InfinispanCacheManagerImpl(EmbeddedCacheManager embeddedCacheManager) {
		super();
		this.embeddedCacheManager = embeddedCacheManager;
	}

	public void destroy() {
//		if (defaultCacheManager != null) {
//			defaultCacheManager.stop();
//		}
		this.stop();
	}
	
	public InfinispanCacheManagerImpl(String configurationFile)
			throws IOException {
		this.embeddedCacheManager = new DefaultCacheManager(configurationFile);
	}
	
	public <K, V> InfinispanCache<K, V> getCache(String cacheName, boolean createIfAbsent){
		Cache<K, V> cache = embeddedCacheManager.getCache(cacheName, createIfAbsent);
		return new InfinispanCacheImpl<K, V>(cache);
	}

	/**
	 * @return
	 * @see org.infinispan.manager.CacheContainer#getCache()
	 */
	public <K, V> InfinispanCache<K, V> getCache() {
		Cache<K, V> cache = embeddedCacheManager.getCache();
		return new InfinispanCacheImpl<K, V>(cache);
	}

	/**
	 * @param cacheName
	 * @return
	 * @see org.infinispan.manager.CacheContainer#getCache(java.lang.String)
	 */
	public <K, V> InfinispanCache<K, V> getCache(String cacheName) {
		Cache<K, V> cache = embeddedCacheManager.getCache(cacheName);
		return new InfinispanCacheImpl<K, V>(cache);
	}
	
//	/**
//	 * @return the configFilePath
//	 */
//	public String getConfigFilePath() {
//		return configFilePath;
//	}
//
//	/**
//	 * @param configFilePath the configFilePath to set
//	 */
//	public void setConfigFilePath(String configFilePath) {
//		this.configFilePath = configFilePath;
//	}

	/**
	 * @param listener
	 * @see org.infinispan.notifications.Listenable#addListener(java.lang.Object)
	 */
	public void addListener(Object listener) {
		embeddedCacheManager.addListener(listener);
	}

	/**
	 * 
	 * @see org.infinispan.lifecycle.Lifecycle#start()
	 */
	public void start() {
		embeddedCacheManager.start();
	}

	/**
	 * 
	 * @see org.infinispan.lifecycle.Lifecycle#stop()
	 */
	public void stop() {
		embeddedCacheManager.stop();
	}

	/**
	 * @param listener
	 * @see org.infinispan.notifications.Listenable#removeListener(java.lang.Object)
	 */
	public void removeListener(Object listener) {
		embeddedCacheManager.removeListener(listener);
	}

	/**
	 * @return
	 * @see org.infinispan.notifications.Listenable#getListeners()
	 */
	public Set<Object> getListeners() {
		return embeddedCacheManager.getListeners();
	}

	/**
	 * @param cacheName
	 * @param configurationOverride
	 * @return
	 * @see org.infinispan.manager.EmbeddedCacheManager#defineConfiguration(java.lang.String, org.infinispan.configuration.cache.Configuration)
	 */
	public Configuration defineConfiguration(String cacheName,
			Configuration configurationOverride) {
		return embeddedCacheManager.defineConfiguration(cacheName,
				configurationOverride);
	}

	/**
	 * @return
	 * @see org.infinispan.manager.EmbeddedCacheManager#getClusterName()
	 */
	public String getClusterName() {
		return embeddedCacheManager.getClusterName();
	}

	/**
	 * @return
	 * @see org.infinispan.manager.EmbeddedCacheManager#getMembers()
	 */
	public List<Address> getMembers() {
		return embeddedCacheManager.getMembers();
	}

	/**
	 * @return
	 * @see org.infinispan.manager.EmbeddedCacheManager#getAddress()
	 */
	public Address getAddress() {
		return embeddedCacheManager.getAddress();
	}

	/**
	 * @return
	 * @see org.infinispan.manager.EmbeddedCacheManager#getCoordinator()
	 */
	public Address getCoordinator() {
		return embeddedCacheManager.getCoordinator();
	}

	/**
	 * @return
	 * @see org.infinispan.manager.EmbeddedCacheManager#isCoordinator()
	 */
	public boolean isCoordinator() {
		return embeddedCacheManager.isCoordinator();
	}

	/**
	 * @return
	 * @see org.infinispan.manager.EmbeddedCacheManager#getStatus()
	 */
	public ComponentStatus getStatus() {
		return embeddedCacheManager.getStatus();
	}

	/**
	 * @return
	 * @see org.infinispan.manager.EmbeddedCacheManager#getCacheManagerConfiguration()
	 */
	public GlobalConfiguration getCacheManagerConfiguration() {
		return embeddedCacheManager.getCacheManagerConfiguration();
	}

	/**
	 * @param name
	 * @return
	 * @see org.infinispan.manager.EmbeddedCacheManager#getCacheConfiguration(java.lang.String)
	 */
	public Configuration getCacheConfiguration(String name) {
		return embeddedCacheManager.getCacheConfiguration(name);
	}

	/**
	 * @return
	 * @see org.infinispan.manager.EmbeddedCacheManager#getDefaultCacheConfiguration()
	 */
	public Configuration getDefaultCacheConfiguration() {
		return embeddedCacheManager.getDefaultCacheConfiguration();
	}

	/**
	 * @return
	 * @see org.infinispan.manager.EmbeddedCacheManager#getCacheNames()
	 */
	public Set<String> getCacheNames() {
		return embeddedCacheManager.getCacheNames();
	}

	/**
	 * @param cacheName
	 * @return
	 * @see org.infinispan.manager.EmbeddedCacheManager#isRunning(java.lang.String)
	 */
	public boolean isRunning(String cacheName) {
		return embeddedCacheManager.isRunning(cacheName);
	}

	/**
	 * @return
	 * @see org.infinispan.manager.EmbeddedCacheManager#isDefaultRunning()
	 */
	public boolean isDefaultRunning() {
		return embeddedCacheManager.isDefaultRunning();
	}

	/**
	 * @param cacheName
	 * @return
	 * @see org.infinispan.manager.EmbeddedCacheManager#cacheExists(java.lang.String)
	 */
	public boolean cacheExists(String cacheName) {
		return embeddedCacheManager.cacheExists(cacheName);
	}

	/**
	 * @param cacheNames
	 * @return
	 * @see org.infinispan.manager.EmbeddedCacheManager#startCaches(java.lang.String[])
	 */
	public EmbeddedCacheManager startCaches(String... cacheNames) {
		return embeddedCacheManager.startCaches(cacheNames);
	}

	/**
	 * @param cacheName
	 * @see org.infinispan.manager.EmbeddedCacheManager#removeCache(java.lang.String)
	 */
	public void removeCache(String cacheName) {
		embeddedCacheManager.removeCache(cacheName);
	}

	/**
	 * @return
	 * @see org.infinispan.manager.EmbeddedCacheManager#getTransport()
	 */
	public Transport getTransport() {
		return embeddedCacheManager.getTransport();
	}

	/**
	 * @param cacheName
	 * @param configurationOverride
	 * @return
	 * @deprecated
	 * @see org.infinispan.manager.EmbeddedCacheManager#defineConfiguration(java.lang.String, org.infinispan.config.Configuration)
	 */
	public org.infinispan.config.Configuration defineConfiguration(
			String cacheName,
			org.infinispan.config.Configuration configurationOverride) {
		return embeddedCacheManager.defineConfiguration(cacheName,
				configurationOverride);
	}

	/**
	 * @param cacheName
	 * @param templateCacheName
	 * @param configurationOverride
	 * @return
	 * @deprecated
	 * @see org.infinispan.manager.EmbeddedCacheManager#defineConfiguration(java.lang.String, java.lang.String, org.infinispan.config.Configuration)
	 */
	public org.infinispan.config.Configuration defineConfiguration(
			String cacheName, String templateCacheName,
			org.infinispan.config.Configuration configurationOverride) {
		return embeddedCacheManager.defineConfiguration(cacheName,
				templateCacheName, configurationOverride);
	}

	/**
	 * @return
	 * @deprecated
	 * @see org.infinispan.manager.EmbeddedCacheManager#getGlobalConfiguration()
	 */
	public org.infinispan.config.GlobalConfiguration getGlobalConfiguration() {
		return embeddedCacheManager.getGlobalConfiguration();
	}

	/**
	 * @return
	 * @deprecated
	 * @see org.infinispan.manager.EmbeddedCacheManager#getDefaultConfiguration()
	 */
	public org.infinispan.config.Configuration getDefaultConfiguration() {
		return embeddedCacheManager.getDefaultConfiguration();
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
		InfinispanXAResourceProducer xaResourceProducer = new InfinispanXAResourceProducer();
		xaResourceProducer.setUniqueName("application.cache.infinispan.uniquename");
        xaResourceProducer.setCacheManager(this.embeddedCacheManager);
        try {
			ResourceRegistrar.register(xaResourceProducer);
		} catch (RecoveryException e) {
			logger.error("将Infinispan注册为XAResource失败。" + e.getMessage());
			throw new BasicException("将Infinispan注册为XAResource失败。");
		}
	}

	
}
