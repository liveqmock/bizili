package com.vteba.cache.hibernate.memcached.strategy;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.CollectionRegion;
import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;
import org.hibernate.cache.spi.access.SoftLock;
import org.hibernate.cfg.Settings;

import com.vteba.cache.hibernate.memcached.Memcache;
import com.vteba.cache.hibernate.memcached.regions.MemcacheCollectionRegion;

/**
 * JTA CollectionRegionAccessStrategy
 */
public class TransactionalMemcacheCollectionRegionAccessStrategy
		extends AbstractMemcacheAccessStrategy<MemcacheCollectionRegion>
		implements CollectionRegionAccessStrategy {

	private final Memcache memcache;

	/**
	 * Construct a new collection region access strategy.
	 *
	 * @param region the Hibernate region.
	 * @param cache the cache.
	 * @param settings the Hibernate settings.
	 */
	public TransactionalMemcacheCollectionRegionAccessStrategy(MemcacheCollectionRegion region, Memcache cache, Settings settings) {
		super( region, settings );
		this.memcache = cache;
	}


	/**
	 * {@inheritDoc}
	 */
	public Object get(Object key, long txTimestamp) throws CacheException {
		String k = keyStrategy.toKey(getRegion().getName(), 0, key);
		return memcache.get(k);
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
	public void unlockItem(Object key, SoftLock lock) throws CacheException {
		// no-op
	}

	/**
	 * {@inheritDoc}
	 */
	public SoftLock lockItem(Object key, Object version) throws CacheException {
		return null;
	}

	/**
	 * 从数据库加载后，尝试缓存对象，成功返回true，失败返回false
	 */
	public boolean putFromLoad(Object key, Object value, long txTimestamp,
							   Object version, boolean minimalPutOverride) throws CacheException {
		String k = keyStrategy.toKey(getRegion().getName(), 0, key);
		if ( minimalPutOverride && memcache.get( k ) != null ) {
			return false;
		}
		//OptimisticCache? versioning?
		memcache.put(k, 0, value);
		return true;
	}

	/**
	 * 从缓存中删除对象
	 */
	@Override
	public void remove(Object key) throws CacheException {
		String k = keyStrategy.toKey(getRegion().getName(), 0, key);
		memcache.delete(k);
	}

}
