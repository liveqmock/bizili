package com.vteba.service.generic;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.vteba.tx.generic.Page;

/**
 * 通用泛型Service接口，简化Service接口定义。
 * @author yinlei 
 * date 2012-6-3 下午12:51:46
 */
public interface IGenericService<T, ID extends Serializable> {

	/**
	 * @param entity
	 * @return
	 * @see com.vteba.tx.generic.IGenericDao#save(java.lang.Object)
	 */
	public ID save(T entity);

	/**
	 * @param entity
	 * @see com.vteba.tx.generic.IGenericDao#persist(java.lang.Object)
	 */
	public void persist(T entity);

	/**
	 * @param hql
	 * @param values
	 * @return
	 * @see com.vteba.tx.hibernate.IHibernateGenericDao#getEntityListByHql(java.lang.String, java.lang.Object[])
	 */
	public List<T> getEntityListByHql(String hql, Object... values);

	/**
	 * @param entity
	 * @see com.vteba.tx.generic.IGenericDao#saveOrUpdate(java.lang.Object)
	 */
	public void saveOrUpdate(T entity);

	/**
	 * @param entity
	 * @see com.vteba.tx.generic.IGenericDao#update(java.lang.Object)
	 */
	public void update(T entity);

	/**
	 * @param entity
	 * @return
	 * @see com.vteba.tx.generic.IGenericDao#merge(java.lang.Object)
	 */
	public T merge(T entity);

	/**
	 * @param entity
	 * @param id
	 * @return
	 * @see com.vteba.tx.generic.IGenericDao#load(java.lang.Class, java.io.Serializable)
	 */
	public T load(Class<T> entity, ID id);

	/**
	 * @param namedQuery
	 * @param values
	 * @return
	 * @see com.vteba.tx.hibernate.IHibernateGenericDao#getEntityListByNamedHql(java.lang.String, java.lang.Object[])
	 */
	public List<T> getEntityListByNamedHql(String namedQuery, Object... values);

	/**
	 * @param id
	 * @return
	 * @see com.vteba.tx.generic.IGenericDao#load(java.io.Serializable)
	 */
	public T load(ID id);

	/**
	 * @param entity
	 * @param id
	 * @return
	 * @see com.vteba.tx.generic.IGenericDao#get(java.lang.Class, java.io.Serializable)
	 */
	public <X> X get(Class<X> entity, ID id);

	/**
	 * @param id
	 * @return
	 * @see com.vteba.tx.generic.IGenericDao#get(java.io.Serializable)
	 */
	public T get(ID id);

	/**
	 * @param id
	 * @see com.vteba.tx.generic.IGenericDao#delete(java.io.Serializable)
	 */
	public void delete(ID id);

	/**
	 * @param hql
	 * @param clazz
	 * @param values
	 * @return
	 * @see com.vteba.tx.hibernate.IHibernateGenericDao#getListByHql(java.lang.String, java.lang.Class, java.lang.Object[])
	 */
	public <E> List<E> getListByHql(String hql, Class<E> clazz, Object... values);

	/**
	 * @param entity
	 * @see com.vteba.tx.generic.IGenericDao#delete(java.lang.Object)
	 */
	public void delete(T entity);

	/**
	 * @param namedQuery
	 * @param clazz
	 * @param values
	 * @return
	 * @see com.vteba.tx.hibernate.IHibernateGenericDao#getListByNamedHql(java.lang.String, java.lang.Class, java.lang.Object[])
	 */
	public <E> List<E> getListByNamedHql(String namedQuery, Class<E> clazz, Object... values);

	/**
	 * @param sql
	 * @param values
	 * @return
	 * @see com.vteba.tx.hibernate.IHibernateGenericDao#getEntityListBySql(java.lang.String, java.lang.Object[])
	 */
	public List<T> getEntityListBySql(String sql, Object... values);

	/**
	 * @param namedSql
	 * @param values
	 * @return
	 * @see com.vteba.tx.hibernate.IHibernateGenericDao#getEntityListByNamedSql(java.lang.String, java.lang.Object[])
	 */
	public List<T> getEntityListByNamedSql(String namedSql, Object... values);

