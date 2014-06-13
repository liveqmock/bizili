package com.vteba.tx.hibernate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.vteba.tx.generic.IGenericDao;
import com.vteba.tx.generic.Page;

/**
 * Hibernate 泛型 DAO接口，简化Entity DAO实现。
 * @author yinlei 
 * date 2012-5-6 下午10:42:35
 * @param <T> 实体类型
 * @param <ID> 主键类型，一般是String或者Long
 */
public interface IHibernateGenericDao<T, ID extends Serializable> extends IGenericDao<T, ID> {
	/** 
     * 查询当前PO List，一般查询单实体。多实体关联查询，请使用{@link #getListByHql(String, Class, Object...)}。<br>
     * 用法：<br>
     * 1、查询全部栏位，select u from User u where...<br>
     * 2、使用select new查询部分栏位，select new User(u.id,u.name) from User u where...，<br>
     *   &nbsp;&nbsp;&nbsp;&nbsp;实体类中要有相应的构造函数<br>
     * 3、直接查询部分栏位，则返回List&lt;Object[]&gt;，不建议这么使用。建议使用第二点中的<br>
     *   &nbsp;&nbsp;&nbsp;&nbsp;select new语法；或使用{@link #getListByHql(String, Class, Object...)}可直接返回JavaBean<br>
     *   &nbsp;&nbsp;&nbsp;&nbsp;<br>
	 * @param hql 可用Jpa风格参数： ?1、?2。命名参数： :subjectName。Hibernate参数： ? (deprecated)。
	 * @param values hql参数，可以使用单个参数，Map，List，AstModel实例，传参。
	 */
	public List<T> getEntityListByHql(String hql, Object... values);
	
	/**
	 * 命名hql查询当前实体Class&lt;T&gt;实例List。是{@link #getEntityListByHql(String, Object...)}的命名参数版。 <br>
	 * 用法：<br>
	 * 1、hql应查询Class&lt;T&gt;实例所有的属性，如：select s from Subject s where .... 。<br>
	 * 2、使用select new T()查询指定属性，如：select new Subject(id, subjectCode) from Subject s where ....<br>
	 *   &nbsp;&nbsp;&nbsp;&nbsp;同时Subject实体中要有对应的构造函数。<br>
	 * 3、直接查询部分栏位，返回List&lt;Object[]&gt;。不建议这么使用。建议使用第二点中的<br>
     *   &nbsp;&nbsp;&nbsp;&nbsp;select new语法；或使用{@link #getListByHql(String, Class, Object...)}可直接返回JavaBean<br>
     *   &nbsp;&nbsp;&nbsp;&nbsp;<br>
	 * @param namedQuery 命名hql语句名，可用Jpa风格参数： ?1、?2，命名参数： :subjectCode
	 * @param values hql参数，可以使用单个参数，Map，List，AstModel实例，传参。
	 */
	public List<T> getEntityListByNamedHql(String namedQuery, Object... values);
	
	/** 
     * hql查询VO Bean List，一般用于多实体连接查询部分栏位。主要基于别名进行结果集转换。<br> 
     * 用法：<br>
     * 1、使用select new查询VO Bean，select new com.vteba.model.AUser(i.sbillno,u) from Inventory i, User u 
     *   &nbsp;&nbsp;&nbsp;&nbsp;where i.scustomerno = u.userAccount，VO中要有对应的构造函数，且要使用包名全路径。<br>
     * 2、直接select i.sbillno,u from Inventory i, User u...，则返回List&lt;Object[]&gt;，其中Object[]是{"billno", User}<br>
     *   &nbsp;&nbsp;&nbsp;&nbsp;，不建议这么用，建议使用{@link #getListByHql(String, Class, Object...)}根据别名进行结果集转换 <br> 
     * 3、直接查询PO也是可以的，但是建议使用{@link #getEntityListByHql(String, Object...)}代替<br> 
	 * @param hql 可用Jpa风格参数： ?1、?2，命名参数： :subjectName，Hibernate参数： ? (deprecated)
	 * @param values hql参数，可以使用单个参数，Map，List，AstModel实例，传参。
	 * @author yinlei
	 * date 2012-12-17 下午10:35:09
	 */
	public <E> List<E> getListByHql(String hql, Object... values);
	
