package com.vteba.cache.hibernate.memcached.strategy;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.EntityRegion;
import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
import org.hibernate.cache.spi.access.SoftLock;
import org.hibernate.cfg.Settings;

import com.vteba.cache.hibernate.memcached.regions.MemcacheEntityRegion;

/**
 * 特定于memcache的只读实体区域访问策略。
 */
public class ReadOnlyMemcacheEntityRegionAccessStrategy extends AbstractMemcacheAccessStrategy<MemcacheEntityRegion>
		implements EntityRegionAccessStrategy {

	/**
	 * Create a read-only access strategy accessing the given entity region。
	 * 用给定的实体区域创建一个只读实体访问策略。
	 */
	public ReadOnlyMemcacheEntityRegionAccessStrategy(MemcacheEntityRegion region, Settings settings) {
		super( region, settings );
	}

	/**
	 * {@inheritDoc}获得实体区域
	 */
	public EntityRegion getRegion() {
		return region;
	}

	/**
	 * {@inheritDoc}从缓存中获取对象
	 */
	public Object get(Object key, long txTimestamp) throws CacheException {
		return region.get( key );
	}

	/**
	 * {@inheritDoc}
	 * 从数据库加载后尝试将对象放入缓存，成功返回true，否则返回false
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
	
	/**
	 * 锁定对象，因是只读缓存，无操作
	 */
	public SoftLock lockItem(Object key, Object version) throws UnsupportedOperationException {
		return null;
	}

	/**
	 * A no-op since this cache is read-only
	 * 解锁对象，因为是只读缓存，所以无操作
	 */
	public void unlockItem(Object key, SoftLock lock) throws CacheException {
		//evict( key );
	}

	/**
	 * 因为缓存是同步的，所以没有操作
	 */
	public boolean insert(Object key, Object value, Object version) throws CacheException {
		return false;
	}

	/**
	 * 保存对象后(insert)，将对象放入缓存中，返回true
	 */
	public boolean afterInsert(Object key, Object value, Object version) throws CacheException {
		region.put( key, value );
		return true;
	}

	/**
	 * Throws UnsupportedOperationException since this cache is read-only。
	 * 抛出不支持操作异常，因为缓存是只读的。
	 * @throws UnsupportedOperationException always
	 */
	public boolean update(Object key, Object value, Object currentVersion, Object previousVersion)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException( "Can't write to a readonly object" );
	}

	/**
	 * Throws UnsupportedOperationException since this cache is read-only。
	 * 抛出不支持操作异常，因为缓存是只读的。
	 * @throws UnsupportedOperationException always
	 */
	public boolean afterUpdate(Object key, Object value, Object currentVersion, Object previousVersion, SoftLock lock)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException( "Can't write to a readonly object" );
	}
}