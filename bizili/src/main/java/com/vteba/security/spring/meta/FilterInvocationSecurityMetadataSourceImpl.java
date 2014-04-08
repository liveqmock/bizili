package com.vteba.security.spring.meta;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.vteba.user.service.IAuthoritiesService;

/**
 * 实现FilterInvocationSecurityMetadataSource接口，进行url级别的拦截，使用servlet filter<br>
 * 实现MethodSecurityMetadataSource接口，进行method级别的拦截，使用aop <br>
 * 初始化加载系统所有的权限以及权限和资源的对应关系。
 * @author yinlei
 */
public class FilterInvocationSecurityMetadataSourceImpl implements FilterInvocationSecurityMetadataSource {
	private IAuthoritiesService authoritiesServiceImpl;
	
	private Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<String, Collection<ConfigAttribute>>();

	public FilterInvocationSecurityMetadataSourceImpl(IAuthoritiesService authoritiesServiceImpl) {
		super();
		this.authoritiesServiceImpl = authoritiesServiceImpl;
		//加载资源和权限
		this.loadResourceAuthConfig();
	}
	
	/**
	 * 从数据库加载资源和权限的对应关系
	 */
	private void loadResourceAuthConfig() {
		List<String> authNameList = authoritiesServiceImpl.getAllAuthorities();
		for (String authName : authNameList) {
			ConfigAttribute ca = new SecurityConfig(authName);// eg:ROLE_ADMIN
			List<String> resourceList = authoritiesServiceImpl.getResourceUrlByAuthName(authName);
			for (String url : resourceList) {
				// 该资源和权限是否有对应关系，如果已经存在，则将新权限添加到对应的资源上
				if (resourceMap.containsKey(url)) {
					Collection<ConfigAttribute> attributes = resourceMap.get(url);
					attributes.add(ca);
					resourceMap.put(url, attributes);
				} else {// 如果是新资源，则将权限添加到对应的资源上
					Collection<ConfigAttribute> atts = new HashSet<ConfigAttribute>();
					atts.add(ca);
					resourceMap.put(url, atts);
				}
			}
		}
	}

	/**
	 * 获得该url需要的权限
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		FilterInvocation filter = (FilterInvocation) object;
		//String url = filter.getRequestUrl();
		for (String resUrl : resourceMap.keySet()) {
			AntPathRequestMatcher matcher = new AntPathRequestMatcher(resUrl);//3.1.1
			if (matcher.matches(filter.getRequest())) {
				return resourceMap.get(resUrl);
			}
		}
		return null;
	}

	/**
	 * 获得所有的权限
	 */
	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();
		for (Map.Entry<String, Collection<ConfigAttribute>> entry : resourceMap.entrySet()) {
			for (ConfigAttribute attrs : entry.getValue()) {
				allAttributes.add(attrs);
			}
		}
		return allAttributes;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
}
