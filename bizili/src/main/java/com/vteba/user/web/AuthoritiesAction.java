package com.vteba.user.web;

import javax.inject.Inject;

import com.vteba.common.constant.CommonConst;
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
public class AuthoritiesAction extends BaseAction<Authorities> {

	private static final long serialVersionUID = 5871784291810811995L;
	private Authorities model = new Authorities();
	private IAuthoritiesService authoritiesServiceImpl;
	private IModuleMenuService moduleMenuServiceImpl;
	
	public void setModel(Authorities model) {
		this.model = model;
	}
	
	@Inject
	public void setAuthoritiesServiceImpl(IAuthoritiesService authoritiesServiceImpl) {
		this.authoritiesServiceImpl = authoritiesServiceImpl;
	}
	
	@Inject
	public void setModuleMenuServiceImpl(IModuleMenuService moduleMenuServiceImpl) {
		this.moduleMenuServiceImpl = moduleMenuServiceImpl;
	}

	@Override
	public Authorities getModel() {
		return model;
	}

	@Override
	public String initial() throws Exception {
		Page<Authorities> pages = new Page<Authorities>();
		Authorities entity = new Authorities();
		pages = authoritiesServiceImpl.queryForPageByModel(page, entity);
		listResult = pages.getResult();
		setAttributeToRequest(CommonConst.PAGE_NAME, pages);
		return SUCCESS;
	}
	
	/**
	 * 新增权限初始化，以及保存权限
	 * @author yinlei
	 * date 2012-6-24 下午11:24:45
	 */
	public String input() throws Exception {
		if (isInit()) {
			String hql = "select a from ModuleMenu a where a.enable = true";
			list = moduleMenuServiceImpl.getEntityListByHql(hql);
			return SUCCESS;
		}
		authoritiesServiceImpl.save(model);
		return SUCCESS;
	}

}
