package com.vteba.user.service;

import com.vteba.service.generic.IGenericService;
import com.vteba.user.model.Roles;

public interface IRolesService extends IGenericService<Roles, Long> {
	/**
	 * 保存角色，级联保存角色包含的权限。
	 * @param roles 角色
	 */
	public void saveRoleAuths(Roles roles);
}
