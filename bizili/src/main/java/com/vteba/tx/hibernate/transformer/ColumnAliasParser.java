package com.vteba.tx.hibernate.transformer;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;

/**
 * 根据sql/hql语句解析sql/hql语句中字段的别名。<br>
 * 已经解析的sql别名，可以被缓存。缓存可被清除和更新。
 * @author yinlei
 * date 2013-06-08
 */
public class ColumnAliasParser {
	private static ConcurrentMap<String, String[]> aliasCache = new ConcurrentHashMap<String, String[]>();
	public static final String MYSQL = "mysql";
	public static final String WHERE = "where";
	public static final String SELECT = "select";
	public static final String FROM = "from";
	private static ColumnAliasParser instance = new ColumnAliasParser();
	
	private ColumnAliasParser() {
		
	}
	
	/**
	 * 获取ColumnAliasParser单例
	 * @return ColumnAliasParser单例
	 * @author yinlei
	 * date 2013-6-12 下午10:22:04
	 */
	public static ColumnAliasParser get() {
		return instance;
	}
	
	/**
	 * 解析sql/hql字段别名，如果没有别名返回字段名
	 * @param sql sql/hql语句
	 * @param isCache 是否缓存
	 * @return 别名String数组
	 */
	public String[] parseColumnAlias(String sql, boolean isCache) {
		if (isCache) {
			String[] columnAlias = aliasCache.get(sql);
			if (columnAlias == null || columnAlias.length == 0) {
				columnAlias = aliasParser(sql);
				aliasCache.put(sql, columnAlias);
			}
			return columnAlias;
		} else {
			return aliasParser(sql);
		}
	}

	private String[] aliasParser(String sql) {
		int from = sql.indexOf(SELECT) + 6;
		int to = sql.indexOf(FROM);
		String sqlAlias = sql.substring(from, to);
		
		String[] columns = StringUtils.split(sqlAlias, ",");
		String[] columnAlias = new String[columns.length];
		int i = 0;
		for (String alias : columns) {
			String[] temp = StringUtils.split(alias, " ");
			String t = temp[temp.length - 1].trim();
			if (t.contains(".")) {
				t = t.substring(t.indexOf(".") + 1);
			}
			columnAlias[i] = t;
			i++;
		}
		return columnAlias;
	}
	
	public String[] parserAlias(String sql) {
		String sqls = StringUtils.substringBefore(sql, WHERE);
		
		SQLStatementParser parser = SQLParserUtils.createSQLStatementParser(sqls, MYSQL);
		SQLSelectStatement sqlSelectStatement = parser.parseSelect();
		SQLSelect sqlSelect = sqlSelectStatement.getSelect();
		SQLSelectQueryBlock sqlSelectQuery = (SQLSelectQueryBlock) sqlSelect.getQuery();
		
		List<SQLSelectItem> sqlSelectItems = sqlSelectQuery.getSelectList();
		int length = sqlSelectItems.size();
		String[] alises = new String[length];
		for (int i = 0; i < length; i++) {
			SQLSelectItem selectItem = sqlSelectItems.get(i);
			if (selectItem.getAlias() != null) {
				alises[i] = selectItem.getAlias();
			} else {
				String column = selectItem.toString();
				if (column.contains(".")) {
					column = column.substring(column.indexOf(".") + 1);
				}
				alises[i] = column;
			}
		}
		return alises;
	}
	
	/**
	 * 删除指定sql的别名缓存
	 * @param sqlKey sql语句
	 * @return sqlKey对应的以前的值
	 * @author yinlei
	 * date 2013-6-12 下午10:18:11
	 */
	public String[] clear(String sqlKey) {
		return aliasCache.remove(sqlKey);
	}
	
	/**
	 * 更新sql别名缓存值
	 * @param sqlKey sql语句
	 * @param sqlAlias sql别名数组
	 * @return sqlKey对应的以前的值
	 * @author yinlei
	 * date 2013-6-12 下午10:18:19
	 */
	public String[] replace(String sqlKey, String[] sqlAlias) {
		return aliasCache.replace(sqlKey, sqlAlias);
	}
	
	public boolean isQueryAll(String hql) {
		String[] columnAlias = ColumnAliasParser.get().parseColumnAlias(hql, true);
		
		int start = hql.indexOf("from") + 4;
		int end = hql.indexOf("where");
		String tableNames = null;
        if (end == -1) {
            tableNames = hql.substring(start);
        } else {
            tableNames = hql.substring(start, end);
        }  
		String[] tables = StringUtils.split(tableNames, ",");
		
		boolean queryAll = false;
		label:
		for (int i = 0; i < tables.length; i++) {
			String[] temp = StringUtils.split(tables[i], " ");
			String t = temp[temp.length - 1].trim();
			for (String alias : columnAlias) {
				if (t.equals(alias)) {
					queryAll = true;
					break label;
				}
			}
		}
//		if (queryAll) {
//			ColumnAliasParser.getInstance().clear(sql);
//		}
		return queryAll;
	}
}
