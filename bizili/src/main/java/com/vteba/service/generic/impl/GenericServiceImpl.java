package com.vteba.service.generic.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.vteba.service.generic.IGenericService;
import com.vteba.tm.generic.Page;
import com.vteba.tm.hibernate.IHibernateGenericDao;
import com.vteba.tm.jdbc.spring.SpringJdbcTemplate;

/**
 * 通用泛型Service实现，供其他Service继承，简化service实现。
 * @author yinlei 
 * date 2012-6-29 下午11:28:32
 */
public abstract class GenericServiceImpl<T, ID extends Serializable> implements IGenericService<T, ID> {

	protected IHibernateGenericDao<T, ID> hibernateGenericDaoImpl;
	protected SpringJdbcTemplate springJdbcTemplate;
	
	/**
	 * 子类如果要使用SpringJdbcTemplate，请重写该方法，注入相应的SpringJdbcTemplate。<br/>
	 * 并且只能用于查询，否则破坏hibernate一级和二级缓存。
	 * @param springJdbcTemplate 具体的SpringJdbcTemplate实例
	 * @author yinlei
	 * date 2013-6-27 下午9:19:15
	 */
	public void setSpringJdbcTemplate(SpringJdbcTemplate springJdbcTemplate) {
		this.springJdbcTemplate = springJdbcTemplate;
	}
	
	public SpringJdbcTemplate getSpringJdbcTemplate() {
		return springJdbcTemplate;
	}
	
	/**
	 * 延迟到子类中注入具体dao实例
	 * @param hibernateGenericDaoImpl 实现IHibernateGenericDao具体的dao实例
	 * @author yinlei
	 * date 2012-6-22 下午4:04:41
	 */
	public abstract void setHibernateGenericDaoImpl(IHibernateGenericDao<T, ID> hibernateGenericDaoImpl);
	
	@Override
	public ID save(T entity) {
		return hibernateGenericDaoImpl.save(entity);
	}

	@Override
	public void persist(T entity) {
		hibernateGenericDaoImpl.persist(entity);
	}

	@Override
	public List<T> getEntityListByHql(String hql, Object... values) {
		return hibernateGenericDaoImpl.getEntityListByHql(hql, values);
	}

	@Override
	public void saveOrUpdate(T entity) {
		hibernateGenericDaoImpl.saveOrUpdate(entity);
	}

	@Override
	public void update(T entity) {
		hibernateGenericDaoImpl.update(entity);
	}

	@Override
	public T merge(T entity) {
		return hibernateGenericDaoImpl.merge(entity);
	}

	@Override
	public T load(Class<T> entity, ID id) {
		return hibernateGenericDaoImpl.load(entity, id);
	}

	@Override
	public List<T> getEntityListByNamedHql(String namedQuery, Object... values) {
		return hibernateGenericDaoImpl.getEntityListByNamedHql(namedQuery,
				values);
	}

	@Override
	public T load(ID id) {
		return hibernateGenericDaoImpl.load(id);
	}

	@Override
	public <X> X get(Class<X> entity, ID id) {
		return hibernateGenericDaoImpl.get(entity, id);
	}

	@Override
	public T get(ID id) {
		return hibernateGenericDaoImpl.get(id);
	}

	@Override
	public void delete(ID id) {
		hibernateGenericDaoImpl.delete(id);
	}

	@Override
	public <E> List<E> getListByHql(String hql, Class<E> clazz,
			Object... values) {
		return hibernateGenericDaoImpl.getListByHql(hql, clazz, values);
	}

	@Override
	public void delete(T entity) {
		hibernateGenericDaoImpl.delete(entity);
	}

	@Override
	public <E> List<E> getListByNamedHql(String namedQuery, Class<E> clazz,
			Object... values) {
		return hibernateGenericDaoImpl.getListByNamedHql(namedQuery, clazz,
				values);
	}

	@Override
	public List<T> getEntityListBySql(String sql, Object... values) {
		return hibernateGenericDaoImpl.getEntityListBySql(sql, values);
	}

	@Override
	public List<T> getEntityListByNamedSql(String namedSql, Object... values) {
		return hibernateGenericDaoImpl
				.getEntityListByNamedSql(namedSql, values);
	}

	@Override
	public <E> List<E> getListBySql(String sql, Class<E> clazz,
			Object... values) {
		return hibernateGenericDaoImpl.getListBySql(sql, clazz, values);
	}

	@Override
	public <E> List<E> getListByNamedSql(String namedSql, Class<E> resultClass,
			Object... values) {
		return hibernateGenericDaoImpl.getListByNamedSql(namedSql, resultClass,
				values);
	}

