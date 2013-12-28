package com.vteba.cache.hibernate.memcached.strategy;


import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;
import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
import org.hibernate.cache.spi.access.NaturalIdRegionAccessStrategy;

import com.vteba.cache.hibernate.memcached.regions.MemcacheCollectionRegion;
import com.vteba.cache.hibernate.memcached.regions.MemcacheEntityRegion;
import com.vteba.cache.hibernate.memcached.regions.MemcacheNaturalIdRegion;

/**
 * 创建{@link org.hibernate.cache.spi.access.RegionAccessStrategy}区域访问策略的工厂
 */
public interface MemcacheAccessStrategyFactory {

	/**
	 * Create {@link EntityRegionAccessStrategy} for the input {@link MemcacheEntityRegion} and {@link AccessType}
	 *
	 * @param entityRegion
	 * @param accessType
	 *
	 * @return the created {@link EntityRegionAccessStrategy}
	 */
	public EntityRegionAccessStrategy createEntityRegionAccessStrategy(MemcacheEntityRegion entityRegion, AccessType accessType);

	/**
	 * Create {@link CollectionRegionAccessStrategy} for the input {@link com.vteba.cache.hibernate.memcached.regions.MemcacheCollectionRegion} and {@link AccessType}
	 *
	 * @param collectionRegion
	 * @param accessType
	 *
	 * @return the created {@link CollectionRegionAccessStrategy}
	 */
	public CollectionRegionAccessStrategy createCollectionRegionAccessStrategy(MemcacheCollectionRegion collectionRegion,
																			   AccessType accessType);
    /**
     * Create {@link NaturalIdRegionAccessStrategy} for the input {@link com.vteba.cache.hibernate.memcached.regions.MemcacheNaturalIdRegion} and {@link AccessType}
     *
     * @param naturalIdRegion
     * @param accessType
     *
     * @return the created {@link NaturalIdRegionAccessStrategy}
     */
    public NaturalIdRegionAccessStrategy createNaturalIdRegionAccessStrategy(MemcacheNaturalIdRegion naturalIdRegion,
                                                                               AccessType accessType);

}
