package com.vteba.cache.memcached.manager;

//import com.alisoft.xplatform.asf.cache.ICacheManager;
//import com.alisoft.xplatform.asf.cache.IMemcachedCache;
//import com.alisoft.xplatform.asf.cache.memcached.CacheUtil;
//import com.alisoft.xplatform.asf.cache.memcached.MemcachedCacheManager;

public class AliCacheManager{// extends MemcachedCacheManager {

//	String configFileName = "memcached.xml";
//	public static final String CACHE_CLIENT_ID = "mclient";
//	
//	//缓存最大过期时间-一个月
//	public static final int EXPIRE_TIME_MAX = 30*24*3600;
//	//缓存过期时间-半天
//	public static final int EXPIRE_TIME_HALFDAY = 12*3600;
//	//缓存过期时间-整天
//	public static final int EXPIRE_TIME_ONEDAY = 24*3600;
//	
//	public AliCacheManager() {
//		super();
//	}
//
//	public void start(){
//		ICacheManager<IMemcachedCache> manager = CacheUtil.getCacheManager(
//				IMemcachedCache.class, MemcachedCacheManager.class.getName());
//		manager.setConfigFile(configFileName);
//		super.start();
//	}
//	
//	public void put(String key, Object value) {
//		getCache(CACHE_CLIENT_ID).put(key, value);
//	}
//	
//	public void put(String key, Object value, int expire) {
//		getCache(CACHE_CLIENT_ID).put(key, value, expire);
//	}	
//	
//	public Object get(String key) {
//		return getCache(CACHE_CLIENT_ID).get(key);
//	}
//	
//	public Object get(CacheKey key) {
//		return getCache(CACHE_CLIENT_ID).get(String.valueOf(key.hashCode()));
//	}
//	
//	public void put(CacheKey key, Object value) {
//		getCache(CACHE_CLIENT_ID).put(String.valueOf(key.hashCode()), value);
//	}
//	
//	public void put(CacheKey key, Object value, int expire) {
//		getCache(CACHE_CLIENT_ID).put(String.valueOf(key.hashCode()), value, expire);
//	}	
//	
//	public void remove(String key) {
//		getCache(CACHE_CLIENT_ID).remove(key);
//	}
//	
//	public void remove(CacheKey key) {
//		getCache(CACHE_CLIENT_ID).remove(String.valueOf(key.hashCode()));
//	}
//	
//	public boolean isInCache(String key) {
//		return getCache(CACHE_CLIENT_ID).containsKey(key);
//	}
//	
//	public boolean isInCache(CacheKey key) {
//		return getCache(CACHE_CLIENT_ID).containsKey(String.valueOf(key.hashCode()));
//	}
}