	@Override
	public <X> List<X> getAll(Class<X> entityClass) {
		return hibernateGenericDaoImpl.getAll(entityClass);
	}
	
	@Override
	public List<T> getListByCriteria(DetachedCriteria detachedCriteria) {
		return hibernateGenericDaoImpl.getListByCriteria(detachedCriteria);
	}
	
	@Override
	public List<T> getListByCriteria(T model, DetachedCriteria detachedCriteria) {
		return hibernateGenericDaoImpl.getListByCriteria(model, detachedCriteria);
	}
	
	@Override
	public List<T> getListByCriteria(T model) {
		return hibernateGenericDaoImpl.getListByCriteria(model);
	}

	@Override
	public List<T> getListByCriteria(T model, Map<String, String> orderMaps) {
		return hibernateGenericDaoImpl.getListByCriteria(model, orderMaps);
	}
	
	@Override
	public <X> List<X> getListByCriteria(Class<X> entityClass, X model, Map<String, String> orderMaps) {
		return hibernateGenericDaoImpl.getListByCriteria(entityClass, model, orderMaps);
	}

	@Override
	public List<T> getListByCriteriaLike(T model) {
		return hibernateGenericDaoImpl.getListByCriteriaLike(model);
	}
	
	@Override
	public List<T> getListByCriteriaLike(T model, Map<String, String> orderMaps) {
		return hibernateGenericDaoImpl.getListByCriteriaLike(model, orderMaps);
	}
	
	@Override
	public <X> List<X> getListByCriteriaLike(Class<X> entityClass, X model, Map<String, String> orderMaps) {
		return hibernateGenericDaoImpl.getListByCriteriaLike(entityClass, model, orderMaps);
	}

	@Override
	public List<Object[]> sqlQueryForObject(String sql, Object... values) {
		return hibernateGenericDaoImpl.sqlQueryForObject(sql, values);
	}

	@Override
	public <X> List<X> sqlQueryForList(String sql, Class<X> clazz,
			Object... values) {
		return hibernateGenericDaoImpl.sqlQueryForList(sql, clazz, values);
	}

	@Override
	public <X> X sqlQueryForObject(String sql, Class<X> clazz, Object... values) {
		return hibernateGenericDaoImpl.sqlQueryForObject(sql, clazz, values);
	}

	@Override
	public List<Object[]> hqlQueryForObject(String hql, boolean namedQuery,
			Object... values) {
		return hibernateGenericDaoImpl.hqlQueryForObject(hql, namedQuery,
				values);
	}

	@Override
	public <X> List<X> hqlQueryForList(String hql, Class<X> clazz,
			Object... values) {
		return hibernateGenericDaoImpl.hqlQueryForList(hql, clazz, values);
	}

	@Override
	public <X> X hqlQueryForObject(String hql, Class<X> clazz, Object... values) {
		return hibernateGenericDaoImpl.hqlQueryForObject(hql, clazz, values);
	}

	@Override
	public T uniqueResultByCriteria(String propertyName, Object value) {
		return hibernateGenericDaoImpl.uniqueResultByCriteria(propertyName, value);
	}
	
	@Override
	public <X> X uniqueResultByCriteria(Class<X> entityClass,
			String propertyName, Object value) {
		return hibernateGenericDaoImpl.uniqueResultByCriteria(entityClass,
				propertyName, value);
	}

	@Override
	public T uniqueResultByCriteria(Map<String, Object> params) {
		return hibernateGenericDaoImpl.uniqueResultByCriteria(params);
	}
	
	@Override
	public <X> X uniqueResultByCriteria(Class<X> entityClass,
			Map<String, Object> params) {
		return hibernateGenericDaoImpl.uniqueResultByCriteria(entityClass, params);
	}

	@Override
	public T uniqueResultByCriteria(T model) {
		return hibernateGenericDaoImpl.uniqueResultByCriteria(model);
	}
	
	@Override
	public <X> X uniqueResultByCriteria(Class<X> entityClass, X model) {
		return hibernateGenericDaoImpl.uniqueResultByCriteria(entityClass, model);
	}
	
	@Override
	public T uniqueResultByHql(String hql, Object... values) {
		return hibernateGenericDaoImpl.uniqueResultByHql(hql, values);
	}
	
	@Override
	public T uniqueResultByNamedHql(String hql, Object... values) {
		return hibernateGenericDaoImpl.uniqueResultByNamedHql(hql, values);
	}

	@Override
	public <X> X uniqueResultByHql(String hql, Class<X> resultClass,
			boolean namedQuery, Object... values) {
		return hibernateGenericDaoImpl.uniqueResultByHql(hql, resultClass,
				namedQuery, values);
	}
	