	/** 
     * hql查询VO Bean List，一般用于多实体连接查询部分栏位。主要基于别名进行结果集转换。<br> 
     * 用法：<br>
     * 1、使用别名查询VO Bean，select i.billNo as id,u as user from Inventory i, User u where i.customerNo<br>
     *   &nbsp;&nbsp;&nbsp;&nbsp;= u.userAccount...，栏位别名要和VO中的属性名一致<br>
     * 2、如果不使用别名进行转换（clazz参数设为null），则返回List&lt;Object[]&gt;，对于第二点中的结果返回<br>
     *   &nbsp;&nbsp;&nbsp;&nbsp;的Object[]是{"billno", User}，不建议这么使用<br> 
     * 3、查询PO也是可以的（clazz参数设为null），但是建议使用{@link #getEntityListByHql(String, Object...)}代替<br> 
	 * @param hql 可用Jpa风格参数： ?1、?2，命名参数： :subjectName，Hibernate参数： ? (deprecated)
	 * @param clazz 结果类型Class&lt;E&gt;。
	 * @param values hql参数，可以使用单个参数，Map，List，AstModel实例，传参。
	 * @author yinlei
	 * date 2012-12-17 下午10:35:09
	 */
	public <E> List<E> getListByHql(String hql, Class<E> clazz, Object... values);
	
	/** 
     * hql查询VO Bean List，一般用于多实体连接查询部分栏位。主要基于别名进行结果集转换。<br>
     * 是{@link #getListByHql(String, Object...)}的命名参数版。 <br> 
     * 用法：<br>
     * 1、使用select new查询VO Bean，select new com.vteba.model.AUser(i.sbillno,u) from Inventory i, User u 
     *   &nbsp;&nbsp;&nbsp;&nbsp;where i.scustomerno = u.userAccount，VO中要有对应的构造函数，且要使用包名全路径。<br>
     * 2、直接select i.sbillno,u from Inventory i, User u...，则返回List&lt;Object[]&gt;，其中Object[]是{"billno", User}<br>
     *   &nbsp;&nbsp;&nbsp;&nbsp;，不建议这么用，建议使用{@link #getListByHql(String, Class, Object...)}根据别名进行结果集转换 <br> 
     * 3、直接查询PO也是可以的，但是建议使用{@link #getEntityListByHql(String, Object...)}代替<br> 
	 * @param hql 可用Jpa风格参数： ?1、?2，命名参数： :subjectName，Hibernate参数： ? (deprecated)
	 * @param values hql参数，可以使用单个参数，Map，List，AstModel实例，传参。
	 * @author yinlei
	 * date 2012-12-17 下午10:35:09
	 */
	public <E> List<E> getListByNamedHql(String hql, Object... values);
	
	/**
	 * 命名hql查询VO List。一般用于多实体连接查询部分栏位。主要基于别名进行结果集转换。<br>
	 * 是{@link #getListByHql(String, Class, Object...)}的命名参数版。 <br>
	 * 用法：<br>
	 * 1、使用别名查询VO Bean，select i.billNo as id,u as user from Inventory i, User u where i.customerNo<br>
     *   &nbsp;&nbsp;&nbsp;&nbsp;= u.userAccount...，栏位别名要和VO中的属性名一致<br>
     * 2、如果不使用别名进行转换（clazz参数设为null），则返回List&lt;Object[]&gt;，对于第二点中的结果返回<br>
     *   &nbsp;&nbsp;&nbsp;&nbsp;的Object[]是{"billno", User}，不建议这么使用<br>
     * 3、查询PO也是可以的（clazz参数设为null），但是强烈建议使用{@link #getEntityListByHql(String, Object...)}代替<br> 
	 * @param namedQuery 命名hql语句名，可用Jpa风格参数： ?1、?2，命名参数： :subjectName
	 * @param clazz 结果类型Class&lt;E&gt;
	 * @param values hql参数，可以使用单个参数，Map，List，AstModel实例，传参。
	 * @author yinlei
	 * date 2012-12-17 下午10:35:09
	 */
	public <E> List<E> getListByNamedHql(String namedQuery, Class<E> clazz, Object... values);
	
