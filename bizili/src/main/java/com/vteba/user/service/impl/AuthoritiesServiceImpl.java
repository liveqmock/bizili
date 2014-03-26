package com.vteba.user.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

	@Override
	public Authorities loadAuthorities(Long authId) {
		Authorities authorities = authoritiesDaoImpl.get(authId);
		StringBuilder resIds = new StringBuilder("");
		StringBuilder resNames = new StringBuilder("");
		for (Resources res : authorities.getResourceSets()) {
			resIds.append(res.getResourceId()).append(",");
			resNames.append(res.getResourceUrl()).append(",");
		}
		authorities.setResIds(resIds.toString());
		authorities.setResNames(resNames.toString());
		return authorities;
	}
	
	public List<String> getResourceUrlByAuthName(String authName) {
		String hql = "select a from Authorities a where a.authName = ?1";
		Authorities auth = authoritiesDaoImpl.uniqueResultByHql(hql, authName);
		List<String> resList = new ArrayList<String>();
		if (auth != null) {
			for (Resources res : auth.getResourceSets()) {
				resList.add(res.getResourceUrl());
			}
		}
		return resList;
		
//		StringBuilder sb = new StringBuilder();
//		sb = sb.append(" select a.resource_url from auth_resource a, authorities c ");
//		sb = sb.append(" where a.id = c.auth_id and ");
//		sb = sb.append(" c.auth_name = ? ");
//		authList = authoritiesDaoImpl.sqlQueryForList(sb.toString(), String.class, authName);
//		return authList;
	}

	//@Cacheable(value="getAllAuthorities", key="'getAllAuthorities'")
	@Override
	public List<String> getAllAuthorities() {
		List<String> authList = new ArrayList<String>();
		String hql = "select a.authName from Authorities a where a.enabled = 1";
		authList = authoritiesDaoImpl.hqlQueryForList(hql, String.class);
		return authList;
	}
}
