package com.vteba.oa.attendance.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.oa.attendance.dao.ILeaveDao;
import com.vteba.oa.attendance.model.Leave;
import com.vteba.persister.hibernate.impl.HibernateGenericDaoImpl;

/**
 * 请假DAO实现
 * @author yinlei 
 * date 2012-7-6 下午9:36:34
 */
@Named
public class LeaveDaoImpl extends HibernateGenericDaoImpl<Leave, String> implements
		ILeaveDao {

	public LeaveDaoImpl() {
		super();
	}

	public LeaveDaoImpl(Class<Leave> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory skmbwSessionFactory) {
		this.sessionFactory = skmbwSessionFactory;
	}

}
