package com.vteba.cache.memcached.manager;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.transaction.TransactionManager;

import net.rubyeye.xmemcached.MemcachedClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.thoughtworks.xstream.XStream;
import com.vteba.cache.hibernate.CacheException;
import com.vteba.cache.hibernate.PropertiesHelper;
import com.vteba.cache.hibernate.memcached.Config;
import com.vteba.cache.hibernate.memcached.Memcache;
import com.vteba.cache.hibernate.memcached.MemcacheClientFactory;
import com.vteba.cache.hibernate.memcached.config.Cache;
import com.vteba.cache.hibernate.memcached.config.MemcacheConfiguration;
import com.vteba.cache.hibernate.memcached.xmemcached.Xmemcache;
import com.vteba.cache.memcached.Element;
import com.vteba.cache.memcached.Store;
import com.vteba.cache.memcached.pool.Pool;
import com.vteba.cache.memcached.pool.PoolEvictor;
import com.vteba.cache.memcached.pool.PoolableStore;
import com.vteba.cache.memcached.pool.SizeOfEngine;
import com.vteba.cache.memcached.pool.impl.BalancedAccessOnHeapPoolEvictor;
import com.vteba.cache.memcached.pool.impl.BoundedPool;
import com.vteba.cache.memcached.pool.impl.DefaultSizeOfEngine;
import com.vteba.cache.memcached.store.DefaultElementValueComparator;
import com.vteba.cache.memcached.store.ElementValueComparator;
import com.vteba.cache.memcached.store.MemoryStore;
import com.vteba.cache.memcached.store.MemoryStoreEvictionPolicy;
import com.vteba.cache.memcached.store.compound.ReadWriteCopyStrategy;
import com.vteba.cache.memcached.store.compound.ReadWriteSerializationCopyStrategy;
import com.vteba.cache.memcached.transaction.DelegatingTransactionIDFactory;
import com.vteba.cache.memcached.transaction.ReadCommittedSoftLockFactory;
import com.vteba.cache.memcached.transaction.SoftLockFactory;
import com.vteba.cache.memcached.transaction.SoftLockManager;
import com.vteba.cache.memcached.transaction.SoftLockManagerImpl;
import com.vteba.cache.memcached.transaction.TransactionController;
import com.vteba.cache.memcached.transaction.TransactionIDFactory;
import com.vteba.cache.memcached.transaction.local.JtaLocalTransactionStore;
import com.vteba.cache.memcached.transaction.local.LocalTransactionStore;
import com.vteba.cache.memcached.transaction.local.LocalTransactionStoreDelegete;
import com.vteba.cache.memcached.transaction.manager.DefaultTransactionManagerLookup;
import com.vteba.cache.memcached.transaction.manager.TransactionManagerLookup;
import com.vteba.cache.memcached.transaction.xa.XATransactionStore;
import com.vteba.common.exception.BaseException;

/**
 * xmemcached client manager
 * @author yinlei
 * date 2012-08-31
 */
public class XMemcachedManager{
	private Logger logger = LoggerFactory.getLogger(getClass());
	private volatile TransactionController transactionController;//存储时的事务控制器
	private volatile TransactionIDFactory transactionIDFactory;
	private TransactionManager transactionManager;//事务管理器
	private String cacheManagerName;//缓存管理器名字
	private Properties properties;//hibernate配置的属性，含有memcache的配置属性
	
	/**
	 * spring缓存xmemcache底层客户端
	 */
	private static final ConcurrentMap<String, MemcachedClient> cacheClientMap = new ConcurrentHashMap<String, MemcachedClient>();
	
	/**
	 * XMemcachedManager缓存管理器实例
	 */
	private static final ConcurrentMap<String, XMemcachedManager> cacheManagerMap = new ConcurrentHashMap<String, XMemcachedManager>();
	
	/**
	 * 缓存对应的软锁定管理器
	 */
	private final ConcurrentMap<String, SoftLockManager> softLockManagers = new ConcurrentHashMap<String, SoftLockManager>();
	
	/**
	 * 缓存中所有的缓存实例
	 */
	private static final ConcurrentMap<String, Memcache> cacheMap = new ConcurrentHashMap<String, Memcache>();
	
