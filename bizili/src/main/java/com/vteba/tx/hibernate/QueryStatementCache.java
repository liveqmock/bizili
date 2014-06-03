package com.vteba.tx.hibernate;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 查询语句缓存
 * @author yinlei
 * date 2013-6-14 上午10:08:11
 */
public class QueryStatementCache {
	private static ConcurrentMap<String, QueryStatement> queryStatementCache = new ConcurrentHashMap<String, QueryStatement>();
	private static QueryStatementCache instance = new QueryStatementCache();
	private QueryStatementCache() {}
	
	public static QueryStatementCache getInstance() {
		return instance;
	}
	
	/**
	 * 将查询语句存入缓存
	 * @param key sql/hql语句
	 * @param statement sql/hql语句解析结果
	 * @return key对应的以前的值
	 */
	public QueryStatement put(String key, QueryStatement statement) {
		return queryStatementCache.put(key, statement);
	}
	
	/**
	 * 删除查询语句
	 * @param key sql/hql语句
	 * @return key对应的以前查询语句
	 */
	public QueryStatement remove(String key) {
		return queryStatementCache.remove(key);
	}
	
	/**
	 * 获取查询语句
	 * @param key sql/hql语句
	 * @return key对应的查询语句或null
	 */
	public QueryStatement get(String key) {
		return queryStatementCache.get(key);
	}
}
