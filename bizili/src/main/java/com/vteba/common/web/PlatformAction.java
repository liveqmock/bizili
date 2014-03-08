package com.vteba.common.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
@Controller
@RequestMapping("/common")
public class PlatformAction extends BaseAction<EmpUser> {

	private IModuleMenuService moduleMenuServiceImpl;

	@RequestMapping("/platform-initial")
	public String initial() throws Exception {
		EmpUser user = SecurityContextHolderUtils.getCurrentUserInfo();
		if (user != null) {
			setAttributeToSession(CommonConst.CONTEXT_USER, user);
		}
		return "common/platform/initial-success";
	}

	public IModuleMenuService getModuleMenuServiceImpl() {
		return moduleMenuServiceImpl;
	}

	public void setModuleMenuServiceImpl(IModuleMenuService moduleMenuServiceImpl) {
		this.moduleMenuServiceImpl = moduleMenuServiceImpl;
	}

}
