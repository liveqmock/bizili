package com.vteba.user.web;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vteba.common.constant.CommonConst;
import com.vteba.common.model.ModuleMenu;
import com.vteba.common.service.IModuleMenuService;
import com.vteba.persister.generic.Page;
import com.vteba.user.model.Authorities;
import com.vteba.user.service.IAuthoritiesService;
import com.vteba.web.action.BaseAction;

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
	public String initial(Authorities model, Map<String, Object> maps) throws Exception {
		Page<Authorities> pages = new Page<Authorities>();
		Authorities entity = new Authorities();
		pages = authoritiesServiceImpl.queryForPageByModel(page, entity);
		listResult = pages.getResult();
		maps.put("listResult", listResult);
		setAttributeToRequest(CommonConst.PAGE_NAME, pages);
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
			return "user/authorities/authorities-input";
		}
		authoritiesServiceImpl.save(model);
		return "user/authorities/auth-input-success";
	}

}
