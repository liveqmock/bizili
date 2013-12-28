package com.vteba.cache.hibernate.memcached.regions;

import java.util.Properties;

import javax.transaction.TransactionManager;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.CacheDataDescription;
import org.hibernate.cache.spi.CollectionRegion;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;
import org.hibernate.cfg.Settings;

import com.vteba.cache.hibernate.memcached.Memcache;
import com.vteba.cache.hibernate.memcached.access.PutFromLoadValidator;
import com.vteba.cache.hibernate.memcached.strategy.MemcacheAccessStrategyFactory;

/**
 * A collection region specific wrapper around an Memcache instance.
 * <p/>
 * This implementation returns Memcache specific access strategy instances for all the non-transactional access types. Transactional access
 * is not supported.
 */
public class MemcacheCollectionRegion extends MemcacheTransactionalDataRegion implements CollectionRegion {

	
    /**
     * Constructs an MemcacheCollectionRegion around the given underlying cache.
     *
     * @param accessStrategyFactory
     */
    public MemcacheCollectionRegion(MemcacheAccessStrategyFactory accessStrategyFactory, Memcache underlyingCache, String regionName, Settings settings,
                                   CacheDataDescription metadata, Properties properties, TransactionManager transactionManager) {
        super( accessStrategyFactory, underlyingCache, regionName, settings, metadata, properties, transactionManager);
    }

    /**
     * {@inheritDoc}
     */
    public CollectionRegionAccessStrategy buildAccessStrategy(AccessType accessType) throws CacheException {
        return accessStrategyFactory.createCollectionRegionAccessStrategy( this, accessType );
    }
    
	public PutFromLoadValidator getPutFromLoadValidator() {
	    return new PutFromLoadValidator(transactionManager);
	}
}