package com.vteba.persister.hibernate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.vteba.persister.generic.IGenericDao;
import com.vteba.persister.generic.Page;

/**
 * Hibernate 泛型 DAO接口，简化Entity DAO实现。
 * @author yinlei 
 * date 2012-5-6 下午10:42:35
 * @param <T> 实体类型
 * @param <ID> 主键类型，一般是String或者Long
 */
public interface IHibernateGenericDao<T, ID extends Serializable> extends IGenericDao<T, ID> {
	
	/**
	 * 通过hql查询当前实体Class&ltT&gt实例的List&ltT&gt。<br>
	 * 1、hql应查询Class&ltT&gt实例所有的属性，如：select s from Subject s where .... 。<br>
	 * 2、使用new T()构造函数指定属性，如：select new Subject(id, subjectCode, subjectName, level) 
	 *    from Subject s where .... 同时Subject实体中要有对应的构造函数才行。<br>
	 * 3、否则返回List&ltObject[]&gt。
	 * @param hql 可用Jpa风格参数： ?1、?2，命名参数： :subjectName，Hibernate参数： ? (deprecated)
	 * @param values hql参数，可以使用单个参数，Map，List，AstModel实例，传参。
	 */
	public List<T> getEntityListByHql(String hql, Object... values);
	
	/**
	 * 通过命名hql查询当前实体Class&ltT&gt实例的List&ltT&gt。<br>
	 * 1、hql应查询Class&ltT&gt实例所有的属性，如：select s from Subject s where .... 。<br>
	 * 2、使用new T()构造函数指定属性，如：select new Subject(id, subjectCode, subjectName, level) 
	 *    from Subject s where .... 同时Subject实体中要有对应的构造函数才行。<br>
	 * 3、否则返回List&ltObject[]&gt。
	 * @param namedQuery 命名hql语句名，可用Jpa风格参数： ?1、?2，命名参数： :subjectName
	 * @param values hql参数，可以使用单个参数，Map，List，AstModel实例，传参。
	 */
	public List<T> getEntityListByNamedHql(String namedQuery, Object... values);
	
	/**
	 * 通过hql查询Class&ltE&gt实例的List&ltE&gt。hql语句可进行多实体连接。<br>
	 * 1、hql应查询Class&ltE&gt实例所有的属性，如：select s from Subject s where .... 。<br>
	 * 2、使用new E()构造函数指定属性，如：select new Subject(id, subjectCode, subjectName, level) 
	 *    from Subject s where .... 同时Subject实体中要有对应的构造函数才行。<br>
	 * 3、查询任意栏位，hql中的栏位名或别名要和Class&ltE&gt实例中的属性名一致。使用AliasedResultTransformer转换任意列。<br>
	 * 4、否则返回List&ltObject[]&gt。
	 * @param hql 可用Jpa风格参数： ?1、?2，命名参数： :subjectName，Hibernate参数： ? (deprecated)
	 * @param clazz 结果类型Class&ltE&gt。
	 * @param values hql参数，可以使用单个参数，Map，List，AstModel实例，传参。
	 * @author yinlei
	 * date 2012-12-17 下午10:35:09
	 */
	public <E> List<E> getListByHql(String hql, Class<E> clazz, Object... values);
	
	/**
	 * 通过hql查询Class&ltE&gt实例的List&ltE&gt。hql语句可进行多实体连接。<br>
	 * 1、hql应查询Class&ltE&gt实例所有的属性，如：select s from Subject s where .... 。<br>
	 * 2、使用new E()构造函数指定属性，如：select new Subject(id, subjectCode, subjectName, level) 
	 *    from Subject s where .... 同时Subject实体中要有对应的构造函数才行。<br>
	 * 3、查询任意栏位，hql中的栏位名或别名要和Class&ltE&gt实例中的属性名一致。使用AliasedResultTransformer转换任意列。<br>
	 * 4、否则返回List&ltObject[]&gt。
	 * @param namedQuery 命名hql语句名，可用Jpa风格参数： ?1、?2，命名参数： :subjectName
	 * @param clazz 结果类型Class&ltE&gt。
	 * @param values hql参数，可以使用单个参数，Map，List，AstModel实例，传参。
	 * @author yinlei
	 * date 2012-12-17 下午10:35:09
	 */
	public <E> List<E> getListByNamedHql(String namedQuery, Class<E> clazz, Object... values);
	
