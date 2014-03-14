package com.vteba.persister.jpa.impl;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.vteba.persister.jpa.IJpaGenericDao;
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
}
