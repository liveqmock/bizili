package com.vteba.supplychain.common.dao.impl;

import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.persister.hibernate.impl.HibernateGenericDaoImpl;
import com.vteba.supplychain.common.dao.ISupplierDao;
import com.vteba.supplychain.common.model.Supplier;

/**
 * 供应商DAO实现
 * @author yinlei 
 * date 2012-8-8 下午5:04:12
 */
@Named
public class SupplierDaoImpl extends HibernateGenericDaoImpl<Supplier, String> implements
		ISupplierDao {

	public SupplierDaoImpl() {
		super();
	}

	public SupplierDaoImpl(Class<Supplier> entityClass) {
		super(entityClass);
	}

	@Override
	@Named
	public void setSessionFactory(SessionFactory supplychainSessionFactory) {
		this.sessionFactory = supplychainSessionFactory;
	}

}