	@Override
	public T uniqueResultBySql(String sql, Object... values) {
		return hibernateGenericDaoImpl.uniqueResultBySql(sql, values);
	}


	@Override
	public <X> X uniqueResultBySql(String sql, Class<X> resultClass, Object... values) {
		return hibernateGenericDaoImpl.uniqueResultBySql(sql, resultClass, values);
	}

	@Override
	public int executeHqlUpdate(String hql, boolean namedQuery, Object... values) {
		return hibernateGenericDaoImpl.executeHqlUpdate(hql, namedQuery, values);
	}

	@Override
	public int executeSqlUpdate(String sql, Object... values) {
		return hibernateGenericDaoImpl.executeSqlUpdate(sql, values);
	}

	@Override
	public Page<T> queryForPageByCriteria(Page<T> page, T entity) {
		return hibernateGenericDaoImpl.queryForPageByCriteria(page, entity);
	}

	@Override
	public Page<T> queryForPageByLeftJoin(Page<T> page, T entity, Object... objects) {
		return hibernateGenericDaoImpl.queryForPageByLeftJoin(page, entity, objects);
	}

	@Override
	public Page<T> queryForPageBySubSelect(Page<T> page, T entity, Object... objects) {
		return hibernateGenericDaoImpl.queryForPageBySubSelect(page, entity, objects);
	}

	@Override
	public Page<T> queryForPageByHql(Page<T> page, String hql, Object... values) {
		return hibernateGenericDaoImpl.queryForPageByHql(page, hql, values);
	}

	@Override
	public List<T> pagedQueryByHql(Page<T> page, String hql, Object... values) {
		return hibernateGenericDaoImpl.pagedQueryByHql(page, hql, values);
	}
	
