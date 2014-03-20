package com.vteba.user.service.impl;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;
import com.vteba.user.dao.IRolesDao;
import com.vteba.user.model.Authorities;
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

	@Override
	public void saveRoleAuths(Roles roles) {
		String authIds = roles.getAuthIds();
		if (authIds != null) {
			String[] authIdsArray = StringUtils.split(authIds, ",");
			Set<Authorities> authSets = new HashSet<Authorities>();
			for (String ids : authIdsArray) {
				Long authId = Long.valueOf(ids);
				Authorities auth = new Authorities(authId);
				authSets.add(auth);
			}
			roles.setAuthSets(authSets);
		}
		rolesDaoImpl.saveOrUpdate(roles);
	}	

}
