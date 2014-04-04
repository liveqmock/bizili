package com.vteba.security.spring.manager;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * spring security 访问决策管理器，spring security 会回调该决策管理器。
 * @author yinlei
 * date 2012-1-4 下午2:41:34
 */
public class AccessDecisionManagerImpl implements AccessDecisionManager {

	/**
	 * authentication 调用者<br/>
	 * object 被调用的对象<br/>
	 * configAttributes 被调用的对象，所需要的权限集合，满足其一即可。
	 */
	@Override
	public void decide(Authentication authentication, Object object,
			Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		if (configAttributes == null) {
			return;
		}
		for (ConfigAttribute ca : configAttributes) {
			String needRole = ((SecurityConfig) ca).getAttribute();
			for (GrantedAuthority ga : authentication.getAuthorities()) {
				if (needRole.equals(ga.getAuthority())) { // ga is user's role
					return;
				}
			}
		}
		throw new AccessDeniedException("You have no right to access the resources.");
	}

	@Override
	public boolean supports(ConfigAttribute configAttribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

}