	/**
	 * 通过sql查询当前实体Class&ltT&gt实例的List&ltT&gt。<br>
	 * 1、sql栏位或者别名要和实体的属性一致，栏位和实体属性名不一致要指定别名。<br>
	 * 如：select id, subject_code subjectCode, subject_name subjectName from subject s where ....<br>
	 * 其中id属性和sql栏位一样，不需要指定别名。<br>
	 * 2、基于别名，使用AliasedResultTransformer，可转换任意列。
	 * @param sql 要执行的sql
	 * @param values sql参数值
	 */
	public List<T> getEntityListBySql(String sql, Object... values);
	
	/**
	 * 通过命名sql查询当前实体Class&ltT&gt实例的List&ltT&gt。<br>
	 * 1、命名sql中配置了resultClass或resultSetMapping，按规则转换。<br>
	 * 2、如果命名sql中没有配置resultClass或resultSetMapping，返回List&ltObject[]&gt。
	 *    可能出现转型错误。不建议(deprecated)这么用。<br>
	 * 3、如果没有配置resultClass或resultSetMapping，建议指定sql栏位别名使用{@link IHibernateGenericDao#getListByNamedSql}
	 * @param namedSql 命名sql名
	 * @param values 命名sql参数
	 * @author yinlei
	 * date 2012-12-17 下午9:33:29
	 */
	public List<T> getEntityListByNamedSql(String namedSql, Object... values);
	
	/**
	 * 根据sql查询实体List&ltE&gt，将结果集转换为Class&ltE&gt的实例。可多表连接。<br>
	 * 1、sql栏位或者别名要和实体的属性一致，栏位和实体属性名不一致要指定别名。<br>
	 *    如：select id, subject_code subjectCode, subject_name subjectName from subject s where ....<br>
	 *    其中id属性和sql栏位一样，不需要指定别名。<br>
	 * 2、基于别名，使用AliasedResultTransformer，可转换任意列。
	 * @param sql sql语句
	 * @param clazz 结果集Class&ltE&gt类
	 * @param values sql中的参数
	 * @author yinlei
	 * date 2012-12-17 下午10:47:38
	 */
	public <E> List<E> getListBySql(String sql, Class<E> clazz, Object... values);
	
	/**
	 * 根据命名sql查询实体List&ltE&gt，将结果集转换为Class&ltE&gt的实例。可多表连接。<br>
	 * 1、sql栏位或者别名要和实体的属性一致，栏位和实体属性名不一致要指定别名。<br>
	 *    如：select id, subject_code subjectCode, subject_name subjectName from subject s where ....<br>
	 *    其中id属性和sql栏位一样，不需要指定别名。<br>
	 * 2、基于别名，使用AliasedResultTransformer，可转换任意列。
	 * @param namedSql 命名sql名
	 * @param resultClass 结果集Class&ltE&gt类
	 * @param values sql参数
	 * @return 实体List
	 * @author yinlei
	 * date 2013-6-10 下午3:54:54
	 */
	public <E> List<E> getListByNamedSql(String namedSql, Class<E> resultClass, Object... values);
	
	/**
	 * 获得指定entity的实体list，<em>慎用</em>，确保不会返回很多对象。
	 * @param entityClass 实体class
	 */
	public <X> List<X> getAll(Class<X> entityClass);
	
	/**
	 * 强制hibernate将对象与数据库同步。
	 */
	public void flush();
	
	/**
	 * 清空hibernate的session缓存，慎用。
	 */
	public void clear();
	
	/**
	 * 根据属性equal查询，使用QBE实现
	 * @param entityClass 要查询的实体类
	 * @param model 携带查询条件model
	 * @param objects 使用Map传参，key是排序字段，value是asc或desc。
	 * @return list 查询结果List&ltX&gt
	 */
	public <X> List<X> getListByPropertyEqual(Class<X> entityClass, X model, Object... objects);
	
	/**
	 * 根据属性like查询，使用QBE实现
	 * @param entityClass 要查询的实体类
	 * @param model 携带查询条件model
	 * @param objects 使用Map传参，key是排序字段，value是asc或desc
	 * @return list 查询结果List&ltX&gt
	 */
	public <X> List<X> getListByPropertyLike(Class<X> entityClass, X model, Object... objects);

	/**
	 * sql查询标量值，返回List&ltObject[]&gt
	 * @param sql sql语句
	 * @param values sql参数值
	 * @return List&ltObject[]&gt
	 * @author yinlei
	 * date 2013-6-11 下午5:10:53
	 */
	public List<Object[]> sqlQueryForObject(String sql, Object... values);
	
	/**
	 * sql查询基本类型及其封装类，String，Date和大数值List&ltX&gt
	 * @param sql sql语句
	 * @param clazz 要转换的基本类型
	 * @param values sql参数值
	 * @return 基本类型List&ltX&gt
	 * @author yinlei
	 * date 2013-6-11 下午5:09:08
	 */
	public <X> List<X> sqlQueryForList(String sql, Class<X> clazz, Object... values);
	
