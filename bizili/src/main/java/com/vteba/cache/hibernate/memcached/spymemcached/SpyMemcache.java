package com.vteba.cache.hibernate.memcached.spymemcached;

import java.util.Collection;
import java.util.Map;

import javax.transaction.TransactionManager;

import net.spy.memcached.MemcachedClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vteba.cache.hibernate.memcached.LoggingMemcacheExceptionHandler;
import com.vteba.cache.hibernate.memcached.Memcache;
import com.vteba.cache.hibernate.memcached.MemcacheExceptionHandler;
import com.vteba.cache.memcached.Element;
import com.vteba.cache.memcached.Store;
import com.vteba.cache.memcached.manager.XMemcachedManager;
import com.vteba.util.cryption.CryptionUtils;

/**
 * hibernate 二级缓存，使用SpyMemcache
 * @author yinlei 
 * date 2012-8-18 下午11:00:56
 */
public class SpyMemcache implements Memcache {

    private static final Logger log = LoggerFactory.getLogger(SpyMemcache.class);
    private MemcacheExceptionHandler exceptionHandler = new LoggingMemcacheExceptionHandler();

    private final MemcachedClient memcachedClient;
    private TransactionManager transactionManager;
    
    public SpyMemcache(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }

    public Object get(String key) {
        try {
            log.debug("MemcachedClient.get({})", key);
            return memcachedClient.get(key);
        } catch (Exception e) {
            exceptionHandler.handleErrorOnGet(key, e);
        }
        return null;
    }

    public Map<String, Object> getMulti(String... keys) {
        try {
            return memcachedClient.getBulk(keys);
        } catch (Exception e) {
            exceptionHandler.handleErrorOnGet(CryptionUtils.join(keys, ", "), e);
        }
        return null;
    }

    public void put(String key, int cacheTimeSeconds, Object o) {
        log.debug("MemcachedClient.set({})", key);
        try {
            memcachedClient.set(key, cacheTimeSeconds, o);
        } catch (Exception e) {
            exceptionHandler.handleErrorOnSet(key, cacheTimeSeconds, o, e);
        }
    }

    public void delete(String key) {
        try {
            memcachedClient.delete(key);
        } catch (Exception e) {
            exceptionHandler.handleErrorOnDelete(key, e);
        }
    }

    public void incr(String key, int factor, int startingValue) {
        try {
            memcachedClient.incr(key, factor, startingValue);
        } catch (Exception e) {
            exceptionHandler.handleErrorOnIncr(key, factor, startingValue, e);
        }
    }

    public void shutdown() {
        log.debug("Shutting down spy MemcachedClient");
        memcachedClient.shutdown();
    }

    public void setExceptionHandler(MemcacheExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }
    
    @Override
	public void setTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	
	@Override
	public TransactionManager getTransactionManager() {
		return transactionManager;
	}

	@Override
	public XMemcachedManager getCacheManager() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMemcacheManager(XMemcachedManager memcacheManager) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public void setStore(Store store) {
//		// TODO Auto-generated method stub
//		
//	}

	@Override
	public Store getStore() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String cacheName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean contains(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void putAll(Collection<Element> elements) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean update(String key, Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void deleteAll(Collection<String> keys) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public ElementValueComparator getElementValueComparator() {
//		// TODO Auto-generated method stub
//		return null;
//	}
    
}
