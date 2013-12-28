package com.vteba.cache.hibernate.memcached;

import java.util.Collection;
import java.util.Map;

import javax.transaction.TransactionManager;

import com.vteba.cache.memcached.Element;
import com.vteba.cache.memcached.Store;
import com.vteba.cache.memcached.manager.XMemcachedManager;

/**
 * Interface to abstract memcache operations.
 */
public interface Memcache {
	/**
	 * 根据key获得缓存中的对象
	 * @param key 缓存对象key
	 * @return 缓存对象
	 * @author yinlei
	 * date 2012-11-9 下午10:53:45
	 */
	public Object get(String key);

    public boolean contains(String key);
    
    public Map<String, Object> getMulti(String... keys);

    public void put(String key, int cacheTimeSeconds, Object obj);
    
    public void putAll(Collection<Element> elements);
    
    public boolean update(String key, Object obj);
    
    public void delete(String key);
    
    public void deleteAll(Collection<String> keys);
    
    public void incr(String key, int factor, int startingValue);

    public void shutdown();
    
    /**
     * 获得事务管理器
     * @author yinlei
     * date 2012-11-9 下午10:52:12
     */
    public TransactionManager getTransactionManager();
    
    /**
     * 设置事务管理器
     * @param transactionManager
     * @author yinlei
     * date 2012-11-9 下午10:52:31
     */
    public void setTransactionManager(TransactionManager transactionManager);
    
    /**
     * 获得缓存管理器
     * @author yinlei
     * date 2012-11-9 下午10:51:54
     */
    public XMemcachedManager getCacheManager();
    
    public String getName();
    
    public void setName(String cacheName);

	public void setMemcacheManager(XMemcachedManager memcacheManager);
	
//	public void setStore(Store store);
//	
	public Store getStore();
	
	//public ElementValueComparator getElementValueComparator();
}
