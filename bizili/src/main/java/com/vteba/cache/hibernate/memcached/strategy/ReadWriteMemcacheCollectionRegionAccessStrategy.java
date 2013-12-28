package com.vteba.cache.hibernate.memcached.strategy;

import org.hibernate.cache.spi.CollectionRegion;
import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;
import org.hibernate.cfg.Settings;

import com.vteba.cache.hibernate.memcached.regions.MemcacheCollectionRegion;

/**
 * 特定于memcache的读写集合区域访问策略
 */
public class ReadWriteMemcacheCollectionRegionAccessStrategy
		extends AbstractReadWriteMemcacheAccessStrategy<MemcacheCollectionRegion>
		implements CollectionRegionAccessStrategy {

	/**
	 * 创建一个读写集合访问策略，访问给定的集合区域
	 */
	public ReadWriteMemcacheCollectionRegionAccessStrategy(MemcacheCollectionRegion region, Settings settings) {
		super( region, settings );
	}

	/**
	 * {@inheritDoc}。
	 * 获得集合区域。
	 */
	public CollectionRegion getRegion() {
		return region;
	}
}