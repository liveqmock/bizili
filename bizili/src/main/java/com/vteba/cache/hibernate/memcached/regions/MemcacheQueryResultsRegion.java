package com.vteba.cache.hibernate.memcached.regions;

import java.util.Properties;

import javax.transaction.TransactionManager;

import org.hibernate.cache.spi.QueryResultsRegion;

import com.vteba.cache.hibernate.memcached.Memcache;
import com.vteba.cache.hibernate.memcached.strategy.MemcacheAccessStrategyFactory;

/**
 * A query results region specific wrapper around an Memcache instance.
 */
public class MemcacheQueryResultsRegion extends MemcacheGeneralDataRegion implements QueryResultsRegion {

	/**
	 * Constructs an MemcacheQueryResultsRegion around the given underlying cache.
	 *
	 * @param accessStrategyFactory
	 */
	public MemcacheQueryResultsRegion(MemcacheAccessStrategyFactory accessStrategyFactory, Memcache underlyingCache, 
			String regionName, Properties properties, TransactionManager transactionManager) {
		super( accessStrategyFactory, underlyingCache, regionName, properties, transactionManager);
	}

}