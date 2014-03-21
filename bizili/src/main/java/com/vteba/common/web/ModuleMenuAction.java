package com.vteba.common.web;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vteba.common.model.ModuleMenu;
import com.vteba.common.service.IModuleMenuService;
import com.vteba.web.action.BaseAction;

@RequestMapping("/common/menu")
@Controller
public class ModuleMenuAction extends BaseAction<ModuleMenu> {
	@Inject
	public IModuleMenuService moduleMenuServiceImpl;
	
	@RequestMapping("/list")
	public String list() {
		
		return "common/module/menu-list";
	}
	
	@RequestMapping("/add")
	public String add() {
		
		return "common/module/menu-add";
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public String delete() {
		
		return SUCCESS;
	}
}
