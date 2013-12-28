package com.vteba.oa.cost.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.oa.cost.dao.IRequestPayDao;
import com.vteba.oa.cost.model.RequestPay;
import com.vteba.persister.hibernate.impl.HibernateGenericDaoImpl;

/**
 * 请款申请单DAO实现
 * @author yinlei 
 * date 2012-7-6 下午10:01:04
 */
@Named
public class RequestPayDaoImpl extends HibernateGenericDaoImpl<RequestPay, String> implements
		IRequestPayDao {

	public RequestPayDaoImpl() {
		super();
	}

	public RequestPayDaoImpl(Class<RequestPay> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory skmbwSessionFactory) {
		this.sessionFactory = skmbwSessionFactory;
	}

}