	/**
	 * 缓存管理器下所管理的缓存
	 */
	private static final ConcurrentMap<String, List<String>> cacheAndManagerMap = new ConcurrentHashMap<String, List<String>>();
	
	public XMemcachedManager() {
		super();
	}

	/**
	 * 构造Memcache管理器
	 * @param transactionManager 事务管理器
	 * @param cacheManagerName 缓存管理器名
	 * @param properties hibernate的properties
	 */
	public XMemcachedManager(TransactionManager transactionManager,
			String cacheManagerName, Properties properties) {
		super();
		this.transactionManager = transactionManager;
		this.cacheManagerName = cacheManagerName;
		this.properties = properties;
		initMemcacheManager();//初始化XMemcacheManager
	}

	/**
	 * 根据缓存名字获取缓存实例，spring cache use
	 * @param cacheName 缓存名字
	 * @author yinlei
	 * date 2012-11-20 下午10:04:05
	 */
	public static MemcachedClient getMemcachedClientInstance(String cacheName) {
		return cacheClientMap.get(cacheName);
	}
	
	/**
	 * 获得缓存的名字数组，spring cache use
	 * @author yinlei
	 * date 2012-11-10 下午9:38:32
	 */
	public String[] getCacheNames() {
		String[] caches = new String[cacheClientMap.size()];
		int i = 0;
		for (Entry<String, MemcachedClient> entry : cacheClientMap.entrySet()) {
			caches[i] = entry.getKey();
			i++;
		}
		return caches;
	}

	/**
	 * 设置/注入缓存实例，spring cache use
	 * @param cacheClient 缓存实例
	 * @author yinlei
	 * date 2012-11-20 下午10:12:38
	 */
	public void setCacheClient(MemcachedClient cacheClient) {
		cacheClientMap.put(cacheClient.getName(), cacheClient);
	}

	public TransactionController getTransactionController() {
		return transactionController;
	}
	
	public static XMemcachedManager getCacheManager(String cacheManagerName){
		return cacheManagerMap.get(cacheManagerName);
	}
	
	/**
	 * 创建事务ID工厂
	 * @author yinlei
	 * date 2012-11-18 上午11:52:13
	 */
	public TransactionIDFactory getOrCreateTransactionIDFactory(){
		if (transactionIDFactory == null) {
			transactionIDFactory = new DelegatingTransactionIDFactory(cacheManagerName);
		}
		return transactionIDFactory;
	}
	
	/**
	 * 初始化缓存管理器及相关的缓存
	 * @author yinlei
	 * date 2012-11-10 下午5:18:08
	 */
	protected void initMemcacheManager() {
		//创建memcache客户端工厂
		Config config = new Config(new PropertiesHelper(this.properties));
		MemcacheClientFactory clientFactory = buildMemcachedClientFactory(config);
		
		Store memcacheStore = null;
		Store finalUnderStore = null;
		
		//首先创建MemcacheStore，因为所有存储都会使用它
		try {
			memcacheStore = clientFactory.createMemcacheStore();
		} catch (Exception e) {
			logger.info("创建基于memcache的存储MemcacheStore失败。", e);
		}
		
		Resource resource = new ClassPathResource(config.getMemcacheConfigFileName());
		InputStream is;
		MemcacheConfiguration memcacheConfig = null;
		
		XStream xstream = new XStream();
		xstream.alias("memcacheConfiguration", MemcacheConfiguration.class);
		xstream.alias("cache", Cache.class);
		
		try {
			is = resource.getInputStream();
			Reader reader = new InputStreamReader(is, "UTF-8");
			memcacheConfig = (MemcacheConfiguration)xstream.fromXML(reader);
		} catch (IOException e) {
			logger.info("读取Memcache配置文件[" + config.getMemcacheConfigFileName() + "]出错");
		}
		
		List<Cache> cacheList = memcacheConfig.getCacheList();
		
		for (Cache cache : cacheList) {
			String cacheName = cache.getCacheName();
			String txMode = cache.getTxMode().name();
			if (txMode == null || txMode.equals("NONE")) {//没有事务，直接使用MemcacheStore
				//实际的底层存储
				finalUnderStore = memcacheStore;
				//构建Memcache实例
				createMemcache(finalUnderStore, cacheName);
			} else if (txMode.equals("LOCAL")) {//本地事务，TransactionController + LocalTransactionStore
				//实际的底层存储
				finalUnderStore = prepareBuildMemcache(memcacheStore, cache);
				
				//创建缓存
				createMemcache(finalUnderStore, cacheName);
			} else if (txMode.equals("JTA")) {//本地jta事务，jta + JtaTransactionStore + LocalTransactionStore
				finalUnderStore = prepareBuildMemcache(memcacheStore, cache);
				createMemcache(finalUnderStore, cacheName);
			} else if (txMode.equals("XA")) {//严格两阶段分布式事务，xa + MemcacheStore
				finalUnderStore = prepareBuildMemcache(memcacheStore, cache);
				createMemcache(finalUnderStore, cacheName);
			}
		}
	}

