package com.vteba.supplychain.common.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;
import com.vteba.supplychain.common.dao.ICustomerDao;
import com.vteba.supplychain.common.model.Customer;
import com.vteba.supplychain.common.service.ICustomerService;

/**
 * 客户服务service实现
 * @author yinlei 
 * date 2012-8-8 下午5:09:54
 */
@Named
public class CustomerServiceImpl extends GenericServiceImpl<Customer, String> implements
		ICustomerService {
	
	private ICustomerDao customerDaoImpl;
	
	public CustomerServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<Customer, String> customerDaoImpl) {
		this.hibernateGenericDaoImpl = customerDaoImpl;
		this.customerDaoImpl = (ICustomerDao) customerDaoImpl;
	}

	public ICustomerDao getCustomerDaoImpl() {
		return customerDaoImpl;
	}

}
