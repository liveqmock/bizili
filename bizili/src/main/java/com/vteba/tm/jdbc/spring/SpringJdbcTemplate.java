package com.vteba.tm.jdbc.spring;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;

/**
 * Spring JdbcTemplate简单封装，封装了JdbcTemplate和NamedParameterJdbcTemplate。<br/>
 * 如果要使用底层方法，可以获得相应的JdbcTemplate进行操作。
 * @author yinlei
 * date 2013-5-11 下午2:18:00
 */
public class SpringJdbcTemplate {
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	private DataSource dataSource;
	
	public SpringJdbcTemplate() {
		super();
	}
	
	/**
	 * 使用数据源实例化SpringJdbcTemplate
	 * @param dataSource java.sql.DataSource实例
	 */
	public SpringJdbcTemplate(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
		initTemplate();
	}
	
	/**
	 * 初始化JdbcTemplate模版
	 * @author yinlei
	 * date 2013-5-11 下午2:35:15
	 */
	private void initTemplate() {
		jdbcTemplate = new JdbcTemplate(dataSource);
		namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		initTemplate();
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	public NamedParameterJdbcTemplate getNamedJdbcTemplate() {
		return namedJdbcTemplate;
	}

	/**
	 * 批量更新数据库，一般用于insert、update、delete。多组参数。
	 * @param sql sql语句，以？为占位符
	 * @param batchArgs sql参数
	 * @return 更新成功的条数
	 * @author yinlei
	 * date 2013-5-12 下午5:09:28
	 */
	public int[] batchUpdate(String sql, List<Object[]> batchArgs) {
		return jdbcTemplate.batchUpdate(sql, batchArgs);
	}

	/**
	 * 批量更新数据库，一般用于insert、update、delete
	 * @param sql sql语句，以？为占位符
	 * @return 更新成功的条数
	 * @throws DataAccessException
	 * @author yinlei
	 * date 2013-5-12 下午5:10:09
	 */
	public int[] batchUpdate(String[] sql) throws DataAccessException {
		return jdbcTemplate.batchUpdate(sql);
	}

	/**
	 * 一般用于调用存储过程和函数
	 * @param csc 回调语句创建器
	 * @param sqlParamList sql参数
	 * @return 成功后返回的对象
	 * @throws DataAccessException
	 * @author yinlei
	 * date 2013-5-12 下午5:10:13
	 */
	public Map<String, Object> call(CallableStatementCreator csc,
			List<SqlParameter> sqlParamList) throws DataAccessException {
		return jdbcTemplate.call(csc, sqlParamList);
	}

	/**
	 * 执行任意sql语句，一般用于DDL语句和存储过程。亦可用于查询
	 * @param call sql语句，以？为占位符
	 * @param action 回调语句
	 * @return 实体
	 * @throws DataAccessException
	 * @author yinlei
	 * date 2013-5-12 下午5:10:17
	 */
	public <T> T execute(String call, CallableStatementCallback<T> action)
			throws DataAccessException {
		return jdbcTemplate.execute(call, action);
	}

	/**
	 * 执行任意sql语句，一般用于DDL语句。亦可用于查询
	 * @param sql sql语句，以？为占位符
	 * @param action 预处理语句回调
	 * @return 实体
	 * @throws DataAccessException
	 * @author yinlei
	 * date 2013-5-12 下午5:10:22
	 */
	public <T> T execute(String sql, PreparedStatementCallback<T> action)
			throws DataAccessException {
		return jdbcTemplate.execute(sql, action);
	}

	/**
	 * 执行DDL SQL语句。
	 * @param sql 无参数sql语句
	 * @throws DataAccessException
	 * @author yinlei
	 * date 2013-5-12 下午5:10:27
	 */
	public void execute(String sql) throws DataAccessException {
		jdbcTemplate.execute(sql);
	}
	
	public void setFetchSize(int fetchSize) {
		jdbcTemplate.setFetchSize(fetchSize);
	}

	public int getFetchSize() {
		return jdbcTemplate.getFetchSize();
	}
	
	public void setMaxRows(int maxRows) {
		jdbcTemplate.setMaxRows(maxRows);
	}

	public int getMaxRows() {
		return jdbcTemplate.getMaxRows();
	}
	
	/**
	 * 执行查询语句。
	 * @param sql sql语句，以？为占位符
	 * @param rch 结果集映射 RowCallbackHandler有状态的
	 * @param args sql参数
	 * @throws DataAccessException
	 * @author yinlei
	 * date 2013-5-12 下午5:11:08
	 */
	public void query(String sql, RowCallbackHandler rch, Object... args)
			throws DataAccessException {
		jdbcTemplate.query(sql, rch, args);
	}

	/**
	 * 执行查询语句。
	 * @param sql sql语句，以？为占位符
	 * @param rowMapper 结果集映射 RowMapper无状态的
	 * @param args sql参数
	 * @return List&lt;T&gt;列表
	 * @throws DataAccessException
	 * @author yinlei
	 * date 2013-5-12 下午5:11:17
	 */
	public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... args)
			throws DataAccessException {
		return jdbcTemplate.query(sql, rowMapper, args);
	}

