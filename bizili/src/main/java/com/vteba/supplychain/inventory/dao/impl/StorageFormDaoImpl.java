package com.vteba.supplychain.inventory.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.persister.hibernate.impl.HibernateGenericDaoImpl;
import com.vteba.supplychain.inventory.dao.IStorageFormDao;
import com.vteba.supplychain.inventory.model.StorageForm;

/**
 * 入库单DAO实现
 * @author yinlei 
 * date 2012-8-8 下午5:46:42
 */
@Named
public class StorageFormDaoImpl extends HibernateGenericDaoImpl<StorageForm, String> implements
		IStorageFormDao {

	public StorageFormDaoImpl() {
		super();
	}

	public StorageFormDaoImpl(Class<StorageForm> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory supplychainSessionFactory) {
		this.sessionFactory = supplychainSessionFactory;
	}

}
