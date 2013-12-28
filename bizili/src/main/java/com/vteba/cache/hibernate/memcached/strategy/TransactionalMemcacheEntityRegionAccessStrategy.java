package com.vteba.cache.hibernate.memcached.strategy;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.EntityRegion;
import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
import org.hibernate.cache.spi.access.SoftLock;
import org.hibernate.cfg.Settings;

import com.vteba.cache.hibernate.memcached.Memcache;
import com.vteba.cache.hibernate.memcached.regions.MemcacheEntityRegion;

/**
 * JTA EntityRegionAccessStrategy.
 */
public class TransactionalMemcacheEntityRegionAccessStrategy extends
		AbstractMemcacheAccessStrategy<MemcacheEntityRegion> implements
		EntityRegionAccessStrategy {
	/**
	 * 底层的缓存对象
	 */
	private final Memcache cache;
	/**
	 * Construct a new entity region access strategy.
	 * 构造一个新的实体却又访问策略
	 * @param region the Hibernate region.
	 * @param cache the cache.
	 * @param settings the Hibernate settings.
	 */
	public TransactionalMemcacheEntityRegionAccessStrategy(MemcacheEntityRegion region, Memcache cache, Settings settings) {
		super( region, settings );
		this.cache = cache;
	}

	/**
	 * Called after an item has been inserted (before the transaction completes),
	 * instead of calling evict().
	 * This method is used by "synchronous" concurrency strategies.
	 * 一个对象被插入后调用(事务完成前)，而不是调用evict，该方法使用同步访问策略
	 * @param key The item key
	 * @param value The item
	 * @param version The item's version value
	 * @return Were the contents of the cache actual changed by this operation?缓存的内容是否真的被这个操作所改变。
	 * @throws CacheException Propogated from underlying {@link org.hibernate.cache.spi.Region}
	 */
	public boolean insert(Object key, Object value, Object version)
			throws CacheException {
		//OptimisticCache? versioning?
		String k = keyStrategy.toKey(getRegion().getName(), 0, key);
		cache.put(k, 0, version);
		return true;
	}
	
	/**
	 * Called after an item has been inserted (after the transaction completes),
	 * instead of calling release().
	 * This method is used by "asynchronous" concurrency strategies.
	 * 一个对象被插入后调用(事务完成后)，而不是调用release，该方法使用异步访问策略
	 * @param key The item key
	 * @param value The item
	 * @param version The item's version value
	 * @return Were the contents of the cache actual changed by this operation?缓存的内容是否真的被这个操作所改变。
	 * @throws CacheException Propogated from underlying {@link org.hibernate.cache.spi.Region}
	 */
	public boolean afterInsert(Object key, Object value, Object version) {
		return false;
	}

	/**
	 * Called after an item has been updated (before the transaction completes),
	 * instead of calling evict(). This method is used by "synchronous" concurrency
	 * strategies.
	 * 一个对象被更新后调用(事务完成前)，而不是调用evict，该方法使用同步访问策略
	 * @param key The item key
	 * @param value The item
	 * @param currentVersion The item's current version value
	 * @param previousVersion The item's previous version value
	 * @return Were the contents of the cache actual changed by this operation?缓存的内容是否真的被这个操作所改变。
	 * @throws CacheException Propogated from underlying {@link org.hibernate.cache.spi.Region}
	 */
	public boolean update(Object key, Object value, Object currentVersion,
						  Object previousVersion) throws CacheException {
		String k = keyStrategy.toKey(getRegion().getName(), 0, key);
		cache.put(k, 0, value);
		return true;
	}
	
	/**
	 * Called after an item has been updated (after the transaction completes),
	 * instead of calling release().  This method is used by "asynchronous"
	 * concurrency strategies.
	 * 一个对象被更新后调用(事务完成后)，而不是调用release，该方法使用异步访问策略
	 * @param key The item key
	 * @param value The item
	 * @param currentVersion The item's current version value
	 * @param previousVersion The item's previous version value
	 * @param lock The lock previously obtained from {@link #lockItem}
	 * @return Were the contents of the cache actual changed by this operation?缓存的内容是否真的被这个操作所改变。
	 * @throws CacheException Propogated from underlying {@link org.hibernate.cache.spi.Region}
	 */
	public boolean afterUpdate(Object key, Object value, Object currentVersion, Object previousVersion, SoftLock lock) {
		return false;
	}

	/**
	 * Attempt to retrieve an object from the cache. Mainly used in attempting
	 * to resolve entities/collections from the second level cache.
	 * 尝试从缓存中查询一个对象。主要被用来从二级缓存中查询实体或集合
	 * @param key The key of the item to be retrieved.
	 * @param txTimestamp a timestamp prior to the transaction start time
	 * @return the cached object or <tt>null</tt>
	 * @throws org.hibernate.cache.CacheException Propogated from underlying {@link org.hibernate.cache.spi.Region}
	 */
	public Object get(Object key, long txTimestamp) throws CacheException {
		String k = keyStrategy.toKey(getRegion().getName(), 0, key);
		return cache.get(k);
	}

	/**
	 * Get the wrapped entity cache region
	 * 获得被包装的实体缓存区域
	 * @return The underlying region。底层区域
	 */
	public EntityRegion getRegion() {
		return region;
	}

	/**
	 * We are going to attempt to update/delete the keyed object. This
	 * method is used by "asynchronous" concurrency strategies.
	 * 我们尝试去更新或删除指定key的对象，这方法使用异步并发策略
	 * <p/>
	 * The returned object must be passed back to {@link #unlockItem}, to release the
	 * lock. Concurrency strategies which do not support client-visible
	 * locks may silently return null.
	 * 返回的对象必须能够使用unlockItem去释放锁。如果并发策略不支持客户端可见的锁定，可以返回null
	 * @param key The key of the item to lock
	 * @param version The item's current version value
	 * @return A representation of our lock on the item; or null.
	 * @throws org.hibernate.cache.CacheException Propogated from underlying {@link org.hibernate.cache.spi.Region}
	 */
	public SoftLock lockItem(Object key, Object version) throws CacheException {
		return null;
	}

	/**
	 * Attempt to cache an object, after loading from the database, explicitly
	 * specifying the minimalPut behavior.
	 * 从数据库加载后，尝试去缓存一个对象。明确的指定minimalPut(最小存入，尽量最小去更新缓存)行为
	 * @param key The item key
	 * @param value The item
	 * @param txTimestamp a timestamp prior to the transaction start time
	 * @param version the item version number
	 * @param minimalPutOverride Explicit minimalPut flag
	 * @return <tt>true</tt> if the object was successfully cached。如果对象被成功缓存，返回true
	 * @throws org.hibernate.cache.CacheException Propogated from underlying {@link org.hibernate.cache.spi.Region}
	 */
	public boolean putFromLoad(Object key, Object value, long txTimestamp,
							   Object version, boolean minimalPutOverride) throws CacheException {
		String k = keyStrategy.toKey(getRegion().getName(), 0, key);
		if ( minimalPutOverride && cache.get(k) != null ) {
			return false;
		}
		//OptimisticCache? versioning?
		cache.put(k, 0, value);
		return true;
	}

	/**
	 * Called after an item has become stale (before the transaction completes).
	 * This method is used by "synchronous" concurrency strategies.
	 * 一个对象变得过时(删除)后被调用。(事务完成前)
	 * @param key The key of the item to remove
	 * @throws org.hibernate.cache.CacheException Propogated from underlying {@link org.hibernate.cache.spi.Region}
	 */
	@Override
	public void remove(Object key) throws CacheException {
		String k = keyStrategy.toKey(getRegion().getName(), 0, key);
		cache.delete(k);
	}

	/**
	 * Called when we have finished the attempted update/delete (which may or
	 * may not have been successful), after transaction completion.  This method
	 * is used by "asynchronous" concurrency strategies.
	 * 当我们尝试去更新或者删除后被调用(可能成功或者没有成功)，事务完成后。该方法使用异步并发策略
	 * @param key The item key
	 * @param lock The lock previously obtained from {@link #lockItem}
	 * @throws org.hibernate.cache.CacheException Propogated from underlying {@link org.hibernate.cache.spi.Region}
	 */
	public void unlockItem(Object key, SoftLock lock) throws CacheException {
		// no-op
	}

}
