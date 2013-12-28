package com.vteba.cache.hibernate.memcached.strategy;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.NaturalIdRegion;
import org.hibernate.cache.spi.access.NaturalIdRegionAccessStrategy;
import org.hibernate.cache.spi.access.SoftLock;
import org.hibernate.cfg.Settings;

import com.vteba.cache.hibernate.memcached.regions.MemcacheNaturalIdRegion;

/**
 * 特定于memcache的 read/write原生ID区域访问策略
 */
public class ReadWriteMemcacheNaturalIdRegionAccessStrategy
		extends AbstractReadWriteMemcacheAccessStrategy<MemcacheNaturalIdRegion>
		implements NaturalIdRegionAccessStrategy {

	/**
	 * 用给定的区域，创建一个读写原生ID区域访问策略 
	 */
	public ReadWriteMemcacheNaturalIdRegionAccessStrategy(MemcacheNaturalIdRegion region, Settings settings) {
		super( region, settings );
	}

	/**
	 * {@inheritDoc}
	 */
	public NaturalIdRegion getRegion() {
		return region;
	}

	/**
	 * A no-op since this is an asynchronous cache access strategy.
	 */
	public boolean insert(Object key, Object value ) throws CacheException {
		return false;
	}

	/**
	 * Inserts will only succeed if there is no existing value mapped to this key.
	 * 对于指定的key，只有当没有对应的值存在时，才会插入成功
	 */
	public boolean afterInsert(Object key, Object value ) throws CacheException {
		region.writeLock( key );
		try {
			Lockable item = (Lockable) region.get( key );
			if ( item == null ) {
				region.put( key, new Item( value, null, region.nextTimestamp() ) );
				return true;
			}
			else {
				return false;
			}
		}
		finally {
			region.writeUnlock( key );
		}
	}

	/**
	 * A no-op since this is an asynchronous cache access strategy.
	 */
	public boolean update(Object key, Object value )
			throws CacheException {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * <p/>
	 * Updates will only succeed if this entry was locked by this transaction and exclusively this transaction for the
	 * duration of this transaction.  It is important to also note that updates will fail if the soft-lock expired during
	 * the course of this transaction.
	 * 实体被当前事务锁定，且在事务的持续时间内，更新才会成功。需要说明的是，在事务过程中，软锁定过期，更新也会失败
	 */
	public boolean afterUpdate(Object key, Object value, SoftLock lock) throws CacheException {
		//what should we do with previousVersion here?
		region.writeLock( key );
		try {
			Lockable item = (Lockable) region.get( key );

			if ( item != null && item.isUnlockable( lock ) ) {
				Lock lockItem = (Lock) item;
				if ( lockItem.wasLockedConcurrently() ) {
					decrementLock( key, lockItem );
					return false;
				}
				else {
					region.put( key, new Item( value, null, region.nextTimestamp() ) );
					return true;
				}
			}
			else {
				handleLockExpiry( key, item );
				return false;
			}
		}
		finally {
			region.writeUnlock( key );
		}
	}
}