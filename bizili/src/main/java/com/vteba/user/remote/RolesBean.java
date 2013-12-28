package com.vteba.user.remote;

import java.util.List;

import javax.inject.Inject;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.spring.SpringCreator;

import com.vteba.user.model.Roles;
import com.vteba.user.model.UserRole;
import com.vteba.user.service.IRolesService;
import com.vteba.user.service.IUserRoleService;

/**
 * 角色相关的dwr操作
 * @author yinlei
 * date 2012-9-9 下午2:17:29
 */
@RemoteProxy(creator = SpringCreator.class)
public class RolesBean {
	
	@Inject
	private IRolesService rolesServiceImpl;
	
	@Inject
	private IUserRoleService userRoleServiceImpl;
	
	/**
	 * 删除角色，当用户拥有这种角色是不能删，同时也会删除和权限的关联关系
	 * @param roleId 角色id
	 * @return inused使用中，success删除成功
	 * @author yinlei
	 * date 2012-9-9 下午2:18:42
	 */
	@RemoteMethod
	public String deleteRole(Long roleId) {
		String userRoleHql = " select ur from UserRole ur where ur.roleId = ?1 ";
		List<UserRole> userRoles = userRoleServiceImpl.getEntityListByHql(userRoleHql, roleId);
		
		if (userRoles.size() > 0) {
			return "inused";
		}
		
		Roles role = rolesServiceImpl.get(roleId);//这样加载后，会删除和权限的关系
		rolesServiceImpl.delete(role);
		return "success";
	}
}
