package com.vteba.user.web;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vteba.common.constant.CommonConst;
import com.vteba.user.model.RoleAuth;
import com.vteba.user.model.Roles;
import com.vteba.user.service.IRoleAuthService;
import com.vteba.user.service.IRolesService;
import com.vteba.web.action.BaseAction;
import com.vteba.web.action.PageBean;

/**
 * 角色管理action
 * @author yinlei 
 * date 2012-6-24 下午11:20:29
 */
@Controller
@RequestMapping("/users")
public class RolesAction extends BaseAction<Roles> {

	private IRolesService rolesServiceImpl;
	private IRoleAuthService roleAuthServiceImpl;
	
	@Inject
	public void setRolesServiceImpl(IRolesService rolesServiceImpl) {
		this.rolesServiceImpl = rolesServiceImpl;
	}
	
	@Inject
	public void setRoleAuthServiceImpl(IRoleAuthService roleAuthServiceImpl) {
		this.roleAuthServiceImpl = roleAuthServiceImpl;
	}

	@RequestMapping("/roles-initial")
	public String initial(PageBean<Roles> pageBean, Map<String, Object> maps) throws Exception {
		Roles entity = new Roles();
		entity.setPriority(1);
		page = pageBean.getPage();
		rolesServiceImpl.queryForPageByCriteria(page, entity);
		listResult = page.getResult();
		maps.put("listResult", listResult);
		setAttributeToRequest(CommonConst.PAGE_NAME, page);
		return "user/roles/roles-initial-success";
	}
	
	/**
	 * 新增角色初始化，以及保存角色 
	 * @author yinlei
	 * date 2012-6-24 下午11:22:12
	 */
	@RequestMapping("/roles-input")
	public String input(Roles model, Map<String, Object> maps) throws Exception {
		if (isInit()) {
			setTokenValue();
			if (model.getRoleId() != null) {
				model = rolesServiceImpl.loadRoles(model.getRoleId());
				maps.put("role", model);
			}
			return "user/roles/roles-input-success";
		}
		if (isTokenValueOK()) {
			rolesServiceImpl.saveRoleAuths(model);
		}
		return "user/roles/roles-input-success";
	}
	
	/**
	 * 查看某一角色的权限明细
	 * @author yinlei
	 * date 2012-6-24 下午11:21:42
	 */
	@RequestMapping("/roles-roleAuthList")
	public String roleAuthList(Roles model, Map<String, Object> maps) throws Exception {
		String hql = "select a from RoleAuth a where a.roleId = ?1 ";
		List<RoleAuth> list = roleAuthServiceImpl.getEntityListByHql(hql, model.getRoleId());
		maps.put("list", list);
		return "user/roles/rolesAuth-list";
	}
	
	/**
	 * 供选择角色时查询用
	 */
	@RequestMapping("/roles-list")
	public String list(Roles model, Map<String, Object> maps) throws Exception {
		Roles entity = new Roles();
		entity.setRoleName(model.getRoleName());
		entity.setRoleDesc(model.getRoleDesc());
		entity.setPriority(1);
		listResult = rolesServiceImpl.getListByCriteriaLike(entity);
		maps.put("listResult", listResult);
		return "user/roles/roles-list";
	}
	
}
