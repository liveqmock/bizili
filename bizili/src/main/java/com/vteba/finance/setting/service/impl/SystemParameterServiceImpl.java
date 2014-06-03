package com.vteba.finance.setting.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.finance.setting.dao.ISystemParameterDao;
import com.vteba.finance.setting.model.SystemParameter;
import com.vteba.finance.setting.service.ISystemParameterService;
import com.vteba.tx.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;

/**
 * 系统参数设置service实现
 * @author yinlei 
 * date 2012-6-29 下午11:20:39
 */
@Named
public class SystemParameterServiceImpl extends GenericServiceImpl<SystemParameter, String>
		implements ISystemParameterService {

	private ISystemParameterDao systemParameterDaoImpl;
	
	public SystemParameterServiceImpl() {
		super();
	}

	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<SystemParameter, String> systemParameterDaoImpl) {
		this.hibernateGenericDaoImpl = systemParameterDaoImpl;
		this.systemParameterDaoImpl = (ISystemParameterDao) systemParameterDaoImpl;
	}

	public ISystemParameterDao getSystemParameterDaoImpl() {
		return systemParameterDaoImpl;
	}

}
