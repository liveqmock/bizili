package com.vteba.security.spring.meta;

import java.lang.reflect.Method;
import java.util.Collection;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.method.MethodSecurityMetadataSource;

/**
 * 方法级别的权限控制，元数据加载。
 * @author yinlei
 * 2014-4-4 下午5:54:26
 */
public class MethodSecurityMetadataSourceImpl implements MethodSecurityMetadataSource {

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Method method, Class<?> targetClass) {
		
		return null;
	}

}
