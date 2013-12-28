package com.vteba.user.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;

import com.vteba.persister.hibernate.impl.HibernateGenericDaoImpl;
import com.vteba.user.dao.IRoleAuthDao;
import com.vteba.user.model.RoleAuth;

@Named
public class RoleAuthDaoImpl extends HibernateGenericDaoImpl<RoleAuth, Long>
		implements IRoleAuthDao {

	public RoleAuthDaoImpl() {
		super();
	}

	public RoleAuthDaoImpl(Class<RoleAuth> entityClass) {
		super(entityClass);
	}
	
	@Inject
	@Qualifier("skmbwSessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
