package com.vteba.cache.infinispan;

import org.infinispan.Cache;

/**
 * 对Infinispan缓存接口实例的封装。隔离Infinispan API。
 * @author yinlei
 * date 2013-6-23 下午7:05:52
 * @param <K> 缓存Key泛型类型
 * @param <V> 缓存value泛型类型
 */
public interface InfinispanCache<K, V> extends Cache<K, V> {
	/**
	 * 获得内部原生的缓存实例。
	 * @return org.infinispan.Cache&ltK, V&gt实例
	 * @author yinlei
	 * date 2013-6-23 下午9:56:29
	 */
	public Cache<K, V> getNativeCache();
}
