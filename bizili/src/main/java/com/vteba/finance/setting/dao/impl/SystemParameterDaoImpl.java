package com.vteba.finance.setting.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.finance.setting.dao.ISystemParameterDao;
import com.vteba.finance.setting.model.SystemParameter;
import com.vteba.tx.hibernate.impl.BaseGenericDaoImpl;

/**
 * 系统参数设定Dao实现
 * @author yinlei 
 * date 2012-6-29 下午11:13:55
 */
@Named
public class SystemParameterDaoImpl extends BaseGenericDaoImpl<SystemParameter, String>
		implements ISystemParameterDao {

	public SystemParameterDaoImpl() {
		super();
	}

	public SystemParameterDaoImpl(Class<SystemParameter> entityClass) {
		super(entityClass);
	}

	@Inject
	public void setSessionFactory(SessionFactory biziliSessionFactory) {
		this.sessionFactory = biziliSessionFactory;
	}

}
