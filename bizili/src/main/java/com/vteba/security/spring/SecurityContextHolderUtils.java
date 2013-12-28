package com.vteba.security.spring;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.vteba.user.model.EmpUser;

/**
 * Spring Security Context Information。
 * @author yinlei 
 * date 2012-6-24 下午3:38:25
 */
public class SecurityContextHolderUtils {
	
	/**
	 * 获得当前用户的认证信息
	 * @author yinlei
	 * date 2012-6-24 下午3:41:29
	 */
	public static Authentication getAuthentication() {
		SecurityContext context = SecurityContextHolder.getContext();
		if (null == context) {
			return null;
		}
		Authentication authentication = context.getAuthentication();
		return authentication;
	}
	
	/**
	 * 获得当前登录的用户信息
	 * @author yinlei
	 * date 2012-6-24 下午3:38:58
	 */
	public static EmpUser getCurrentUserInfo() {
		if (null == getAuthentication()) {
			return null;
		}
		Object detail = getAuthentication().getPrincipal();
		if (!(detail instanceof UserDetails)) {
			return null;
		}
		EmpUser user = (EmpUser) detail;
		return user;
	}
	
	/**
	 * 当前登录用户IP
	 * @author yinlei
	 * date 2012-6-24 下午3:39:40
	 */
	public static String getCurrentUserIP() {
		Object details = getAuthentication().getDetails();
		if (null == details) {
			return "";
		}
		if (!(details instanceof WebAuthenticationDetails)) {
			return "";
		}
		WebAuthenticationDetails auth = (WebAuthenticationDetails) details;
		return auth.getRemoteAddress();
	}
	
	/**
	 * 当前登录用户是否有某些角色(权限)
	 * @param roles
	 * @author yinlei
	 * date 2012-6-24 下午3:40:05
	 */
	public static boolean hasAnyRole(String... roles) {
		Authentication authentication = getAuthentication();
		if (null == authentication) {
			return false;
		}
		//Collection<GrantedAuthority> grantedAuthorities = authentication.getAuthorities();
		Collection<? extends GrantedAuthority> grantedAuthorities = authentication.getAuthorities();
		for (String role : roles) {
			for (GrantedAuthority authority : grantedAuthorities) {
				if (role.equals(authority.getAuthority())) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 将user保存到spring security安全上下文
	 * @param user
	 * @param request
	 * @author yinlei
	 * date 2012-6-24 下午3:40:52
	 */
	public static void saveUserDetailsToSecurityContext(UserDetails user,
			HttpServletRequest request) {
		PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(
				user, user.getPassword(), user.getAuthorities());
		if (null != request) {
			authentication.setDetails(new WebAuthenticationDetails(request));
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
