package com.vteba.struts2.json;

import java.util.Set;

import com.alibaba.fastjson.serializer.PropertyFilter;

public class JsonPropertyFilter implements PropertyFilter {

	private Class<?> targetClass;
	private Set<String> excludeProperties;
	public JsonPropertyFilter(Class<?> targetClass) {
		this.targetClass = targetClass;
	}
	
	@Override
	public boolean apply(Object source, String name, Object value) {
		if (source.getClass() == targetClass) {
			if (excludeProperties.contains(name)) {
				return true;
			}
		}
		return false;
	}

}
