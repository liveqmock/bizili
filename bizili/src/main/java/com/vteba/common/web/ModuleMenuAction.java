package com.vteba.common.web;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vteba.common.constant.CommonConst;
import com.vteba.common.model.ModuleMenu;
import com.vteba.common.service.IModuleMenuService;
import com.vteba.service.generic.IGenericService;
import com.vteba.utils.reflection.ReflectUtils;
import com.vteba.web.action.BaseAction;
import com.vteba.web.action.PageBean;

@RequestMapping("/menu")
@Controller
public class ModuleMenuAction extends BaseAction<ModuleMenu> {
	@Inject
	public IModuleMenuService moduleMenuServiceImpl;
	
	@RequestMapping("/list")
	public String list(ModuleMenu model, PageBean<ModuleMenu> pageBean, Map<String, Object> maps) {
		if (isQuery()) {
			ReflectUtils.emptyToNulls(model);
		}
		page = pageBean.getPage();
		moduleMenuServiceImpl.queryForPageByCriteria(page, model);
		maps.put("listResult", page.getResult());
		setAttributeToRequest(CommonConst.PAGE_NAME, page);
		return "common/menu/menu-list";
	}
	
	@RequestMapping("/add")
	public String add(ModuleMenu model, Map<String, Object> maps) {
		if (isInit()) {
			setTokenValue();
			if (StringUtils.isNotBlank(model.getModuleId())) {// 编辑
				model = moduleMenuServiceImpl.get(model.getModuleId());
				maps.put("entity", model);
			}
			return "common/menu/menu-add";
		}
		if (isTokenValueOK()) {
			if (StringUtils.isBlank(model.getModuleId())) {
				model.setModuleId(null);
				model.setCreateTime(new Date());
			}
			moduleMenuServiceImpl.saveOrUpdate(model);
		}
		return "common/menu/menu-add";
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public String delete(String moduleId) {
		moduleMenuServiceImpl.delete(moduleId);
		return SUCCESS;
	}

	@Override
	public void setGenericServiceImpl(
			IGenericService<ModuleMenu, ? extends Serializable> genericServiceImpl) {
		// TODO Auto-generated method stub
		
	}
}
