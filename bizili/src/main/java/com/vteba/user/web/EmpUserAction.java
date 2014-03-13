package com.vteba.user.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vteba.common.constant.CommonConst;
import com.vteba.persister.generic.Page;
import com.vteba.security.spring.SecurityContextHolderUtils;
import com.vteba.user.model.Authorities;
import com.vteba.user.model.EmpUser;
import com.vteba.user.model.Roles;
import com.vteba.user.service.IAuthoritiesService;
import com.vteba.user.service.IEmpUserService;
import com.vteba.user.service.IRolesService;
import com.vteba.util.common.ObjectUtils;
import com.vteba.util.reflection.ReflectUtils;
import com.vteba.util.web.struts.StrutsUtils;
import com.vteba.web.action.BaseAction;
import com.vteba.web.action.PageBean;

/**
 * 用户管理action
 * @author yinlei 
 * date 2012-6-24 下午11:20:12
 */
@Controller
@RequestMapping("/users")
public class EmpUserAction extends BaseAction<EmpUser> {

	private IEmpUserService empUserServiceImpl;
	private IRolesService rolesServiceImpl;
	private IAuthoritiesService authoritiesServiceImpl;
	private ShaPasswordEncoder shaPasswordEncoder;
	private List<Long> ids;//封装页面的选择的id
	
	@Inject
	public void setEmpUserServiceImpl(IEmpUserService empUserServiceImpl) {
		this.empUserServiceImpl = empUserServiceImpl;
	}
	
	@Inject
	public void setRolesServiceImpl(IRolesService rolesServiceImpl) {
		this.rolesServiceImpl = rolesServiceImpl;
	}
	
	@Inject
	public void setAuthoritiesServiceImpl(IAuthoritiesService authoritiesServiceImpl) {
		this.authoritiesServiceImpl = authoritiesServiceImpl;
	}

	@Inject
	public void setShaPasswordEncoder(ShaPasswordEncoder shaPasswordEncoder) {
		this.shaPasswordEncoder = shaPasswordEncoder;
	}
	
	@RequestMapping("/empUser-initial")
	public String initial(EmpUser model, PageBean<EmpUser> pageBean, Map<String, Object> maps) throws Exception {
		Page<EmpUser> pages = new Page<EmpUser>();
		long dd = System.nanoTime();
		ReflectUtils.emptyToNulls(model);
		System.out.println(System.nanoTime() - dd);
		pages = empUserServiceImpl.queryForPageByModel(pageBean.getPage(), model);
		listResult = pages.getResult();
		maps.put("listResult", listResult);
		setAttributeToRequest(CommonConst.PAGE_NAME, pages);
		return "user/empUser/empUser-initial-success";
	}
	
	@RequestMapping("/empUser-input")
	public String input(EmpUser model, Map<String, Object> maps) throws Exception {
		if (isInit()) {//初始化或者编辑
			setTokenValue();
			if (model.getUserId() != null) {
				EmpUser user = empUserServiceImpl.loadEmpUser(model.getUserId());
				maps.put("entity", user);
			}
			return "user/empUser/empUser-input-success";
		}
		if (isTokenValueOK()) {//保存或更新
//			EmpUser user = new EmpUser();
//			user.setCreateDate(new Date());
//			//user.setDescription(model.getDescription());
//			user.setName(model.getName());
			String encodePass = shaPasswordEncoder.encodePassword(model.getPass(), model.getName());
//			user.setPass(encodePass);
//			user.setUserAccount(model.getUserAccount());
//			user.setEnable(model.getEnable());
//			//user.setDomain(model.getDomain());
//			user.setEmail(model.getEmail());
//			user.setUsername(model.getUsername());//封装了用户的权限
//			user.setTenantIdentifier(model.getTenantIdentifier());
			model.setPass(encodePass);
			model.setCreateDate(new Date());
			int ret = empUserServiceImpl.saveUserAndRole(model);
			if (ret == 1) {
				return "user/empUser/empUser-input-success";
			}
		}
		return "user/empUser/empUser-input-success";
	}
	
	@RequestMapping("delete")
	public void delete() throws Exception {
		try {
			empUserServiceImpl.deleteEntityBatch(ids);
			renderText("1");
		} catch (Exception e) {
			logger.info("delete user " + e.getMessage());
			renderText(e.getMessage());
		}
		
	}
	
	public void update() throws Exception {
		
	}
	
	@RequestMapping("/empUser-roles")
	public String roles(EmpUser model, Map<String, Object> maps) throws Exception {
		Page<Roles> rolesPage = new Page<Roles>();
		rolesPage.setPageNo(page.getPageNo());//因为page封装的默认是T EmpUser
		rolesPage.setPageSize(page.getPageSize());
		rolesPage.setOrderBy(page.getOrderBy());
		rolesPage.setAscDesc(page.getAscDesc());
		
		EmpUser user = SecurityContextHolderUtils.getCurrentUserInfo();
		StringBuilder sql = new StringBuilder(" select distinct rr.* from emp_user uu,user_role ur,roles rr ");
		sql.append(" where uu.user_id = ur.user_id ");
		sql.append(" and ur.role_id = rr.role_id ");
		
		Object[] values = new Object[6];//使用list，无法自动还原为本来的类型
		if (isInit()) {
			values[0] = user.getUserId();
			sql.append(" and uu.user_id = ? ");//默认查询自己的
			StrutsUtils.getRequest().setAttribute("userName", user.getName());
		} else { //之所以这样做，是因为不想拼装sql 字符串，防止sql注入
			if (model.getUserId() != null) {
				values[0] = model.getUserId();
				sql.append(" and uu.user_id = ? ");
			}
			if (StringUtils.isNotBlank(model.getName())) {
				values[1] = ObjectUtils.sqlLike(model.getName());
				sql.append(" and uu.name like ? ");
			}
			if (StringUtils.isNotBlank(model.getUsername())) {//封装权限名
				values[2] = ObjectUtils.sqlLike(model.getUsername());
				sql.append(" and rr.role_name like ?");
			}
			if (StringUtils.isNotBlank(model.getUserAccount())) {//封装权限描述
				values[3] = ObjectUtils.sqlLike(model.getUserAccount());
				sql.append(" and rr.role_desc like ?");
			}
		}
		Object[] objects = ObjectUtils.deleteNullValue(values);
		rolesServiceImpl.queryForPageBySql(rolesPage, sql.toString(), objects);
		List<Roles> list = rolesPage.getResult();
		maps.put("list", list);
		setAttributeToRequest(CommonConst.PAGE_NAME, rolesPage);
		return "user/empUser/empUser-roles-success";
	}
	
	public String authority() throws Exception {
		Page<Authorities> authPage = new Page<Authorities>();
		authPage.setPageNo(page.getPageNo());
		authPage.setPageSize(page.getPageSize());
		authPage.setOrderBy(page.getOrderBy());
		authPage.setAscDesc(page.getAscDesc());
		
		StringBuilder sql = new StringBuilder("");
		
		authoritiesServiceImpl.queryForPageBySql(authPage, sql.toString(), "");
		
		return "";
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

}
