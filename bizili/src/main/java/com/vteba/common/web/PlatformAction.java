package com.vteba.common.web;

import com.vteba.common.constant.CommonConst;
import com.vteba.common.service.IModuleMenuService;
import com.vteba.security.spring.SecurityContextHolderUtils;
import com.vteba.user.model.EmpUser;
import com.vteba.web.action.BaseAction;

/**
 * 跳转到菜单首页
 * @author yinlei
 * date 2012-7-15 下午3:41:08
 */
public class PlatformAction extends BaseAction<EmpUser> {

	private static final long serialVersionUID = 655140884138631009L;
	private EmpUser model = new EmpUser();
	private IModuleMenuService moduleMenuServiceImpl;
	
	@Override
	public EmpUser getModel() {
		return model;
	}

	@Override
	public String initial() throws Exception {
		EmpUser user = SecurityContextHolderUtils.getCurrentUserInfo();
		if (user != null) {
			setAttributeToSession(CommonConst.CONTEXT_USER, user);
		}
		return SUCCESS;
	}

	public IModuleMenuService getModuleMenuServiceImpl() {
		return moduleMenuServiceImpl;
	}

	public void setModuleMenuServiceImpl(IModuleMenuService moduleMenuServiceImpl) {
		this.moduleMenuServiceImpl = moduleMenuServiceImpl;
	}

}
