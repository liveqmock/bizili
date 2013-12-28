package com.vteba.user.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;
import com.vteba.user.dao.IUserRoleDao;
import com.vteba.user.model.UserRole;
import com.vteba.user.service.IUserRoleService;

@Named
public class UserRoleServiceImpl extends GenericServiceImpl<UserRole, Long> implements IUserRoleService {

	public UserRoleServiceImpl() {
		super();
	}
	private IUserRoleDao userRoleDaoImpl;
	
	@Inject
	@Override
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<UserRole, Long> userRoleDaoImpl) {
		this.hibernateGenericDaoImpl = userRoleDaoImpl;
		this.userRoleDaoImpl = (IUserRoleDao) userRoleDaoImpl;
		
	}

	public IUserRoleDao getUserRoleDaoImpl() {
		return userRoleDaoImpl;
	}
	
	
}
