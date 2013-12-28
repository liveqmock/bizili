package com.vteba.cache.hibernate.memcached.strategy;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.NaturalIdRegion;
import org.hibernate.cache.spi.access.NaturalIdRegionAccessStrategy;
import org.hibernate.cache.spi.access.SoftLock;
import org.hibernate.cfg.Settings;

import com.vteba.cache.hibernate.memcached.regions.MemcacheNaturalIdRegion;

/**
 * Memcache specific read-only NaturalId region access strategy
 */
public class ReadOnlyMemcacheNaturalIdRegionAccessStrategy
		extends AbstractMemcacheAccessStrategy<MemcacheNaturalIdRegion>
		implements NaturalIdRegionAccessStrategy {

	/**
	 * Create a read-only access strategy accessing the given NaturalId region.
	 */
	public ReadOnlyMemcacheNaturalIdRegionAccessStrategy(MemcacheNaturalIdRegion region, Settings settings) {
		super( region, settings );
	}

	/**
	 * {@inheritDoc}
	 */
	public NaturalIdRegion getRegion() {
		return region;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object get(Object key, long txTimestamp) throws CacheException {
		return region.get( key );
	}

	/**
	 * 从数据库加载后，尝试缓存对象，缓存成功返回true，否则返回false
	 */
	public boolean putFromLoad(Object key, Object value, long txTimestamp, Object version, boolean minimalPutOverride)
			throws CacheException {
		if ( minimalPutOverride && region.contains( key ) ) {
			return false;
		}
		else {
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
		region.remove( key );
	}

	/**
	 * 因为缓存是异步的，所以没有操作
	 */
	public boolean insert(Object key, Object value ) throws CacheException {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean afterInsert(Object key, Object value ) throws CacheException {
		region.put( key, value );
		return true;
	}

	/**
	 * Throws UnsupportedOperationException since this cache is read-only
	 * 因为是只读缓存，所以抛出不支持操作异常
	 * @throws UnsupportedOperationException always
	 */
	public boolean update(Object key, Object value ) throws UnsupportedOperationException {
		throw new UnsupportedOperationException( "Can't write to a readonly object" );
	}

	/**
	 * Throws UnsupportedOperationException since this cache is read-only。
	 * 因为是只读缓存，所以抛出不支持操作异常
	 * @throws UnsupportedOperationException always
	 */
	public boolean afterUpdate(Object key, Object value, SoftLock lock) throws UnsupportedOperationException {
		throw new UnsupportedOperationException( "Can't write to a readonly object" );
	}
}
