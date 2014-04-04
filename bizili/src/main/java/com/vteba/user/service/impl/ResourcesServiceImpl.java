package com.vteba.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.tm.hibernate.IHibernateGenericDao;
import com.vteba.cache.infinispan.InfinispanCache;
import com.vteba.cache.infinispan.InfinispanCacheManager;
import com.vteba.common.constant.Cc;
import com.vteba.common.model.ModuleMenu;
import com.vteba.common.service.IModuleMenuService;
import com.vteba.service.generic.impl.GenericServiceImpl;
import com.vteba.user.dao.IResourcesDao;
import com.vteba.user.model.Resources;
import com.vteba.user.service.IResourcesService;
import com.vteba.util.json.FastJsonUtils;
import com.vteba.util.json.Node;

/**
 * 资源service实现。
 * @author yinlei
 * 2012-3-21 下午12:33:14
 */
@Named
public class ResourcesServiceImpl extends GenericServiceImpl<Resources, Long> implements IResourcesService {
	private IResourcesDao resourcesDaoImpl;
	@Inject
	private IModuleMenuService moduleMenuServiceImpl;
	@Inject
	InfinispanCacheManager infinispanCacheManager;
	
	public ResourcesServiceImpl() {
		super();
	}
	
	@Inject
	@Override
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<Resources, Long> resourcesDaoImpl) {
		this.hibernateGenericDaoImpl = resourcesDaoImpl;
		this.resourcesDaoImpl = (IResourcesDao) resourcesDaoImpl;
		
	}

	public String getResNode() {
		InfinispanCache<String, String> cache = infinispanCacheManager.getCache(Cc.Resources.class.getName());
		String json = cache.get(Cc.Resources.JSON);
		if (json == null) {
			json = loadResNodeCache();
		}
		return json;
	}
	
	public String loadResNodeCache() {
		List<Node> resNodes = new ArrayList<Node>();
		Node root = new Node("0", "资源列表");
		root.setOpen(true);
		root.setNocheck(true);
		resNodes.add(root);
		
		List<Node> children = new ArrayList<Node>();
		root.setChildren(children);
		
		List<ModuleMenu> moduleMenus = moduleMenuServiceImpl.loadModuleMenus();
		for (ModuleMenu menu : moduleMenus) {
			Node node = new Node(menu.getModuleId(), menu.getModuleName());
			children.add(node);
			String hql = "select new com.vteba.util.json.Node(r.resourceId, r.resourceName) from Resources r where r.moduleId = ?1";
			List<Node> nodeChildList = resourcesDaoImpl.getListByHql(hql, Node.class, menu.getModuleId());
			node.setChildren(nodeChildList);
		}
		String json = FastJsonUtils.toJson(resNodes);
		InfinispanCache<String, String> cache = infinispanCacheManager.getCache(Cc.Resources.class.getName());
		cache.putAsync(Cc.Resources.JSON, json);
		return json;
	}
}