	@Override
	public Page<T> queryForPageBySql(Page<T> page, String sql, Object... values) {
		return hibernateGenericDaoImpl.queryForPageBySql(page, sql, values);
	}

//	@Override
//	public long countHqlResult(String hql, Object... values) {
//		return hibernateGenericDaoImpl.countHqlResult(hql, values);
//	}
//
//	@Override
//	public long countSqlResult(String sql, Object... values) {
//		return hibernateGenericDaoImpl.countSqlResult(sql, values);
//	}

//	@Override
//	public Long getSequenceLongValue(String sequenceName) {
//		return hibernateGenericDaoImpl.getSequenceLongValue(sequenceName);
//	}
	
//	public ID save(T entity){
//		return hibernateGenericDaoImpl.save(entity);
//	}
//	
//	public void saveOrUpdate(T entity){
//		hibernateGenericDaoImpl.saveOrUpdate(entity);
//	}
//	
//	public void update(T entity){
//		hibernateGenericDaoImpl.update(entity);
//	}
//    
//    public T merge(T entity){
//    	return hibernateGenericDaoImpl.merge(entity);
//    }
//    
//    public void persist(T entity) {
//    	hibernateGenericDaoImpl.persist(entity);
//    }
//
//    public void delete(ID id){
//    	hibernateGenericDaoImpl.delete(id);
//    }
//
//    public void delete(T entity){
//    	hibernateGenericDaoImpl.delete(entity);
//    }
//
//    public T get(ID id){
//    	return hibernateGenericDaoImpl.get(id);
//    }
//    
//    public T load(ID id){
//    	return hibernateGenericDaoImpl.load(id);
//    }
//    
//    public List<T> listAll(){
//    	return hibernateGenericDaoImpl.getAll();
//    }
//    
//    public List<T> queryListByHql(String hql, Object...values){
//    	return hibernateGenericDaoImpl.getEntityListByHql(hql, values);
//    }
//    
//    public List<T> queryListByNamedHql(String namedQuery, Object...values) {
//    	return hibernateGenericDaoImpl.getEntityListByNamedHql(namedQuery, values);
//    }
//    
//    public List<T> queryListByPropertyLike(Class<T> entityClass, T entity, Object... objects){
//    	return hibernateGenericDaoImpl.getListByPropertyLike(entityClass, entity, objects);
//    }
//    
//    public List<T> queryListByPropertyEqual(Class<T> entityClass, T entity, Object... objects){
//    	return hibernateGenericDaoImpl.getListByPropertyEqual(entityClass, entity, objects);
//    }
//    
//    public Page<T> queryPageListByHql(Page<T> page, String hql, Object...values){
//    	return hibernateGenericDaoImpl.queryForPageByHql(page, hql, values);
//    }
//    
//    public Page<T> queryPageListBySql(Page<T> page, String sql, Object...values) {
//    	return hibernateGenericDaoImpl.queryForPageBySql(page, sql, values);
//    }
//    
//    public Page<T> queryPageListByModel(Page<T> page, T entity){
//    	return hibernateGenericDaoImpl.queryForPageByModel(page, entity);
//    }
//    
//	public Page<T> queryPageListByModelSelect(Page<T> page, T entity, Object... objects) {
//		return hibernateGenericDaoImpl.queryForPageBySubSelect(page, entity, objects);
//	}
//    
//	public Page<T> getPageListByModelLeftJoin(Page<T> page, T entity, Object... objects) {
//		return hibernateGenericDaoImpl.queryForPageByLeftJoin(page, entity, objects);
//	}
//    
////    @Override
////	public List<String> sqlQueryForString(String sql, Object... values) {
////		return hibernateGenericDaoImpl.sqlQueryForString(sql, values);
////	}
////
////	@Override
////	public List<Long> sqlQueryForLong(String sql, Object... values) {
////		return hibernateGenericDaoImpl.sqlQueryForLong(sql, values);
////	}
////
////	@Override
////	public List<Double> sqlQueryForDouble(String sql, Object... values) {
////		return hibernateGenericDaoImpl.sqlQueryForDouble(sql, values);
////	}
//
//	@Override
//	public List<Object[]> sqlQueryForObject(String sql, Object... values) {
//		return hibernateGenericDaoImpl.sqlQueryForObject(sql, values);
//	}
//
////	@Override
////	public List<String> hqlQueryForString(String hql, boolean namedQuery, Object... values) {
////		return hibernateGenericDaoImpl.hqlQueryForString(hql, namedQuery, values);
////	}
////
////	@Override
////	public List<Long> hqlQueryForLong(String hql, boolean namedQuery, Object... values) {
////		return hibernateGenericDaoImpl.hqlQueryForLong(hql, namedQuery, values);
////	}
////
////	@Override
////	public List<Double> hqlQueryForDouble(String hql, boolean namedQuery, Object... values) {
////		return hibernateGenericDaoImpl.hqlQueryForDouble(hql, namedQuery, values);
////	}
//
//	@Override
//	public List<Object[]> hqlQueryForObject(String hql, boolean namedQuery, Object... values) {
//		return hibernateGenericDaoImpl.hqlQueryForObject(hql, namedQuery, values);
//	}
//	
//	@Override
//	public <X> X getUniqueResultByProperty(Class<X> entityClass, String propertyName, Object value) {
//		return hibernateGenericDaoImpl.getUniqueResultByProperty(entityClass, propertyName, value);
//	}
//
//	@Override
//	public <X> X getUniqueResultByProperty(Class<X> entityClass, Map<String, Object> params) {
//		return hibernateGenericDaoImpl.getUniqueResultByProperty(entityClass, params);
//	}
//	
//	@Override
//	public T uniqueResultByHql(String hql, boolean namedQuery, Object... values) {
//		return hibernateGenericDaoImpl.uniqueResultByHql(hql, namedQuery, values);
//	}
//
//	@Override
//	public T uniqueResultBySql(String sql, Object... values) {
//		return hibernateGenericDaoImpl.uniqueResultBySql(sql, values);
//	}
//
//	@Override
//	public <X> X getUniqueResultByHql(String hql, Class<X> clazz, boolean namedQuery, Object... values) {
//		return hibernateGenericDaoImpl.getUniqueResultByHql(hql, clazz, namedQuery, values);
//	}
//
//	@Override
//	public <X> X getUniqueResultBySql(String sql, Class<X> clazz, Object... values) {
//		return hibernateGenericDaoImpl.getUniqueResultBySql(sql, clazz, values);
//	}
//	
//	public int batchExecuteHql(String hql, boolean namedQuery, Object... values) {
//		return hibernateGenericDaoImpl.batchExecuteHql(hql, namedQuery, values);
//	}
//	
//	public int batchExecuteSql(String sql, Object... values) {
//		return hibernateGenericDaoImpl.batchExecuteSql(sql, values);
//	}
	
	@Override
	public void saveEntityBatch(List<T> list, int batchSize) {
		for (int i = 0; i< list.size(); i++) {
			T entity = list.get(i);
			persist(entity);
			if (i != 0 && i % batchSize == 0) {
				hibernateGenericDaoImpl.flush();
			}
		}
	}
	
	@Override
	public void deleteEntityBatch(List<ID> list) {
		for (ID id : list) {
			delete(id);
		}
	}
	
}
