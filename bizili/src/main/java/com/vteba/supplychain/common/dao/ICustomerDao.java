package com.vteba.supplychain.common.dao;

import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.supplychain.common.model.Customer;

/**
 * 客户DAO
 * @author yinlei 
 * date 2012-8-8 下午4:58:27
 */
public interface ICustomerDao extends IHibernateGenericDao<Customer, String> {

}
