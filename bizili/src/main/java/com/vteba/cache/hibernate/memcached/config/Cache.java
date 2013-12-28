package com.vteba.cache.hibernate.memcached.config;

/**
 * 缓存配置元素
 * @author yinlei
 * date 2013-4-4 下午12:43:49
 */
public class Cache {
	private String cacheName;//缓存名
	private TransactionMode txMode;//事务模式
	private String txProvidorClazz;//事务提供者
	private String memoryEvictPolicy;//内存移除算法
	private Long cacheSize;//缓存容量

	public String getCacheName() {
		return cacheName;
	}

	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	public TransactionMode getTxMode() {
		return txMode;
	}

	public void setTxMode(String txMode) {
		this.txMode = TransactionMode.valueOf(txMode.toUpperCase());
	}

	public String getTxProvidorClazz() {
		return txProvidorClazz;
	}

	public void setTxProvidorClazz(String txProvidorClazz) {
		this.txProvidorClazz = txProvidorClazz;
	}

	public String getMemoryEvictPolicy() {
		return memoryEvictPolicy;
	}

	public void setMemoryEvictPolicy(String memoryEvictPolicy) {
		this.memoryEvictPolicy = memoryEvictPolicy;
	}

	public Long getCacheSize() {
		return cacheSize;
	}

	public void setCacheSize(Long cacheSize) {
		this.cacheSize = cacheSize;
	}
	
}
