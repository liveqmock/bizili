package com.vteba.cache.hibernate.memcached.strategy;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.CollectionRegion;
import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;
import org.hibernate.cache.spi.access.SoftLock;
import org.hibernate.cfg.Settings;

import com.vteba.cache.hibernate.memcached.regions.MemcacheCollectionRegion;

/**
 * Memcache specific read-only collection region access strategy。
 * 特定于Memcahce的只读集合区域访问策略。
 */
public class ReadOnlyMemcacheCollectionRegionAccessStrategy
		extends AbstractMemcacheAccessStrategy<MemcacheCollectionRegion>
		implements CollectionRegionAccessStrategy {

	/**
	 * Create a read-only access strategy accessing the given collection region.
	 */
	public ReadOnlyMemcacheCollectionRegionAccessStrategy(MemcacheCollectionRegion region, Settings settings) {
		super( region, settings );
	}

	/**
	 * {@inheritDoc}
	 */
	public CollectionRegion getRegion() {
		return region;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object get(Object key, long txTimestamp) throws CacheException {
		return region.get( key );
	}

	/**
	 * 从数据库加载后，尝试去缓存一个对象。如果缓存成功返回true
	 * 加载后，如果区域中没有或minimalPutOverride=false，将对象放入缓存中
	 */
	public boolean putFromLoad(Object key, Object value, long txTimestamp, Object version, boolean minimalPutOverride)
			throws CacheException {
		if ( minimalPutOverride && region.contains( key ) ) {
			return false;
		} else {
			region.put( key, value );
			return true;
		}
	}

	public SoftLock lockItem(Object key, Object version) throws UnsupportedOperationException {
		return null;
	}

	/**
	 * A no-op since this cache is read-only
	 */
	public void unlockItem(Object key, SoftLock lock) throws CacheException {
	}
}
