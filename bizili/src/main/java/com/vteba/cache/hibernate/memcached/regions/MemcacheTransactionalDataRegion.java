package com.vteba.cache.hibernate.memcached.regions;

import java.util.Properties;

import javax.transaction.TransactionManager;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.CacheDataDescription;
import org.hibernate.cache.spi.TransactionalDataRegion;
import org.hibernate.cfg.Settings;

import com.vteba.cache.hibernate.memcached.Memcache;
import com.vteba.cache.hibernate.memcached.concurrent.CacheLockProvider;
import com.vteba.cache.hibernate.memcached.concurrent.LockType;
import com.vteba.cache.hibernate.memcached.concurrent.StripedReadWriteLockSync;
import com.vteba.cache.hibernate.memcached.strategy.MemcacheAccessStrategyFactory;

/**
 * 基于Memcache的TransactionalDataRegion.
 * <p/>
 * 这是实体和集合区域的普通超类
 */
public abstract class MemcacheTransactionalDataRegion extends MemcacheDataRegion implements TransactionalDataRegion {
	
	private static final int LOCAL_LOCK_PROVIDER_CONCURRENCY = 128;
	/**
	 * 缓存锁定提供者
	 */
	private CacheLockProvider lockProvider = new StripedReadWriteLockSync(LOCAL_LOCK_PROVIDER_CONCURRENCY);
	/**
	 * Hibernate settings associated with the persistence unit.
	 */
	protected final Settings settings;

	/**
	 * Metadata associated with the objects stored in the region.
	 */
	protected final CacheDataDescription metadata;

	/**
	 * Construct an transactional Hibernate cache region around the given
	 * Memcache instance.
	 */
	public MemcacheTransactionalDataRegion(
			MemcacheAccessStrategyFactory accessStrategyFactory,
			Memcache cache, String regionName, Settings settings,
			CacheDataDescription metadata, Properties properties,TransactionManager transactionManager) {
		super(accessStrategyFactory, cache, regionName, properties, transactionManager);
		this.settings = settings;
		this.metadata = metadata;
	}

	/**
	 * Return the hibernate settings
	 *
	 * @return settings
	 */
	public Settings getSettings() {
		return settings;
	}

	/**
	 * {@inheritDoc}。是否是事务感知的。
	 */
	public boolean isTransactionAware() {
		return transactionManager != null;
	}

	/**
	 * {@inheritDoc}
	 */
	public CacheDataDescription getCacheDataDescription() {
		return metadata;
	}
	
	/**
	 * 返回key对应的value，没有返回null
	 */
	public final Object get(Object key) {
		String name = getName();
		String k = keyStrategy.toKey(name, 0, key);
		Object object = cache.get(k);
		return object;
	}

	/**
	 * Map the given value to the given key, replacing any existing mapping for this key
	 * 将value映射到key，已经存在key对应的value，取代它
	 */
	public final void put(Object key, Object value) throws CacheException {
		cache.put(keyStrategy.toKey(getName(), 0, key), 0, value);
	}

	/**
	 * Remove the mapping for this key (if any exists).
	 */
	public final void remove(Object key) throws CacheException {
		cache.delete(keyStrategy.toKey(getName(), 0, key));
	}

	/**
	 * Remove all mapping from this cache region。
	 * 暂不支持。
	 */
	public final void clear() throws CacheException {
		
	}

	/**
	 * Attempts to write lock the mapping for the given key.
	 */
	public final void writeLock(Object key) {
		try {
			lockProvider.getSyncForKey( key ).lock( LockType.WRITE );
		} catch (com.vteba.cache.hibernate.CacheException e ) {
			throw new CacheException( e );
		}
	}

	/**
	 * Attempts to write unlock the mapping for the given key.
	 */
	public final void writeUnlock(Object key) {
		try {
			lockProvider.getSyncForKey( key ).unlock( LockType.WRITE );
		} catch (com.vteba.cache.hibernate.CacheException e ) {
			throw new CacheException( e );
		}
	}

	/**
	 * Attempts to read lock the mapping for the given key.
	 */
	public final void readLock(Object key) {
		try {
			lockProvider.getSyncForKey( key ).lock( LockType.WRITE );
		} catch (com.vteba.cache.hibernate.CacheException e ) {
			throw new CacheException(e);
		}
	}

	/**
	 * Attempts to read unlock the mapping for the given key.
	 */
	public final void readUnlock(Object key) {
		try {
			lockProvider.getSyncForKey( key ).unlock( LockType.WRITE );
		} catch (com.vteba.cache.hibernate.CacheException e) {
			throw new CacheException( e );
		}
	}

	/**
	 * Returns <code>true</code> if the locks used by the locking methods of this region are the independent of the cache.
	 * <p/>
	 * Independent locks are not locked by the cache when the cache is accessed directly.  This means that for an independent lock
	 * lock holds taken through a region method will not block direct access to the cache via other means.
	 */
	public final boolean locksAreIndependentOfCache() {
		return lockProvider instanceof StripedReadWriteLockSync;
	}
}