	/**
	 * 通过sql查询当前实体Class&lt;T&gt;实例的List&lt;T&gt;。<br>
	 * 1、sql栏位或者别名要和实体的属性一致，栏位和实体属性名不一致要指定别名。<br>
	 * 如：select id, subject_code subjectCode, subject_name subjectName from subject s where ....<br>
	 * 其中id属性和sql栏位一样，不需要指定别名。<br>
	 * 2、基于别名，使用AliasedResultTransformer，可转换任意列。
	 * @param sql 要执行的sql
	 * @param values sql参数值
	 */
	public List<T> getEntityListBySql(String sql, Object... values);
	
	/**
	 * 通过命名sql查询当前实体Class&lt;T&gt;实例的List&lt;T&gt;。<br>
	 * 1、命名sql中配置了resultClass或resultSetMapping，按规则转换。<br>
	 * 2、如果命名sql中没有配置resultClass或resultSetMapping，返回List&lt;Object[]&gt;。
	 *    可能出现转型错误。不建议(deprecated)这么用。<br>
	 * 3、如果没有配置resultClass或resultSetMapping，建议指定sql栏位别名使用{@link IHibernateGenericDao#getListByNamedSql}
	 * @param namedSql 命名sql名
	 * @param values 命名sql参数
	 * @author yinlei
	 * date 2012-12-17 下午9:33:29
	 */
	public List<T> getEntityListByNamedSql(String namedSql, Object... values);
	
	/**
	 * 根据sql查询实体List&lt;E&gt;，将结果集转换为Class&lt;E&gt;的实例。可多表连接。<br>
	 * 1、sql栏位或者别名要和实体的属性一致，栏位和实体属性名不一致要指定别名。<br>
	 *    如：select id, subject_code subjectCode, subject_name subjectName from subject s where ....<br>
	 *    其中id属性和sql栏位一样，不需要指定别名。<br>
	 * 2、查询全部栏位select * from user where...，此时VO中的属性要和数据库表中的栏位一一对应，不多不少。否则使用spring jdbc。<br>
	 * 3、基于别名，使用AliasedResultTransformer，可转换任意列。
	 * @param sql sql语句
	 * @param clazz 结果集Class&lt;E&gt;类
	 * @param values sql中的参数
	 * @author yinlei
	 * date 2012-12-17 下午10:47:38
	 */
	public <E> List<E> getListBySql(String sql, Class<E> clazz, Object... values);
	
	/**
	 * 根据命名sql查询实体List&lt;E&gt;，将结果集转换为Class&lt;E&gt;的实例。可多表连接。<br>
	 * 1、sql栏位或者别名要和实体的属性一致，栏位和实体属性名不一致要指定别名。<br>
	 *    如：select id, subject_code subjectCode, subject_name subjectName from subject s where ....<br>
	 *    其中id属性和sql栏位一样，不需要指定别名。<br>
	 * 2、查询全部栏位select * from user where...<br>
	 * 3、基于别名，使用AliasedResultTransformer，可转换任意列。
	 * @param namedSql 命名sql名
	 * @param resultClass 结果集Class&lt;E&gt;类
	 * @param values sql参数
	 * @return 实体List
	 * @author yinlei
	 * date 2013-6-10 下午3:54:54
	 */
	public <E> List<E> getListByNamedSql(String namedSql, Class<E> resultClass, Object... values);
	
	/**
	 * 通过Spring JdbcTemplate查询当前实体，使用字节码自动构建实体Class&lt;T&gt;实例。性能略低于回调2%以内。<br>
	 * 使用时，dao要注入相应的SpringJdbcTemplate
	 * @param sql sql语句
	 * @param values sql参数
	 * @return
	 * @author yinlei
	 * @date 2013年6月25日 下午10:19:45
	 */
	public List<T> getEntityListBySpring(String sql, Object... values);
	
