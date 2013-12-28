package com.vteba.cache.infinispan;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.infinispan.AdvancedCache;
import org.infinispan.Cache;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.lifecycle.ComponentStatus;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.util.concurrent.NotifyingFuture;

/**
 * 对Infinispan缓存实例的封装。底层委托给org.infinispan.Cache&ltK, V&gt的实例。
 * @author yinlei
 * date 2013-6-23 下午10:59:21
 * @param <K> 缓存Key泛型类型
 * @param <V> 缓存value泛型类型
 */
public class InfinispanCacheImpl<K, V> implements InfinispanCache<K, V>{
	private Cache<K, V> cache;
	
	/**
	 * 使用org.infinispan.Cache&ltK, V&gt的实例构造封装类InfinispanCacheImpl。
	 * @param cache org.infinispan.Cache&ltK, V&gt实例
	 */
	public InfinispanCacheImpl(Cache<K, V> cache) {
		super();
		this.cache = cache;
	}

	public Cache<K, V> getNativeCache() {
		return cache;
	}
	
	/**
	 * @param key
	 * @param value
	 * @return
	 * @see java.util.concurrent.ConcurrentMap#putIfAbsent(java.lang.Object, java.lang.Object)
	 */
	public V putIfAbsent(K key, V value) {
		return cache.putIfAbsent(key, value);
	}

	/**
	 * @param listener
	 * @see org.infinispan.notifications.Listenable#addListener(java.lang.Object)
	 */
	public void addListener(Object listener) {
		cache.addListener(listener);
	}

	/**
	 * 
	 * @see org.infinispan.lifecycle.Lifecycle#start()
	 */
	public void start() {
		cache.start();
	}

	/**
	 * 
	 * @see org.infinispan.lifecycle.Lifecycle#stop()
	 */
	public void stop() {
		cache.stop();
	}

	/**
	 * @param listener
	 * @see org.infinispan.notifications.Listenable#removeListener(java.lang.Object)
	 */
	public void removeListener(Object listener) {
		cache.removeListener(listener);
	}

