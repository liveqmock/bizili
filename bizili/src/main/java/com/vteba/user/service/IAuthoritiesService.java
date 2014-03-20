package com.vteba.user.service;

import com.vteba.service.generic.IGenericService;
import com.vteba.user.model.Authorities;

public interface IAuthoritiesService extends IGenericService<Authorities, Long> {
	/**
	 * 保存权限，同时级联保存权限对应的资源。
	 * @param authorities 权限
	 */
	public void saveAuthRes(Authorities authorities);
}
