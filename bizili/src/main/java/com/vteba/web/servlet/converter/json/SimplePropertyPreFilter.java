package com.vteba.web.servlet.converter.json;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;

public class SimplePropertyPreFilter implements PropertyPreFilter {

	@Override
	public boolean apply(JSONSerializer serializer, Object object, String name) {
		
		return false;
	}

}
