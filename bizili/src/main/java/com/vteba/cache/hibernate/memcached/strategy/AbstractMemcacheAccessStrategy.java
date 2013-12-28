package com.vteba.cache.hibernate.memcached.strategy;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.access.SoftLock;
import org.hibernate.cfg.Settings;

import com.vteba.cache.hibernate.KeyStrategy;
import com.vteba.cache.hibernate.Sha1KeyStrategy;
import com.vteba.cache.hibernate.memcached.regions.MemcacheTransactionalDataRegion;

/**
 * Ultimate superclass for all Memcache specific Hibernate AccessStrategy implementations.
 * 所有特定于Memcache的hibernate访问策略实现的最终超类。
 * @param <T> type of the enclosed region
 */
public abstract class AbstractMemcacheAccessStrategy<T extends MemcacheTransactionalDataRegion> {
	/**
	 * 主键生成策略
	 */
	protected KeyStrategy keyStrategy = new Sha1KeyStrategy();
	/**
	 * The wrapped Hibernate cache region.
	 * 被包装的hibernate缓存区域
	 */
	protected final T region;
	/**
	 * The settings for this persistence unit。
	 * 持久化单元的配置
	 */
	protected final Settings settings;

	/**
	 * Create an access strategy wrapping the given region.
	 * 创建一个访问策略，去包装给定的缓存区域
	 */
	AbstractMemcacheAccessStrategy(T region, Settings settings) {
		this.region = region;
		this.settings = settings;
	}

	/**
	 * This method is a placeholder for method signatures supplied by interfaces pulled in further down the class
	 * hierarchy。
	 * 这个方法是，为将来下层类层次结构提供的方法签名的一个占位符。
	 * Attempt to cache an object, after loading from the database.
	 * 从数据库加载后，尝试去缓存一个对象。
	 * @param key The item key
	 * @param value The item
	 * @param txTimestamp a timestamp prior to the transaction start time
	 * @param version the item version number
	 * @return <tt>true</tt> if the object was successfully cached。如果对象被成功的缓存了，返回true
	 * @throws org.hibernate.cache.CacheException Propogated from underlying {@link org.hibernate.cache.spi.Region}
	 * @see org.hibernate.cache.spi.access.EntityRegionAccessStrategy#putFromLoad(java.lang.Object, java.lang.Object, long, java.lang.Object)
	 * @see org.hibernate.cache.spi.access.CollectionRegionAccessStrategy#putFromLoad(java.lang.Object, java.lang.Object, long, java.lang.Object)
	 */
	public final boolean putFromLoad(Object key, Object value, long txTimestamp, Object version) throws CacheException {
		return putFromLoad( key, value, txTimestamp, version, settings.isMinimalPutsEnabled() );
	}

	/**
	 * This method is a placeholder for method signatures supplied by interfaces pulled in further down the class
	 * hierarchy。
	 * 这个方法是，为将来下层类层次结构提供的方法签名的一个占位符。
	 * Attempt to cache an object, after loading from the database, explicitly
	 * specifying the minimalPut behavior.
	 * 从数据库加载后，尝试去缓存一个对象。明确的指定minimalPut(最小存入)行为
	 * @param key The item key
	 * @param value The item
	 * @param txTimestamp a timestamp prior to the transaction start time
	 * @param version the item version number
	 * @param minimalPutOverride Explicit minimalPut flag
	 * @return <tt>true</tt> if the object was successfully cached。对象被成功缓存，返回true
	 * @throws org.hibernate.cache.CacheException Propogated from underlying {@link org.hibernate.cache.spi.Region}
	 * @see org.hibernate.cache.spi.access.EntityRegionAccessStrategy#putFromLoad(java.lang.Object, java.lang.Object, long, java.lang.Object, boolean)
	 * @see org.hibernate.cache.spi.access.CollectionRegionAccessStrategy#putFromLoad(java.lang.Object, java.lang.Object, long, java.lang.Object, boolean)
	 */
	public abstract boolean putFromLoad(Object key, Object value, long txTimestamp, Object version, boolean minimalPutOverride)
			throws CacheException;

	/**
	 * Region locks are not supported.
	 * 区域锁定是不支持的
	 * @return <code>null</code>
	 * Lock the entire region。锁定整个缓存区域
	 * @see org.hibernate.cache.spi.access.EntityRegionAccessStrategy#lockRegion()
	 * @see org.hibernate.cache.spi.access.CollectionRegionAccessStrategy#lockRegion()
	 */
	public final SoftLock lockRegion() {
		return null;
	}

	/**
	 * Region locks are not supported - perform a cache clear as a precaution。
	 * 区域锁定时不支持的。作为预防，将缓存clear
	 * Called after we have finished the attempted invalidation of the entire
	 * region。我们完成尝试失效整个区域后调用
	 * @see org.hibernate.cache.spi.access.EntityRegionAccessStrategy#unlockRegion(org.hibernate.cache.spi.access.SoftLock)
	 * @see org.hibernate.cache.spi.access.CollectionRegionAccessStrategy#unlockRegion(org.hibernate.cache.spi.access.SoftLock)
	 */
	public final void unlockRegion(SoftLock lock) throws CacheException {
		region.clear();
	}

	/**
	 * A no-op since this is an asynchronous cache access strategy.
	 * Called after an item has become stale (before the transaction completes).
	 * This method is used by "synchronous" concurrency strategies.该方法被同步并发策略使用
	 * @see org.hibernate.cache.spi.access.EntityRegionAccessStrategy#remove(java.lang.Object)
	 * @see org.hibernate.cache.spi.access.CollectionRegionAccessStrategy#remove(java.lang.Object)
	 */
	public void remove(Object key) throws CacheException {
		region.remove(key);
	}

	/**
	 * Called to evict data from the entire region
	 *
	 * @throws CacheException Propogated from underlying {@link org.hibernate.cache.spi.Region}
	 * @see org.hibernate.cache.spi.access.EntityRegionAccessStrategy#removeAll()
	 * @see org.hibernate.cache.spi.access.CollectionRegionAccessStrategy#removeAll()
	 */
	public final void removeAll() throws CacheException {
		region.clear();
	}

	/**
	 * Remove the given mapping without regard to transactional safety。
	 * Forcibly evict an item from the cache immediately without regard for transaction
	 * isolation.
	 * 强制立即移除给定的对象，不管是否事务安全
	 * @see org.hibernate.cache.spi.access.EntityRegionAccessStrategy#evict(java.lang.Object)
	 * @see org.hibernate.cache.spi.access.CollectionRegionAccessStrategy#evict(java.lang.Object)
	 */
	public final void evict(Object key) throws CacheException {
		region.remove( key );
	}

	/**
	 * Remove all mappings without regard to transactional safety。
	 * Forcibly evict all items from the cache immediately without regard for transaction
	 * isolation.
	 * 移除所有的对象，不管是否事务安全
	 * @see org.hibernate.cache.spi.access.EntityRegionAccessStrategy#evictAll()
	 * @see org.hibernate.cache.spi.access.CollectionRegionAccessStrategy#evictAll()
	 */
	public final void evictAll() throws CacheException {
		region.clear();
	}
}