package com.vteba.persister.generic.impl;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vteba.persister.generic.IGenericDao;
import com.vteba.persister.jdbc.spring.SpringJdbcTemplate;
import com.vteba.util.reflection.ReflectUtils;

/**
 * 泛型DAO抽象类。用于继承，简化子类实现。
 * @author yinlei
 * date 2012-4-2 下午10:47:34
 * @param <T> 实体类型
 * @param <ID> 主键类型，一般是String或者Long
 */
@SuppressWarnings("unchecked")
public abstract class GenericDaoImpl<T, ID extends Serializable> implements IGenericDao<T, ID> {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	protected Class<T> entityClass;
	protected SessionFactory sessionFactory;
	protected SpringJdbcTemplate springJdbcTemplate;
	
	public GenericDaoImpl(){
		entityClass = ReflectUtils.getClassGenericType(this.getClass());
	}
	
	public GenericDaoImpl(Class<T> entityClass){
		this.entityClass = entityClass;
	}

	/**
	 * 子类如果要使用SpringJdbcTemplate，请重写该方法，注入相应的SpringJdbcTemplate。<br/>
	 * 并且只能用于查询，否则破坏hibernate一级和二级缓存。
	 * @param springJdbcTemplate 具体的SpringJdbcTemplate实例
	 * @author yinlei
	 * date 2012-7-22 上午12:05:09
	 */
	public void setSpringJdbcTemplate(SpringJdbcTemplate springJdbcTemplate) {
		this.springJdbcTemplate = springJdbcTemplate;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	/**
	 * 延迟到子类中注入具体的sessionFactory实例
	 * @param sessionFactory 具体的sessionFactory
	 * @author yinlei
	 * date 2012-6-22 下午4:06:15
	 */
	public abstract void setSessionFactory(SessionFactory sessionFactory);
	
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public ID save(T entity) {
		ID id = (ID) getSession().save(entity);
		logger.info("save entity : {},id={}", entity,id);
		return id;
	}

	public void persist(T entity) {
		getSession().persist(entity);
		logger.info("persist entity : {}", entity);
	}

	public void saveOrUpdate(T entity) {
		getSession().saveOrUpdate(entity);
		logger.info("saveOrUpdate entity : {}", entity);
	}
	
	public void update(T entity) {
		getSession().update(entity);
		logger.info("update entity : {}", entity);
	}

	public T merge(T entity) {
		T result = (T) getSession().merge(entity);
		logger.info("meger entity : {}", entity);
		return result;
	}

	public T load(Class<T> entity, ID id) {
		return (T) getSession().load(entity, id);
	}

	public T load(ID id) {
		return (T) getSession().load(entityClass, id);
	}

	public <X> X get(Class<X> entity, ID id) {
		return (X) getSession().get(entity, id);
	}

	public T get(ID id) {
		return (T) getSession().get(entityClass, id);
	}
	
	public void delete(ID id) {
		getSession().delete(load(id));
		logger.info("delete entity's id : {}", id);
	}

	public void delete(T entity) {
		getSession().delete(entity);
		logger.info("delete entity : {}", entity);
	}
}