	/**
	 * @param sql
	 * @param clazz
	 * @param values
	 * @return
	 * @see com.vteba.tx.hibernate.IHibernateGenericDao#getListBySql(java.lang.String, java.lang.Class, java.lang.Object[])
	 */
	public <E> List<E> getListBySql(String sql, Class<E> clazz, Object... values);

	/**
	 * @param namedSql
	 * @param resultClass
	 * @param values
	 * @return
	 * @see com.vteba.tx.hibernate.IHibernateGenericDao#getListByNamedSql(java.lang.String, java.lang.Class, java.lang.Object[])
	 */
	public <E> List<E> getListByNamedSql(String namedSql, Class<E> resultClass, Object... values);

	/**
	 * @param entityClass
	 * @return
	 * @see com.vteba.tx.hibernate.IHibernateGenericDao#getAll(java.lang.Class)
	 */
	public <X> List<X> getAll(Class<X> entityClass);

	/**
	 * 根据属性equal查询，使用QBE实现
	 * @param detachedCriteria 携带查询条件，DetachedCriteria实例，复杂条件
	 * @return list 查询结果List&lt;T&gt;
	 */
	public List<T> getListByCriteria(DetachedCriteria detachedCriteria);
	
	/**
	 * 根据属性equal查询，使用QBE实现
	 * @param model 携带查询条件，实体实例，简单条件，一般是等于
	 * @param detachedCriteria 携带查询条件，DetachedCriteria实例，复杂条件
	 * @return list 查询结果List&lt;T&gt;
	 */
	public List<T> getListByCriteria(T model, DetachedCriteria detachedCriteria);
	
	/**
	 * 根据属性equal查询，使用QBE实现
	 * @param model 携带查询条件model
	 * @return list 查询结果List&lt;T&gt;
	 */
	public List<T> getListByCriteria(T model);
	
	/**
	 * 根据属性equal查询，使用QBE实现
	 * @param model 携带查询条件model
	 * @param orderMaps 使用Map传参，key是排序字段，value是asc或desc。
	 * @return list 查询结果List&lt;T&gt;
	 * @see 
	 */
	public List<T> getListByCriteria(T model, Map<String, String> orderMaps);
	
	/**
	 * @param entityClass
	 * @param model
	 * @param orderMaps
	 * @return
	 * @see com.vteba.tx.hibernate.IHibernateGenericDao#getListByCriteria(java.lang.Class, java.lang.Object, java.util.Map)
	 */
	public <X> List<X> getListByCriteria(Class<X> entityClass, X model, Map<String, String> orderMaps);

	/**
	 * String属性like查询，其它等于，使用QBE实现
	 * @param model 携带查询条件model
	 * @return list 查询结果List&lt;X&gt;
	 */
	public List<T> getListByCriteriaLike(T model);
	
	/**
	 * String属性like查询，其它等于，使用QBE实现
	 * @param model 携带查询条件model
	 * @param orderMaps 使用Map传参，key是排序字段，value是asc或desc
	 * @return list 查询结果List&lt;X&gt;
	 */
	public List<T> getListByCriteriaLike(T model, Map<String, String> orderMaps);
	
	/**
	 * String属性like查询，其它等于，使用QBE实现
	 * @param entityClass
	 * @param model
	 * @param orderMaps
	 * @return
	 * @see com.vteba.tx.hibernate.IHibernateGenericDao#getListByCriteriaLike(java.lang.Class, java.lang.Object, java.util.Map)
	 */
	public <X> List<X> getListByCriteriaLike(Class<X> entityClass, X model, Map<String, String> orderMaps);

	/**
	 * @param sql
	 * @param values
	 * @return
	 * @see com.vteba.tx.hibernate.IHibernateGenericDao#sqlQueryForObject(java.lang.String, java.lang.Object[])
	 */
	public List<Object[]> sqlQueryForObject(String sql, Object... values);

	/**
	 * @param sql
	 * @param clazz
	 * @param values
	 * @return
	 * @see com.vteba.tx.hibernate.IHibernateGenericDao#sqlQueryForList(java.lang.String, java.lang.Class, java.lang.Object[])
	 */
	public <X> List<X> sqlQueryForList(String sql, Class<X> clazz, Object... values);