	/**
	 * 执行查询语句。使用字节码自动构建实体Class&lt;T&gt;实例。性能略低于回调2%以内。
	 * @param sql sql语句
	 * @param resultClass 结果类型Class&ltT&gt
	 * @param args sql参数
	 * @return 实体List&lt;T&gt;
	 * @author yinlei
	 * date 2013-7-5 下午10:37:21
	 */
	public <T> List<T> query(String sql, Class<T> resultClass, Object... args) {
        //RowMapper<T> rowMapper = new GenericRowMapper<T>(resultClass, sql);
		RowMapper<T> rowMapper = new FieldRowMapper<T>(resultClass, sql);
        return query(sql, rowMapper, args);
    }
	
	/**
	 * 返回唯一结果，多条记录，返回第一条。使用字节码自动构建实体Class&lt;T&gt;实例。性能略低于回调2%以内。
	 * @param sql sql语句
	 * @param resultClass 结果类型Class&lt;T&gt;
	 * @param args sql参数
	 * @return 实体List&lt;T&gt;
	 * @author yinlei
	 * date 2013-7-5 下午10:37:21
	 */
	public <T> T uniqueResult(String sql, Class<T> resultClass, Object... args) {
        List<T> list = query(sql, resultClass, args);
        T result = null;
        if (list != null && !list.isEmpty()) {
        	result = list.get(0);
        }
        return result;
    }
	
	/**
	 * 执行查询语句。只能查询基本类型。
	 * @param sql sql语句，以？为占位符
	 * @param elementType 基本类型封装类
	 * @param args sql参数
	 * @return 基本类型List
	 * @throws DataAccessException
	 * @author yinlei
	 * date 2013-5-12 下午5:11:32
	 */
	public <T> List<T> queryForList(String sql, Class<T> elementType,
			Object... args) throws DataAccessException {
		return jdbcTemplate.queryForList(sql, elementType, args);
	}

	/**
	 * 执行查询语句。
	 * @param sql sql语句，以？为占位符
	 * @param args sql参数
	 * @return Map list，Map中key = 数据库column名，value = 对应栏位值
	 * @throws DataAccessException
	 * @author yinlei
	 * date 2013-5-12 下午5:11:42
	 */
	public List<Map<String, Object>> queryForList(String sql, Object... args)
			throws DataAccessException {
		return jdbcTemplate.queryForList(sql, args);
	}

	/**
	 * 执行查询语句。
	 * @param sql sql语句，以？为占位符
	 * @param args sql参数
	 * @return Map，Map中key = 数据库column名，value = 对应栏位值
	 * @throws DataAccessException
	 * @author yinlei
	 * date 2013-5-12 下午6:56:06
	 */
	public Map<String, Object> queryForMap(String sql, Object... args)
			throws DataAccessException {
		return jdbcTemplate.queryForMap(sql, args);
	}

