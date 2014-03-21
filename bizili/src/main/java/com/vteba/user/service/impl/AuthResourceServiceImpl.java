package com.vteba.user.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.tm.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;
import com.vteba.user.dao.IAuthResourceDao;
import com.vteba.user.model.AuthResource;
import com.vteba.user.service.IAuthResourceService;

@Named
public class AuthResourceServiceImpl extends GenericServiceImpl<AuthResource, Long> implements IAuthResourceService {
	
	public AuthResourceServiceImpl() {
		super();
	}
	private IAuthResourceDao authResourceDaoImpl;
	
	@Inject
	@Override
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<AuthResource, Long> authResourceDaoImpl) {
		this.hibernateGenericDaoImpl = authResourceDaoImpl;
		this.authResourceDaoImpl = (IAuthResourceDao) authResourceDaoImpl;
		
	}

	public IAuthResourceDao getAuthResourceDaoImpl() {
		return authResourceDaoImpl;
	}
	
	public List<AuthResource> getAllList() {
		return authResourceDaoImpl.getAll(AuthResource.class);
	}
}
