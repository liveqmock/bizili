package com.vteba.supplychain.common.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;
import com.vteba.supplychain.common.dao.ISupplierDao;
import com.vteba.supplychain.common.model.Supplier;
import com.vteba.supplychain.common.service.ISupplierService;

/**
 * 供应商service实现
 * @author yinlei 
 * date 2012-8-8 下午5:21:09
 */
@Named
public class SupplierServiceImpl extends GenericServiceImpl<Supplier, String> implements
		ISupplierService {
	
	private ISupplierDao supplierDaoImpl;
	
	@Override
	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<Supplier, String> supplierDaoImpl) {
		this.hibernateGenericDaoImpl = supplierDaoImpl;
		this.supplierDaoImpl = (ISupplierDao) supplierDaoImpl;
	}

	public ISupplierDao getSupplierDaoImpl() {
		return supplierDaoImpl;
	}

}
