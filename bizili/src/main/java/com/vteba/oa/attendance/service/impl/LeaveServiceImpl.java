package com.vteba.oa.attendance.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.oa.attendance.dao.ILeaveDao;
import com.vteba.oa.attendance.model.Leave;
import com.vteba.oa.attendance.service.ILeaveService;
import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;

/**
 * 请假service实现
 * @author yinlei 
 * date 2012-7-6 下午9:44:24
 */
@Named
public class LeaveServiceImpl extends GenericServiceImpl<Leave, String> implements
		ILeaveService {
	
	private ILeaveDao leaveDaoImpl;
	
	public LeaveServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<Leave, String> leaveDaoImpl) {
		this.hibernateGenericDaoImpl = leaveDaoImpl;
		this.leaveDaoImpl = (ILeaveDao) leaveDaoImpl;
	}

	public ILeaveDao getLeaveDaoImpl() {
		return leaveDaoImpl;
	}

}
