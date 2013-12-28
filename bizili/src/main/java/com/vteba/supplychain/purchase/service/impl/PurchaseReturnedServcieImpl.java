package com.vteba.supplychain.purchase.service.impl;

import javax.inject.Named;

import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;
import com.vteba.supplychain.purchase.dao.IPurchaseReturnedDao;
import com.vteba.supplychain.purchase.model.PurchaseReturned;
import com.vteba.supplychain.purchase.service.IPurchaseReturnedService;

/**
 * 采购退货单service实现
 * @author yinlei 
 * date 2012-8-25 下午4:28:57
 */
@Named
public class PurchaseReturnedServcieImpl extends GenericServiceImpl<PurchaseReturned, String>
		implements IPurchaseReturnedService {
	
	private IPurchaseReturnedDao purchaseReturnedDaoImpl;
	
	public PurchaseReturnedServcieImpl() {
		super();
	}

	@Override
	@Named
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<PurchaseReturned, String> purchaseReturnedDaoImpl) {
		this.hibernateGenericDaoImpl = purchaseReturnedDaoImpl;
		this.purchaseReturnedDaoImpl = (IPurchaseReturnedDao)purchaseReturnedDaoImpl;
	}

	public IPurchaseReturnedDao getPurchaseReturnedDaoImpl() {
		return purchaseReturnedDaoImpl;
	}

}
