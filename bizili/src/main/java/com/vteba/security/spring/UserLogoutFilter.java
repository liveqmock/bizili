package com.vteba.security.spring;

import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * 
 * @author yinlei
 *
 */
public class UserLogoutFilter extends LogoutFilter {

	public UserLogoutFilter(LogoutSuccessHandler logoutSuccessHandler,
			LogoutHandler... handlers) {
		super(logoutSuccessHandler, handlers);
		// TODO Auto-generated constructor stub
	}

	public UserLogoutFilter(String logoutSuccessUrl, LogoutHandler[] handlers) {
		super(logoutSuccessUrl, handlers);
		// TODO Auto-generated constructor stub
	}

}
