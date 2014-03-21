package com.vteba.user.service.impl;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.vteba.tm.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;
import com.vteba.user.dao.IAuthoritiesDao;
import com.vteba.user.model.Authorities;
import com.vteba.user.model.Resources;
import com.vteba.user.service.IAuthoritiesService;

/**
 * 权限service实现。
 * @author yinlei
 * 2012-4-12 下午1:52:10
 */
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

	@Override
	public void saveAuthRes(Authorities authorities) {
		String resIds = authorities.getResIds();
		if (resIds != null) {
			Set<Resources> resourceSets = new HashSet<Resources>();
			String[] resourceIds = StringUtils.split(authorities.getResIds(), ",");
			for (String ids : resourceIds) {
				Long id = Long.valueOf(ids);
				Resources res = new Resources(id);
				resourceSets.add(res);
			}
			authorities.setResourceSets(resourceSets);
		}
		authoritiesDaoImpl.saveOrUpdate(authorities);
	}

	@Override
	public Authorities loadAuthoritiesEager(Long authId) {
		Authorities authorities = authoritiesDaoImpl.get(authId);
		authoritiesDaoImpl.initProxyObject(authorities.getResourceSets());
		return authorities;
	}
	

}
