package com.vteba.cache.hibernate.memcached.strategy;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.NaturalIdRegion;
import org.hibernate.cache.spi.access.NaturalIdRegionAccessStrategy;
import org.hibernate.cache.spi.access.SoftLock;
import org.hibernate.cfg.Settings;

import com.vteba.cache.hibernate.memcached.regions.MemcacheNaturalIdRegion;

/**
 * Memcache specific non-strict read/write NaturalId region access strategy
 */
public class NonStrictReadWriteMemcacheNaturalIdRegionAccessStrategy
		extends AbstractMemcacheAccessStrategy<MemcacheNaturalIdRegion>
		implements NaturalIdRegionAccessStrategy {

	/**
	 * Create a non-strict read/write access strategy accessing the given NaturalId region.
	 */
	public NonStrictReadWriteMemcacheNaturalIdRegionAccessStrategy(MemcacheNaturalIdRegion region, Settings settings) {
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
	 * {@inheritDoc}
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

	/**
	 * Since this is a non-strict read/write strategy item locking is not used.
	 */
	public SoftLock lockItem(Object key, Object version) throws CacheException {
		return null;
	}

	/**
	 * Since this is a non-strict read/write strategy item locking is not used.
	 */
	public void unlockItem(Object key, SoftLock lock) throws CacheException {
		region.remove( key );
	}

	/**
	 * Returns <code>false</code> since this is an asynchronous cache access strategy.
	 */
	public boolean insert(Object key, Object value ) throws CacheException {
		return false;
	}

	/**
	 * Returns <code>false</code> since this is a non-strict read/write cache access strategy
	 */
	public boolean afterInsert(Object key, Object value ) throws CacheException {
		return false;
	}

	/**
	 * Removes the entry since this is a non-strict read/write cache strategy.
	 */
	public boolean update(Object key, Object value ) throws CacheException {
		remove( key );
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean afterUpdate(Object key, Object value, SoftLock lock) throws CacheException {
		unlockItem( key, lock );
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remove(Object key) throws CacheException {
		region.remove( key );
	}
}
