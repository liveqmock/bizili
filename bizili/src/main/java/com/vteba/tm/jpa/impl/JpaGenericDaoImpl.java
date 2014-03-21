package com.vteba.tm.jpa.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.vteba.tm.jpa.spi.IJpaGenericDao;
import com.vteba.util.reflection.ReflectUtils;

public abstract class JpaGenericDaoImpl<T, ID extends Serializable> implements IJpaGenericDao<T, ID> {
	//private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private Class<T> entityClass;
	
//	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
//		this.entityManagerFactory = entityManagerFactory;
//	}

	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public JpaGenericDaoImpl(){
		super();
		this.entityClass = ReflectUtils.getClassGenericType(getClass());
	}
	
	public abstract void setEntityManager(EntityManager entityManager);
	
	public void persist(T entity){
		entityManager.persist(entity);
	}
	
	public void delete(T entity){
		entityManager.remove(entity);
	}
	
	public T get(ID id){
		return entityManager.find(entityClass, id);
	}
	
	public Query createQuery(String ql, Object... values) {
		Query query = entityManager.createQuery(ql);
		for (int i = 0; i < values.length; i++) {
			if (values[i] instanceof Map){
				@SuppressWarnings("unchecked")
				Map<String, Object> map = (Map<String, Object>)values[i];
				for (Entry<String, Object> entry : map.entrySet()) {
					query.setParameter(entry.getKey(), entry.getValue());
				}
			} else {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}
	
	public TypedQuery<T> createTypedQuery(String ql, Object... values) {
		TypedQuery<T> typedQuery = entityManager.createQuery(ql, entityClass);
		
		return typedQuery;
	}
	
	public List<T> getEntityList(String ql, Object... values) {
		List<T> result = new ArrayList<T>();
		TypedQuery<T> typedQuery = createTypedQuery(ql, values);
		result = typedQuery.getResultList();
		
		return result;
	}
	
	public List<T> getListByCriteria(T criteria) {
		List<T> result = new ArrayList<T>();
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		Root<T> root = criteriaQuery.from(entityClass);
		criteriaQuery.select(root);
		
		TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);
		result = typedQuery.getResultList();
		return result;
	}
}
