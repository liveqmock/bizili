package com.vteba.cache.jmemcache;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.thimbleware.jmemcached.CacheImpl;
import com.thimbleware.jmemcached.Key;
import com.thimbleware.jmemcached.LocalCacheElement;
import com.thimbleware.jmemcached.MemCacheDaemon;
import com.thimbleware.jmemcached.storage.CacheStorage;
import com.thimbleware.jmemcached.storage.hash.ConcurrentLinkedHashMap;

/**
 * JMemcached的封装，启动JMemcached Server。
 * @author yinlei
 * date 2012-9-19 下午10:30:40
 */
public class JMemcachedFactoryBean implements InitializingBean, DisposableBean {

	private static Logger logger = LoggerFactory.getLogger(JMemcachedFactoryBean.class);
	private MemCacheDaemon<LocalCacheElement> cacheDaemon;

	private String serverAddress = "localhost";
	private int port = 11210;
	private int maxItems = 1024 * 1024 * 100;
	private long maxBytes = 1024 * 1024 * 200;

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("Initializing JMemcached Server ...");
		cacheDaemon = new MemCacheDaemon<LocalCacheElement>();
		CacheStorage<Key, LocalCacheElement> storage = ConcurrentLinkedHashMap.create(
				ConcurrentLinkedHashMap.EvictionPolicy.FIFO, maxItems, maxBytes);
		
		cacheDaemon.setCache(new CacheImpl(storage));
		cacheDaemon.setBinary(true);
		cacheDaemon.setAddr(new InetSocketAddress(serverAddress, port));
		cacheDaemon.start();
		logger.info("Initialized JMemcached Server Successfully.");
	}

	@Override
	public void destroy() throws Exception {
		logger.info("Shutdowning Jmemcached Server ...");
		cacheDaemon.stop();
		logger.info("Shutdowned Jmemcached Server Successfully.");
	}

	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setMaxItems(int maxItems) {
		this.maxItems = maxItems;
	}

	public void setMaxBytes(long maxBytes) {
		this.maxBytes = maxBytes;
	}
}