	/**
	 * 为构建Memcache实例准备参数
	 * @param memcacheStore MemcacheStore实例
	 * @param memcache 
	 * @param cacheName 缓存名
	 * @param evictPolicy 内存移除策略算法
	 * @return
	 * @author yinlei
	 * date 2013-4-4 下午6:34:48
	 */
	private Store prepareBuildMemcache(Store memcacheStore, Cache cache) {
		//事务id工厂
		TransactionIDFactory txIdFactory = getOrCreateTransactionIDFactory();
		this.transactionIDFactory = txIdFactory;
		//事务控制器
		this.transactionController = new TransactionController(txIdFactory, 1000);
		//创建软锁定管理器
		SoftLockManager softLockManager = createSoftLockManager(cache.getCacheName());
		//pool满后的移除策略
		PoolEvictor<PoolableStore> evictor = new BalancedAccessOnHeapPoolEvictor();
		//计算元素大小的引擎
		SizeOfEngine defaultSizeOfEngine = new DefaultSizeOfEngine(6, true);
		//存储池
		Pool<PoolableStore> pool = new BoundedPool(Long.MAX_VALUE, evictor, defaultSizeOfEngine);
		//内存存储
		Store memoryStore = MemoryStore.create(MemoryStoreEvictionPolicy.fromString(cache.getMemoryEvictPolicy()), pool);
		//元素读写复制策略
		ReadWriteCopyStrategy<Element> copyStrategy = new ReadWriteSerializationCopyStrategy();
		//元素比较器
		ElementValueComparator comparator = new DefaultElementValueComparator();
		//本地事务存储
		LocalTransactionStore localStore = new LocalTransactionStore(transactionController, txIdFactory, softLockManager, 
				cache.getCacheName(), memcacheStore, memoryStore, copyStrategy, comparator);
		
		Store finalStore = new LocalTransactionStoreDelegete(localStore);
		
		if (cache.getTxMode().name().equalsIgnoreCase("jta")) {//本地jta事务
			//事务管理器回调接口，其实可以用hibernate的事务管理器代替，以后重构
			TransactionManagerLookup txnManagerLookup = new DefaultTransactionManagerLookup();
			txnManagerLookup.setTransactionManager(transactionManager);
			txnManagerLookup.init();
			//Jta事务存储
			finalStore = new JtaLocalTransactionStore(localStore, memoryStore, txnManagerLookup, transactionController, copyStrategy);
		} else if(cache.getTxMode().name().equalsIgnoreCase("xa")){//jta，严格两阶段提交
			TransactionManagerLookup txnManagerLookup = new DefaultTransactionManagerLookup();
			txnManagerLookup.setTransactionManager(transactionManager);
			txnManagerLookup.init();
			finalStore = new XATransactionStore(txnManagerLookup, softLockManager, txIdFactory, memcacheStore, memoryStore, copyStrategy);
		} 
		return finalStore;
	}

