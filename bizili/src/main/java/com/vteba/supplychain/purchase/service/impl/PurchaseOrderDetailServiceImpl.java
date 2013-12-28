package com.vteba.supplychain.purchase.service.impl;

import javax.inject.Named;

import com.opensymphony.xwork2.inject.Inject;
import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;
import com.vteba.supplychain.purchase.dao.IPurchaseOrderDetailDao;
import com.vteba.supplychain.purchase.model.PurchaseOrderDetail;
import com.vteba.supplychain.purchase.service.IPurchaseOrderDetailService;

/**
 * 采购订单明细service实现
 * @author yinlei 
 * date 2012-8-25 下午4:23:19
 */
@Named
public class PurchaseOrderDetailServiceImpl extends GenericServiceImpl<PurchaseOrderDetail, String>
		implements IPurchaseOrderDetailService {
	
	private IPurchaseOrderDetailDao purchaseOrderDetailDaoImpl;
	
	public PurchaseOrderDetailServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<PurchaseOrderDetail, String> purchaseOrderDetailDaoImpl) {
		this.hibernateGenericDaoImpl = purchaseOrderDetailDaoImpl;
		this.purchaseOrderDetailDaoImpl = (IPurchaseOrderDetailDao) purchaseOrderDetailDaoImpl;
	}

	public IPurchaseOrderDetailDao getPurchaseOrderDetailDaoImpl() {
		return purchaseOrderDetailDaoImpl;
	}

}
