package com.vteba.user.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;

import com.vteba.tx.hibernate.impl.BaseGenericDaoImpl;
import com.vteba.user.dao.IResourcesDao;
import com.vteba.user.model.Resources;

/**
 * 资源dao实现。
 * @author yinlei
 * 2012-3-21 下午12:34:15
 */
@Named
public class ResourcesDaoImpl extends BaseGenericDaoImpl<Resources, Long> implements
		IResourcesDao {

	public ResourcesDaoImpl() {
		super();
	}

	public ResourcesDaoImpl(Class<Resources> entityClass) {
		super(entityClass);
	}
	
	@Inject
	@Qualifier("skmbwSessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
