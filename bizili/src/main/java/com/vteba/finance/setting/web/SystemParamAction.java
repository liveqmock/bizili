package com.vteba.finance.setting.web;

import java.io.Serializable;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vteba.finance.setting.model.SystemParameter;
import com.vteba.finance.setting.service.ISystemParameterService;
import com.vteba.service.generic.BaseService;
import com.vteba.utils.reflection.ReflectUtils;
import com.vteba.web.action.BasicAction;

/**
 * 系统参数设定action
 * @author yinlei 
 * date 2012-6-24 下午11:12:46
 */
@Controller
@RequestMapping("/setting")
public class SystemParamAction extends BasicAction<SystemParameter> {
	private ISystemParameterService systemParameterServiceImpl;
	
	@Inject
	public void setSystemParameterServiceImpl(
			ISystemParameterService systemParameterServiceImpl) {
		this.systemParameterServiceImpl = systemParameterServiceImpl;
	}

	@RequestMapping("/systemparam-initial")
	public String initial(SystemParameter model, Map<String, Object> maps) throws Exception {
		ReflectUtils.emptyToNull(model);
		listResult = systemParameterServiceImpl.getEntityList(model);
		maps.put("listResult", listResult);
		return "setting/systemparam/systemparam-initial-success";
	}

	@Override
	public void setBaseServiceImpl(
			BaseService<SystemParameter, ? extends Serializable> BaseServiceImpl) {
		// TODO Auto-generated method stub
		
	}
	
}
