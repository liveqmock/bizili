package com.vteba.cache.hibernate.memcached.xmemcached;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.TransactionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vteba.cache.hibernate.memcached.LoggingMemcacheExceptionHandler;
import com.vteba.cache.hibernate.memcached.Memcache;
import com.vteba.cache.hibernate.memcached.MemcacheExceptionHandler;
import com.vteba.cache.memcached.Element;
import com.vteba.cache.memcached.Store;
import com.vteba.cache.memcached.manager.XMemcachedManager;

/**
 * Hibernate二级缓存，使用xmemcache implementation。
 * @author yinlei
 */
public class Xmemcache implements Memcache {
	
	private static final Logger log = LoggerFactory.getLogger(Xmemcache.class);
	private MemcacheExceptionHandler exceptionHandler = new LoggingMemcacheExceptionHandler();
	//保留和以前的兼容，应该将其放到Store中
	//private final MemcachedClient memcachedClient;//memcache客户端,
	private TransactionManager transactionManager;//事务管理器
	private XMemcachedManager memcacheManager;//缓存管理器
	private String name;//缓存名字
	private Store store;//底层存储
	
	/**
	 * 使用Store构造Xmemcache实例。
	 * @param store
	 */
	public Xmemcache(Store store) {
		this.store = store;
	}
	
	public Object get(String key) {
		try {
			log.debug("MemcachedClient.get({})", key);
			//Object object = this.memcachedClient.get(key);
			Object object = getInternal(key);
			if (object != null && object.toString().equals("")) {
				return null;
			} else {
				return object;
			}
		} catch (Exception e) {
			this.exceptionHandler.handleErrorOnGet(key, e);
		}
		return null;
	}
	
	public Object getInternal(String key) {
		Element obj = store.get(key);
		if (obj != null) {
			return obj.getObjectValue();
		} else {
			return null;
		}
	}
	
	public Map<String, Object> getMulti(String... keys) {
		try {
			//return this.memcachedClient.get(Arrays.asList(keys));
			return getMultiInternal(keys);
		} catch (Exception e) {
			this.exceptionHandler.handleErrorOnGet(keys.toString(), e);
		}
		return null;
	}
	
	public Map<String, Object> getMultiInternal(String... keys){
		store.getAll(Arrays.asList(keys));
		Map<String, Object> maps = new HashMap<String, Object>();
		
		return maps;
	}
	
	public void put(String key, int cacheTimeSeconds, Object obj) {
		log.debug("MemcachedClient.set({})", key);
		try {
			//this.memcachedClient.set(key, cacheTimeSeconds, o);
			putInternal(key, cacheTimeSeconds, obj);
		} catch (Exception e) {
			this.exceptionHandler.handleErrorOnSet(key, cacheTimeSeconds, obj, e);
		}
	}
	
	public void putInternal(String key, int cacheTimeSeconds, Object obj){
		Element element = new Element(key, obj, cacheTimeSeconds);
		store.put(element);
	}
	
	public void delete(String key) {
		try {
			//this.memcachedClient.delete(key);
			deleteInternal(key);
		} catch (Exception e) {
			this.exceptionHandler.handleErrorOnDelete(key, e);
		}
	}

	public void deleteInternal(String key) {
		store.remove(key);
	}
	
	public void incr(String key, int factor, int startingValue) {
		try {
			this.store.getMemcacheClientDelegate().incr(key, factor, startingValue);
		} catch (Exception e) {
			this.exceptionHandler.handleErrorOnIncr(key, factor, startingValue, e);
		}
	}

	public void shutdown() {
		log.debug("Shutting down XMemcachedClient");
		try {
			this.store.getMemcacheClientDelegate().shutdown();
		} catch (Exception e) {
			log.error("Shut down XMemcachedClient error", e);
		}

	}

	public void setExceptionHandler(MemcacheExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}
	
	@Override
	public void setTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
		
	}
	
	@Override
	public TransactionManager getTransactionManager() {
		return transactionManager;
	}

	@Override
	public XMemcachedManager getCacheManager() {
		return memcacheManager;
	}

	@Override
	public void setMemcacheManager(XMemcachedManager memcacheManager) {
		this.memcacheManager = memcacheManager;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

//	@Override
//	public void setStore(Store store) {
//		this.store = store;
//	}
//
	@Override
	public Store getStore() {
		return store;
	}

	@Override
	public boolean contains(String key) {
		return store.containsKey(key);
	}

	@Override
	public void putAll(Collection<Element> elements) {
		store.putAll(elements);
	}

	@Override
	public boolean update(String key, Object obj) {
		return false;
	}

	@Override
	public void deleteAll(Collection<String> keys) {
		store.removeAll(keys);
	}

//	@Override
//	public ElementValueComparator getElementValueComparator() {
//		return new DefaultElementValueComparator();
//	}
	
}
