package com.vteba.user.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;
import com.vteba.user.dao.IRolesDao;
import com.vteba.user.model.Roles;
import com.vteba.user.service.IRolesService;

@Named
public class RolesServiceImpl extends GenericServiceImpl<Roles, Long> implements IRolesService {
	
	public RolesServiceImpl() {
		super();
	}
	
	private IRolesDao rolesDaoImpl;
		
	@Inject
	@Override
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<Roles, Long> rolesDaoImpl) {
		this.hibernateGenericDaoImpl = rolesDaoImpl;
		this.rolesDaoImpl = (IRolesDao) rolesDaoImpl;
	}

	public IRolesDao getRolesDaoImpl() {
		return rolesDaoImpl;
	}	

}
