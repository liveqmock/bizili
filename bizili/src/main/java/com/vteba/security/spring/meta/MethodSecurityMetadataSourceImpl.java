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
	private IAuthoritiesService authoritiesServiceImpl;
	private Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
	
	public MethodSecurityMetadataSourceImpl(
			IAuthoritiesService authoritiesServiceImpl) {
		super();
		this.authoritiesServiceImpl = authoritiesServiceImpl;
		loadMethodAuthConfig();
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
		
		return null;
	}

	public void loadMethodAuthConfig() {
		List<String> authNameList = authoritiesServiceImpl.getAllAuthorities();
		Collection<ConfigAttribute> atts = new HashSet<ConfigAttribute>();
		for (String authName : authNameList) {
			ConfigAttribute ca = new SecurityConfig(authName);// eg:ROLE_ADMIN
			atts.add(ca);// 如果atts使用ArrayList实现，则在此处将ca添加到atts，如果atts的实现是Set，则可随便
			List<String> resourceList = authoritiesServiceImpl.getResourceUrlByAuthName(authName);
			for (String url : resourceList) {
				// 该资源和权限是否有对应关系，如果已经存在，则将新权限添加到对应的资源上
				if (resourceMap.containsKey(url)) {
					Collection<ConfigAttribute> attributes = resourceMap.get(url);
					attributes.add(ca);
					resourceMap.put(url, attributes);
				} else {// 如果是新资源，则将权限添加到对应的资源上
					// atts.add(ca);//如果atts的实现是Set则可在此处将ca添加到atts，如果atts的实现是ArrayList，则必须在上面添加
					resourceMap.put(url, atts);
				}
			}
		}
	}
}
