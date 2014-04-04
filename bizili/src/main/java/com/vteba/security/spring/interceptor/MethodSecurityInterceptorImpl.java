package com.vteba.security.spring.interceptor;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;

import com.vteba.security.spring.meta.MethodSecurityMetadataSourceImpl;

/**
 * 基于AOP的方法级别的spring security拦截器。
 * @author yinlei
 * @date 2014-04-04
 */
public class MethodSecurityInterceptorImpl extends AbstractSecurityInterceptor {
	private MethodSecurityMetadataSourceImpl methodSecurityMetadataSourceImpl;
	
	@Override
	public Class<?> getSecureObjectClass() {
		return MethodInvocation.class;
	}

	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return methodSecurityMetadataSourceImpl;
	}

	public void setMethodSecurityMetadataSourceImpl(
			MethodSecurityMetadataSourceImpl methodSecurityMetadataSourceImpl) {
		this.methodSecurityMetadataSourceImpl = methodSecurityMetadataSourceImpl;
	}

}
