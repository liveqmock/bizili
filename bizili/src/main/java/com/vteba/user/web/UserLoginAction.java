package com.vteba.user.web;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vteba.user.model.EmpUser;
import com.vteba.user.service.IEmpUserService;
import com.vteba.web.action.BaseAction;

/**
 * 用户登录跳转 
 * @author yinlei 
 * date 2012-6-8 下午11:13:40
 */
@Controller
@RequestMapping("/user")
public class UserLoginAction extends BaseAction<EmpUser> {
	
	private IEmpUserService empUserServiceImpl;
	
	public IEmpUserService getEmpUserServiceImpl() {
		return empUserServiceImpl;
	}

	@Inject
	public void setEmpUserServiceImpl(IEmpUserService empUserServiceImpl) {
		this.empUserServiceImpl = empUserServiceImpl;
	}

	@RequestMapping("/userLogin")
	public String initial() throws Exception {
		if (isInit()) {
			return "login";
		} else if (isAuthError()) {
			setAttributeToRequest("msg", "您输入的用户名或密码错误。");
		} else if (isExpired()) {
			setAttributeToRequest("msg", "该用户已经登录了。");
		}
		return "login";
	}
	
	//超过并发session数，session失效
	public boolean isExpired() {
		String expired = getHttpServletRequest().getParameter("expired");
		if (expired != null && expired.equals("true")) {
			return true;
		}
		return false;
	}

	//验证失败
	public boolean isAuthError() {
		String authError = getHttpServletRequest().getParameter("authError");
		if (authError != null && authError.equals("true")) {
			return true;
		}
		return false;
	}

}
