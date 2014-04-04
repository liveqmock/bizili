package com.vteba.security.spring.interceptor;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;

import com.vteba.security.spring.meta.MethodSecurityMetadataSourceImpl;

public class AopSecurityInterceptorImpl extends AbstractSecurityInterceptor {
	private MethodSecurityMetadataSourceImpl methodSecurityMetadataSourceImpl;
	
	@Override
	public Class<?> getSecureObjectClass() {
		return null;
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
