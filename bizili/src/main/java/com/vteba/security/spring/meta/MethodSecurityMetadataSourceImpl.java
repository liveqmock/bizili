package com.vteba.security.spring.meta;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.method.AbstractMethodSecurityMetadataSource;
import org.springframework.security.access.method.MethodSecurityMetadataSource;

import com.vteba.user.service.IAuthoritiesService;

/**
 * 方法级别的权限控制，元数据加载。
 * @author yinlei
 * 2014-4-4 下午5:54:26
 */
public class MethodSecurityMetadataSourceImpl extends AbstractMethodSecurityMetadataSource implements MethodSecurityMetadataSource {
	public static final String SHARP = "#";
	
	private IAuthoritiesService authoritiesServiceImpl;
	private Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
	
	public MethodSecurityMetadataSourceImpl(
			IAuthoritiesService authoritiesServiceImpl) {
		super();
		this.authoritiesServiceImpl = authoritiesServiceImpl;
		loadMethodAuthConfig();// 加载配置
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();  
        for (Map.Entry<String, Collection<ConfigAttribute>> entry : resourceMap.entrySet()) {  
            allAttributes.addAll(entry.getValue());  
        }  
        return allAttributes;  
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Method method, Class<?> targetClass) {
		String methodFullPath = targetClass.getName() + SHARP + method.getName();
		return resourceMap.get(methodFullPath);
	}

	public void loadMethodAuthConfig() {
		List<String> authNameList = authoritiesServiceImpl.getAllAuthorities();
		for (String authName : authNameList) {
			ConfigAttribute ca = new SecurityConfig(authName);// eg：ROLE_ADMIN
			List<String> resourceList = authoritiesServiceImpl.getMethodByAuthName(authName);
			for (String method : resourceList) {
				// 该资源和权限是否有对应关系，如果已经存在，则将新权限添加到对应的资源上（这个资源需要哪些权限）
				if (resourceMap.containsKey(method)) {
					Collection<ConfigAttribute> attributes = resourceMap.get(method);
					attributes.add(ca);
					resourceMap.put(method, attributes);
				} else {// 如果是新资源，则将权限添加到对应的资源上
					Collection<ConfigAttribute> atts = new HashSet<ConfigAttribute>();
					atts.add(ca);
					resourceMap.put(method, atts);
				}
			}
		}
	}
}
