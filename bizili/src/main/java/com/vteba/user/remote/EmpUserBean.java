package com.vteba.user.remote;

import javax.inject.Inject;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.spring.SpringCreator;

import com.vteba.user.model.EmpUser;
import com.vteba.user.service.IEmpUserService;

/**
 * 用户相关的dwr ajax操作
 * @author yinlei
 * date 2012-9-8 上午10:52:15
 */
@RemoteProxy(creator = SpringCreator.class)
public class EmpUserBean {
	
	@Inject
	private IEmpUserService empUserServiceImpl;
	
	/**
	 * 根据用户ID删除用户
	 * @param userId 用户ID
	 * @param roleIds 角色ID数组
	 * @return 是否成功
	 * @author yinlei
	 * date 2012-9-8 上午10:57:49
	 */
	@RemoteMethod
	public String deleteUser(Long userId) {//, Long[] roleIds
		//两种方式：
		//1、把roleIds传进来，组装数据，直接删
		//2、根据userId加载一次，然后再删
		String result = "success";
		
		EmpUser user = new EmpUser();
		//user.setUserId(userId);
		user = empUserServiceImpl.get(userId);
//		for (Long roleId : roleIds) {
//			Roles role = new Roles();
//			role.setRoleId(roleId);
//			user.getRoleSet().add(role);
//		}
		empUserServiceImpl.delete(user);
		
		return result;
	}
}
