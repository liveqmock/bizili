package com.vteba.user.service;

import java.util.List;

import com.vteba.service.generic.IGenericService;
import com.vteba.user.model.AuthResource;

public interface IAuthResourceService extends IGenericService<AuthResource, Long> {
	
	public List<AuthResource> getAllList();
}
