package com.vteba.cache.hibernate.memcached.regions;

import java.util.Map;
import java.util.Properties;

import javax.transaction.TransactionManager;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.GeneralDataRegion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vteba.cache.hibernate.memcached.Memcache;
import com.vteba.cache.hibernate.memcached.strategy.MemcacheAccessStrategyFactory;

/**
 * An Memcache specific GeneralDataRegion.
 * <p/>
 * GeneralDataRegion instances are used for both the timestamps and query caches.
 */
public abstract class MemcacheGeneralDataRegion extends MemcacheDataRegion implements GeneralDataRegion {

    private static final Logger log = LoggerFactory.getLogger(MemcacheGeneralDataRegion.class);
    
    /**缓存击穿，访问数据库，保护激活*/
    private boolean dogpilePreventionEnabled = false;
    private double dogpilePreventionExpirationFactor = 2;
    private int cacheTimeSeconds = 300;
    private boolean clearSupported = false;
    private final String clearIndexKey;
    public static final Integer DOGPILE_TOKEN = 0;
    private final String regionName;
    
    /**
     * Creates an EhcacheGeneralDataRegion using the given Memcache instance as a backing.
     */
    public MemcacheGeneralDataRegion(MemcacheAccessStrategyFactory accessStrategyFactory, Memcache cache,
    		String regionName, Properties properties, TransactionManager transactionManager) {
        super( accessStrategyFactory, cache, regionName, properties, transactionManager);
        this.regionName = regionName;
        this.clearIndexKey = this.regionName.replaceAll("\\s", "") + ":index_key";
    }
    
    private String dogpileTokenKey(String objectKey) {
        return objectKey + ".dogpileTokenKey";
    }

    private Object memcacheGet(Object key) {
        String objectKey = toKey(key);

        if (dogpilePreventionEnabled) {
            return getUsingDogpilePrevention(objectKey);

        } else {
            log.debug("Memcache.get({})", objectKey);
            return cache.get(objectKey);
        }
    }

    private Object getUsingDogpilePrevention(String objectKey) {
        Map<String, Object> multi;

        String dogpileKey = dogpileTokenKey(objectKey);
        log.debug("Checking dogpile key: [{}]", dogpileKey);

        log.debug("Memcache.getMulti({}, {})", objectKey, dogpileKey);
        multi = cache.getMulti(dogpileKey, objectKey);

        if ((multi == null) || (multi.get(dogpileKey) == null)) {
            log.debug("Dogpile key ({}) not found updating token and returning null", dogpileKey);
            cache.put(dogpileKey, cacheTimeSeconds, DOGPILE_TOKEN);
            return null;
        }

        return multi.get(objectKey);
    }

    private void memcacheSet(Object key, Object o) {
        String objectKey = toKey(key);

        int cacheTime = cacheTimeSeconds;

        if (dogpilePreventionEnabled) {
            String dogpileKey = dogpileTokenKey(objectKey);
            log.debug("Dogpile prevention enabled, setting token and adjusting object cache time. Key: [{}]", dogpileKey);
            cache.put(dogpileKey, cacheTimeSeconds, DOGPILE_TOKEN);
            cacheTime = (int) (cacheTimeSeconds * dogpilePreventionExpirationFactor);
        }

        log.debug("Memcache.set({})", objectKey);

        cache.put(objectKey, cacheTime, o);
    }
    
    public String toString() {
        return "Memcached (" + regionName + ")";
    }

    private long getClearIndex() {
        Long index = null;

        if (clearSupported) {
            Object value = cache.get(clearIndexKey);
            if (value != null) {
                if (value instanceof String) {
                    index = Long.valueOf((String) value);
                } else if (value instanceof Long) {
                    index = (Long) value;
                } else {
                    throw new IllegalArgumentException(
                            "Unsupported type [" + value.getClass() + "] found for clear index at cache key [" + clearIndexKey + "]");
                }
            }
            if (index != null) {
                return index;
            }
        }

        return 0L;
    }
    
    private String toKey(Object key) {
        return keyStrategy.toKey(regionName, getClearIndex(), key);
    }
    
    /**
     * {@inheritDoc}
     */
    public Object get(Object key) throws CacheException {
        return memcacheGet(key);
    }

    /**
     * {@inheritDoc}
     */
    public void put(Object key, Object value) throws CacheException {
        log.debug( "key: %s value: %s", key, value );
        memcacheSet(key, value);
    }

    /**
     * {@inheritDoc}
     */
    public void evict(Object key) throws CacheException {
        cache.delete(toKey(key));
    }

    /**
     * {@inheritDoc}
     */
    public void evictAll() throws CacheException {
       //暂不支持 
    }
    
    public int getCacheTimeSeconds() {
        return cacheTimeSeconds;
    }

    public void setCacheTimeSeconds(int cacheTimeSeconds) {
        this.cacheTimeSeconds = cacheTimeSeconds;
    }

    public boolean isClearSupported() {
        return clearSupported;
    }

    public void setClearSupported(boolean clearSupported) {
        this.clearSupported = clearSupported;
    }

    public boolean isDogpilePreventionEnabled() {
        return dogpilePreventionEnabled;
    }

    public void setDogpilePreventionEnabled(boolean dogpilePreventionEnabled) {
        this.dogpilePreventionEnabled = dogpilePreventionEnabled;
    }

    public double getDogpilePreventionExpirationFactor() {
        return dogpilePreventionExpirationFactor;
    }

    public void setDogpilePreventionExpirationFactor(double dogpilePreventionExpirationFactor) {
        if (dogpilePreventionExpirationFactor < 1.0) {
            throw new IllegalArgumentException("dogpilePreventionExpirationFactor must be greater than 1.0");
        }
        this.dogpilePreventionExpirationFactor = dogpilePreventionExpirationFactor;
    }
}
