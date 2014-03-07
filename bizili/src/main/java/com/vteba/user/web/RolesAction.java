package com.vteba.user.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vteba.common.constant.CommonConst;
import com.vteba.persister.generic.Page;
import com.vteba.user.model.Roles;
import com.vteba.user.service.IRoleAuthService;
import com.vteba.user.service.IRolesService;
import com.vteba.web.action.BaseAction;

/**
 * 角色管理action
 * @author yinlei 
 * date 2012-6-24 下午11:20:29
 */
@Controller
@RequestMapping("/user/roles")
public class RolesAction extends BaseAction<Roles> {

	private Roles model = new Roles();
	private List<Roles> roleList = new ArrayList<Roles>();
	private IRolesService rolesServiceImpl;
	private IRoleAuthService roleAuthServiceImpl;
	
	public void setModel(Roles model) {
		this.model = model;
	}
	
	@Inject
	public void setRolesServiceImpl(IRolesService rolesServiceImpl) {
		this.rolesServiceImpl = rolesServiceImpl;
	}
	
	@Inject
	public void setRoleAuthServiceImpl(IRoleAuthService roleAuthServiceImpl) {
		this.roleAuthServiceImpl = roleAuthServiceImpl;
	}

	public Roles getModel() {
		return model;
	}

	@Override
	@RequestMapping("/initial")
	public String initial() throws Exception {
		Page<Roles> pages = new Page<Roles>();
		Roles entity = new Roles();
		entity.setPriority(1);
		pages = rolesServiceImpl.queryForPageByModel(page, entity);
		listResult = pages.getResult();
		setAttributeToRequest(CommonConst.PAGE_NAME, pages);
		return "user/roles/roles-initial-success";
	}
	
	/**
	 * 新增角色初始化，以及保存角色 
	 * @author yinlei
	 * date 2012-6-24 下午11:22:12
	 */
	@RequestMapping("/input")
	public String input() throws Exception {
		if (isInit()) {
			return "user/roles/roles-input-success";
		}
		for (Roles entity : roleList) {
			if (StringUtils.isNotEmpty(entity.getRoleName())) {
				entity.setCreateTime(new Date());
				rolesServiceImpl.save(entity);
			}
		}
		return "user/roles/roles-input-success";
	}
	
	/**
	 * 查看某一角色的权限明细
	 * @author yinlei
	 * date 2012-6-24 下午11:21:42
	 */
	@RequestMapping("/roleAuthList")
	public String roleAuthList() throws Exception {
		String hql = "select a from RoleAuth a where a.roleId = ?1 ";
		list = roleAuthServiceImpl.getEntityListByHql(hql, model.getRoleId());
		return "user/roles/roleAuth-list";
	}
	
	/**
	 * 供选择角色时查询用
	 */
	@RequestMapping("/list")
	public String list() throws Exception {
		Roles entity = new Roles();
		entity.setRoleName(model.getRoleName());
		entity.setRoleDesc(model.getRoleDesc());
		entity.setPriority(1);
		listResult = rolesServiceImpl.getListByPropertyLike(Roles.class, entity);
		return "user/roles/roles-list";
	}

	public List<Roles> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Roles> roleList) {
		this.roleList = roleList;
	}
	
}
