package com.vteba.oa.attendance.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.oa.attendance.dao.IOvertimeDao;
import com.vteba.oa.attendance.model.Overtime;
import com.vteba.persister.hibernate.impl.HibernateGenericDaoImpl;

/**
 * 加班DAO实现
 * @author yinlei 
 * date 2012-7-6 下午9:37:44
 */
@Named
public class OvertimeDaoImpl extends HibernateGenericDaoImpl<Overtime, String> implements
		IOvertimeDao {

	public OvertimeDaoImpl() {
		super();
	}

	public OvertimeDaoImpl(Class<Overtime> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory skmbwSessionFactory) {
		this.sessionFactory = skmbwSessionFactory;
	}

}