	/**
	 * @param sql
	 * @param clazz
	 * @param values
	 * @return
	 * @see com.vteba.tx.hibernate.IHibernateGenericDao#sqlQueryForObject(java.lang.String, java.lang.Class, java.lang.Object[])
	 */
	public <X> X sqlQueryForObject(String sql, Class<X> clazz, Object... values);

	/**
	 * @param hql
	 * @param namedQuery
	 * @param values
	 * @return
	 * @see com.vteba.tx.hibernate.IHibernateGenericDao#hqlQueryForObject(java.lang.String, boolean, java.lang.Object[])
	 */
	public List<Object[]> hqlQueryForObject(String hql, boolean namedQuery, Object... values);

	/**
	 * @param hql
	 * @param clazz
	 * @param values
	 * @return
	 * @see com.vteba.tx.hibernate.IHibernateGenericDao#hqlQueryForList(java.lang.String, java.lang.Class, java.lang.Object[])
	 */
	public <X> List<X> hqlQueryForList(String hql, Class<X> clazz, Object... values);

	/**
	 * @param hql
	 * @param clazz
	 * @param values
	 * @return
	 * @see com.vteba.tx.hibernate.IHibernateGenericDao#hqlQueryForObject(java.lang.String, java.lang.Class, java.lang.Object[])
	 */
	public <X> X hqlQueryForObject(String hql, Class<X> clazz, Object... values);

	/**
	 * 获得唯一实体，请确保属性具有唯一性
	 * @param propertyName 属性名
	 * @param value 属性值
	 * @return 实体&lt;T&gt;
	 */
	public T uniqueResultByCriteria(String propertyName, Object value);
	
	/**
	 * @param entityClass
	 * @param propertyName
	 * @param value
	 * @return
	 * @see com.vteba.tx.hibernate.IHibernateGenericDao#uniqueResultByCriteria(java.lang.Class, java.lang.String, java.lang.Object)
	 */
	public <X> X uniqueResultByCriteria(Class<X> entityClass, String propertyName, Object value);

	/**
	 * 获得唯一实体，请确保属性具有唯一性
	 * @param params 携带查询参数，key为属性名，value为值
	 * @return 实体&lt;X&gt;
	 * @author yinlei
	 * date 2013-6-11 下午5:19:04
	 */
	public T uniqueResultByCriteria(Map<String, Object> params);
	
	/**
	 * @param entityClass
	 * @param params
	 * @return
	 * @see com.vteba.tx.hibernate.IHibernateGenericDao#uniqueResultByCriteria(java.lang.Class, java.util.Map)
	 */
	public <X> X uniqueResultByCriteria(Class<X> entityClass, Map<String, Object> params);

	/**
	 * 获得唯一实体，请确保属性具有唯一性
	 * @param model 携带查询参数实体
	 * @return 实体&lt;T&gt;实例
	 * @author yinlei
	 * date 2013-6-11 下午5:21:11
	 */
	public T uniqueResultByCriteria(T model);
	
	/**
	 * 获得唯一实体，请确保属性具有唯一性
	 * @param entityClass 要查询的实体类
	 * @param model 携带查询参数实体
	 * @return 实体&lt;X&gt;实例
	 * @author yinlei
	 * date 2013-6-11 下午5:22:34
	 */
	public <X> X uniqueResultByCriteria(Class<X> entityClass, X model);
	
	/**
	 * 使用hql获得唯一实体。<br>
	 * 1、hql应查询Class&lt;T&gt;实例所有的属性，如：select s from Subject s where .... 。<br>
	 * 2、使用new T()构造函数指定属性，如：select new Subject(id, subjectCode, subjectName, level) 
	 *    from Subject s where .... 同时Subject实体中要有对应的构造函数才行。<br>
	 * 3、查询任意栏位，hql中的栏位名或别名要和Class&lt;T&gt;实例中的属性名一致。使用AliasedResultTransformer转换任意列。<br>
	 * @param hql 查询语句
	 * @param values hql中绑定的参数值
	 * @return 当前实体&lt;T&gt;
	 */
	public T uniqueResultByHql(String hql, Object... values);
	
