package com.vteba.security.spring.meta;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.security.web.util.AntUrlPathMatcher;
//import org.springframework.security.web.util.UrlMatcher;


import com.vteba.user.service.IAuthoritiesService;
//import com.vteba.user.service.IEmpUserService;

/**
 * 实现FilterInvocationSecurityMetadataSource接口，进行url级别的拦截，使用servlet filter
 * 实现MethodSecurityMetadataSource接口，进行method级别的拦截，使用aop
 * 
 * @author yinlei
 */
public class FilterInvocationSecurityMetadataSourceImpl implements FilterInvocationSecurityMetadataSource {
	//private IEmpUserService empUserServiceImpl;
	private IAuthoritiesService authoritiesServiceImpl;
	
	//private UrlMatcher urlMatcher = new AntUrlPathMatcher();
	private Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<String, Collection<ConfigAttribute>>();

	public FilterInvocationSecurityMetadataSourceImpl(IAuthoritiesService authoritiesServiceImpl) {
		super();
		//this.empUserServiceImpl = empUserServiceImpl;// 只能使用构造函数注入
		this.authoritiesServiceImpl = authoritiesServiceImpl;
		//加载资源和权限
		this.loadResourceAuthConfig();
	}
	
	/**
	 * 从数据库加载资源和权限的对应关系
	 */
	private void loadResourceAuthConfig() {
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

	/**
	 * 获得该url需要的权限
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		FilterInvocation filter = (FilterInvocation) object;
		//String url = filter.getRequestUrl();
		Iterator<String> it = resourceMap.keySet().iterator();
		while (it.hasNext()) {
			String resURL = it.next();
			//if (urlMatcher.pathMatchesUrl(url, resURL)) {//spring security 3.0.7的方式,好像url和resURL位置要调换
				//return resourceMap.get(resURL);
			//}
			AntPathRequestMatcher matcher = new AntPathRequestMatcher(resURL);//3.1.1
			if (matcher.matches(filter.getRequest())) {
				return resourceMap.get(resURL);
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