	/**
	 * @return
	 * @see org.infinispan.notifications.Listenable#getListeners()
	 */
	public Set<Object> getListeners() {
		return cache.getListeners();
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 * @see java.util.concurrent.ConcurrentMap#remove(java.lang.Object, java.lang.Object)
	 */
	public boolean remove(Object key, Object value) {
		return cache.remove(key, value);
	}

	/**
	 * @param key
	 * @param oldValue
	 * @param newValue
	 * @return
	 * @see java.util.concurrent.ConcurrentMap#replace(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	public boolean replace(K key, V oldValue, V newValue) {
		return cache.replace(key, oldValue, newValue);
	}

	/**
	 * @return
	 * @see org.infinispan.api.BasicCache#getName()
	 */
	public String getName() {
		return cache.getName();
	}

	/**
	 * @return
	 * @see org.infinispan.api.BasicCache#getVersion()
	 */
	public String getVersion() {
		return cache.getVersion();
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 * @see java.util.concurrent.ConcurrentMap#replace(java.lang.Object, java.lang.Object)
	 */
	public V replace(K key, V value) {
		return cache.replace(key, value);
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 * @see org.infinispan.api.BasicCache#put(java.lang.Object, java.lang.Object)
	 */
	public V put(K key, V value) {
		return cache.put(key, value);
	}

	/**
	 * @return
	 * @see java.util.Map#size()
	 */
	public int size() {
		return cache.size();
	}

	/**
	 * @param key
	 * @param value
	 * @param lifespan
	 * @param unit
	 * @return
	 * @see org.infinispan.api.BasicCache#put(java.lang.Object, java.lang.Object, long, java.util.concurrent.TimeUnit)
	 */
	public V put(K key, V value, long lifespan, TimeUnit unit) {
		return cache.put(key, value, lifespan, unit);
	}

	/**
	 * @return
	 * @see java.util.Map#isEmpty()
	 */
	public boolean isEmpty() {
		return cache.isEmpty();
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	public boolean containsKey(Object key) {
		return cache.containsKey(key);
	}

	/**
	 * @param key
	 * @param value
	 * @param lifespan
	 * @param unit
	 * @return
	 * @see org.infinispan.api.BasicCache#putIfAbsent(java.lang.Object, java.lang.Object, long, java.util.concurrent.TimeUnit)
	 */
	public V putIfAbsent(K key, V value, long lifespan, TimeUnit unit) {
		return cache.putIfAbsent(key, value, lifespan, unit);
	}

	/**
	 * @param key
	 * @param value
	 * @see org.infinispan.Cache#putForExternalRead(java.lang.Object, java.lang.Object)
	 */
	public void putForExternalRead(K key, V value) {
		cache.putForExternalRead(key, value);
	}

	/**
	 * @param map
	 * @param lifespan
	 * @param unit
	 * @see org.infinispan.api.BasicCache#putAll(java.util.Map, long, java.util.concurrent.TimeUnit)
	 */
	public void putAll(Map<? extends K, ? extends V> map, long lifespan,
			TimeUnit unit) {
		cache.putAll(map, lifespan, unit);
	}

	/**
	 * @param value
	 * @return
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	public boolean containsValue(Object value) {
		return cache.containsValue(value);
	}

	/**
	 * @param key
	 * @param value
	 * @param lifespan
	 * @param unit
	 * @return
	 * @see org.infinispan.api.BasicCache#replace(java.lang.Object, java.lang.Object, long, java.util.concurrent.TimeUnit)
	 */
	public V replace(K key, V value, long lifespan, TimeUnit unit) {
		return cache.replace(key, value, lifespan, unit);
	}

	/**
	 * @param key
	 * @param oldValue
	 * @param value
	 * @param lifespan
	 * @param unit
	 * @return
	 * @see org.infinispan.api.BasicCache#replace(java.lang.Object, java.lang.Object, java.lang.Object, long, java.util.concurrent.TimeUnit)
	 */
	public boolean replace(K key, V oldValue, V value, long lifespan,
			TimeUnit unit) {
		return cache.replace(key, oldValue, value, lifespan, unit);
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Map#get(java.lang.Object)
	 */
	public V get(Object key) {
		return cache.get(key);
	}

	/**
	 * @param key
	 * @param value
	 * @param lifespan
	 * @param lifespanUnit
	 * @param maxIdleTime
	 * @param maxIdleTimeUnit
	 * @return
	 * @see org.infinispan.api.BasicCache#put(java.lang.Object, java.lang.Object, long, java.util.concurrent.TimeUnit, long, java.util.concurrent.TimeUnit)
	 */
	public V put(K key, V value, long lifespan, TimeUnit lifespanUnit,
			long maxIdleTime, TimeUnit maxIdleTimeUnit) {
		return cache.put(key, value, lifespan, lifespanUnit, maxIdleTime,
				maxIdleTimeUnit);
	}

	/**
	 * @param key
	 * @see org.infinispan.Cache#evict(java.lang.Object)
	 */
	public void evict(K key) {
		cache.evict(key);
	}

	/**
	 * @param key
	 * @param value
	 * @param lifespan
	 * @param lifespanUnit
	 * @param maxIdleTime
	 * @param maxIdleTimeUnit
	 * @return
	 * @see org.infinispan.api.BasicCache#putIfAbsent(java.lang.Object, java.lang.Object, long, java.util.concurrent.TimeUnit, long, java.util.concurrent.TimeUnit)
	 */
	public V putIfAbsent(K key, V value, long lifespan,
			TimeUnit lifespanUnit, long maxIdleTime,
			TimeUnit maxIdleTimeUnit) {
		return cache.putIfAbsent(key, value, lifespan, lifespanUnit,
				maxIdleTime, maxIdleTimeUnit);
	}

	/**
	 * @return
	 * @see org.infinispan.Cache#getCacheConfiguration()
	 */
	public Configuration getCacheConfiguration() {
		return cache.getCacheConfiguration();
	}

	/**
	 * @return
	 * @see org.infinispan.Cache#startBatch()
	 */
	public boolean startBatch() {
		return cache.startBatch();
	}

	/**
	 * @param successful
	 * @see org.infinispan.Cache#endBatch(boolean)
	 */
	public void endBatch(boolean successful) {
		cache.endBatch(successful);
	}

	/**
	 * @param map
	 * @param lifespan
	 * @param lifespanUnit
	 * @param maxIdleTime
	 * @param maxIdleTimeUnit
	 * @see org.infinispan.api.BasicCache#putAll(java.util.Map, long, java.util.concurrent.TimeUnit, long, java.util.concurrent.TimeUnit)
	 */
	public void putAll(Map<? extends K, ? extends V> map, long lifespan,
			TimeUnit lifespanUnit, long maxIdleTime,
			TimeUnit maxIdleTimeUnit) {
		cache.putAll(map, lifespan, lifespanUnit, maxIdleTime,
				maxIdleTimeUnit);
	}

	/**
	 * @return
	 * @see org.infinispan.Cache#getCacheManager()
	 */
	public EmbeddedCacheManager getCacheManager() {
		return cache.getCacheManager();
	}

	/**
	 * @return
	 * @see org.infinispan.Cache#getAdvancedCache()
	 */
	public AdvancedCache<K, V> getAdvancedCache() {
		return cache.getAdvancedCache();
	}

	/**
	 * 
	 * @see org.infinispan.Cache#compact()
	 */
	public void compact() {
		cache.compact();
	}

	/**
	 * @param key
	 * @param value
	 * @param lifespan
	 * @param lifespanUnit
	 * @param maxIdleTime
	 * @param maxIdleTimeUnit
	 * @return
	 * @see org.infinispan.api.BasicCache#replace(java.lang.Object, java.lang.Object, long, java.util.concurrent.TimeUnit, long, java.util.concurrent.TimeUnit)
	 */
	public V replace(K key, V value, long lifespan, TimeUnit lifespanUnit,
			long maxIdleTime, TimeUnit maxIdleTimeUnit) {
		return cache.replace(key, value, lifespan, lifespanUnit,
				maxIdleTime, maxIdleTimeUnit);
	}

	/**
	 * @return
	 * @see org.infinispan.Cache#getStatus()
	 */
	public ComponentStatus getStatus() {
		return cache.getStatus();
	}

	/**
	 * @return
	 * @see org.infinispan.Cache#keySet()
	 */
	public Set<K> keySet() {
		return cache.keySet();
	}

	/**
	 * @param key
	 * @param oldValue
	 * @param value
	 * @param lifespan
	 * @param lifespanUnit
	 * @param maxIdleTime
	 * @param maxIdleTimeUnit
	 * @return
	 * @see org.infinispan.api.BasicCache#replace(java.lang.Object, java.lang.Object, java.lang.Object, long, java.util.concurrent.TimeUnit, long, java.util.concurrent.TimeUnit)
	 */
	public boolean replace(K key, V oldValue, V value, long lifespan,
			TimeUnit lifespanUnit, long maxIdleTime,
			TimeUnit maxIdleTimeUnit) {
		return cache.replace(key, oldValue, value, lifespan, lifespanUnit,
				maxIdleTime, maxIdleTimeUnit);
	}

	/**
	 * @return
	 * @see org.infinispan.Cache#values()
	 */
	public Collection<V> values() {
		return cache.values();
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 * @see org.infinispan.api.BasicCache#putAsync(java.lang.Object, java.lang.Object)
	 */
	public NotifyingFuture<V> putAsync(K key, V value) {
		return cache.putAsync(key, value);
	}

	/**
	 * @return
	 * @see org.infinispan.Cache#entrySet()
	 */
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return cache.entrySet();
	}

	/**
	 * @param m
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	public void putAll(Map<? extends K, ? extends V> m) {
		cache.putAll(m);
	}

	/**
	 * @param key
	 * @param value
	 * @param lifespan
	 * @param unit
	 * @return
	 * @see org.infinispan.api.BasicCache#putAsync(java.lang.Object, java.lang.Object, long, java.util.concurrent.TimeUnit)
	 */
	public NotifyingFuture<V> putAsync(K key, V value, long lifespan,
			TimeUnit unit) {
		return cache.putAsync(key, value, lifespan, unit);
	}

	/**
	 * @param key
	 * @param value
	 * @param lifespan
	 * @param lifespanUnit
	 * @param maxIdle
	 * @param maxIdleUnit
	 * @return
	 * @see org.infinispan.api.BasicCache#putAsync(java.lang.Object, java.lang.Object, long, java.util.concurrent.TimeUnit, long, java.util.concurrent.TimeUnit)
	 */
	public NotifyingFuture<V> putAsync(K key, V value, long lifespan,
			TimeUnit lifespanUnit, long maxIdle, TimeUnit maxIdleUnit) {
		return cache.putAsync(key, value, lifespan, lifespanUnit, maxIdle,
				maxIdleUnit);
	}

	/**
	 * 
	 * @see java.util.Map#clear()
	 */
	public void clear() {
		cache.clear();
	}

	/**
	 * @param data
	 * @return
	 * @see org.infinispan.api.BasicCache#putAllAsync(java.util.Map)
	 */
	public NotifyingFuture<Void> putAllAsync(
			Map<? extends K, ? extends V> data) {
		return cache.putAllAsync(data);
	}

	/**
	 * @param data
	 * @param lifespan
	 * @param unit
	 * @return
	 * @see org.infinispan.api.BasicCache#putAllAsync(java.util.Map, long, java.util.concurrent.TimeUnit)
	 */
	public NotifyingFuture<Void> putAllAsync(
			Map<? extends K, ? extends V> data, long lifespan, TimeUnit unit) {
		return cache.putAllAsync(data, lifespan, unit);
	}

	/**
	 * @param data
	 * @param lifespan
	 * @param lifespanUnit
	 * @param maxIdle
	 * @param maxIdleUnit
	 * @return
	 * @see org.infinispan.api.BasicCache#putAllAsync(java.util.Map, long, java.util.concurrent.TimeUnit, long, java.util.concurrent.TimeUnit)
	 */
	public NotifyingFuture<Void> putAllAsync(
			Map<? extends K, ? extends V> data, long lifespan,
			TimeUnit lifespanUnit, long maxIdle, TimeUnit maxIdleUnit) {
		return cache.putAllAsync(data, lifespan, lifespanUnit, maxIdle,
				maxIdleUnit);
	}

	/**
	 * @return
	 * @see org.infinispan.api.BasicCache#clearAsync()
	 */
	public NotifyingFuture<Void> clearAsync() {
		return cache.clearAsync();
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 * @see org.infinispan.api.BasicCache#putIfAbsentAsync(java.lang.Object, java.lang.Object)
	 */
	public NotifyingFuture<V> putIfAbsentAsync(K key, V value) {
		return cache.putIfAbsentAsync(key, value);
	}

	/**
	 * @param key
	 * @param value
	 * @param lifespan
	 * @param unit
	 * @return
	 * @see org.infinispan.api.BasicCache#putIfAbsentAsync(java.lang.Object, java.lang.Object, long, java.util.concurrent.TimeUnit)
	 */
	public NotifyingFuture<V> putIfAbsentAsync(K key, V value,
			long lifespan, TimeUnit unit) {
		return cache.putIfAbsentAsync(key, value, lifespan, unit);
	}

	/**
	 * @param key
	 * @param value
	 * @param lifespan
	 * @param lifespanUnit
	 * @param maxIdle
	 * @param maxIdleUnit
	 * @return
	 * @see org.infinispan.api.BasicCache#putIfAbsentAsync(java.lang.Object, java.lang.Object, long, java.util.concurrent.TimeUnit, long, java.util.concurrent.TimeUnit)
	 */
	public NotifyingFuture<V> putIfAbsentAsync(K key, V value,
			long lifespan, TimeUnit lifespanUnit, long maxIdle,
			TimeUnit maxIdleUnit) {
		return cache.putIfAbsentAsync(key, value, lifespan, lifespanUnit,
				maxIdle, maxIdleUnit);
	}

	/**
	 * @param key
	 * @return
	 * @see org.infinispan.api.BasicCache#remove(java.lang.Object)
	 */
	public V remove(Object key) {
		return cache.remove(key);
	}

	/**
	 * @param key
	 * @return
	 * @see org.infinispan.api.BasicCache#removeAsync(java.lang.Object)
	 */
	public NotifyingFuture<V> removeAsync(Object key) {
		return cache.removeAsync(key);
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 * @see org.infinispan.api.BasicCache#removeAsync(java.lang.Object, java.lang.Object)
	 */
	public NotifyingFuture<Boolean> removeAsync(Object key, Object value) {
		return cache.removeAsync(key, value);
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 * @see org.infinispan.api.BasicCache#replaceAsync(java.lang.Object, java.lang.Object)
	 */
	public NotifyingFuture<V> replaceAsync(K key, V value) {
		return cache.replaceAsync(key, value);
	}

	/**
	 * @param key
	 * @param value
	 * @param lifespan
	 * @param unit
	 * @return
	 * @see org.infinispan.api.BasicCache#replaceAsync(java.lang.Object, java.lang.Object, long, java.util.concurrent.TimeUnit)
	 */
	public NotifyingFuture<V> replaceAsync(K key, V value, long lifespan,
			TimeUnit unit) {
		return cache.replaceAsync(key, value, lifespan, unit);
	}

	/**
	 * @param key
	 * @param value
	 * @param lifespan
	 * @param lifespanUnit
	 * @param maxIdle
	 * @param maxIdleUnit
	 * @return
	 * @see org.infinispan.api.BasicCache#replaceAsync(java.lang.Object, java.lang.Object, long, java.util.concurrent.TimeUnit, long, java.util.concurrent.TimeUnit)
	 */
	public NotifyingFuture<V> replaceAsync(K key, V value, long lifespan,
			TimeUnit lifespanUnit, long maxIdle, TimeUnit maxIdleUnit) {
		return cache.replaceAsync(key, value, lifespan, lifespanUnit,
				maxIdle, maxIdleUnit);
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.Map#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		return cache.equals(o);
	}

	/**
	 * @param key
	 * @param oldValue
	 * @param newValue
	 * @return
	 * @see org.infinispan.api.BasicCache#replaceAsync(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	public NotifyingFuture<Boolean> replaceAsync(K key, V oldValue,
			V newValue) {
		return cache.replaceAsync(key, oldValue, newValue);
	}

	/**
	 * @param key
	 * @param oldValue
	 * @param newValue
	 * @param lifespan
	 * @param unit
	 * @return
	 * @see org.infinispan.api.BasicCache#replaceAsync(java.lang.Object, java.lang.Object, java.lang.Object, long, java.util.concurrent.TimeUnit)
	 */
	public NotifyingFuture<Boolean> replaceAsync(K key, V oldValue,
			V newValue, long lifespan, TimeUnit unit) {
		return cache.replaceAsync(key, oldValue, newValue, lifespan, unit);
	}

	/**
	 * @return
	 * @see java.util.Map#hashCode()
	 */
	public int hashCode() {
		return cache.hashCode();
	}

	/**
	 * @param key
	 * @param oldValue
	 * @param newValue
	 * @param lifespan
	 * @param lifespanUnit
	 * @param maxIdle
	 * @param maxIdleUnit
	 * @return
	 * @see org.infinispan.api.BasicCache#replaceAsync(java.lang.Object, java.lang.Object, java.lang.Object, long, java.util.concurrent.TimeUnit, long, java.util.concurrent.TimeUnit)
	 */
	public NotifyingFuture<Boolean> replaceAsync(K key, V oldValue,
			V newValue, long lifespan, TimeUnit lifespanUnit, long maxIdle,
			TimeUnit maxIdleUnit) {
		return cache.replaceAsync(key, oldValue, newValue, lifespan,
				lifespanUnit, maxIdle, maxIdleUnit);
	}

	/**
	 * @param key
	 * @return
	 * @see org.infinispan.api.BasicCache#getAsync(java.lang.Object)
	 */
	public NotifyingFuture<V> getAsync(K key) {
		return cache.getAsync(key);
	}

	@Deprecated
	public org.infinispan.config.Configuration getConfiguration() {
		return cache.getConfiguration();
	}
}
