package com.vteba.user.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;
import com.vteba.user.dao.IAuthoritiesDao;
import com.vteba.user.model.Authorities;
import com.vteba.user.service.IAuthoritiesService;

@Named
public class AuthoritiesServiceImpl extends GenericServiceImpl<Authorities, Long> implements IAuthoritiesService {

	public AuthoritiesServiceImpl() {
		super();
	}
	
	private IAuthoritiesDao authoritiesDaoImpl;
		
	@Inject
	@Override
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<Authorities, Long> authoritiesDaoImpl) {
		this.hibernateGenericDaoImpl = authoritiesDaoImpl;
		this.authoritiesDaoImpl = (IAuthoritiesDao) authoritiesDaoImpl;
		
	}

	public IAuthoritiesDao getAuthoritiesDaoImpl() {
		return authoritiesDaoImpl;
	}
	

}
