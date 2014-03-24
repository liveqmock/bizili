package com.vteba.user.service;

import com.vteba.service.generic.IGenericService;
import com.vteba.user.model.Authorities;

/**
 * 权限service接口。
 * @author yinlei
 * @date 2012-4-20
 */
public interface IAuthoritiesService extends IGenericService<Authorities, Long> {
	/**
	 * 保存权限，同时级联保存权限对应的资源。
	 * @param authorities 权限
	 */
	public void saveAuthRes(Authorities authorities);
	
	/**
	 * 即时加载权限，以及其所包含的资源。
	 * @param authId 权限ID
	 * @return 权限，含资源
	 */
	public Authorities loadAuthoritiesEager(Long authId);
	
	/**
	 * 即时加载权限，包含权限下的资源，以及资源resIds（以,分割），resNames（以,分割）
	 * @param authId 权限id
	 * @return 权限
	 */
	public Authorities loadAuthorities(Long authId);
}