	/**
	 * 通过Spring JdbcTemplate查询任意数据，使用字节码自动构建VO Class&lt;T&gt;实例。性能略低于回调2%以内。<br>
	 * 使用时，dao要注入相应的SpringJdbcTemplate
	 * @param sql sql语句
	 * @param values sql参数
	 * @return
	 * @author yinlei
	 * @date 2013年6月25日 下午10:17:45
	 */
	public <E> List<E> getListBySpring(String sql, Class<E> resultClass, Object... values);
	
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
	 * @param propertyName 属性名
	 * @param propertyValue 属性值
	 * @return list 查询结果List&lt;T&gt;
	 */
	public List<T> getListByCriteria(String propertyName, Object propertyValue);
	
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
	 */
	public List<T> getListByCriteria(T model, Map<String, String> orderMaps);
	
	/**
	 * 根据属性equal查询，使用QBE实现
	 * @param entityClass 要查询的实体类
	 * @param model 携带查询条件model
	 * @param orderMaps 使用Map传参，key是排序字段，value是asc或desc。
	 * @return list 查询结果List&lt;X&gt;
	 */
	public <X> List<X> getListByCriteria(Class<X> entityClass, X model, Map<String, String> orderMaps);
	
	/**
	 * String属性like查询，使用QBE实现
	 * @param propertyName 属性名
	 * @param propertyValue 属性值
	 * @return list 查询结果List&lt;T&gt;
	 */
	public List<T> getListByCriteriaLike(String propertyName, String propertyValue);
	
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
	 * @param entityClass 要查询的实体类
	 * @param model 携带查询条件model
	 * @param orderMaps 使用Map传参，key是排序字段，value是asc或desc
	 * @return list 查询结果List&lt;X&gt;
	 */
	public <X> List<X> getListByCriteriaLike(Class<X> entityClass, X model, Map<String, String> orderMaps);

	/**
	 * sql查询标量值，返回List&lt;Object[]&gt;
	 * @param sql sql语句
	 * @param values sql参数值
	 * @return List&lt;Object[]&gt;
	 * @author yinlei
	 * date 2013-6-11 下午5:10:53
	 */
	public List<Object[]> sqlQueryForObject(String sql, Object... values);
	
	/**
	 * sql查询基本类型及其封装类，String，Date和大数值List&lt;X&gt;
	 * @param sql sql语句
	 * @param clazz 要转换的基本类型
	 * @param values sql参数值
	 * @return 基本类型List&lt;X&gt;
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
	 * hql查询标量值，返回List&lt;Object[]&gt;
	 * @param hql hql语句
	 * @param namedQuery 是否命名hql
	 * @param values hql参数
	 * @return List&lt;Object[]&gt;
	 * @author yinlei
	 * date 2013-6-11 下午5:12:18
	 */
	public List<Object[]> hqlQueryForObject(String hql, boolean namedQuery, Object... values);
	
	/**
	 * hql查询基本类型及其封装类，String，Date和大数值List&lt;X&gt;
	 * @param hql hql语句
	 * @param clazz 基本类型
	 * @param values hql参数值
	 * @return List&lt;X&gt;
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
	 * QBC条件查询获得唯一实体，请确保属性具有唯一性
	 * @param propertyName 属性名
	 * @param value 属性值
	 * @return 实体&lt;T&gt;
	 */
	public T uniqueResultByCriteria(String propertyName, Object value);
	
	/**
	 * QBC条件查询获得唯一实体，请确保属性具有唯一性
	 * @param entityClass 要查询的实体类
	 * @param propertyName 属性名
	 * @param value 属性值
	 * @return 实体&lt;X&gt;
	 */
	public <X> X uniqueResultByCriteria(Class<X> entityClass, String propertyName, Object value);
	
	/**
	 * QBC条件查询获得唯一实体，请确保属性具有唯一性
	 * @param params 携带查询参数，key为属性名，value为值
	 * @return 实体&lt;X&gt;
	 * @author yinlei
	 * date 2013-6-11 下午5:19:04
	 */
	public T uniqueResultByCriteria(Map<String, Object> params);
	
	/**
	 * QBE条件查询获得唯一实体，请确保属性具有唯一性
	 * @param entityClass 要查询的实体类
	 * @param params 携带查询参数，key为属性名，value为值
	 * @return 实体&lt;X&gt;
	 * @author yinlei
	 * date 2013-6-11 下午5:19:04
	 */
	public <X> X uniqueResultByCriteria(Class<X> entityClass, Map<String, Object> params);
	
