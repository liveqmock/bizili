package com.vteba.tm.hibernate.impl;

import java.io.Serializable;
import java.util.Arrays;
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
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.query.spi.HQLQueryPlan;
import org.hibernate.engine.query.spi.QueryPlanCache;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.LongType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vteba.annotation.common.OK;
import com.vteba.common.exception.BasicException;
import com.vteba.common.model.AstModel;
import com.vteba.tm.generic.Page;
import com.vteba.tm.generic.impl.GenericDaoImpl;
import com.vteba.tm.hibernate.IHibernateGenericDao;
import com.vteba.tm.hibernate.MatchType;
import com.vteba.tm.hibernate.transformer.AliasedResultTransformer;
import com.vteba.tm.hibernate.transformer.ColumnAliasParser;
import com.vteba.tm.hibernate.transformer.FieldAliasedTransformer;
import com.vteba.tm.hibernate.transformer.PrimitiveResultTransformer;

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

	private static final Logger logger = LoggerFactory.getLogger(HibernateGenericDaoImpl.class);
	/**问号*/
	public static final String QMark = "?";
	public HibernateGenericDaoImpl() {
		super();
	}
	
	public HibernateGenericDaoImpl(Class<T> entityClass) {
		super(entityClass);
	}
	
	@OK
	public List<T> getEntityListByHql(String hql, Object... values) {
		if (logger.isInfoEnabled()) {
			logger.info("HQL query, hql = [{}], parameter = {}.", hql, Arrays.toString(values));
		}
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
		if (logger.isInfoEnabled()) {
			logger.info("HQL named query, hql = [{}], parameter = {}.", query.getQueryString(), Arrays.toString(values));
		}
		List<T> list = query.list();
		if (list == null) {
			list = Collections.emptyList();
		}
		return list;
	}
	
	@OK
	public <E> List<E> getListByHql(String hql, Class<E> resultClass, Object... values){
		if (logger.isInfoEnabled()) {
			logger.info("HQL query, will use AliasedResultTransformer mapping the result, hql = [{}], parameter = {}.",
					hql, Arrays.toString(values));
		}
		Query query = createQuery(hql, values);
		if (resultClass != null && hql.indexOf("select new") < 0) {
			query.setResultTransformer(new AliasedResultTransformer(resultClass, hql, true));
			//query.setResultTransformer(new FieldAliasedTransformer(resultClass, hql, true));
			
		}
		List<E> list = query.list();
		if (list == null) {
			list = Collections.emptyList();
		}
		return list;
	}
	
	public <E> List<E> getListByNamedHql(String namedQuery, Class<E> resultClass, Object... values){
		Query query = createNamedQuery(namedQuery, values);
		if (logger.isInfoEnabled()) {
			logger.info("HQL named query, will use AliasedResultTransformer mapping the result, hql = [{}], parameter = {}.", 
					query.getQueryString(), Arrays.toString(values));
		}
		if (resultClass != null) {
			query.setResultTransformer(new AliasedResultTransformer(resultClass, query.getQueryString(), true));
			//query.setResultTransformer(new FieldAliasedTransformer(resultClass, query.getQueryString(), true));
		}
		List<E> list = query.list();
		if (list == null) {
			list = Collections.emptyList();
		}
		return list;
	}
	
	public List<T> getEntityListBySql(String sql, Object... values){
		if (logger.isInfoEnabled()) {
			logger.info("SQL query, will use AliasedResultTransformer mapping the result, sql = [{}], parameter = {}.", 
					sql, Arrays.toString(values));
		}
		SQLQuery query = createSqlQuery(sql, entityClass, values);
		List<T> list = query.list();
		if (list == null) {
			list = Collections.emptyList();
		}
		return list;
	}
	
	public List<T> getEntityListBySpring(String sql, Object... values) {
		if (logger.isInfoEnabled()) {
			logger.info("SQL query, will use Spring JdbcTemplate and AsmUtils mapping the result, sql = [{}], parameter = {}.", 
					sql, Arrays.toString(values));
		}
		return springJdbcTemplate.query(sql, entityClass, values);
	}
	
	public List<T> getEntityListByNamedSql(String namedSql, Object... values){
		SQLQuery query = createNamedSQLQuery(namedSql, values);
		if (logger.isInfoEnabled()) {
			logger.info("SQL named query, will use Entity annotation mapping the result, sql = [{}], parameter = {}.", 
					query.getQueryString(), Arrays.toString(values));
		}
		List<T> list = query.list();
		if (list == null) {
			list = Collections.emptyList();
		}
		return list;
	}
	
	public <E> List<E> getListBySql(String sql, Class<E> resultClass, Object... values){
		if (logger.isInfoEnabled()) {
			logger.info("SQL query, will use AliasedResultTransformer mapping the result, sql = [{}], parameter = {}.", 
					sql, Arrays.toString(values));
		}
		SQLQuery query = createSqlQuery(sql, resultClass, values);
		List<E> list = query.list();
		if (list == null) {
			list = Collections.emptyList();
		}
		return list;
	}
	
	public <E> List<E> getListBySpring(String sql, Class<E> resultClass, Object... values){
		if (logger.isInfoEnabled()) {
			logger.info("SQL query, will use Spring JdbcTemplate and AsmUtils mapping the result, sql = [{}], parameter = {}.", 
					sql, Arrays.toString(values));
		}
		return springJdbcTemplate.query(sql, resultClass, values);
	}
	
	public <E> List<E> getListByNamedSql(String namedSql, Class<E> resultClass, Object... values){
		SQLQuery sqlQuery = createNamedSQLQuery(namedSql, values);// 事实上就是SQLQuery
		if (logger.isInfoEnabled()) {
			logger.info("SQL query, will use AliasedResultTransformer mapping the result, sql = [{}], parameter = {}.", 
					sqlQuery.getQueryString(), Arrays.toString(values));
		}
		setResultTransformer(sqlQuery, resultClass, sqlQuery.getQueryString());
		//setFieldAliasTransformer(sqlQuery, resultClass, sqlQuery.getQueryString());
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
			if (hql.indexOf(QMark + (i + 1)) > 0) {
				logger.info("Use JPA style's position parameter binding.");
				if (values[i] instanceof List) {
					query.setParameterList(Integer.toString(i + 1), (List<?>)values[i]);
				} else {
					query.setParameter(Integer.toString(i + 1), values[i]);
				}
			} else if (values[i] instanceof Map){
				logger.info("Use named parameter binding.");
				Map<String, Object> map = (Map<String, Object>)values[i];
				for (Entry<String, Object> entry : map.entrySet()) {
					if (entry.getValue() instanceof List) {
						query.setParameterList(entry.getKey(), (List<?>)entry.getValue());
					} else {
						query.setParameter(entry.getKey(), entry.getValue());
					}
				}
			} else if (values[i] instanceof AstModel) {
				logger.info("Use AstModel pass parameter, this is named parameter binding, hibernate will use reflection, deprecated.");
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
		//setFieldAliasTransformer(sqlQuery, resultClass, sql);
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
	 * 给SQLQuery设置基于别名的结果集转换器。
	 * @param sqlQuery SQLQuery实例
	 * @param resultClass 结果集类
	 * @param sql sql语句
	 * @author yinlei
	 * date 2013-11-10 下午3:51:58
	 */
	protected void setFieldAliasTransformer(SQLQuery sqlQuery, Class<?> resultClass, String sql) {
		if (resultClass != null) {
			FieldAliasedTransformer transformer = new FieldAliasedTransformer(resultClass, sql, false);
			Class<?>[] fieldTypes = transformer.getFieldTypes();
			String[] columnAlias = transformer.getColumnAlias();
			for (int j = 0; j < columnAlias.length; j++) {
				sqlQuery.addScalar(columnAlias[j], MatchType.matchResultType(fieldTypes[j]));
			}
			sqlQuery.setResultTransformer(transformer);
		}
	}
	
	/**
	 * 创建命名查询，hibernate session中不区分hql和sql，内部区分。<br>
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
				logger.info("Use named parameter binding.");
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
				logger.info("Use AstModel pass parameter, named parameter binding, hibernate will use reflection, deprecated.");
				query.setProperties(values[i]);
			} else {
				logger.info("Use JPA style's position parameter binding.");
				if (values[i] instanceof List) {//in 语法
					query.setParameterList(Integer.toString(i + 1), (List<?>)values[i]);
				} else {
					query.setParameter(Integer.toString(i + 1), values[i]);
				}
			}
		}	
		return query;
	}
	
	/**
	 * 创建命名SQL查询，使用{@link #createNamedQuery(String, Object...)}实现 <br>
	 * 使用JPA位置参数(如：?1，?2)。可使用List传参或者单个分别传参；<br>
	 * 命名参数，即指定参数名字，使用Map传递参数。Map的key为命名参数名，value为值，value中可以放List；<br>
	 * 命名参数，使用JavaBean传参，JavaBean实现AstModel接口，JavaBean中的属性名和命名参数名一致；<br>
	 * @param namedQuery 命名语句的名字
	 * @param values QL语句要绑定的参数
	 * @return Query实例
	 * @author yinlei
	 * date 2012-8-15 上午12:09:55
	 */
	protected SQLQuery createNamedSQLQuery(String namedQuery, Object... values) {
		return (SQLQuery)createNamedQuery(namedQuery, values);
	}
	
	/**
	 * 创建指定实体的Criteria
	 * @param entityClass 指定的实体
	 * @param criterions Criterion条件对象
	 * @author yinlei
	 * date 2012-12-17 下午10:58:06
	 */
	protected <X> Criteria createCriteria(Class<X> entityClass, Criterion... criterions) {
		if (logger.isInfoEnabled()) {
			logger.info("Create Criteria query by QBC. Entity = [{}].", entityClass.getName());
		}
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
	protected <X> Criteria createCriteriaByModel(X entity) {
		if (logger.isInfoEnabled()) {
			logger.info("Create Criteria query by QBE. Entity = [{}].", entityClass.getName());
		}
		Example example = Example.create(entity);
		example.ignoreCase().enableLike(MatchMode.START);
		Criteria criteria = getSession().createCriteria(entity.getClass()).add(example);
		return criteria;
	}
	
	public <X> List<X> getAll(Class<X> entityClass) {
		return createCriteria(entityClass).list();
	}
	
	public DetachedCriteria getDetachedCriteria() {
		return DetachedCriteria.forClass(entityClass);
	}
	
	public <X> DetachedCriteria getDetachedCriteria(Class<X> entityClass) {
		return DetachedCriteria.forClass(entityClass);
	}
	
	public List<T> getListByCriteria(DetachedCriteria detachedCriteria) {
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		List<T> list = criteria.list();
		if (list == null) {
			list = Collections.emptyList();
		}
		return list;
	}
	
	public List<T> getListByCriteria(T model, DetachedCriteria detachedCriteria) {
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		criteria.add(Example.create(model));
		List<T> list = criteria.list();
		if (list == null) {
			list = Collections.emptyList();
		}
		return list;
	}
	
	public List<T> getListByCriteria(String propertyName, Object propertyValue) {
		Criteria criteria = getSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq(propertyName, propertyValue));
		List<T> list = criteria.list();
		if (list == null) {
			list = Collections.emptyList();
		}
		return list;
	}
	
	public List<T> getListByCriteria(T model) {
		if (logger.isInfoEnabled()) {
			logger.info("Create Criteria query by QBE. Entity = [{}].", entityClass.getName());
		}
		Example example = Example.create(model);
		Criteria criteria = getSession().createCriteria(entityClass).add(example);
		
		List<T> list = criteria.list();
		if (list == null) {
			list = Collections.emptyList();
		}
		return list;
	}
	
	public List<T> getListByCriteria(T model, Map<String, String> orderMaps) {
		if (logger.isInfoEnabled()) {
			logger.info("Create Criteria query by QBE. Entity = [{}].", entityClass.getName());
		}
		Example example = Example.create(model);
		Criteria criteria = getSession().createCriteria(entityClass).add(example);
		for (Entry<String, String> entry : orderMaps.entrySet()) {
			if (entry.getValue().equals("desc")) {
				criteria.addOrder(Order.desc(entry.getKey()));
			} else {
				criteria.addOrder(Order.asc(entry.getKey()));
			}
		}
		List<T> list = criteria.list();
		if (list == null) {
			list = Collections.emptyList();
		}
		return list;
	}
	
	public <X> List<X> getListByCriteria(Class<X> entityClass, X model, Map<String, String> maps) {
		if (logger.isInfoEnabled()) {
			logger.info("Create Criteria query by QBE. Entity = [{}].", entityClass.getName());
		}
		Example example = Example.create(model);
		Criteria criteria = getSession().createCriteria(entityClass).add(example);
		for (Entry<String, String> entry : maps.entrySet()) {
			if (entry.getValue().equals("desc")) {
				criteria.addOrder(Order.desc(entry.getKey()));
			} else {
				criteria.addOrder(Order.asc(entry.getKey()));
			}
		}
		List<X> list = criteria.list();
		if (list == null) {
			list = Collections.emptyList();
		}
		
		return list;
	}
	
	public List<T> getListByCriteriaLike(String propertyName, String propertyValue) {
		Criteria criteria = getSession().createCriteria(entityClass);
		criteria.add(Restrictions.like(propertyName, propertyValue));
		List<T> list = criteria.list();
		if (list == null) {
			list = Collections.emptyList();
		}
		return list;
	}
	
	public List<T> getListByCriteriaLike(T model) {
		if (logger.isInfoEnabled()) {
			logger.info("Create Criteria query by QBE. Entity = [{}].", entityClass.getName());
		}
		Example example = Example.create(model);
		example.ignoreCase().enableLike(MatchMode.START);
		Criteria criteria = getSession().createCriteria(entityClass).add(example);
		List<T> list = criteria.list();
		if (list == null) {
			list = Collections.emptyList();
		}
		return list;
	}
	
	public List<T> getListByCriteriaLike(T model, Map<String, String> orderMaps){
		if (logger.isInfoEnabled()) {
			logger.info("Create Criteria query by QBE. Entity = [{}].", entityClass.getName());
		}
		Example example = Example.create(model);
		example.ignoreCase().enableLike(MatchMode.START);
		Criteria criteria = getSession().createCriteria(entityClass).add(example);
		for (Entry<String, String> entry : orderMaps.entrySet()) {
			if (entry.getValue().equals("desc")) {
				criteria.addOrder(Order.desc(entry.getKey()));
			} else {
				criteria.addOrder(Order.asc(entry.getKey()));
			}
		}
		List<T> list = criteria.list();
		if (list == null) {
			list = Collections.emptyList();
		}
		return list;
	}
	
	public <X> List<X> getListByCriteriaLike(Class<X> entityClass, X model, Map<String, String> orderMaps){
		if (logger.isInfoEnabled()) {
			logger.info("Create Criteria query by QBE. Entity = [{}].", entityClass.getName());
		}
		Example example = Example.create(model);
		example.ignoreCase().enableLike(MatchMode.START);
		Criteria criteria = getSession().createCriteria(entityClass).add(example);
		for (Entry<String, String> entry : orderMaps.entrySet()) {
			if (entry.getValue().equals("desc")) {
				criteria.addOrder(Order.desc(entry.getKey()));
			} else {
				criteria.addOrder(Order.asc(entry.getKey()));
			}
		}
		List<X> list = criteria.list();
		if (list == null) {
			list = Collections.emptyList();
		}
		return list;
	}
	
	public T uniqueResultByCriteria(String proName, Object value) {
		Criterion criterion = Restrictions.eq(proName, value);
		return (T) createCriteria(entityClass, criterion).uniqueResult();
	}
	
	public <X> X uniqueResultByCriteria(Class<X> entityClass, String proName, Object value) {
		Criterion criterion = Restrictions.eq(proName, value);
		return (X) createCriteria(entityClass, criterion).uniqueResult();
	}

	public T uniqueResultByCriteria(Map<String, Object> params) {
		Criteria criteria = createCriteria(entityClass);
		for (Entry<String, Object> entry : params.entrySet()) {
			criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
		}
		return (T) criteria.uniqueResult();
	}
	
	public <X> X uniqueResultByCriteria(Class<X> entityClass, Map<String, Object> params) {
		Criteria criteria = createCriteria(entityClass);
		for (Entry<String, Object> entry : params.entrySet()) {
			criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
		}
		return (X) criteria.uniqueResult();
	}
	
	public T uniqueResultByCriteria(T model) {
		return uniqueResultByCriteria(entityClass, model);
	}
	
	public <X> X uniqueResultByCriteria(Class<X> entityClass, X model) {
		Example example = Example.create(model);
		return (X) createCriteria(entityClass, example).uniqueResult();
	}
	
	public T uniqueResultByHql(String hql, Object... values) {
		Query query = createQuery(hql, values);
		return (T) query.uniqueResult();
		//return uniqueResultByHql(hql, entityClass, false, values);
	}
	
	public T uniqueResultByNamedHql(String hql, Object... values) {
		Query query = createNamedQuery(hql, values);
		return (T) query.uniqueResult();
		//return uniqueResultByHql(hql, entityClass, true, values);
	}
	
	public <X> X uniqueResultByHql(String hql, Class<X> resultClass, Object... values) {
		return uniqueResultByHql(hql, resultClass, false, values);
	}
	
	public <X> X uniqueResultByHql(String hql, Class<X> resultClass, boolean namedQuery, Object... values) {
		if (resultClass == null) {
			throw new BasicException("resultClass can't be null.");
		}
		Query query = null;
		if (namedQuery) {
			query = createNamedQuery(hql, values);
		} else {
			query = createQuery(hql, values);
		}
		if (logger.isInfoEnabled()) {
			logger.info("uniqueResultByHql, hql = [{}], parameter = {}, resultClass = [{}].", 
					(namedQuery ? query.getQueryString() : hql), Arrays.toString(values), resultClass.getName());
		}
		query.setResultTransformer(new AliasedResultTransformer(resultClass, hql, true));
		return (X) query.uniqueResult();
	}
	
	@OK
	public T uniqueResultBySql(String sql, Object... values) {
		if (logger.isInfoEnabled()) {
			logger.info("uniqueResultBySql, sql = [{}], parameter = {}.", sql, Arrays.toString(values));
		}
		return (T) createSqlQuery(sql, entityClass, values).uniqueResult();
	}
	
	public <X> X uniqueResultBySql(String sql, Class<X> resultClass, Object... values) {
		if (logger.isInfoEnabled()) {
			logger.info("uniqueResultBySql, sql = [{}], parameter = {}, resultClass = [{}].", 
					sql, Arrays.toString(values), resultClass.getName());
		}
		return (X) createSqlQuery(sql, resultClass, values).uniqueResult();
	}

	protected Query distinct(Query query) {
		query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return query;
	}
	
	public List<Object[]> sqlQueryForObject(String sql, Object... values){
		if (logger.isInfoEnabled()) {
			logger.info("sqlQueryForObject, sql = [{}], parameter = {}.", sql, Arrays.toString(values));
		}
		SQLQuery query = createSqlQuery(sql, null, values);
		List<Object[]> list = query.list();
		if (list == null) {
			list = Collections.emptyList();
		}
		return list;
	}
	
	public <X> List<X> sqlQueryForList(String sql, Class<X> clazz, Object... values){
		if (!MatchType.isPrimitive(clazz)) {
			throw new BasicException("clazz 参数不是原生类型或封转类，或String，或Date，或大数值。");
		}
		if (logger.isInfoEnabled()) {
			logger.info("sqlQueryForList, sql = [{}], parameter = {}, resultClass = [{}].", sql, Arrays.toString(values), clazz.getName());
		}
		SQLQuery query = createSqlQuery(sql, null, values);
//		if (sql.indexOf("nextValue('") > -1) {//sequence
//			query.addScalar("seq", LongType.INSTANCE);
//		} else {
//			String[] columns = ColumnAliasParser.get().parseColumnAlias(sql, true);
//			query.addScalar(columns[0], MatchType.matchResultType(clazz));
//		}
		String[] columns = ColumnAliasParser.get().parseColumnAlias(sql, true);
		query.addScalar(columns[0], MatchType.matchResultType(clazz));
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
		if (logger.isInfoEnabled()) {
			logger.info("hqlQueryForObject, hql = [{}], parameter = {}.", (namedQuery ? query.getQueryString() : hql), Arrays.toString(values));
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
			throw new BasicException("clazz 参数不是原生类型或封转类，或String，或Date，或大数值。");
		}
		if (logger.isInfoEnabled()) {
			logger.info("hqlQueryForList, hql = [{}], parameter = {}, resultClass = [{}].", hql, Arrays.toString(values), clazz.getName());
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

	public int executeHqlUpdate(String hql, boolean namedQuery, Object... values) {
		if (logger.isInfoEnabled()) {
			logger.info("Execute HQL, named = {}, hql = [{}], parameter = {}.", namedQuery, hql, Arrays.toString(values));
		}
		if (namedQuery) {
			return createNamedQuery(hql, values).executeUpdate();
		} else {
			return createQuery(hql, values).executeUpdate();
		}
	}
	
	public int executeSqlUpdate(String sql, Object... values){
		if (logger.isInfoEnabled()) {
			logger.info("Execute SQL, sql = [{}], parameter = {}.", sql, Arrays.toString(values));
		}
		return this.createSqlQuery(sql, null, values).executeUpdate();
	}
	
	public void initProxyObject(Object proxy) {
		Hibernate.initialize(proxy);
	}
    
	public Long getSequenceLongValue(String sequenceName) {
		String sql = "select nextValue('" + sequenceName + "') seq";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.addScalar("seq", LongType.INSTANCE);
		Long seq = (Long) sqlQuery.uniqueResult();
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

	public Page<T> queryForPageByCriteria(Page<T> page, T entity) {
		if (logger.isInfoEnabled()) {
			logger.info("Criteria Paged Query, entity = [{}], page from [{}] to [{}].", 
					entity.getClass().getName(), page.getStartIndex(), page.getPageSize());
		}
		Criteria criteria = createCriteriaByModel(entity);
		long totalRecordCount = countCriteriaResult(criteria);
		page.setTotalRecordCount(totalRecordCount);
		setParameterToCriteria(page, criteria);
		List<T> result = criteria.list();
		page.setResult(result);
		return page;
	}
	
	public Page<T> queryForPageByCriteria(Page<T> page, DetachedCriteria detachedCriteria) {
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		long totalRecordCount = countCriteriaResult(criteria);
		page.setTotalRecordCount(totalRecordCount);
		setParameterToCriteria(page, criteria);
		List<T> list = criteria.list();
		if (list == null) {
			list = Collections.emptyList();
		}
		page.setResult(list);
		return page;
	}
	
	public Page<T> queryForPageByCriteria(Page<T> page, T entity, DetachedCriteria detachedCriteria) {
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		Example example = Example.create(entity);
		criteria.add(example);
		long totalRecordCount = countCriteriaResult(criteria);
		page.setTotalRecordCount(totalRecordCount);
		setParameterToCriteria(page, criteria);
		List<T> list = criteria.list();
		if (list == null) {
			list = Collections.emptyList();
		}
		page.setResult(list);
		return page;
	}
	
	@Deprecated
	public Page<T> queryForPageBySubSelect(Page<T> page, T entity, Object... objects){
		if (logger.isInfoEnabled()) {
			logger.info("Criteria Paged Query, entity = [{}], page from [{}] to [{}].", 
					entity.getClass().getName(), page.getStartIndex(), page.getPageSize());
		}
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
	
	@Deprecated
	public Page<T> queryForPageByLeftJoin(Page<T> page, T entity, Object... objects) {
		if (logger.isInfoEnabled()) {
			logger.info("Criteria Paged Query, entity = [{}], page from [{}] to [{}].", 
					entity.getClass().getName(), page.getStartIndex(), page.getPageSize());
		}
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
		if (logger.isInfoEnabled()) {
			logger.info("HQL Paged Query, hql = [{}], parameter = {}, page from [{}] to [{}].", 
					hql, Arrays.toString(values), page.getStartIndex(), page.getPageSize());
		}
		Query query = createQuery(hql, values);
		long totalRecordCount = countHqlResult(hql, values);
		page.setTotalRecordCount(totalRecordCount);
		setParameterToQuery(page, query);
		List<T> result = query.list();
		page.setResult(result);
		return page;
	}
	
	@Override
	public List<T> pagedQueryByHql(Page<T> page, String hql, Object... values) {
		if (logger.isInfoEnabled()) {
			logger.info("HQL Paged Query, hql = [{}], parameter = {}, page from [{}] to [{}].", 
					hql, Arrays.toString(values), page.getStartIndex(), page.getPageSize());
		}
		Query query = createQuery(hql, values);
		setParameterToQuery(page, query);
		List<T> result = query.list();
		return result;
	}
	
	public Page<T> queryForPageBySql(Page<T> page, String sql, Object... values) {
		if (logger.isInfoEnabled()) {
			logger.info("SQL Paged Query, hql = [{}], parameter = {}, page from [{}] to [{}]", 
					sql, Arrays.toString(values), page.getStartIndex(), page.getPageSize());
		}
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
			throw new BasicException("Pagesize must be lager than 0.");
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
			throw new BasicException("Pagesize must be lager than 0.");
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
			throw new RuntimeException("HQL can't be auto count, hql is:" + countHql, e);
		}
	}

	public long countSqlResult(String sql, Object... values) {
		String countSql = prepareCountSql(sql);
		try {
			Long count = sqlQueryForObject(countSql, Long.class, values);
			return count.longValue();
		} catch (Exception e) {
			throw new RuntimeException("SQL can't be auto count, sql is:" + countSql, e);
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

	public SessionFactoryImpl getSessionFactoryImpl() {
		return (SessionFactoryImpl) getSessionFactory();
	}
	
	public QueryPlanCache getQueryPlanCache() {
		return getSessionFactoryImpl().getQueryPlanCache();
	}
	
	public HQLQueryPlan getHQLQueryPlan(String hql) {
		return getQueryPlanCache().getHQLQueryPlan(hql, false, Collections.EMPTY_MAP);
	}
	
}
