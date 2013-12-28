package com.vteba.cache.hibernate.memcached.regions;

import java.util.Properties;

import javax.transaction.TransactionManager;

import org.hibernate.cache.spi.TimestampsRegion;

import com.vteba.cache.hibernate.memcached.Memcache;
import com.vteba.cache.hibernate.memcached.strategy.MemcacheAccessStrategyFactory;

/**
 * A timestamps region specific wrapper around an Memcache instance.
 */
public class MemcacheTimestampsRegion extends MemcacheGeneralDataRegion implements TimestampsRegion {

	/**
	 * Constructs an MemcacheTimestampsRegion around the given underlying cache.
	 *
	 * @param accessStrategyFactory
	 */
	public MemcacheTimestampsRegion(MemcacheAccessStrategyFactory accessStrategyFactory, Memcache underlyingCache,
			String regionName, Properties properties, TransactionManager transactionManager) {
		super(accessStrategyFactory, underlyingCache, regionName, properties, transactionManager);
	}
}