package com.vteba.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.vteba.common.dao.IModuleMenuDao;
import com.vteba.common.model.ModuleMenu;
import com.vteba.common.service.IModuleMenuService;
import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;
import com.vteba.user.model.Authorities;
import com.vteba.user.model.EmpUser;
import com.vteba.user.service.IAuthoritiesService;

/**
 * 菜单service实现
 * @author yinlei 
 * date 2012-6-12 下午12:22:38
 */
@Named
public class ModuleMenuServiceImpl extends GenericServiceImpl<ModuleMenu, String> implements
		IModuleMenuService {

	private IModuleMenuDao moduleMenuDaoImpl;
	private IAuthoritiesService authoritiesServiceImpl;
	
	public ModuleMenuServiceImpl() {
		super();
	}
	
	@Inject
	public void setAuthoritiesServiceImpl(IAuthoritiesService authoritiesServiceImpl) {
		this.authoritiesServiceImpl = authoritiesServiceImpl;
	}

	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<ModuleMenu, String> moduleMenuDaoImpl) {
		this.hibernateGenericDaoImpl = moduleMenuDaoImpl;
		this.moduleMenuDaoImpl = (IModuleMenuDao) moduleMenuDaoImpl;
	}
	
	public List<ModuleMenu> getModuleMenuList(EmpUser user){
		String hql = "from ModuleMenu mm where mm.enable = true order by orders asc";
		List<ModuleMenu> menuList = moduleMenuDaoImpl.getEntityListByHql(hql);

		if (menuList != null && menuList.size() > 0) {
			String mhql = "from Authorities aa where aa.moduleId = :moduleId and aa.userId = :userId and aa.enabled = 1 order by :orders asc";
			for (ModuleMenu menu : menuList) {//获得各模组下的菜单(权限)
				List<Authorities> authorities = new ArrayList<Authorities>();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("moduleId", menu.getModuleId());
				map.put("userId", user.getUserId());
				map.put("orders", "orders");
				authorities = authoritiesServiceImpl.getEntityListByHql(mhql, map);
				
				List<Authorities> sets = new ArrayList<Authorities>();
				for (Authorities auth : authorities) {
					Set<String> urls = new HashSet<String>();
					urls = splitUrls(auth.getUrls());
					if (urls != null) {//该权限下所有的动作(URL)
						auth.setResUrls(urls);
					}
					sets.add(auth);
				}
				menu.setAuthorities(sets);
			}
		}
		List<ModuleMenu> finalMenus = new ArrayList<ModuleMenu>();
		for (ModuleMenu menu : menuList) {
			//有权限，才显示相应的菜单
			if (menu.getAuthorities() != null && menu.getAuthorities().size() > 0) {
				finalMenus.add(menu);
			}
		}
		return finalMenus;
	}
	
	/**
	 * 拆分权限string，以#分割
	 * @param urls
	 */
	protected Set<String> splitUrls(String urls){
		if (StringUtils.isNotEmpty(urls)) {
			String[] list = StringUtils.split(urls, "#");
			Set<String> retUrls = new HashSet<String>();
			for (int i =0; i < list.length; i++) {
				String temp = list[i];
				if (StringUtils.isNotEmpty(temp)) {
					retUrls.add(temp);
				}
			}
			return retUrls;
		}
		return null;
	}

}
