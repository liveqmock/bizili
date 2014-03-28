package com.vteba.user.service;

import com.vteba.service.generic.IGenericService;
import com.vteba.user.model.Resources;

public interface IResourcesService extends IGenericService<Resources, Long> {
	
	/**
	 * 获得资源json字符串，用来构造js树。
	 * @return json字符串
	 */
	public String getResNode();
}