	/**
	 * 使用命名hql获得唯一实体。<br>
	 * 1、hql应查询Class&lt;T&gt;实例所有的属性，如：select s from Subject s where .... 。<br>
	 * 2、使用new T()构造函数指定属性，如：select new Subject(id, subjectCode, subjectName, level) 
	 *    from Subject s where .... 同时Subject实体中要有对应的构造函数才行。<br>
	 * 3、查询任意栏位，hql中的栏位名或别名要和Class&lt;T&gt;实例中的属性名一致。使用AliasedResultTransformer转换任意列。<br>
	 * @param hql 查询语句
	 * @param values hql中绑定的参数值
	 * @return 当前实体&lt;T&gt;
	 */
	public T uniqueResultByNamedHql(String hql, Object... values);
	
	/**
	 * @param hql
	 * @param namedQuery
	 * @param values
	 * @return
	 * @see com.vteba.tm.hibernate.IHibernateGenericDao#uniqueResultByHql(java.lang.String, boolean, java.lang.Object[])
	 */
	//public T uniqueResultByHql(String hql, boolean namedQuery, Object... values);
	
	/**
	 * @param hql
	 * @param resultClass
	 * @param namedQuery
	 * @param values
	 * @return
	 * @see com.vteba.tx.hibernate.IHibernateGenericDao#uniqueResultByHql(java.lang.String, java.lang.Class, boolean, java.lang.Object[])
	 */
	public <X> X uniqueResultByHql(String hql, Class<X> resultClass, boolean namedQuery, Object... values);

	/**
	 * @param sql
	 * @param values
	 * @return
	 * @see com.vteba.tx.hibernate.IHibernateGenericDao#uniqueResultBySql(java.lang.String, java.lang.Object[])
	 */
	public T uniqueResultBySql(String sql, Object... values);

	/**
	 * @param sql
	 * @param resultClass
	 * @param values
	 * @return
	 * @see com.vteba.tx.hibernate.IHibernateGenericDao#getUniqueResultBySql(java.lang.String, java.lang.Class, java.lang.Object[])
	 */
	public <X> X uniqueResultBySql(String sql, Class<X> resultClass, Object... values);

	/**
	 * @param hql
	 * @param namedQuery
	 * @param values
	 * @return
	 * @see com.vteba.tx.hibernate.IHibernateGenericDao#executeUpdateByHql(java.lang.String, boolean, java.lang.Object[])
	 */
	public int executeHqlUpdate(String hql, boolean namedQuery, Object... values);

	/**
	 * @param sql
	 * @param values
	 * @return
	 * @see com.vteba.tx.hibernate.IHibernateGenericDao#executeUpdateBySql(java.lang.String, java.lang.Object[])
	 */
	public int executeSqlUpdate(String sql, Object... values);

	/**
	 * @param page
	 * @param entity
	 * @return
	 * @see com.vteba.tx.hibernate.IHibernateGenericDao#queryForPageByCriteria(com.vteba.tx.generic.Page, java.lang.Object)
	 */
	public Page<T> queryForPageByCriteria(Page<T> page, T entity);

	/**
	 * @param page
	 * @param entity
	 * @param objects
	 * @return
	 * @see com.vteba.tx.hibernate.IHibernateGenericDao#queryForPageByLeftJoin(com.vteba.tx.generic.Page, java.lang.Object, java.lang.Object[])
	 */
	public Page<T> queryForPageByLeftJoin(Page<T> page, T entity, Object... objects);

	/**
	 * @param page
	 * @param entity
	 * @param objects
	 * @return
	 * @see com.vteba.tx.hibernate.IHibernateGenericDao#queryForPageBySubSelect(com.vteba.tx.generic.Page, java.lang.Object, java.lang.Object[])
	 */
	public Page<T> queryForPageBySubSelect(Page<T> page, T entity, Object... objects);

	/**
	 * @param page
	 * @param hql
	 * @param values
	 * @return
	 * @see com.vteba.tx.hibernate.IHibernateGenericDao#queryForPageByHql(com.vteba.tx.generic.Page, java.lang.String, java.lang.Object[])
	 */
	public Page<T> queryForPageByHql(Page<T> page, String hql, Object... values);

	/**
	 * 分页查询但是不返回总记录数。
	 * @param page 分页参数
	 * @param hql hql语句
	 * @param values hql参数
	 * @return 结果List
	 * @author yinlei
	 * @date 2013年10月4日 下午5:32:29
	 */
	public List<T> pagedQueryByHql(Page<T> page, String hql, Object... values);
	
