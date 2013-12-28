package com.vteba.finance.setting.web;

import javax.inject.Inject;

import com.vteba.finance.setting.model.SystemParameter;
import com.vteba.finance.setting.service.ISystemParameterService;
import com.vteba.util.reflection.ReflectUtils;
import com.vteba.web.action.BaseAction;

/**
 * 系统参数设定action
 * @author yinlei 
 * date 2012-6-24 下午11:12:46
 */
public class SystemParamAction extends BaseAction<SystemParameter> {
	private static final long serialVersionUID = 2508517033078958845L;
	private SystemParameter model = new SystemParameter();
	private ISystemParameterService systemParameterServiceImpl;
	
	@Inject
	public void setSystemParameterServiceImpl(
			ISystemParameterService systemParameterServiceImpl) {
		this.systemParameterServiceImpl = systemParameterServiceImpl;
	}

	@Override
	public SystemParameter getModel() {
		return model;
	}

	@Override
	public String initial() throws Exception {
		ReflectUtils.emptyToNull(model);
		listResult = systemParameterServiceImpl.getListByPropertyEqual(SystemParameter.class, model);
		return SUCCESS;
	}
	
	public String input() throws Exception {
		
		return INPUT;
	}

}
