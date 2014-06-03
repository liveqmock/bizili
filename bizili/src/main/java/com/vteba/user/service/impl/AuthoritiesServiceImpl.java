package com.vteba.user.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.vteba.tx.hibernate.IHibernateGenericDao;
import com.vteba.cache.infinispan.InfinispanCache;
import com.vteba.cache.infinispan.InfinispanCacheManager;
import com.vteba.common.constant.Cc;
import com.vteba.common.model.ModuleMenu;
import com.vteba.common.service.IModuleMenuService;
import com.vteba.service.generic.impl.GenericServiceImpl;
import com.vteba.user.dao.IAuthoritiesDao;
import com.vteba.user.model.Authorities;
import com.vteba.user.model.Resources;
import com.vteba.user.service.IAuthoritiesService;
import com.vteba.util.json.FastJsonUtils;
import com.vteba.util.json.Node;

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
	private InfinispanCacheManager infinispanCacheManager;
	@Inject
	private IModuleMenuService moduleMenuServiceImpl;
	
	@Inject
	@Override
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<Authorities, Long> authoritiesDaoImpl) {
		this.hibernateGenericDaoImpl = authoritiesDaoImpl;
		this.authoritiesDaoImpl = (IAuthoritiesDao) authoritiesDaoImpl;
		
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
				if (res.getResourceType().equals(Resources.URL)) {
					resList.add(res.getResourceUrl());
				}
			}
		}
		return resList;
	}

	//@Cacheable(value="getAllAuthorities", key="'getAllAuthorities'")
	@Override
	public List<String> getAllAuthorities() {
		List<String> authList = new ArrayList<String>();
		String hql = "select a.authName from Authorities a where a.enabled = 1";
		authList = authoritiesDaoImpl.hqlQueryForList(hql, String.class);
		return authList;
	}
	
	public String loadAuthJson() {
		InfinispanCache<String, String> authCache = infinispanCacheManager.getCache(Cc.Auth.class.getName());
		String nodeList = authCache.get(Cc.Auth.JSON);
		if (nodeList != null) {
			return nodeList;
		}
		nodeList = loadAuthJsonCache();
		return nodeList;
	}
	
	public String loadAuthJsonCache() {
		List<Node> nodeList = new ArrayList<Node>();
		Node root = new Node("0", "权限列表");
		root.setOpen(true);
		root.setNocheck(true);
		nodeList.add(root);
		
		List<Node> children = new ArrayList<Node>();
		root.setChildren(children);
		
		List<ModuleMenu> moduleMenus = moduleMenuServiceImpl.loadModuleMenus();
		for (ModuleMenu menu : moduleMenus) {
			Node node = new Node(menu.getModuleId(), menu.getModuleName());
			children.add(node);
			String hql = "select new com.vteba.util.json.Node(r.authId, r.authName) from Authorities r where r.moduleId = ?1";
			List<Node> nodeChildList = authoritiesDaoImpl.getListByHql(hql, Node.class, menu.getModuleId());
			node.setChildren(nodeChildList);
		}
		
		InfinispanCache<String, String> authJsonCache = infinispanCacheManager.getCache(Cc.Auth.class.getName());
    	String json = FastJsonUtils.toJson(nodeList);
    	authJsonCache.put(Cc.Auth.JSON, json);
		return json;
	}
	
	public List<String> getMethodByAuthName(String authName) {
		String hql = "select a from Authorities a where a.authName = ?1";
		Authorities auth = authoritiesDaoImpl.uniqueResultByHql(hql, authName);
		List<String> resList = new ArrayList<String>();
		if (auth != null) {
			for (Resources res : auth.getResourceSets()) {
				if (res.getResourceType().equals(Resources.METHOD)) {
					resList.add(res.getResourceUrl());
				}
			}
		}
		return resList;
	}
}
