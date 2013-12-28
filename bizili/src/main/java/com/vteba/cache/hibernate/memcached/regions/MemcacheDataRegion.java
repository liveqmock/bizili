package com.vteba.cache.hibernate.memcached.regions;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vteba.cache.hibernate.KeyStrategy;
import com.vteba.cache.hibernate.Sha1KeyStrategy;
import com.vteba.cache.hibernate.memcached.Memcache;
import com.vteba.cache.hibernate.memcached.access.CacheHelper;
import com.vteba.cache.hibernate.memcached.strategy.MemcacheAccessStrategyFactory;

/**
 * 特定于memcache的数据区域实现。
 * <p/>
 * 该类是所有基于memcache的hibernate的缓存区域的最终超类。
 */
public abstract class MemcacheDataRegion implements Region {
	protected final TransactionManager transactionManager;
	private enum InvalidateState { INVALID, CLEARING, VALID };
	protected final Object invalidationMutex = new Object();
	protected final AtomicReference<InvalidateState> invalidateState = new AtomicReference<InvalidateState>(InvalidateState.VALID);
	private Logger log = LoggerFactory.getLogger(getClass());
	/**
	 * 缓存锁定超时，配置属性
	 */
    private static final String CACHE_LOCK_TIMEOUT_PROPERTY = "memcache.hibernate.cache_lock_timeout";
    /**
     * 默认缓存锁定超时时间，1分钟
     */
    private static final int DEFAULT_CACHE_LOCK_TIMEOUT = 60000;
    /**
     * 缓存主键生成策略
     */
    protected KeyStrategy keyStrategy = new Sha1KeyStrategy();

    /**
     * hibernate数据区域背后的Memcache实例
     */
    protected final Memcache cache;
    /**
     * 缓存区域名
     */
    protected final String regionName;
    /**
     * {@link MemcacheAccessStrategyFactory}.被用来创建多种多样的访问策略.
     */
    protected final MemcacheAccessStrategyFactory accessStrategyFactory;
    /**
     * 缓存锁定超时
     */
    private final int cacheLockTimeout;

    /**
     * 使用给定的memcache实例，创建hibernate数据区域
     */
    public MemcacheDataRegion(MemcacheAccessStrategyFactory accessStrategyFactory, Memcache cache, 
    		String regionName, Properties properties,TransactionManager transactionManager) {
        this.accessStrategyFactory = accessStrategyFactory;
        this.cache = cache;
        String timeout = properties.getProperty(
                CACHE_LOCK_TIMEOUT_PROPERTY,
                Integer.toString( DEFAULT_CACHE_LOCK_TIMEOUT )
        );
        this.cacheLockTimeout = 5 * Integer.decode( timeout );
        this.regionName = regionName;
        this.transactionManager = transactionManager;
    }

    /**
     * {@inheritDoc}。获得缓存区域名字。regionName就是在实体注解上的region
     */
    public String getName() {
    	//暂时这么实现，regionName就是在实体注解上的region
        return regionName;
    }

    /**
     * {@inheritDoc}
     * 。sessionFactory关闭时，是否关闭memcached。暂时不做任何操作。
     */
    public void destroy() throws CacheException {
        //sessionFactory关闭时，是否关闭memcached。暂时不做任何操作
    }

    /**
     * {@inheritDoc}
     * 不支持返回-1
     */
    public long getSizeInMemory() {
        return -1;
    }

    /**
     * {@inheritDoc}
     * 不支持返回-1
     */
    public long getElementCountInMemory() {
        return -1;
    }

    /**
     * {@inheritDoc}
     * 不支持返回-1
     */
    public long getElementCountOnDisk() {
        return -1;
    }

    /**
     * {@inheritDoc}。返回空集合。
     */
    public Map<Object, Object> toMap() {
        return Collections.emptyMap();
    }

    /**
     * {@inheritDoc}
     */
    public long nextTimestamp() {
    	return System.currentTimeMillis() / 100;
    }

    /**
     * {@inheritDoc}。获得缓存锁定超时时间
     */
    public int getTimeout() {
        return cacheLockTimeout;
    }

    /**
     * 返回hibernate数据区域后的Memcache实例
     */
    public Memcache getCache() {
        return cache;
    }

    /**
     * 如果缓存区域包含给定key的值，返回true
     */
    public boolean contains(Object key) {
        return cache.get(keyStrategy.toKey(regionName, 0, key)) == null ? false : true;
    }
    
    /**
     * 如果当前事务不为null，返回当前事务，否则返回当前线程
     * @return
     * @author yinlei
     * date 2012-10-27 下午11:12:18
     */
    public Object getOwnerForPut() {
        Transaction tx = null;
        try {
            if (transactionManager != null) {
                tx = transactionManager.getTransaction();
            }
        } catch (SystemException se) {
            throw new CacheException("Could not obtain transaction", se);
        }
        return tx == null ? Thread.currentThread() : tx;
     }

     /**
      * 告诉TransactionManager挂起任何正在运行的事务
      * @return 被挂起的事务，如果没有事务返回null
      */
     public Transaction suspend() {
         Transaction tx = null;
         try {
             if (transactionManager != null) {
                 tx = transactionManager.suspend();
             }
         } catch (SystemException se) {
             throw new CacheException("Could not suspend transaction", se);
         }
         return tx;
     }

     /**
      * 告诉TransactionManager去恢复指定的事务
      * @param tx 被挂起的事务，可能为null
      */
     public void resume(Transaction tx) {
         try {
             if (tx != null) {
            	 transactionManager.resume(tx);
             }
         } catch (Exception e) {
             throw new CacheException("Could not resume transaction", e);
         }
     }

     public TransactionManager getTransactionManager() {
        return transactionManager;
     }
     
     public boolean checkValid() {
         boolean valid = isValid();
         if (!valid) {
            synchronized (invalidationMutex) {
               if (invalidateState.compareAndSet(InvalidateState.INVALID, InvalidateState.CLEARING)) {
                  Transaction tx = suspend();
                  try {
						CacheHelper.withinTx(cache.getTransactionManager(),
								new Callable<Void>() {
									@Override
									public Void call() throws Exception {
										// cacheAdapter.withFlags(FlagAdapter.CACHE_MODE_LOCAL,
										// FlagAdapter.ZERO_LOCK_ACQUISITION_TIMEOUT).clear();
										return null;
									}
								});
                     // Clear region in a separate transaction
//                     cache.withinTx(new Callable<Void>() {
//                        @Override
//                        public Void call() throws Exception {
//                           //cacheAdapter.withFlags(FlagAdapter.CACHE_MODE_LOCAL,
//                           //      FlagAdapter.ZERO_LOCK_ACQUISITION_TIMEOUT).clear();
//                           return null;
//                        }
//                     });
                     invalidateState.compareAndSet(InvalidateState.CLEARING, InvalidateState.VALID);
                  }
                  catch (Exception e) {
                     if (log.isTraceEnabled()) {
                        log.trace("Could not invalidate region: " + e.getLocalizedMessage());
                     }
                  }
                  finally {
                     resume(tx);
                  }
               }
            }
            valid = isValid();
         }
         
         return valid;
      }

      protected boolean isValid() {
         return invalidateState.get() == InvalidateState.VALID;
      }
}
