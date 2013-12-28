package com.vteba.user.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;
import com.vteba.user.dao.IRoleAuthDao;
import com.vteba.user.model.RoleAuth;
import com.vteba.user.service.IRoleAuthService;

@Named
public class RoleAuthServiceImpl extends GenericServiceImpl<RoleAuth, Long> implements IRoleAuthService {

	public RoleAuthServiceImpl() {
		super();
	}
	private IRoleAuthDao roleAuthDaoImpl;
	
	@Inject
	@Override
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<RoleAuth, Long> roleAuthDaoImpl) {
		this.hibernateGenericDaoImpl = roleAuthDaoImpl;
		this.roleAuthDaoImpl = (IRoleAuthDao) roleAuthDaoImpl;
		
	}

	public IRoleAuthDao getRoleAuthDaoImpl() {
		return roleAuthDaoImpl;
	}
	
}
