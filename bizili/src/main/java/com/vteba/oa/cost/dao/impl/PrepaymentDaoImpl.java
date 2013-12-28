package com.vteba.oa.cost.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.oa.cost.dao.IPrepaymentDao;
import com.vteba.oa.cost.model.Prepayment;
import com.vteba.persister.hibernate.impl.HibernateGenericDaoImpl;

/**
 * 预付款DAO实现
 * @author yinlei 
 * date 2012-7-6 下午9:58:12
 */
@Named
public class PrepaymentDaoImpl extends HibernateGenericDaoImpl<Prepayment, String> implements
		IPrepaymentDao {

	public PrepaymentDaoImpl() {
		super();
	}

	public PrepaymentDaoImpl(Class<Prepayment> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory skmbwSessionFactory) {
		this.sessionFactory = skmbwSessionFactory;
	}

	

}