	/**
	 * 执行查询语句。只能查询基本类型。
	 * @param sql sql语句，以？为占位符
	 * @param requiredType 基本类型类
	 * @param args sql参数
	 * @return 基本类型
	 * @throws DataAccessException
	 * @author yinlei
	 * date 2013-5-12 下午6:56:19
	 */
	public <T> T queryForObject(String sql, Class<T> requiredType,
			Object... args) throws DataAccessException {
		return jdbcTemplate.queryForObject(sql, requiredType, args);
	}

	/**
	 * 执行查询语句。
	 * @param sql sql语句，以？为占位符
	 * @param rowMapper 结果集映射
	 * @param args sql参数
	 * @return Class&ltT&gt实例
	 * @throws DataAccessException
	 * @author yinlei
	 * date 2013-5-12 下午6:56:47
	 */
	public <T> T queryForObject(String sql, RowMapper<T> rowMapper,
			Object... args) throws DataAccessException {
		return jdbcTemplate.queryForObject(sql, rowMapper, args);
	}

	/**
	 * 执行更新操作。一般用于insert，并且返回主键
	 * @param psc 预处理语句创建者回调
	 * @param keyHolder 主键持有者，一般使用GeneratedKeyHolder
	 * @return 更新成功的条数
	 * @throws DataAccessException
	 * @author yinlei
	 * date 2013-5-12 下午6:57:20
	 */
	public int update(PreparedStatementCreator psc, KeyHolder keyHolder)
			throws DataAccessException {
		return jdbcTemplate.update(psc, keyHolder);
	}

	/**
	 * 执行更新操作。可用于insert、update、delete
	 * @param sql sql语句，以？为占位符
	 * @param args sql参数
	 * @return 更新成功的条数
	 * @throws DataAccessException
	 * @author yinlei
	 * date 2013-5-12 下午6:57:25
	 */
	public int update(String sql, Object... args) throws DataAccessException {
		return jdbcTemplate.update(sql, args);
	}

	//以下是命名参数方法
	
	/**
	 * 批量更新数据库，一般用于insert、update、delete
	 * @param sql 命名参数sql语句
	 * @param paramMap sql参数，key为命名参数名
	 * @return 更新成功的条数
	 * @author yinlei
	 * date 2013-5-12 下午7:30:02
	 */
	public int[] batchUpdate(String sql, Map<String, ?>[] paramMap) {
		return namedJdbcTemplate.batchUpdate(sql, paramMap);
	}

	/**
	 * 执行sql语句。
	 * @param sql 命名参数sql语句
	 * @param paramMap sql参数，key为命名参数名
	 * @param action 预处理语句回调
	 * @return 实体
	 * @throws DataAccessException
	 * @author yinlei
	 * date 2013-5-12 下午7:31:19
	 */
	public <T> T execute(String sql, Map<String, ?> paramMap,
			PreparedStatementCallback<T> action) throws DataAccessException {
		return namedJdbcTemplate.execute(sql, paramMap, action);
	}

	/**
	 * 执行查询语句。
	 * @param sql 命名参数sql语句
	 * @param paramMap sql参数，key为命名参数名
	 * @param rch 结果集处理
	 * @throws DataAccessException
	 * @author yinlei
	 * date 2013-5-12 下午7:34:40
	 */
	public void query(String sql, Map<String, ?> paramMap,
			RowCallbackHandler rch) throws DataAccessException {
		namedJdbcTemplate.query(sql, paramMap, rch);
	}

	/**
	 * 执行查询语句。
	 * @param sql 命名参数sql语句
	 * @param paramMap sql参数，key为命名参数名
	 * @param rowMapper 结果集映射
	 * @return 实体List
	 * @throws DataAccessException
	 * @author yinlei
	 * date 2013-5-12 下午7:35:10
	 */
	public <T> List<T> query(String sql, Map<String, ?> paramMap,
			RowMapper<T> rowMapper) throws DataAccessException {
		return namedJdbcTemplate.query(sql, paramMap, rowMapper);
	}

