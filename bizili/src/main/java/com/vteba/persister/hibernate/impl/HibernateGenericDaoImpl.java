package com.vteba.persister.hibernate.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.LongType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vteba.annotation.common.OK;
import com.vteba.common.constant.Sequence;
import com.vteba.common.exception.BaseException;
import com.vteba.common.model.AstModel;
import com.vteba.persister.generic.Page;
import com.vteba.persister.generic.impl.GenericDaoImpl;
import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.persister.hibernate.MatchType;
import com.vteba.persister.hibernate.transformer.AliasedResultTransformer;
import com.vteba.persister.hibernate.transformer.ColumnAliasParser;
import com.vteba.persister.hibernate.transformer.PrimitiveResultTransformer;

/**
 * 泛型DAO Hibernate实现，简化Entity DAO实现。
 * @author yinlei 
 * date 2012-5-6 下午10:39:42
 * @param <T> 实体类型
 * @param <ID> 主键类型，一般是String或者Long
 */
@SuppressWarnings("unchecked")
public abstract class HibernateGenericDaoImpl<T, ID extends Serializable>
		extends GenericDaoImpl<T, ID> implements IHibernateGenericDao<T, ID> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public HibernateGenericDaoImpl() {
		super();
	}
	
	public HibernateGenericDaoImpl(Class<T> entityClass) {
		super(entityClass);
	}
	
	@OK
	public List<T> getEntityListByHql(String hql, Object... values){
		Query query = createQuery(hql, values);
		List<T> list = query.list();
		if (list == null) {
			list = Collections.emptyList();
		}
		return list;
	}
	
	@OK
	public List<T> getEntityListByNamedHql(String namedQuery, Object... values) {
		Query query = createNamedQuery(namedQuery, values);
		List<T> list = query.list();
		if (list == null) {
			list = Collections.emptyList();
		}
		return list;
	}
	
	@OK
	public <E> List<E> getListByHql(String hql, Class<E> resultClass, Object... values){
		Query query = createQuery(hql, values);
		if (resultClass != null) {
			query.setResultTransformer(new AliasedResultTransformer(resultClass, hql, true));
		}
		List<E> list = query.list();
		if (list == null) {
			list = Collections.emptyList();
		}
		return list;
	}
	
	public <E> List<E> getListByNamedHql(String namedQuery, Class<E> resultClass, Object... values){
		Query query = createNamedQuery(namedQuery, values);
		if (resultClass != null) {
			query.setResultTransformer(new AliasedResultTransformer(resultClass, query.getQueryString(), true));
		}
		List<E> list = query.list();
		if (list == null) {
			list = Collections.emptyList();
		}
		return list;
	}
	
	public List<T> getEntityListBySql(String sql, Object... values){
		SQLQuery query = createSqlQuery(sql, entityClass, values);
		List<T> list = query.list();
		if (list == null) {
			list = Collections.emptyList();
		}
		return list;
	}
	
	public List<T> getEntityListBySpring(String sql, Object... values){
		return springJdbcTemplate.query(sql, entityClass, values);
	}
	
	public List<T> getEntityListByNamedSql(String namedSql, Object... values){
		Query query = createNamedQuery(namedSql, values);
		List<T> list = query.list();
		if (list == null) {
			list = Collections.emptyList();
		}
		return list;
	}
	
	public <E> List<E> getListBySql(String sql, Class<E> resultClass, Object... values){
		SQLQuery query = createSqlQuery(sql, resultClass, values);
		List<E> list = query.list();
		if (list == null) {
			list = Collections.emptyList();
		}
		return list;
	}
	
	public <E> List<E> getListBySpring(String sql, Class<E> resultClass, Object... values){
		return springJdbcTemplate.query(sql, resultClass, values);
	}
	
	public <E> List<E> getListByNamedSql(String namedSql, Class<E> resultClass, Object... values){
		SQLQuery sqlQuery = (SQLQuery) createNamedQuery(namedSql, values);// 事实上就是SQLQuery
		setResultTransformer(sqlQuery, resultClass, sqlQuery.getQueryString());
		List<E> list = sqlQuery.list();
		if (list == null) {
			list = Collections.emptyList();
		}
		return list;
	}
	
	public void flush() {
		getSession().flush();
	}

	public void clear() {
		getSession().clear();
	}
	
	/**
	 * 创建Query并绑定参数。<br>
	 * 1、使用JPA位置参数(如：?1，?2)。可使用List传参或者单个分别传参；<br>
	 * 2、命名参数，即指定参数名字，使用Map传递参数。Map的key为命名参数名，value为值，value中可以放List；<br>
	 * 3、命名参数，使用JavaBean传参，JavaBean实现AstModel接口，JavaBean中的属性名和命名参数名一致；<br>
	 * 4、HQL位置参数（如：?，?），不建议使用（deprecated）。<br>
	 * 5、in语法的list绑定，如：foo.bar in (:value_list)。Query.setParameterList("value_list", Collection vals)。
	 * @param hql 要执行的hql
	 * @param values hql要绑定的参数值
	 * @author yinlei
	 * date 2012-7-15 上午12:16:29
	 */
	protected Query createQuery(String hql, Object... values) {
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < values.length; i++) {
			if (hql.indexOf("?" + (i + 1)) > 0) {
				logger.info("use JPA style's position parameter binding.");
				if (values[i] instanceof List) {
					query.setParameterList((i + 1) + "", (List<?>)values[i]);
				} else {
					query.setParameter((i + 1) + "", values[i]);
				}
			} else if (values[i] instanceof Map){
				logger.info("use named parameter binding.");
				Map<String, Object> map = (Map<String, Object>)values[i];
				for (Entry<String, Object> entry : map.entrySet()) {
					if (entry.getValue() instanceof List) {
						query.setParameterList(entry.getKey(), (List<?>)entry.getValue());
					} else {
						query.setParameter(entry.getKey(), entry.getValue());
					}
				}
			} else if (values[i] instanceof AstModel) {
				logger.info("use AstModel pass parameter, named parameter binding.hibernate 使用反射，不建议。");
				query.setProperties(values[i]);
			} else {
				logger.warn("HQL position parameter binding is deprecated, please use JPA style's.");
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * 创建SQLQuery，并绑定参数。
	 * @param sql sql语句，sql中使用？为占位符，或者命名参数
	 * @param resultClazz 结果类型
	 * @param values sql中的参数，单个传值，或者使用Map传值
	 */
	protected SQLQuery createSqlQuery(String sql, Class<?> resultClass, Object... values){
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		for (int i = 0; i < values.length; i++) {
			if (values[i] instanceof Map){
				logger.info("SQL Query, use named parameter binding.");
				Map<String, Object> map = (Map<String, Object>)values[i];
				for (Entry<String, Object> entry : map.entrySet()) {
					if (entry.getValue() instanceof List) {// in clause
						sqlQuery.setParameterList(entry.getKey(), (List<?>)entry.getValue());
					} else {
						sqlQuery.setParameter(entry.getKey(), entry.getValue());
					}
				}
			} else {
				logger.info("SQL Query, use position parameter binding.");
				sqlQuery.setParameter(i, values[i]);
			}
		}
		setResultTransformer(sqlQuery, resultClass, sql);
		return sqlQuery;
	}
	
	/**
	 * 给SQLQuery设置基于别名的结果集转换器。
	 * @param sqlQuery SQLQuery实例
	 * @param resultClass 结果集类
	 * @param sql sql语句
	 * @author yinlei
	 * date 2013-6-10 下午3:51:58
	 */
	protected void setResultTransformer(SQLQuery sqlQuery, Class<?> resultClass, String sql) {
		if (resultClass != null) {
			AliasedResultTransformer transformer = new AliasedResultTransformer(resultClass, sql, false);
			Class<?>[][] argsTypes = transformer.getArgsTypes();
			String[] columnAlias = transformer.getColumnAlias();
			for (int j = 0; j < columnAlias.length; j++) {
				if (columnAlias[j] != null) {
					sqlQuery.addScalar(columnAlias[j], MatchType.matchResultType(argsTypes[j][0]));
				}
			}
			sqlQuery.setResultTransformer(transformer);
		}
	}
	
	/**
	 * 创建命名查询，hibernate不区分hql和sql。<br>
	 * 使用JPA位置参数(如：?1，?2)。可使用List传参或者单个分别传参；<br>
	 * 命名参数，即指定参数名字，使用Map传递参数。Map的key为命名参数名，value为值，value中可以放List；<br>
	 * 命名参数，使用JavaBean传参，JavaBean实现AstModel接口，JavaBean中的属性名和命名参数名一致；<br>
	 * @param namedQuery 命名语句的名字
	 * @param values QL语句要绑定的参数
	 * @return Query实例
	 * @author yinlei
	 * date 2012-8-15 上午12:04:01
	 */
	protected Query createNamedQuery(String namedQuery, Object... values) {
		Query query = getSession().getNamedQuery(namedQuery);
		for (int i = 0; i < values.length; i++) {
			if (values[i] instanceof Map){
				logger.info("use named parameter binding.");
				Map<String, Object> map = (Map<String, Object>)values[i];
				//如果是纯Map其实可以用query.setProperties
				for (Entry<String, Object> entry : map.entrySet()) {
					if (entry.getValue() instanceof List) {//in 语法
						query.setParameterList(entry.getKey(), (List<?>)entry.getValue());
					} else {
						query.setParameter(entry.getKey(), entry.getValue());
					}
				}
			} else if (values[i] instanceof AstModel) {
				logger.info("use AstModel pass parameter, named parameter binding.");
				query.setProperties(values[i]);
			} else {
				logger.info("use JPA style's position parameter binding.");
				if (values[i] instanceof List) {//in 语法
					query.setParameterList((i + 1) + "", (List<?>)values[i]);
				} else {
					query.setParameter((i + 1) + "", values[i]);
				}
			}
		}	
		return query;
	}
	
	/**
	 * 创建指定实体的Criteria
	 * @param entityClass 指定的实体
	 * @param criterions Criterion条件对象
	 * @author yinlei
	 * date 2012-12-17 下午10:58:06
	 */
	protected <X> Criteria createCriteria(Class<X> entityClass, Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}
	
	/**
	 * 使用QBE创建Criteria
	 * @param entity 携带条件的实体
	 * @author yinlei
	 * date 2012-6-17 下午10:53:21
	 */
	protected <X> Criteria createCriteriaByModel(X entity){
		Example example = Example.create(entity);
		example.ignoreCase().enableLike(MatchMode.START);
		Criteria criteria = getSession().createCriteria(entity.getClass()).add(example);
		logger.info("create Criteria query by QBE.");
		return criteria;
	}
	
	public <X> List<X> getAll(Class<X> entityClass){
		return createCriteria(entityClass).list();
	}
	
	public <X> List<X> getListByPropertyEqual(Class<X> entityClass, X model, Object... objects){
		Example example = Example.create(model);
		Criteria criteria = getSession().createCriteria(entityClass).add(example);
		for (int i = 0; i < objects.length; i++) {
			if (objects[i] instanceof Map) {
				Map<String, String> map = (Map<String, String>)objects[i];
				for (Entry<String, String> entry : map.entrySet()) {
					if (entry.getValue().equals("desc")) {
						criteria.addOrder(Order.desc(entry.getKey()));
					} else {
						criteria.addOrder(Order.asc(entry.getKey()));
					}
				}
			}
		}
		List<X> list = criteria.list();
		if (list == null) {
			list = Collections.emptyList();
		}
		logger.info("Criteria query by QBE.");
		return list;
	}
	
	public <X> List<X> getListByPropertyLike(Class<X> entityClass, X model, Object... objects){
		Example example = Example.create(model);
		example.ignoreCase().enableLike(MatchMode.START);
		Criteria criteria = getSession().createCriteria(entityClass).add(example);
		for (int i = 0; i < objects.length; i++) {
			if (objects[i] instanceof Map) {
				Map<String, String> map = (Map<String, String>)objects[i];
				for (Entry<String, String> entry : map.entrySet()) {
					if (entry.getValue().equals("desc")) {
						criteria.addOrder(Order.desc(entry.getKey()));
					} else {
						criteria.addOrder(Order.asc(entry.getKey()));
					}
				}
			}
		}
		List<X> list = criteria.list();
		if (list == null) {
			list = Collections.emptyList();
		}
		logger.info("criteria query by QBE.");
		return list;
	}
	
	@OK
	public <X> X getUniqueResultByProperty(Class<X> entityClass, String proName, Object value) {
		Criterion criterion = Restrictions.eq(proName, value);
		return (X) createCriteria(entityClass, criterion).uniqueResult();
	}

	public <X> X getUniqueResultByProperty(Class<X> entityClass, Map<String, Object> params) {
		Criteria criteria = createCriteria(entityClass);
		for (Entry<String, Object> entry : params.entrySet()) {
			criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
		}
		return (X) criteria.uniqueResult();
	}
	
	public T uniqueResultByHql(String hql, boolean namedQuery, Object... values){
		return getUniqueResultByHql(hql, entityClass, namedQuery, values);
	}
	
	@OK
	public T uniqueResultBySql(String sql, Object... values) {
		return (T) createSqlQuery(sql, entityClass, values).uniqueResult();
	}

	public <X> X getUniqueResultByHql(String hql, Class<X> resultClass, boolean namedQuery, Object... values) {
		if (resultClass == null) {
			throw new BaseException("resultClass can't be null.");
		}
		Query query = null;
		if (namedQuery) {
			query = createNamedQuery(hql, values);
		} else {
			query = createQuery(hql, values);
		}
		query.setResultTransformer(new AliasedResultTransformer(resultClass, hql, true));
		return (X) query.uniqueResult();
	}
	
	public <X> X getUniqueResultBySql(String sql, Class<X> resultClass, Object... values) {
		return (X) createSqlQuery(sql, resultClass, values).uniqueResult();
	}

	protected Query distinct(Query query) {
		query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return query;
	}
	
	public List<Object[]> sqlQueryForObject(String sql, Object... values){
		SQLQuery query = createSqlQuery(sql, null, values);
		List<Object[]> list = query.list();
		if (list == null) {
			list = Collections.emptyList();
		}
		return list;
	}
	
	public <X> List<X> sqlQueryForList(String sql, Class<X> clazz, Object... values){
		if (!MatchType.isPrimitive(clazz)) {
			throw new BaseException("clazz 参数不是原生类型或封转类，或String，或Date，或大数值。");
		}
		SQLQuery query = createSqlQuery(sql, null, values);
		if (sql.indexOf(Sequence.FUN_NAME) > -1) {//sequence
			query.addScalar("seq", LongType.INSTANCE);
		} else {
			String[] columns = ColumnAliasParser.get().parseColumnAlias(sql, true);
			query.addScalar(columns[0], MatchType.matchResultType(clazz));
		}
		List<X> list = query.list();
		if (list == null) {
			list = Collections.emptyList();
		}
		return list;
	}
	
	public <X> X sqlQueryForObject(String sql, Class<X> clazz, Object... values) {
		List<X> list = sqlQueryForList(sql, clazz, values);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
	
	public List<Object[]> hqlQueryForObject(String hql, boolean namedQuery, Object... values){
		Query query = null;
		if (namedQuery) {
			query = createNamedQuery(hql, values);
		} else {
			query = createQuery(hql, values);
		}
		List<Object[]> list = query.list();
		if (list == null) {
			list = Collections.emptyList();
		}
		return list;
	}
	
	public <X> X hqlQueryForObject(String hql, Class<X> clazz, Object... values) {
		List<X> list = hqlQueryForList(hql, clazz, values);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
	
	public <X> List<X> hqlQueryForList(String hql, Class<X> clazz, Object... values) {
		if (!MatchType.isPrimitive(clazz)) {
			throw new BaseException("clazz 参数不是原生类型或封转类，或String，或Date，或大数值。");
		}
		Query query = createQuery(hql, values);
		query.setResultTransformer(new PrimitiveResultTransformer(clazz));
		List<X> list = query.list();
		if (list == null) {
			return Collections.emptyList();
		}
		return list;
	}
	
	protected Criteria distinct(Criteria criteria) {
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria;
	}

	public int executeUpdateByHql(String hql, boolean namedQuery, Object... values) {
		if (namedQuery) {
			return createNamedQuery(hql, values).executeUpdate();
		} else {
			return createQuery(hql, values).executeUpdate();
		}
	}
	
	public int executeUpdateBySql(String sql, Object... values){
		return this.createSqlQuery(sql, null, values).executeUpdate();
	}
	
	public void initProxyObject(Object proxy) {
		Hibernate.initialize(proxy);
	}
    
	public Long getSequenceLongValue(String sequenceName) {
		String sql = "select nextValue('" + sequenceName + "') seq";
		Long seq = sqlQueryForObject(sql, Long.class);
		return seq;
	}
	
	protected Page<T> queryForPage(Page<T> page, Criterion... criterions) {
		Criteria criteria = createCriteria(entityClass, criterions);
		long totalRecordCount = countCriteriaResult(criteria);
		page.setTotalRecordCount(totalRecordCount);
		setParameterToCriteria(page, criteria);
		List<T> result = criteria.list();
		page.setResult(result);
		return page;
	}

	public Page<T> queryForPageByModel(Page<T> page, T entity) {
		Criteria criteria = createCriteriaByModel(entity);
		long totalRecordCount = countCriteriaResult(criteria);
		page.setTotalRecordCount(totalRecordCount);
		setParameterToCriteria(page, criteria);
		List<T> result = criteria.list();
		page.setResult(result);
		return page;
	}
	
	public Page<T> queryForPageBySubSelect(Page<T> page, T entity, Object... objects){
		Criteria criteria = createCriteriaByModel(entity);
		distinct(criteria);
		for (Object obj : objects) {//使用第二个查询初始化延迟加载集合
			criteria.setFetchMode(obj.toString(), FetchMode.SELECT);
		}
		long totalRecordCount = countCriteriaResult(criteria);
		page.setTotalRecordCount(totalRecordCount);
		setParameterToCriteria(page, criteria);
		List<T> result = criteria.list();
		page.setResult(result);
		return page;
	}
	
	public Page<T> queryForPageByLeftJoin(Page<T> page, T entity, Object... objects) {
		Criteria criteria = createCriteriaByModel(entity);
		for (Object obj : objects) {// 使用左外连接加载集合
			criteria.setFetchMode(obj.toString(), FetchMode.JOIN);
		}
		long totalRecordCount = countCriteriaResult(criteria);
		page.setTotalRecordCount(totalRecordCount);
		setParameterToCriteria(page, criteria);
		List<T> result = criteria.list();
		page.setResult(result);
		return page;
	}
	
	public Page<T> queryForPageByHql(Page<T> page, String hql, Object... values) {
		Query query = createQuery(hql, values);
		long totalRecordCount = countHqlResult(hql, values);
		page.setTotalRecordCount(totalRecordCount);
		setParameterToQuery(page, query);
		List<T> result = query.list();
		page.setResult(result);
		return page;
	}
	
	public Page<T> queryForPageBySql(Page<T> page, String sql, Object... values) {
		SQLQuery query = createSqlQuery(sql, entityClass, values);
		long totalRecordCount = countSqlResult(sql, values);
		page.setTotalRecordCount(totalRecordCount);
		setParameterToQuery(page, query);
		List<T> result = query.list();
		page.setResult(result);
		return page;
	}
	
	/**
	 * 给Query设置分页参数
	 * @author yinlei
	 * date 2012-5-14 下午11:43:13
	 */
	protected Query setParameterToQuery(Page<T> page, Query query){
		if (page.getPageSize() < 0) {
			throw new BaseException("pagesize must be lager than zero");
		}
		query.setFirstResult(page.getStartIndex());
		query.setMaxResults(page.getPageSize());
		return query;
	}
	
	/**
	 * 给Criteria设置分页和排序参数
	 * @author yinlei
	 * date 2012-5-14 下午11:42:29
	 */
	protected Criteria setParameterToCriteria(Page<T> page, Criteria criteria){
		if (page.getPageSize() < 0) {
			throw new BaseException("pagesize must be lager than zero");
		}
		if (page.getAscDesc() != null && page.getAscDesc().equals("desc")) {
			criteria.addOrder(Order.desc(page.getOrderBy()));
		} else if (page.getAscDesc() != null && page.getAscDesc().equals("asc")) {
			criteria.addOrder(Order.asc(page.getOrderBy()));
		}
		criteria.setFirstResult(page.getStartIndex());
		criteria.setMaxResults(page.getPageSize());
		return criteria;
	}
	
	public long countHqlResult(String hql, Object... values) {
		String countHql = prepareCountHql(hql);
		try {
			Long count = hqlQueryForObject(countHql, Long.class, values);
			return count.longValue();
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
		}
	}

	public long countSqlResult(String sql, Object... values) {
		String countSql = prepareCountSql(sql);
		try {
			Long count = sqlQueryForObject(countSql, Long.class, values);
			return count.longValue();
		} catch (Exception e) {
			throw new RuntimeException("sql can't be auto count, sql is:" + countSql, e);
		}
	}
	
	/**
	 * 统计Criteria查询有多少记录，分页查询用
	 * @param c 要执行的Criteria
	 * @return 记录数
	 * @author yinlei
	 * date 2012-5-14 下午11:36:27
	 */
	protected long countCriteriaResult(Criteria criteria) {
		CriteriaImpl impl = (CriteriaImpl) criteria;
		
		//先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();
		
		//List<CriteriaImpl.OrderEntry> orderEntries = null;
		//orderEntries = (List<CriteriaImpl.OrderEntry>) ReflectionUtils.getFieldValue(impl, "orderEntries");
		//ReflectionUtils.setFieldValue(impl, "orderEntries", new ArrayList<T>());
		
		//执行Count查询
		Long totalCountObject = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
		long totalCount = (totalCountObject != null) ? totalCountObject.longValue() : 0L;
		
		//将之前的Projection,ResultTransformer和OrderBy条件重新设回去
		criteria.setProjection(projection);
		if (projection == null) {
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null) {
			criteria.setResultTransformer(transformer);
		}
		//ReflectionUtils.setFieldValue(impl, "orderEntries", orderEntries);
		return totalCount;
	}
	
	/**
	 * select显示的栏位与order by排序会影响count查询效率，进行简单的排除，未考虑union
	 * @param hql 原始hql
	 * @return 排除order by和显示栏位后的hql
	 * @author yinlei
	 * date 2012-5-14 下午11:30:14
	 */
	protected String prepareCountHql(String hql) {
		String fromHql = hql;
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");
		String countHql = "select count(*) " + fromHql;
		return countHql;
	}
	
	/**
	 * select显示的栏位与order by排序会影响count查询效率，进行简单的排除，未考虑union
	 * @param sql 原始sql
	 * @return 排除order by和显示栏位后的sql
	 * @author yinlei
	 * date 2012-7-14 下午11:31:21
	 */
	protected String prepareCountSql(String sql) {
		String fromSql = sql;
		fromSql = "from " + StringUtils.substringAfter(fromSql, "from");
		fromSql = StringUtils.substringBefore(fromSql, "order by");
		String countSql = "select count(*) count " + fromSql;
		return countSql;
	}

}
