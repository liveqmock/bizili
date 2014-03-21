package com.vteba.tm.jdbc.spring;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Spring RowMapper 栏位映射信息缓存。
 * @author yinlei
 * date 2013-6-25 上午10:08:11
 */
public class RowMapInfoCache {
	private static ConcurrentMap<String, RowMapInfo> rowMapInfoCache = new ConcurrentHashMap<String, RowMapInfo>();
	private static RowMapInfoCache instance = new RowMapInfoCache();
	private RowMapInfoCache() {}
	
	/**
	 * 获得RowMapInfoCache单例。
	 * @author yinlei
	 * date 2013-6-25 下午7:43:22
	 */
	public static RowMapInfoCache getInstance() {
		return instance;
	}
	
	/**
	 * 将RowMapInfo存入缓存
	 * @param key sql/hql语句
	 * @param rowMapInfo RowMapInfo信息
	 * @return key对应的以前的值
	 */
	public RowMapInfo put(String key, RowMapInfo rowMapInfo) {
		return rowMapInfoCache.put(key, rowMapInfo);
	}
	
	/**
	 * 删除RowMapInfo
	 * @param key sql/hql语句
	 * @return key对应的以前的RowMapInfo
	 */
	public RowMapInfo remove(String key) {
		return rowMapInfoCache.remove(key);
	}
	
	/**
	 * 获取查询语句
	 * @param key sql/hql语句
	 * @return key对应的RowMapInfo或null
	 */
	public RowMapInfo get(String key) {
		return rowMapInfoCache.get(key);
	}
}
