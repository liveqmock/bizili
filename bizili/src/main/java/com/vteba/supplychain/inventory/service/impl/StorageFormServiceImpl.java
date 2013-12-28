package com.vteba.supplychain.inventory.service.impl;

import javax.inject.Named;

import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;
import com.vteba.supplychain.inventory.dao.IStorageFormDao;
import com.vteba.supplychain.inventory.model.StorageForm;
import com.vteba.supplychain.inventory.service.IStorageFormService;

/**
 * 入库单service实现
 * @author yinlei 
 * date 2012-8-8 下午6:14:00
 */
@Named
public class StorageFormServiceImpl extends GenericServiceImpl<StorageForm, String> implements
		IStorageFormService {

	private IStorageFormDao storageFormDaoImpl;
	
	public StorageFormServiceImpl() {
		super();
	}

	@Override
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<StorageForm, String> storageFormDaoImpl) {
		this.hibernateGenericDaoImpl = storageFormDaoImpl;
		this.storageFormDaoImpl = (IStorageFormDao) storageFormDaoImpl;
	}

	public IStorageFormDao getStorageFormDaoImpl() {
		return storageFormDaoImpl;
	}
}