	/**
	 * 构建Memcache实例
	 * @param finalUnderStore 最终的底层存储
	 * @param cacheName 缓存名字
	 * @author yinlei
	 * date 2013-4-4 下午6:27:53
	 */
	private void createMemcache(Store finalUnderStore, String cacheName) {
		Memcache memcache = new Xmemcache(finalUnderStore);//使用底层存储构造Memcache实例
		memcache.setName(cacheName);
		memcache.setTransactionManager(transactionManager);
		memcache.setMemcacheManager(this);
		
		finalUnderStore.setMemcache(memcache);//设置Store所属的Memcache实例
		
		//将缓存放入Map
		if (cacheMap.get(cacheName) != null) {
			throw new BaseException("存在同名的缓存，cacheName=[" + cacheName + "]");
		} else {
			cacheMap.put(cacheName, memcache);
		}
		
		//缓存管理器下所管理的缓存名的对应关系
		List<String> cacheNameList = cacheAndManagerMap.get(cacheManagerName);
		if (cacheNameList == null) {
			cacheNameList = new CopyOnWriteArrayList<String>();
		}
		cacheNameList.add(cacheName);
		cacheAndManagerMap.put(cacheManagerName, cacheNameList);
		
		//缓存管理器
		cacheManagerMap.put(cacheManagerName, this);
	}

	/**
	 * 创建软锁定管理器
	 * @param cache Memcache实例
	 * @return 软锁定管理器
	 * @author yinlei
	 * date 2012-11-18 下午1:21:57
	 */
	protected SoftLockManager createSoftLockManager(String cacheName) {
        SoftLockManager softLockManager;
        SoftLockFactory lockFactory = new ReadCommittedSoftLockFactory();
        softLockManager = softLockManagers.get(cacheName);
        if (softLockManager == null) {
        	softLockManager = new SoftLockManagerImpl(cacheName, lockFactory);
            SoftLockManager old = softLockManagers.putIfAbsent(cacheName, softLockManager);
            if (old != null) {
                softLockManager = old;
            }
        }
        return softLockManager;
    }

	public String getCacheManagerName() {
		return cacheManagerName;
	}

	/**
	 * 获得memcache实例
	 * @author yinlei
	 * date 2012-11-10 下午8:15:59
	 */
	public Memcache getMemcache(String cacheName) {
		return cacheMap.get(cacheName);
	}
	
	/**
	 * 创建MemcacheClientFactory实例
	 * @param config hibernate的配置属性
	 * @author yinlei
	 * date 2012-11-10 下午8:29:46
	 */
	protected MemcacheClientFactory buildMemcachedClientFactory(Config config) {
        String factoryClassName = config.getMemcachedClientFactoryName();
        Constructor<?> constructor;
        try {
            constructor = Class.forName(factoryClassName).getConstructor(PropertiesHelper.class);
        } catch (ClassNotFoundException e) {
            throw new CacheException("Unable to find factory class [" + factoryClassName + "]", e);
        } catch (NoSuchMethodException e) {
            throw new CacheException("Unable to find PropertiesHelper constructor for factory class [" + factoryClassName + "]", e);
        }

        MemcacheClientFactory clientFactory;
        try {
            clientFactory = (MemcacheClientFactory) constructor.newInstance(config.getPropertiesHelper());
        } catch (Exception e) {
            throw new CacheException("Unable to instantiate factory class [" + factoryClassName + "]", e);
        }
        return clientFactory;
    }
	
	/**
	 * 关闭所有缓存管理器下的所有缓存。
	 * @author yinlei
	 * date 2012-11-22 下午8:49:03
	 */
	public static void shutdownAll() {
		//直接关闭缓存即可，不需要经过缓存管理器
		for (Entry<String, Memcache> entry : cacheMap.entrySet()) {
			Memcache mem = entry.getValue();
			if (mem != null) {
				mem.shutdown();
			}
		}
	}
	
	/**
	 * 关闭指定缓存管理器下的所有缓存。由于底层使用相同MemcachedClient，所以会关闭所有的缓存。
	 * @param cacheManagerName缓存管理名
	 * @author yinlei
	 * date 2012-11-22 下午8:51:15
	 */
	public static void shutdown(String cacheManagerName) {
		List<String> cacheNameList = cacheAndManagerMap.get(cacheManagerName);
		for (String cacheName : cacheNameList) {
			Memcache mem = cacheMap.get(cacheName);
			if (mem != null) {
				mem.shutdown();
			}
		}
	}
}
