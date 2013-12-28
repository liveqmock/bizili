package com.vteba.supplychain.purchase.service.impl;

import javax.inject.Named;

import com.opensymphony.xwork2.inject.Inject;
import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;
import com.vteba.supplychain.purchase.dao.IPurchaseFormDao;
import com.vteba.supplychain.purchase.model.PurchaseForm;
import com.vteba.supplychain.purchase.service.IPurchaseFormService;

/**
 * 采购单service实现
 * @author yinlei 
 * date 2012-8-25 下午4:17:35
 */
@Named
public class PurchaseFormServiceImpl extends GenericServiceImpl<PurchaseForm, String>
		implements IPurchaseFormService {
	
	private IPurchaseFormDao purchaseFormDaoImpl;
	
	public PurchaseFormServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<PurchaseForm, String> purchaseFormDaoImpl) {
		this.hibernateGenericDaoImpl = purchaseFormDaoImpl;
		this.purchaseFormDaoImpl = (IPurchaseFormDao) purchaseFormDaoImpl;
	}

	public IPurchaseFormDao getPurchaseFormDaoImpl() {
		return purchaseFormDaoImpl;
	}

}
