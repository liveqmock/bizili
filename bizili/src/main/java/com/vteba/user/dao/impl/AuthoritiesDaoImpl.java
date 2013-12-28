package com.vteba.user.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;

import com.vteba.persister.hibernate.impl.HibernateGenericDaoImpl;
import com.vteba.user.dao.IAuthoritiesDao;
import com.vteba.user.model.Authorities;

@Named
public class AuthoritiesDaoImpl extends
		HibernateGenericDaoImpl<Authorities, Long> implements IAuthoritiesDao {

	public AuthoritiesDaoImpl() {
		super();
	}

	public AuthoritiesDaoImpl(Class<Authorities> entityClass) {
		super(entityClass);
	}
	
	@Inject
	@Qualifier("skmbwSessionFactory")
	public void setSessionFactory(SessionFactory skmbwSessionFactory) {
		this.sessionFactory = skmbwSessionFactory;
	}

}
