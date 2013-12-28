package com.vteba.cache.hibernate.memcached.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.TransactionManager;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.thoughtworks.xstream.XStream;
import com.vteba.cache.memcached.transaction.manager.DefaultTransactionManagerLookup;

/**
 * Memcache缓存配置
 * @author yinlei
 * date 2013-4-4 下午12:46:57
 */
public class MemcacheConfiguration {
	private List<Cache> cacheList;
	private String transactionProviderClass;
	private TransactionManager transactionManager;
	
	public List<Cache> getCacheList() {
		return cacheList;
	}

	public void setCacheList(List<Cache> cacheList) {
		this.cacheList = cacheList;
	}

	public String getTransactionProviderClass() {
		return transactionProviderClass;
	}

	public void setTransactionProviderClass(String transactionProviderClass) {
		this.transactionProviderClass = transactionProviderClass;
	}

	public TransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	
	public static void main(String aa[]) {
		List<Cache> cacheList = new ArrayList<Cache>();
		
		Cache cache = new Cache();
		cache.setCacheName("cache1");
		cache.setTxMode("LOCAL");
		cacheList.add(cache);
		
		Cache cache2 = new Cache();
		cache2.setCacheName("cache2");
		cache2.setTxMode("LOCAL");
		cacheList.add(cache2);
		
		MemcacheConfiguration config = new MemcacheConfiguration();
		config.setCacheList(cacheList);
		
		config.setTransactionProviderClass(DefaultTransactionManagerLookup.class.getName());
		
		XStream xstream = new XStream();
		xstream.alias("memcacheConfiguration", MemcacheConfiguration.class);
		xstream.alias("cache", Cache.class);
		
		System.out.println(xstream.toXML(config));
		
		Resource resource = new ClassPathResource("memcache-config.xml");
		InputStream is;
		MemcacheConfiguration memConfig = null;
		try {
			is = resource.getInputStream();
			Reader reader = new InputStreamReader(is, "UTF-8");
			
			memConfig = (MemcacheConfiguration)xstream.fromXML(reader);
		} catch (IOException e) {
			
		}
		memConfig.getCacheList();
	}
}