	/**
	 * sql查询基本类型及其封装类，String，Date和大数值
	 * @param sql sql语句
	 * @param clazz 要转换的基本类型，String，Date和大数值
	 * @param values sql参数
	 * @return 基本类型值
	 * @author yinlei
	 * date 2013-6-4 下午10:17:32
	 */
	public <X> X sqlQueryForObject(String sql, Class<X> clazz, Object... values);

	/**
	 * hql查询标量值，返回List&ltObject[]&gt
	 * @param hql hql语句
	 * @param namedQuery 是否命名hql
	 * @param values hql参数
	 * @return List&ltObject[]&gt
	 * @author yinlei
	 * date 2013-6-11 下午5:12:18
	 */
	public List<Object[]> hqlQueryForObject(String hql, boolean namedQuery, Object... values);
	
	/**
	 * hql查询基本类型及其封装类，String，Date和大数值List&ltX&gt
	 * @param hql hql语句
	 * @param clazz 基本类型
	 * @param values hql参数值
	 * @return List&ltX&gt
	 * @author yinlei
	 * date 2013-6-11 下午5:13:42
	 */
	public <X> List<X> hqlQueryForList(String hql, Class<X> clazz, Object... values);
	
	/**
	 * hql查询基本类型及其封装类，String，Date和大数值
	 * @param hql hql语句
	 * @param clazz 要转换的基本类型
	 * @param values hql参数
	 * @return 基本类型值
	 * @author yinlei
	 * date 2013-6-4 下午10:15:36
	 */
	public <X> X hqlQueryForObject(String hql, Class<X> clazz, Object... values);
	
	/**
	 * 获得唯一实体，请确保属性具有唯一性
	 * @param entityClass 要查询的实体类
	 * @param propertyName 属性名
	 * @param value 属性值
	 * @return 实体&ltX&gt
	 */
	public <X> X getUniqueResultByProperty(Class<X> entityClass, String propertyName, Object value);
	
	/**
	 * 获得唯一实体，请确保属性具有唯一性
	 * @param entityClass 要查询的实体类
	 * @param params 携带查询参数，key为属性名，value为值
	 * @return 实体&ltX&gt
	 * @author yinlei
	 * date 2013-6-11 下午5:19:04
	 */
	public <X> X getUniqueResultByProperty(Class<X> entityClass, Map<String, Object> params);
	
	/**
	 * 使用hql获得唯一实体。<br>
	 * 1、hql应查询Class&ltT&gt实例所有的属性，如：select s from Subject s where .... 。<br>
	 * 2、使用new T()构造函数指定属性，如：select new Subject(id, subjectCode, subjectName, level) 
	 *    from Subject s where .... 同时Subject实体中要有对应的构造函数才行。<br>
	 * 3、查询任意栏位，hql中的栏位名或别名要和Class&ltT&gt实例中的属性名一致。使用AliasedResultTransformer转换任意列。<br>
	 * @param hql 查询语句
	 * @param namedQuery 是否命名查询
	 * @param values hql中绑定的参数值
	 * @return 当前实体&ltT&gt
	 */
	public T uniqueResultByHql(String hql, boolean namedQuery, Object... values);
	
	/**
	 * 使用sql获得唯一实体<br>
	 * 1、sql栏位或者别名要和实体的属性一致，栏位和实体属性名不一致要指定别名。<br>
	 * 如：select id, subject_code subjectCode, subject_name subjectName from subject s where ....<br>
	 * 其中id属性和sql栏位一样，不需要指定别名。<br>
	 * 2、基于别名，使用AliasedResultTransformer，可转换任意列。
	 * @param sql 要执行的sql
	 * @param values sql绑定的参数
	 * @return 当前实体&ltT&gt
	 */
	public T uniqueResultBySql(String sql, Object...values);
	
	/**
	 * 通过hql获得唯一实体，hql语句可进行多实体连接。<br>
	 * 1、hql应查询Class&ltX&gt实例所有的属性，如：select s from Subject s where .... 。<br>
	 * 2、使用new X()构造函数指定属性，如：select new Subject(id, subjectCode, subjectName, level) 
	 *    from Subject s where .... 同时Subject实体中要有对应的构造函数才行。<br>
	 * 3、查询任意栏位，hql中的栏位名或别名要和Class&ltX&gt实例中的属性名一致。使用AliasedResultTransformer转换任意列。<br>
	 * @param <X> 查询的实体
	 * @param hql 要执行的hql语句
	 * @param resultClass 结果类型Class&ltX&gt
	 * @param namedQuery 是否命名查询
	 * @param values hql参数值
	 * @return 实体&ltX&gt
	 */
	public <X> X getUniqueResultByHql(String hql, Class<X> resultClass, boolean namedQuery, Object... values);
	
