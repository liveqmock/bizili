package com.vteba.cache.memcached;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.exception.MemcachedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vteba.cache.hibernate.CacheException;
import com.vteba.cache.hibernate.memcached.Memcache;
import com.vteba.cache.memcached.store.ElementValueComparator;

/**
 * 基于Memcache客户端实现的存储实现
 * @author yinlei
 * date 2012-11-18 下午1:30:16
 */
public class MemcacheStore implements Store {

	private MemcacheClientDelegate client;
	private Memcache memcache;
	private Logger logger = LoggerFactory.getLogger(getClass());
	//private final List<String> keyList = new CopyOnWriteArrayList<String>();
	private final ConcurrentMap<String, String> keyMap = new ConcurrentHashMap<String, String>();
	/**
	 * 构建基于MemcacheClientDelegate的存储实例,委托给MemcachedClient
	 * @param client MemcacheClientDelegate实例
	 */
	public MemcacheStore(MemcacheClientDelegate client) {
		super();
		this.client = client;
	}

	@Override
	public boolean put(Element element) throws CacheException {
		String key = element.getKey();
		try {
			client.set(key, 0, element.getObjectValue());
		} catch (TimeoutException e) {
			logger.info("保存缓存元素[" + element + "]超时异常", e);
		} catch (InterruptedException e) {
			logger.info("保存缓存元素[" + element + "]中断异常", e);
		} catch (MemcachedException e) {
			logger.info("保存缓存元素[" + element + "]异常", e);
		}
		//keyList.add(key);
		keyMap.put(key, key);
		return true;
	}

	@Override
	public void putAll(Collection<Element> elements) throws CacheException {
		for (Element element : elements) {
			put(element);
		}
	}

	@Override
	public Element get(String key) {
		Object object = null;
		try {
			object = client.get(key);
		} catch (TimeoutException e) {
			logger.info("根据key[" + key + "]获得元素超时异常", e);
		} catch (InterruptedException e) {
			logger.info("根据key[" + key + "]获得元素中断异常", e);
		} catch (MemcachedException e) {
			logger.info("根据key[" + key + "]获得元素异常", e);
		}
		if (object != null && !object.toString().equals("")) {
			return new Element(key, object);
		} else {
			return null;
		}
	}

	@Override
	public List<String> getKeys() {
		//return keyList;
		return new ArrayList<String>(keyMap.keySet());
	}

	@Override
	public Element remove(String key) {
		try {
			client.delete(key);
		} catch (TimeoutException e) {
			logger.info("根据key[" + key + "]删除元素超时异常", e);
		} catch (InterruptedException e) {
			logger.info("根据key[" + key + "]删除元素中断异常", e);
		} catch (MemcachedException e) {
			logger.info("根据key[" + key + "]删除元素异常", e);
		}
		//keyList.remove(key);
		keyMap.remove(key);
		return null;
	}

	@Override
	public void removeAll(Collection<String> keys) {
		for (String key : keys) {
			remove(key);
		}
	}

	@Override
	public Element putIfAbsent(Element element) throws NullPointerException {
		Element ele = get(element.getKey());
		if (ele == null) {
			put(element);
			return null;
		}
		return ele;
	}

	@Override
	public Element removeElement(Element element, ElementValueComparator comparator) throws NullPointerException {
		if (element != null) {
			Element ele = get(element.getKey());
			if (ele != null) {
				if (comparator.equals(ele, element)) {
					remove(element.getKey());
					return ele;
				}
			}
		}
		return null;
	}

	@Override
	public boolean replace(Element old, Element element, ElementValueComparator comparator)
			throws NullPointerException, IllegalArgumentException {
		if (old != null && element != null) {
			if (comparator.equals(old, element)) {
				put(element);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsKey(String key) {
		//Element element = get(key);
		String k = keyMap.get(key);
		if (k != null && k.equals(key)) {
			return true;
		}
		return false;
	}

	@Override
	public void expireElements() {
		//for (String key : keyList) {
		for (String key : getKeys()) {
			try {
				client.delete(key);
			} catch (TimeoutException e) {
				logger.info("根据key[" + key + "]删除元素超时异常", e);
			} catch (InterruptedException e) {
				logger.info("根据key[" + key + "]删除元素中断异常", e);
			} catch (MemcachedException e) {
				logger.info("根据key[" + key + "]删除元素异常", e);
			}
			//keyList.remove(key);
			keyMap.remove(key);
		}
		
	}

	@Override
	public Map<String, Element> getAll(Collection<String> keys) {
		Map<String, Element> map = new HashMap<String, Element>();
		for (String key : keys) {
			Element element = get(key);
			map.put(key, element);
		}
		return map;
	}

	@Override
	public MemcacheClientDelegate getMemcacheClientDelegate() {
		return client;
	}

	@Override
	public Memcache getMemcache() {
		return memcache;
	}

	@Override
	public void setMemcache(Memcache memcache) {
		this.memcache = memcache;
	}

}
