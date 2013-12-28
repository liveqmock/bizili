package com.vteba.oa.attendance.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.oa.attendance.dao.IOvertimeDao;
import com.vteba.oa.attendance.model.Overtime;
import com.vteba.oa.attendance.service.IOvertimeService;
import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;

/**
 * 加班service实现
 * @author yinlei 
 * date 2012-7-6 下午9:47:42
 */
@Named
public class OvertimeServiceImpl extends GenericServiceImpl<Overtime, String> implements
		IOvertimeService {
	
	private IOvertimeDao overtimeDaoImpl;
	
	public OvertimeServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<Overtime, String> overtimeDaoImpl) {
		this.hibernateGenericDaoImpl = overtimeDaoImpl;
		this.overtimeDaoImpl = (IOvertimeDao) overtimeDaoImpl;
	}

	public IOvertimeDao getOvertimeDaoImpl() {
		return overtimeDaoImpl;
	}

}