	/**
	 * 通过sql获得唯一实体<br>
	 * 1、sql栏位或者别名要和实体的属性一致，栏位和实体属性名不一致要指定别名。<br>
	 * 如：select id, subject_code subjectCode, subject_name subjectName from subject s where ....<br>
	 * 其中id属性和sql栏位一样，不需要指定别名。<br>
	 * 2、基于别名，使用AliasedResultTransformer，可转换任意列。
	 * @param <X> 要查询的实体
	 * @param sql 要执行的sql语句
	 * @param resultClass 结果类型Class&ltX&gt
	 * @param values sql参数值
	 * @return 实体&ltX&gt
	 */
	public <X> X getUniqueResultBySql(String sql, Class<X> resultClass, Object... values);
	
	/**
	 * 执行任意hql，常用于update，delete，insert
	 * @param hql 要执行的hql
	 * @param values hql中绑定的参数值
	 * @param namedQuery 是否命名查询
	 * @return 影响的实体数
	 */
	public int executeUpdateByHql(String hql, boolean namedQuery, Object... values);
	
	/**
	 * 执行任意sql，常用于update，delete，insert
	 * @param sql 要执行的sql
	 * @param values sql中绑定的参数值
	 * @return 影响的记录数
	 */
	public int executeUpdateBySql(String sql, Object... values);
	
	/**
	 * 初始化延迟加载的对象，load默认延迟加载
	 */
	public void initProxyObject(Object proxy);
	
	/**
	 * 分页查询，使用criteria实现
	 * @param page 分页数据
	 * @param entity 携带查询条件
	 * @return Page&ltT&gt分页，携带查询结果
	 * @author yinlei
	 * date 2012-7-8 下午10:34:23
	 */
	public Page<T> queryForPageByModel(Page<T> page, T entity);
	
	/**
	 * 分页查询，使用criteria实现，左外连接立即初始化延迟加载的集合
	 * @param page 分页数据
	 * @param entity 实体
	 * @param objects 实体中延迟加载的集合的名字
	 * @return Page&ltT&gt分页，携带查询结果
	 * @author yinlei
	 * date 2012-6-26 下午4:50:03
	 */
	public Page<T> queryForPageByLeftJoin(Page<T> page, T entity, Object... objects);
	
	/**
	 * 分页查询，使用criteria实现，第二个select立即初始化延迟加载的集合
	 * @param page 分页数据
	 * @param entity 实体
	 * @param objects 实体中延迟加载的集合的名字
	 * @return Page&ltT&gt分页，携带查询结果
	 * @author yinlei
	 * date 2012-6-26 下午4:50:03
	 */
	public Page<T> queryForPageBySubSelect(Page<T> page, T entity, Object... objects);
	
	/**
	 * 使用hql进行分页查询
	 * @param page 分页条件
	 * @param hql hql语句
	 * @param values hql参数值
	 * @return Page&ltT&gt分页，携带查询结果
	 * @author yinlei
	 * date 2013-6-11 下午5:28:27
	 */
	public Page<T> queryForPageByHql(Page<T> page, String hql, Object... values);
	
	/**
	 * 使用sql进行分页查询
	 * @param page 分页条件
	 * @param sql sql语句
	 * @param values sql参数值
	 * @return Page&ltT&gt分页，携带查询结果
	 * @author yinlei
	 * date 2013-6-11 下午5:28:32
	 */
	public Page<T> queryForPageBySql(Page<T> page, String sql, Object... values);

	/**
	 * 统计hql查询返回多少条记录，分页查询使用
	 * @param hql 要执行的hql
	 * @param values hql绑定的参数值
	 * @return 记录数
	 * @author yinlei
	 * date 2012-5-14 下午11:39:33
	 */
	public long countHqlResult(String hql, Object... values);
	/**
	 * 统计sql查询返回多少条记录，分页查询使用
	 * @param sql 要执行的sql
	 * @param values sql绑定的参数值
	 * @return 记录数
	 * @author yinlei
	 * date 2012-5-14 下午11:40:33
	 */
	public long countSqlResult(String sql, Object... values);
	
	/**
	 * table模拟sequence，使用mysql function实现
	 * @param sequenceName sequence表中，seq_name ID
	 * @return Long sequence值
	 * @author yinlei
	 * date 2012-7-3 下午3:33:12
	 */
	public Long getSequenceLongValue(String sequenceName);
}
