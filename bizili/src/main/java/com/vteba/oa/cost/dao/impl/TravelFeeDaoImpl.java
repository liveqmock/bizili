package com.vteba.oa.cost.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.oa.cost.dao.ITravelFeeDao;
import com.vteba.oa.cost.model.TravelFee;
import com.vteba.persister.hibernate.impl.HibernateGenericDaoImpl;

/**
 * 旅费DAO实现
 * @author yinlei 
 * date 2012-7-6 下午10:03:45
 */
@Named
public class TravelFeeDaoImpl extends HibernateGenericDaoImpl<TravelFee, String> implements
		ITravelFeeDao {

	public TravelFeeDaoImpl() {
		super();
	}

	public TravelFeeDaoImpl(Class<TravelFee> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory skmbwSessionFactory) {
		this.sessionFactory = skmbwSessionFactory;
	}

}
