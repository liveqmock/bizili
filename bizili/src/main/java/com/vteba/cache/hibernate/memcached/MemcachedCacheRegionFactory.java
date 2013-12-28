package com.vteba.cache.hibernate.memcached;

import java.util.Properties;

import javax.transaction.TransactionManager;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.CacheDataDescription;
import org.hibernate.cache.spi.CollectionRegion;
import org.hibernate.cache.spi.EntityRegion;
import org.hibernate.cache.spi.NaturalIdRegion;
import org.hibernate.cache.spi.QueryResultsRegion;
import org.hibernate.cache.spi.RegionFactory;
import org.hibernate.cache.spi.TimestampsRegion;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cfg.Settings;
import org.hibernate.service.jta.platform.spi.JtaPlatform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vteba.cache.hibernate.memcached.regions.MemcacheCollectionRegion;
import com.vteba.cache.hibernate.memcached.regions.MemcacheEntityRegion;
import com.vteba.cache.hibernate.memcached.regions.MemcacheNaturalIdRegion;
import com.vteba.cache.hibernate.memcached.regions.MemcacheQueryResultsRegion;
import com.vteba.cache.hibernate.memcached.regions.MemcacheTimestampsRegion;
import com.vteba.cache.hibernate.memcached.strategy.MemcacheAccessStrategyFactory;
import com.vteba.cache.hibernate.memcached.strategy.MemcacheAccessStrategyFactoryImpl;
import com.vteba.cache.memcached.manager.XMemcachedManager;

/**
 * 实现hibernate的缓存区域接口，将memcache插入到hibernate的缓存系统中
 * @author yinlei
 * date 2012-8-23 下午3:32:07
 */
public class MemcachedCacheRegionFactory implements RegionFactory {
	
	private static final long serialVersionUID = -6792884904005001079L;
	private final Logger log = LoggerFactory.getLogger(MemcachedCacheRegionFactory.class);
    //private Memcache memcache;
    private TransactionManager transactionManager;
    private JtaPlatform jtaPlatform;
    private volatile XMemcachedManager memcacheManager;
    
    /**
     * hibernate持久化 单元相关设置
     */
    protected Settings settings;
    
    /**
     * 缓存访问策略工厂
     */
    protected final MemcacheAccessStrategyFactory accessStrategyFactory = new MemcacheAccessStrategyFactoryImpl();
    
    public MemcachedCacheRegionFactory() {
	}
    
    public MemcachedCacheRegionFactory(Properties properties) {
		super();
	}

    /**
     * No clue what this is for, Hibernate docs don't say.
     * @return long {@link org.hibernate.cache.Timestamper#next()}
     */
    @Override
    public long nextTimestamp() {
        return System.currentTimeMillis() / 100;
    }
    
    /**
     * 根据hibernate参考文档，属性MinimalPutsEnabledByDefault对于分布式缓存，应该是true
     * @return true
     */
    @Override
    public boolean isMinimalPutsEnabledByDefault() {
        return true;
    }

	@Override
	public CollectionRegion buildCollectionRegion(String regionName, Properties properties, CacheDataDescription metadata) throws CacheException {
		return new MemcacheCollectionRegion(accessStrategyFactory,
				memcacheManager.getMemcache(regionName), regionName, settings, metadata, properties, transactionManager);
	}

	@Override
	public EntityRegion buildEntityRegion(String regionName, Properties properties, CacheDataDescription metadata) throws CacheException {
		Memcache memcache = memcacheManager.getMemcache(regionName);
		return new MemcacheEntityRegion(accessStrategyFactory, memcache, regionName, settings, metadata, properties, transactionManager);
	}

	@Override
	public NaturalIdRegion buildNaturalIdRegion(String regionName, Properties properties, CacheDataDescription metadata) throws CacheException {
		return new MemcacheNaturalIdRegion(accessStrategyFactory, memcacheManager.getMemcache(regionName), regionName, settings, metadata, properties,transactionManager);
	}
	
	@Override
	public QueryResultsRegion buildQueryResultsRegion(String regionName, Properties properties) throws CacheException {
		return new MemcacheQueryResultsRegion(accessStrategyFactory, memcacheManager.getMemcache(regionName), regionName, properties,transactionManager);
	}

	@Override
	public TimestampsRegion buildTimestampsRegion(String regionName, Properties properties)
			throws CacheException {
		return new MemcacheTimestampsRegion(accessStrategyFactory, memcacheManager.getMemcache(regionName), regionName, properties,transactionManager);
	}

	@Override
	public AccessType getDefaultAccessType() {
		return AccessType.READ_WRITE;
	}
	
	@Override
	public void start(Settings settings, Properties properties) throws CacheException {
		log.info("Starting MemcachedClient ...");
        try {
        	if (settings != null) {
        		jtaPlatform = settings.getJtaPlatform();
            	if (jtaPlatform != null) {
            		transactionManager = jtaPlatform.retrieveTransactionManager();
            	} else {
            		transactionManager = null;
            	}
        	} else {
        		transactionManager = null;
        	}
        	
        	//MemcacheClientFactory factory = getMemcachedClientFactory(new Config(new PropertiesHelper(properties)));
            //memcache = factory.createMemcacheClient();
            //memcache.setTransactionManager(transactionManager);
            String cacheManagerName = properties.getProperty("hibernate.memcache.cacheManagerName", "cacheManagerName");
            memcacheManager = new XMemcachedManager(transactionManager, cacheManagerName, properties);//new XMemcachedManager(memcache, cacheManagerName, properties);
            log.info("Started MemcachedClient Successfully.");
        } catch (Exception e) {
        	log.info("Start MemcachedClient Failing.");
            throw new CacheException("Unable to initialize MemcachedClient", e);
        }
    }
	
	@Override
	public void stop() {
//        if (memcacheManager.getMemcache() != null) {
//            log.info("Shutting down Memcache client。");
//            try {
//            	memcacheManager.getMemcache().shutdown();
//                log.info("Shutted down Memcache client successfully.");
//			} catch (Exception e) {
//				log.info("Shutting Memcache client Failing.");
//	            throw new CacheException("Unable to shut Memcache client", e);
//			}
//        }
        //memcache = null;
		if (memcacheManager != null) {
			XMemcachedManager.shutdown(memcacheManager.getCacheManagerName());
		}
    }
}
