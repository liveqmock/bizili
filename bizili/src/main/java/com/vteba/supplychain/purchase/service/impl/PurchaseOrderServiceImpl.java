package com.vteba.supplychain.purchase.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;
import com.vteba.supplychain.purchase.dao.IPurchaseOrderDao;
import com.vteba.supplychain.purchase.model.PurchaseOrder;
import com.vteba.supplychain.purchase.service.IPurchaseOrderService;

/**
 * 采购订单service实现
 * @author yinlei 
 * date 2012-8-8 下午4:56:27
 */
@Named
public class PurchaseOrderServiceImpl extends GenericServiceImpl<PurchaseOrder, String>
		implements IPurchaseOrderService {
	
	private IPurchaseOrderDao purchaseOrderDaoImpl;
	
	public PurchaseOrderServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<PurchaseOrder, String> purchaseOrderDaoImpl) {
		this.hibernateGenericDaoImpl = purchaseOrderDaoImpl;
		this.purchaseOrderDaoImpl = (IPurchaseOrderDao) purchaseOrderDaoImpl;
	}

	public IPurchaseOrderDao getPurchaseOrderDaoImpl() {
		return purchaseOrderDaoImpl;
	}

}
