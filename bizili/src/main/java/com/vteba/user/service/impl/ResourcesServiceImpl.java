package com.vteba.user.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.tm.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;
import com.vteba.user.dao.IResourcesDao;
import com.vteba.user.model.Resources;
import com.vteba.user.service.IResourcesService;

/**
 * 资源service实现。
 * @author yinlei
 * 2012-3-21 下午12:33:14
 */
@Named
public class ResourcesServiceImpl extends GenericServiceImpl<Resources, Long> implements IResourcesService {

	public ResourcesServiceImpl() {
		super();
	}
	private IResourcesDao resourcesDaoImpl;
	
	@Inject
	@Override
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<Resources, Long> resourcesDaoImpl) {
		this.hibernateGenericDaoImpl = resourcesDaoImpl;
		this.resourcesDaoImpl = (IResourcesDao) resourcesDaoImpl;
		
	}

	public IResourcesDao getResourcesDaoImpl() {
		return resourcesDaoImpl;
	}
	
	
}
