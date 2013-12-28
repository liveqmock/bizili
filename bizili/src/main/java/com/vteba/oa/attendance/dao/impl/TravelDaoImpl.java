package com.vteba.oa.attendance.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.oa.attendance.dao.ITravelDao;
import com.vteba.oa.attendance.model.Travel;
import com.vteba.persister.hibernate.impl.HibernateGenericDaoImpl;

/**
 * 出差单DAO实现
 * @author yinlei 
 * date 2012-7-6 下午9:28:13
 */
@Named
public class TravelDaoImpl extends HibernateGenericDaoImpl<Travel, String>
		implements ITravelDao {
	
	public TravelDaoImpl() {
		super();
	}

	public TravelDaoImpl(Class<Travel> entityClass) {
		super(entityClass);
	}

	@Inject
	public void setSessionFactory(SessionFactory skmbwSessionFactory) {
		this.sessionFactory = skmbwSessionFactory;
	}

}
