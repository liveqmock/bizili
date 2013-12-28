package com.vteba.cache.hibernate.memcached;

import com.vteba.cache.hibernate.PropertiesHelper;
import com.vteba.cache.hibernate.Sha1KeyStrategy;

/**
 * 配置文件属性列表
 * @author Ray Krueger，yinlei
 */
public class Config {

    public static final String PROP_PREFIX = "hibernate.memcached.";

    private static final String CACHE_TIME_SECONDS = "cacheTimeSeconds";
    public static final String PROP_CACHE_TIME_SECONDS = PROP_PREFIX + "cacheTimeSeconds";

    private static final String CLEAR_SUPPORTED = "clearSupported";
    public static final String PROP_CLEAR_SUPPORTED = PROP_PREFIX + CLEAR_SUPPORTED;

    private static final String MEMCACHE_CLIENT_FACTORY = "memcacheClientFactory";
    public static final String PROP_MEMCACHE_CLIENT_FACTORY = PROP_PREFIX + MEMCACHE_CLIENT_FACTORY;

    private static final String DOGPILE_PREVENTION = "dogpilePrevention";
    public static final String PROP_DOGPILE_PREVENTION = PROP_PREFIX + DOGPILE_PREVENTION;

    private static final String DOGPILE_PREVENTION_EXPIRATION_FACTOR = "dogpilePrevention.expirationFactor";
    public static final String PROP_DOGPILE_PREVENTION_EXPIRATION_FACTOR = PROP_PREFIX + DOGPILE_PREVENTION_EXPIRATION_FACTOR;
    
    public static final String PROP_REGION_NAME = "regionName";
    
    private static final String KEY_STRATEGY = "keyStrategy";
    
    public static final String MEMCACHE_CONFIG_FILENAME = "hibernate.memcached.config.filename";

    public static final int DEFAULT_CACHE_TIME_SECONDS = 300;
    public static final boolean DEFAULT_CLEAR_SUPPORTED = false;
    public static final boolean DEFAULT_DOGPILE_PREVENTION = false;
    public static final String DEFAULT_MEMCACHE_CLIENT_FACTORY = "com.skmbw.cache.hibernate.memcached.spymemcached.SpyMemcacheClientFactory";

    private PropertiesHelper props;
    private static final int DEFAULT_DOGPILE_EXPIRATION_FACTOR = 2;

    public Config(PropertiesHelper props) {
        this.props = props;
    }

    public int getCacheTimeSeconds(String cacheRegion) {
        int globalCacheTimeSeconds = props.getInt(PROP_CACHE_TIME_SECONDS,
                DEFAULT_CACHE_TIME_SECONDS);
        return props.getInt(cacheRegionPrefix(cacheRegion) + CACHE_TIME_SECONDS,
                globalCacheTimeSeconds);
    }

    public String getKeyStrategyName(String cacheRegion) {
        String globalKeyStrategy = props.get(PROP_PREFIX + KEY_STRATEGY,
                Sha1KeyStrategy.class.getName());
        return props.get(cacheRegionPrefix(cacheRegion) + KEY_STRATEGY, globalKeyStrategy);
    }

    public boolean isClearSupported(String cacheRegion) {
        boolean globalClearSupported = props.getBoolean(PROP_CLEAR_SUPPORTED,
                DEFAULT_CLEAR_SUPPORTED);
        return props.getBoolean(cacheRegionPrefix(cacheRegion) + CLEAR_SUPPORTED,
                globalClearSupported);
    }

    public boolean isDogpilePreventionEnabled(String cacheRegion) {
        boolean globalDogpilePrevention = props.getBoolean(PROP_DOGPILE_PREVENTION,
                DEFAULT_DOGPILE_PREVENTION);
        return props.getBoolean(cacheRegionPrefix(cacheRegion) + DOGPILE_PREVENTION,
                globalDogpilePrevention);
    }

    public double getDogpilePreventionExpirationFactor(String cacheRegion) {
        double globalFactor = props.getDouble(PROP_DOGPILE_PREVENTION_EXPIRATION_FACTOR,
                DEFAULT_DOGPILE_EXPIRATION_FACTOR);
        return props.getDouble(cacheRegionPrefix(cacheRegion) + DOGPILE_PREVENTION_EXPIRATION_FACTOR,
                globalFactor);
    }

    public String getMemcachedClientFactoryName() {
        return props.get(PROP_MEMCACHE_CLIENT_FACTORY,
                DEFAULT_MEMCACHE_CLIENT_FACTORY);
    }

    /**
     * 获得Memcache配置文件名
     * @author yinlei
     * date 2013-4-5 下午4:42:52
     */
    public String getMemcacheConfigFileName() {
    	return props.get(MEMCACHE_CONFIG_FILENAME);
    }
    
    private String cacheRegionPrefix(String cacheRegion) {
        return PROP_PREFIX + cacheRegion + ".";
    }

    public PropertiesHelper getPropertiesHelper() {
        return props;
    }
}
