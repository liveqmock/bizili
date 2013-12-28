package com.vteba.supplychain.common.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.persister.hibernate.impl.HibernateGenericDaoImpl;
import com.vteba.supplychain.common.dao.ICustomerDao;
import com.vteba.supplychain.common.model.Customer;

/**
 * 客户DAO实现
 * @author yinlei 
 * date 2012-8-8 下午5:02:35
 */
@Named
public class CustomerDaoImpl extends HibernateGenericDaoImpl<Customer, String> implements
		ICustomerDao {
	
	public CustomerDaoImpl() {
		super();
	}

	public CustomerDaoImpl(Class<Customer> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory supplychainSessionFactory) {
		this.sessionFactory = supplychainSessionFactory;
	}

}