	/**
	 * QBE条件查询获得唯一实体，请确保属性具有唯一性
	 * @param model 携带查询参数实体
	 * @return 实体&lt;T&gt;实例
	 * @author yinlei
	 * date 2013-6-11 下午5:21:11
	 */
	public T uniqueResultByCriteria(T model);
	
	/**
	 * QBE条件查询获得唯一实体，请确保属性具有唯一性
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
	 * @param hql 查询语句
	 * @param values hql中绑定的参数值
	 * @return 当前实体&lt;T&gt;
	 */
	public T uniqueResultByNamedHql(String hql, Object... values);
	
	/**
	 * 通过hql获得唯一实体，hql语句可进行多实体连接。<br>
	 * 1、hql应查询Class&lt;X&gt;实例所有的属性，如：select s from Subject s where .... 。<br>
	 *    这种方式建议使用{@link #uniqueResultByHql(String, Object...)}<br>
	 * 2、使用new X()构造函数指定属性，如：select new Subject(id, subjectCode, subjectName, level) <br>
	 *    from Subject s where .... 同时Subject中要有对应的构造函数才行。<br>
	 *    Subject不是实体类，可以使用这种方式。<br>
	 *    如果Subject是实体类，建议使用{@link #uniqueResultByHql(String, Object...)}<br>
	 * 3、查询任意栏位，hql中的栏位名或别名要和Class&lt;X&gt;实例中的属性名一致。使用AliasedResultTransformer转换任意列。<br>
	 * @param <X> 查询的实体
	 * @param hql 要执行的hql语句
	 * @param resultClass 结果类型Class&lt;X&gt;
	 * @param values hql参数值
	 * @return 实体&lt;X&gt;
	 */
	public <X> X uniqueResultByHql(String hql, Class<X> resultClass, Object... values);
	
	/**
	 * 通过hql获得唯一实体，hql语句可进行多实体连接。<br>
	 * 1、hql应查询Class&lt;X&gt;实例所有的属性，如：select s from Subject s where .... 。<br>
	 *    这种方式建议使用{@link #uniqueResultByHql(String, Object...)}<br>
	 * 2、使用new X()构造函数指定属性，如：select new Subject(id, subjectCode, subjectName, level) <br>
	 *    from Subject s where .... 同时Subject中要有对应的构造函数才行。<br>
	 *    Subject不是实体类，可以使用这种方式。<br>
	 *    如果Subject是实体类，建议使用{@link #uniqueResultByHql(String, Object...)}<br>
	 * 3、查询任意栏位，hql中的栏位名或别名要和Class&lt;X&gt;实例中的属性名一致。使用AliasedResultTransformer转换任意列。<br>
	 * @param <X> 查询的实体
	 * @param hql 要执行的hql语句/命名hql名称
	 * @param resultClass 结果类型Class&lt;X&gt;
	 * @param namedQuery 是否命名查询
	 * @param values hql参数值
	 * @return 实体&lt;X&gt;
	 */
	public <X> X uniqueResultByHql(String hql, Class<X> resultClass, boolean namedQuery, Object... values);
	
	/**
	 * 使用sql获得唯一实体<br>
	 * 1、sql栏位或者别名要和实体的属性一致，栏位和实体属性名不一致要指定别名。<br>
	 * 如：select id, subject_code subjectCode, subject_name subjectName from subject s where ....<br>
	 * 其中id属性和sql栏位一样，不需要指定别名。<br>
	 * 2、基于别名，使用AliasedResultTransformer，可转换任意列。
	 * @param sql 要执行的sql
	 * @param values sql绑定的参数
	 * @return 当前实体&lt;T&gt;
	 */
	public T uniqueResultBySql(String sql, Object...values);
	
	/**
	 * 通过sql获得唯一实体<br>
	 * 1、sql栏位或者别名要和实体的属性一致，栏位和实体属性名不一致要指定别名。<br>
	 * 如：select id, subject_code subjectCode, subject_name subjectName from subject s where ....<br>
	 * 其中id属性和sql栏位一样，不需要指定别名。<br>
	 * 2、基于别名，使用AliasedResultTransformer，可转换任意列。
	 * @param <X> 要查询的实体
	 * @param sql 要执行的sql语句
	 * @param resultClass 结果类型Class&lt;X&gt;
	 * @param values sql参数值
	 * @return 实体&lt;X&gt;
	 */
	public <X> X uniqueResultBySql(String sql, Class<X> resultClass, Object... values);
	
