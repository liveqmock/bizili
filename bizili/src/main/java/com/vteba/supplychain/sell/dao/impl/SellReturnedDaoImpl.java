package com.vteba.supplychain.sell.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.persister.hibernate.impl.HibernateGenericDaoImpl;
import com.vteba.supplychain.sell.dao.ISellReturnedDao;
import com.vteba.supplychain.sell.model.SellReturned;

/**
 * 销售退货单DAO实现
 * @author yinlei 
 * date 2012-8-12 上午11:45:56
 */
@Named
public class SellReturnedDaoImpl extends HibernateGenericDaoImpl<SellReturned, String>
		implements ISellReturnedDao {

	public SellReturnedDaoImpl() {
		super();
	}

	public SellReturnedDaoImpl(Class<SellReturned> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory supplychainSessionFactory) {
		this.sessionFactory = supplychainSessionFactory;
	}

}
