package com.vteba.user.web;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.vteba.common.constant.CommonConst;
import com.vteba.persister.generic.Page;
import com.vteba.user.model.AuthResource;
import com.vteba.user.service.IAuthResourceService;
import com.vteba.util.reflection.ReflectUtils;
import com.vteba.web.action.BaseAction;

/**
 * 资源管理action
 * @author yinlei 
 * date 2012-6-24 下午11:19:54
 */
public class AuthResourcesAction extends BaseAction<AuthResource> {

	private static final long serialVersionUID = -2620615490931609228L;
	private AuthResource model = new AuthResource();
	private IAuthResourceService authResourceServiceImpl;
	private List<AuthResource> authList = new ArrayList<AuthResource>();
	
	public void setModel(AuthResource model) {
		this.model = model;
	}
	
	@Inject
	public void setAuthResourceServiceImpl(IAuthResourceService authResourceServiceImpl) {
		this.authResourceServiceImpl = authResourceServiceImpl;
	}

	@Override
	public AuthResource getModel() {
		return model;
	}

	@Override
	public String initial() throws Exception {
		Page<AuthResource> pages = new Page<AuthResource>();
		if (!(model.getEnable() == null && model.getResourceName() == null
				&& model.getResourceType() == null && model.getResourceUrl() == null)) {
			ReflectUtils.emptyToNull(model);
		}
		pages = authResourceServiceImpl.queryForPageByModel(page, model);
		listResult = pages.getResult();
		setAttributeToRequest(CommonConst.PAGE_NAME, pages);
		return SUCCESS;
	}
	
	/**
	 * 新增资源初始化，以及保存资源
	 * @author yinlei
	 * date 2012-6-24 下午11:34:05
	 */
	public String input() throws Exception {
		if (isInit()) {
			return SUCCESS;
		}
		for (AuthResource model : authList) {
			if (StringUtils.isNotEmpty(model.getResourceUrl()) && StringUtils.isNotEmpty(model.getResourceType())) {
				authResourceServiceImpl.save(model);
			}
		}
		setAttributeToRequest("msg", "新增资源成功。");
		return SUCCESS;
	}
	
	public String edit() throws Exception {
		
		return SUCCESS;
	}
	
	public String update() throws Exception {
		
		return DETAIL;
	}
	
	/**
	 * 新增权限时，该权限供选择的资源
	 * @author yinlei
	 * date 2012-6-24 下午11:33:30
	 */
	public String list() throws Exception {
		Page<AuthResource> pages = new Page<AuthResource>();
		page.setPageSize(20);
		pages = authResourceServiceImpl.queryForPageByModel(page, model);
		listResult = pages.getResult();
		setAttributeToRequest(CommonConst.PAGE_NAME, pages);
		return LIST;
	}
	
	/**********setter and getter********/
	
	public List<AuthResource> getAuthList() {
		return authList;
	}

	public void setAuthList(List<AuthResource> authList) {
		this.authList = authList;
	}

}