	/**
	 * @param page
	 * @param sql
	 * @param values
	 * @return
	 * @see com.vteba.tx.hibernate.IHibernateGenericDao#queryForPageBySql(com.vteba.tx.generic.Page, java.lang.String, java.lang.Object[])
	 */
	public Page<T> queryForPageBySql(Page<T> page, String sql, Object... values);

	/**
	 * @param hql
	 * @param values
	 * @return
	 * @see com.vteba.tm.hibernate.IHibernateGenericDao#countHqlResult(java.lang.String, java.lang.Object[])
	 */
	//public long countHqlResult(String hql, Object... values);

	/**
	 * @param sql
	 * @param values
	 * @return
	 * @see com.vteba.tm.hibernate.IHibernateGenericDao#countSqlResult(java.lang.String, java.lang.Object[])
	 */
	//public long countSqlResult(String sql, Object... values);

	/**
	 * @param sequenceName
	 * @return
	 * @see com.vteba.tm.hibernate.IHibernateGenericDao#getSequenceLongValue(java.lang.String)
	 */
	//public Long getSequenceLongValue(String sequenceName);

	/**
	 * 批量保存一批数据
	 * @param list 实体list
	 * @param batchSize 批次大小，每batchSize flush一次
	 * @author yinlei
	 * date 2012-7-7 下午10:10:15
	 */
	public void saveEntityBatch(List<T> list, int batchSize);