	/**
	 * 执行任意hql，常用于update，delete，insert
	 * @param hql 要执行的hql
	 * @param values hql中绑定的参数值
	 * @param namedQuery 是否命名查询
	 * @return 影响的实体数
	 */
	public int executeHqlUpdate(String hql, boolean namedQuery, Object... values);
	
	/**
	 * 执行任意sql，常用于update，delete，insert
	 * @param sql 要执行的sql
	 * @param values sql中绑定的参数值
	 * @return 影响的记录数
	 */
	public int executeSqlUpdate(String sql, Object... values);
	
	/**
	 * 初始化延迟加载的对象，load默认延迟加载
	 */
	public void initProxyObject(Object proxy);
	
	/**
	 * 分页查询，使用criteria实现
	 * @param page 分页数据
	 * @param entity 携带查询条件，一般简单“等于”条件
	 * @return Page&lt;T&gt;分页，携带查询结果
	 * @author yinlei
	 * date 2012-7-8 下午10:34:23
	 */
	public Page<T> queryForPageByCriteria(Page<T> page, T entity);
	
	/**
	 * 分页查询，使用criteria实现
	 * @param page 分页数据
	 * @param detachedCriteria 携带查询条件，DetachedCriteria实例，复杂条件
	 * @return Page&lt;T&gt;分页，携带查询结果
	 * @author yinlei
	 * date 2012-7-8 下午10:35:13
	 */
	public Page<T> queryForPageByCriteria(Page<T> page, DetachedCriteria detachedCriteria);
	
	/**
	 * 分页查询，使用criteria实现
	 * @param page 分页数据
	 * @param entity 携带查询条件，实体实例，一般简单条件
	 * @param detachedCriteria 携带查询条件，DetachedCriteria实例，复杂条件
	 * @return Page&lt;T&gt;分页，携带查询结果
	 * @author yinlei
	 * date 2012-7-8 下午10:37:22
	 */
	public Page<T> queryForPageByCriteria(Page<T> page, T entity, DetachedCriteria detachedCriteria);
	
	/**
	 * 分页查询，使用criteria实现，左外连接立即初始化延迟加载的集合
	 * @param page 分页数据
	 * @param entity 实体
	 * @param objects 实体中延迟加载的集合的名字
	 * @return Page&lt;T&gt;分页，携带查询结果
	 * @author yinlei
	 * date 2012-6-26 下午4:50:03
	 */
	public Page<T> queryForPageByLeftJoin(Page<T> page, T entity, Object... objects);
	
	/**
	 * 分页查询，使用criteria实现，第二个select立即初始化延迟加载的集合
	 * @param page 分页数据
	 * @param entity 实体
	 * @param objects 实体中延迟加载的集合的名字
	 * @return Page&lt;T&gt;分页，携带查询结果
	 * @author yinlei
	 * date 2012-6-26 下午4:50:03
	 */
	public Page<T> queryForPageBySubSelect(Page<T> page, T entity, Object... objects);
	
	/**
	 * 使用hql进行分页查询
	 * @param page 分页条件
	 * @param hql hql语句
	 * @param values hql参数值
	 * @return Page&lt;T&gt;分页，携带查询结果
	 * @author yinlei
	 * date 2013-6-11 下午5:28:27
	 */
	public Page<T> queryForPageByHql(Page<T> page, String hql, Object... values);
	
	/**
	 * 分页查询但是不返回总记录数。
	 * @param page 分页参数
	 * @param hql hql语句
	 * @param values hql参数
	 * @return 结果List
	 * @author yinlei
	 * date 2013-10-4 17:27
	 */
	public List<T> pagedQueryByHql(Page<T> page, String hql, Object... values);
	
	/**
	 * 使用sql进行分页查询
	 * @param page 分页条件
	 * @param sql sql语句
	 * @param values sql参数值
	 * @return Page&lt;T&gt;分页，携带查询结果
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
