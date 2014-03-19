package com.vteba.user.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vteba.common.constant.CommonConst;
import com.vteba.persister.generic.Page;
import com.vteba.user.model.AuthResource;
import com.vteba.user.service.IAuthResourceService;
import com.vteba.util.reflection.ReflectUtils;
import com.vteba.web.action.BaseAction;
import com.vteba.web.action.PageBean;

/**
 * 资源管理action
 * @author yinlei 
 * date 2012-6-24 下午11:19:54
 */
@Controller
@RequestMapping("/users")
public class AuthResourcesAction extends BaseAction<AuthResource> {

	private IAuthResourceService authResourceServiceImpl;
	private List<AuthResource> authList = new ArrayList<AuthResource>();
	
	@Inject
	public void setAuthResourceServiceImpl(IAuthResourceService authResourceServiceImpl) {
		this.authResourceServiceImpl = authResourceServiceImpl;
	}

	@RequestMapping("/resources-initial")
	public String initial(AuthResource model, PageBean<AuthResource> pageBean, Map<String, Object> maps) throws Exception {
		Page<AuthResource> pages = new Page<AuthResource>();
		if (!(model.getEnable() == null && model.getResourceName() == null
				&& model.getResourceType() == null && model.getResourceUrl() == null)) {
			ReflectUtils.emptyToNull(model);
		}
		pages = authResourceServiceImpl.queryForPageByModel(pageBean.getPage(), model);
		listResult = pages.getResult();
		maps.put("listResult", listResult);
		setAttributeToRequest(CommonConst.PAGE_NAME, pages);
		return "user/resources/resource-initial-success";
	}
	
	/**
	 * 新增资源初始化，以及保存资源
	 * @author yinlei
	 * date 2012-6-24 下午11:34:05
	 */
	@RequestMapping("/resources-input")
	public String input() throws Exception {
		if (isInit()) {
			return "user/resources/resource-input-success";
		}
		for (AuthResource model : authList) {
			if (StringUtils.isNotEmpty(model.getResourceUrl()) && StringUtils.isNotEmpty(model.getResourceType())) {
				authResourceServiceImpl.save(model);
			}
		}
		setAttributeToRequest("msg", "新增资源成功。");
		return "user/resources/resource-input-success";
	}
	
	public String edit() throws Exception {
		
		return "";
	}
	
	public String update() throws Exception {
		
		return DETAIL;
	}
	
	/**
	 * 新增权限时，该权限供选择的资源
	 * @author yinlei
	 * date 2012-6-24 下午11:33:30
	 */
	@RequestMapping("resources-list")
	public String list(AuthResource model, PageBean<AuthResource> pageBean, Map<String, Object> maps) throws Exception {
		page = pageBean.getPage();
		page.setPageSize(20);
		authResourceServiceImpl.queryForPageByModel(page, model);
		listResult = page.getResult();
		maps.put("listResult", listResult);
		setAttributeToRequest(CommonConst.PAGE_NAME, page);
		return "user/resources/resource-list";
	}
	
	/**********setter and getter********/
	
	public List<AuthResource> getAuthList() {
		return authList;
	}

	public void setAuthList(List<AuthResource> authList) {
		this.authList = authList;
	}

}