	/**
	 * 执行查询语句。只能查询基本类型。
	 * @param sql 命名参数sql语句
	 * @param paramMap sql参数，key为命名参数名
	 * @param elementType 泛型基本类型类
	 * @return 基本类型List
	 * @throws DataAccessException
	 * @author yinlei
	 * date 2013-5-12 下午7:35:14
	 */
	public <T> List<T> queryForList(String sql, Map<String, ?> paramMap,
			Class<T> elementType) throws DataAccessException {
		return namedJdbcTemplate.queryForList(sql, paramMap, elementType);
	}

	/**
	 * 执行查询语句。
	 * @param sql 命名参数sql语句
	 * @param paramMap sql参数，key为命名参数名
	 * @return Map List，Map中key = 数据库栏位名，value = 对应栏位值
	 * @throws DataAccessException
	 * @author yinlei
	 * date 2013-5-12 下午7:35:17
	 */
	public List<Map<String, Object>> queryForList(String sql,
			Map<String, ?> paramMap) throws DataAccessException {
		return namedJdbcTemplate.queryForList(sql, paramMap);
	}

	/**
	 * 执行查询语句。
	 * @param sql 命名参数sql语句
	 * @param paramMap sql参数，key为命名参数名
	 * @return Map，Map中key = 数据库栏位名，value = 对应栏位值
	 * @throws DataAccessException
	 * @author yinlei
	 * date 2013-5-12 下午7:35:21
	 */
	public Map<String, Object> queryForMap(String sql, Map<String, ?> paramMap)
			throws DataAccessException {
		return namedJdbcTemplate.queryForMap(sql, paramMap);
	}

	/**
	 * 执行查询语句。只能查询基本类型。
	 * @param sql 命名参数sql语句
	 * @param paramMap sql参数，key为命名参数名
	 * @param requiredType 参数基本类型类
	 * @return 基本类型
	 * @throws DataAccessException
	 * @author yinlei
	 * date 2013-5-12 下午7:35:24
	 */
	public <T> T queryForObject(String sql, Map<String, ?> paramMap,
			Class<T> requiredType) throws DataAccessException {
		return namedJdbcTemplate.queryForObject(sql, paramMap, requiredType);
	}

	/**
	 * 执行查询语句。
	 * @param sql 命名参数sql语句
	 * @param paramMap sql参数，key为命名参数名
	 * @param rowMapper 结果集映射
	 * @return Class&ltT&gt实例
	 * @throws DataAccessException
	 * @author yinlei
	 * date 2013-5-12 下午7:35:27
	 */
	public <T> T queryForObject(String sql, Map<String, ?> paramMap,
			RowMapper<T> rowMapper) throws DataAccessException {
		return namedJdbcTemplate.queryForObject(sql, paramMap, rowMapper);
	}

	/**
	 * 更新数据库 ，一般用于insert、delete、update。
	 * @param sql 命名参数sql语句
	 * @param paramMap sql参数，key为命名参数名
	 * @return 更新成功的条数
	 * @throws DataAccessException
	 * @author yinlei
	 * date 2013-5-12 下午7:35:35
	 */
	public int update(String sql, Map<String, ?> paramMap)
			throws DataAccessException {
		return namedJdbcTemplate.update(sql, paramMap);
	}

	/**
	 * 更新数据库 ，一般用于insert并且返回主键。
	 * @param sql 命名参数sql
	 * @param paramMap sql参数
	 * @param keyHolder 主键持有者，一般使用GeneratedKeyHolder
	 * @return 更新成功的条数
	 * @throws DataAccessException
	 * @author yinlei
	 * date 2013-7-20 下午10:16:42
	 */
	public int update(String sql, Map<String, ?> paramMap,
			KeyHolder keyHolder) throws DataAccessException {
		SqlParameterSource paramSource = new MapSqlParameterSource(paramMap);
		return namedJdbcTemplate.update(sql, paramSource, keyHolder);
	}
	
}
