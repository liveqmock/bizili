package com.vteba.finance.setting.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.finance.setting.dao.ISystemParameterDao;
import com.vteba.finance.setting.model.SystemParameter;
import com.vteba.finance.setting.service.ISystemParameterService;
import com.vteba.tx.hibernate.BaseGenericDao;
import com.vteba.service.generic.impl.BaseServiceImpl;

/**
 * 系统参数设置service实现
 * @author yinlei 
 * date 2012-6-29 下午11:20:39
 */
@Named
public class SystemParameterServiceImpl extends BaseServiceImpl<SystemParameter, String>
		implements ISystemParameterService {

	private ISystemParameterDao systemParameterDaoImpl;
	
	public SystemParameterServiceImpl() {
		super();
	}

	@Inject
	public void setBaseGenericDaoImpl(
			BaseGenericDao<SystemParameter, String> systemParameterDaoImpl) {
		this.baseGenericDaoImpl = systemParameterDaoImpl;
		this.systemParameterDaoImpl = (ISystemParameterDao) systemParameterDaoImpl;
	}

	public ISystemParameterDao getSystemParameterDaoImpl() {
		return systemParameterDaoImpl;
	}

}