	/**
	 * 根据id list批量删除实体
	 * @param list ids列表
	 * @author yinlei
	 * date 2012-9-5 下午3:34:40
	 */
	public void deleteEntityBatch(List<ID> list);
	
//	public ID save(T model);
//
//    public void saveOrUpdate(T model);
//    
//    public void update(T model);
//    
//    public T merge(T model);
//    
//    public void persist(T entity);
//
//    public void delete(ID id);
//
//    public void delete(T model);
//
//    public T get(ID id);
//    
//    public T load(ID id);
//    
//    /**
//     * 慎用，确保不会返回很多数据，否则可能导致内存溢出
//     */
//    public List<T> listAll();
//    
//    /**
//     * 根据hql查询
//     * @param hql hql语句
//     * @param values 查询参数
//     * @author yinlei
//     * date 2012-6-22 下午3:59:03
//     */
//	public List<T> queryListByHql(String hql, Object... values);
//    
//    /**
//     * hql命名查询
//     * @param namedQuery 命名hql
//     * @param values 查询参数
//     * @author yinlei
//     * date 2012-7-11 下午11:59:02
//     */
//    public List<T> queryListByNamedHql(String namedQuery, Object... values);
//    
//    /**
//     * String属性进行like匹配，其余相等，QBE Query
//     * @param entityClass 实体类
//     * @param entity 查询model
//     * @param objects 使用Map传参，key是排序字段，value是asc或desc
//     * @author yinlei
//     * @date 2012-6-22 下午3:44:56
//     */
//    public List<T> queryListByPropertyLike(Class<T> entityClass, T entity, Object... objects);
//    
//    /**
//     * 根据属性值相等进行查询，QBE Query
//     * @param entityClass 实体类
//     * @param entity 查询model
//     * @param objects 使用Map传参，key是排序字段，value是asc或desc
//     * @author yinlei
//     * @date 2012-6-22 下午3:51:54
//     */
//    public List<T> queryListByPropertyEqual(Class<T> entityClass, T entity, Object... objects);
//    
//    /**
//     * 使用hql，查询分页数据
//     */
//    public Page<T> queryPageListByHql(Page<T> page, String hql, Object... values);
//    
//    /**
//     * 使用sql，查询分页数据
//     */
//    public Page<T> queryPageListBySql(Page<T> page, String sql, Object... values);
//    
//    /**
//     * 根据model查询，String like，other equal
//     * @param page 分页数据
//     * @param entity 查询参数
//     * @author yinlei
//     * @date 2012-6-22 下午3:44:25
//     */
//    public Page<T> queryPageListByModel(Page<T> page, T entity);
//    
//    /**
//     * 分页查询，使用criteria实现，第二次查询立即初始化延迟加载的集合
//     * @param page 分页数据
//     * @param entity 实体，携带查询条件
//     * @param objects 实体中延迟加载的集合的名字
//     * @return 查询出来的数据
//     * @author yinlei
//     * date 2012-6-22 下午4:10:34
//     */
//    public Page<T> queryPageListByModelSelect(Page<T> page, T entity, Object... objects);
//    
//    /**
//     * 分页查询，使用criteria实现，左外连接立即初始化延迟加载的集合
//     * @param page 分页数据
//     * @param entity 实体
//     * @param objects 实体中延迟加载的集合的名字
//     * @author yinlei
//     * date 2012-6-26 下午4:51:48
//     */
//    public Page<T> getPageListByModelLeftJoin(Page<T> page, T entity, Object... objects);
//    
//	public List<Object[]> sqlQueryForObject(String sql, Object... values);
//	
//	/**
//	 * hql查询返回List<String>
//	 * @param hql hql语句
//	 * @param namedQuery 是否命名查询
//	 * @param values 查询参数
//	 * @author yinlei
//	 * date 2012-7-11 下午11:10:13
//	 */
//	public List<Object[]> hqlQueryForObject(String hql, boolean namedQuery, Object... values);
//	
//	/**
//	 * 获得唯一结果，调用时请确保属性具有唯一性
//	 * @param entityClass 实体类
//	 * @param propertyName 属性名
//	 * @param value 属性值
//	 */
//	public <X> X getUniqueResultByProperty(Class<X> entityClass, String propertyName, Object value);
//	
//	/**
//	 * 获得唯一结果，调用时请确保属性具有唯一性
//	 * @param entityClass 实体类
//	 * @param params 参数Map，key是属性名，value是属性值
//	 */
//	public <X> X getUniqueResultByProperty(Class<X> entityClass, Map<String, Object> params);
//	
//	/**
//	 * 使用hql获得唯一实体
//	 * @param hql hql语句 
//	 * @paran namedQuery 是否命名查询
//	 * @param values hql查询参数
//	 * @return
//	 */
//	public T uniqueResultByHql(String hql, boolean namedQuery, Object... values);
//	
//	/**
//	 * 使用sql获得唯一实体
//	 * @param sql sql语句
//	 * @param values 查询参数
//	 * @return
//	 */
//	public T uniqueResultBySql(String sql, Object... values);
//	
//	/**
//	 * 通过hql获得唯一实体
//	 * @param <X> 查询返回的实体
//	 * @param hql hql语句
//	 * @param clazz 返回实体类型
//	 * @param namedQuery 是否命名查询
//	 * @param values 查询参数
//	 * @return
//	 */
//	public <X> X getUniqueResultByHql(String hql, Class<X> clazz, boolean namedQuery, Object... values);
//	
//	/**
//	 * 通过sql获得唯一实体
//	 * @param <X> 查询返回的实体
//	 * @param sql sql语句
//	 * @param clazz 查询的实体类型
//	 * @param values 查询参数
//	 * @return
//	 */
//	public <X> X getUniqueResultBySql(String sql, Class<X> clazz, Object... values);
//	
//	/**
//	 * 批量保存一批数据
//	 * @param list 实体list
//	 * @param batchSize 批次大小，每batchSize flush一次
//	 * @author yinlei
//	 * date 2012-7-7 下午10:10:15
//	 */
//	public void saveEntityBatch(List<T> list, int batchSize);
//	
//	/**
//	 * 根据id list批量删除实体
//	 * @param list ids列表
//	 * @author yinlei
//	 * date 2012-9-5 下午3:34:40
//	 */
//	public void deleteEntityBatch(List<ID> list);
//	
//	/**
//	 * 执行批量hql
//	 * @param hql hql语句
//	 * @param namedQuery 是否命名查询
//	 * @param values 查询参数
//	 * @return
//	 */
//	public int batchExecuteHql(String hql, boolean namedQuery, Object... values);
//	
//	/**
//	 * 执行批量sql
//	 * @param sql sql语句
//	 * @param values 查询参数
//	 * @return
//	 */
//	public int batchExecuteSql(String sql, Object... values);
    
}
