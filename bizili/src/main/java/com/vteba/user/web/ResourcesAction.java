package com.vteba.user.web;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vteba.common.constant.CommonConst;
import com.vteba.common.model.ModuleMenu;
import com.vteba.common.service.IModuleMenuService;
import com.vteba.service.generic.BaseService;
import com.vteba.user.model.Resources;
import com.vteba.user.service.IResourcesService;
import com.vteba.web.action.BaseAction;
import com.vteba.web.action.PageBean;

/**
 * 资源管理action
 * @author yinlei 
 * date 2012-6-24 下午11:19:54
 */
@Controller
@RequestMapping("/users")
public class ResourcesAction extends BaseAction<Resources> {

	private IResourcesService resourcesServiceImpl;
	@Inject
	private IModuleMenuService moduleMenuServiceImpl;
	
	@RequestMapping("/resources-initial")
	public String initial(Resources model, PageBean<Resources> pageBean, Map<String, Object> maps) throws Exception {
		queryForPage(model, pageBean, maps);
		
		List<ModuleMenu> list = moduleMenuServiceImpl.loadModuleMenus();
		maps.put("list", list);
		
		return "user/resources/resource-initial-success";
	}
	
	/**
	 * 新增资源初始化，以及保存资源
	 * @author yinlei
	 * date 2012-6-24 下午11:34:05
	 */
	@RequestMapping("/resources-input")
	public String input(Resources model, Map<String, Object> maps) throws Exception {
		if (isInit()) {
			setTokenValue();
			if (model.getResourceId() != null) {
				model = resourcesServiceImpl.get(model.getResourceId());
				maps.put("res", model);
			}
			List<ModuleMenu> list = moduleMenuServiceImpl.loadModuleMenus();
			maps.put("list", list);
			return "user/resources/resource-input-success";
		}
		if (isTokenValueOK()) {
			if (StringUtils.isNotEmpty(model.getResourceName()) && StringUtils.isNotEmpty(model.getResourceUrl())) {
				resourcesServiceImpl.saveOrUpdate(model);
				setAttributeToRequest("msg", "新增资源成功。");
			}
		}
		return "user/resources/resource-input-success";
	}
	
	public String edit() throws Exception {
		
		return "edit";
	}
	
	@RequestMapping("/resources-delete")
	@ResponseBody
	public String delete(Long resourceId) throws Exception {
		resourcesServiceImpl.delete(resourceId);
		return SUCCESS;
	}
	
	/**
	 * 新增权限时，该权限供选择的资源
	 * @author yinlei
	 * date 2012-6-24 下午11:33:30
	 */
	@RequestMapping("resources-list")
	public String list(Resources model, PageBean<Resources> pageBean, Map<String, Object> maps) throws Exception {
		page = pageBean.getPage();
		//page.setPageSize(20);
		resourcesServiceImpl.queryForPage(page, model);
		listResult = page.getResult();
		maps.put("listResult", listResult);
		setAttributeToRequest(CommonConst.PAGE_NAME, page);
		return "user/resources/resource-list";
	}

	@Inject
	@Override
	public void setBaseServiceImpl(BaseService<Resources, ?> resourcesServiceImpl) {
		this.baseServiceImpl = resourcesServiceImpl;
		this.resourcesServiceImpl = (IResourcesService) resourcesServiceImpl;
	}

}
