package com.vteba.user.web;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vteba.common.constant.CommonConst;
import com.vteba.common.model.ModuleMenu;
import com.vteba.common.service.IModuleMenuService;
import com.vteba.user.model.Authorities;
import com.vteba.user.service.IAuthoritiesService;
import com.vteba.web.action.BaseAction;
import com.vteba.web.action.PageBean;

/**
 * 权限action
 * @author yinlei 
 * date 2012-6-24 下午11:19:32
 */
@Controller
@RequestMapping("/users")
public class AuthoritiesAction extends BaseAction<Authorities> {

	private IAuthoritiesService authoritiesServiceImpl;
	private IModuleMenuService moduleMenuServiceImpl;
	
	@Inject
	public void setAuthoritiesServiceImpl(IAuthoritiesService authoritiesServiceImpl) {
		this.authoritiesServiceImpl = authoritiesServiceImpl;
	}
	
	@Inject
	public void setModuleMenuServiceImpl(IModuleMenuService moduleMenuServiceImpl) {
		this.moduleMenuServiceImpl = moduleMenuServiceImpl;
	}

	@RequestMapping("/authorities-initial")
	public String initial(Authorities model, PageBean<Authorities> pageBean, Map<String, Object> maps) throws Exception {
		page = pageBean.getPage();
		Authorities entity = new Authorities();
		authoritiesServiceImpl.queryForPageByModel(page, entity);
		listResult = page.getResult();
		maps.put("listResult", listResult);
		setAttributeToRequest(CommonConst.PAGE_NAME, page);
		return "user/authorities/auth-initial-success";
	}
	
	/**
	 * 新增权限初始化，以及保存权限
	 * @author yinlei
	 * date 2012-6-24 下午11:24:45
	 */
	@RequestMapping("/authorities-input")
	public String input(Authorities model, Map<String, Object> maps) throws Exception {
		if (isInit()) {
			String hql = "select a from ModuleMenu a where a.enable = true";
			List<ModuleMenu> list = moduleMenuServiceImpl.getEntityListByHql(hql);
			maps.put("list", list);
			return "user/authorities/auth-input-success";
		}
		authoritiesServiceImpl.saveAuthRes(model);
		return "user/authorities/auth-input-success";
	}

	/**
	 * 删除权限
	 * @param authId 权限id
	 */
	@ResponseBody
	@RequestMapping("/auth-delete")
	public void delete(Long authId) {
		authoritiesServiceImpl.delete(authId);
		renderText(SUCCESS);
	}
	
	/**
	 * 查询角色权限列表
	 * @param authorities 参数
	 * @param pageBean 分页信息
	 * @param maps 返回值
	 * @return 角色权限列表页逻辑视图
	 */
	@RequestMapping("/auth-list")
	public String list(Authorities authorities, PageBean<Authorities> pageBean, Map<String, Object> maps) {
		page = pageBean.getPage();
		page.setPageSize(20);
		authoritiesServiceImpl.queryForPageByModel(page, authorities);
		listResult = page.getResult();
		maps.put("listResult", listResult);
		
		String hql = "select a from ModuleMenu a where a.enable = true";
		List<ModuleMenu> list = moduleMenuServiceImpl.getEntityListByHql(hql);
		maps.put("list", list);
		
		setAttributeToRequest(CommonConst.PAGE_NAME, page);
		return "user/authorities/auth-list";
	}
	
	@RequestMapping("/auth-resource")
	public String authResource(Authorities model, Map<String, Object> maps) {
		model = authoritiesServiceImpl.loadAuthoritiesEager(model.getAuthId());
		maps.put("list", model.getResourceSets());
		return "user/authorities/auth-resource";
	}
}
