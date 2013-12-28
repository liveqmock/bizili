package com.vteba.supplychain.purchase.service.impl;

import javax.inject.Named;

import com.opensymphony.xwork2.inject.Inject;
import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;
import com.vteba.supplychain.purchase.dao.IPurchaseFormDetailDao;
import com.vteba.supplychain.purchase.model.PurchaseFormDetail;
import com.vteba.supplychain.purchase.service.IPurchaseFormDetailService;

/**
 * 采购订单明细service实现
 * @author yinlei 
 * date 2012-8-25 下午4:20:16
 */
@Named
public class PurchaseFormDetailServiceImpl extends GenericServiceImpl<PurchaseFormDetail, String>
		implements IPurchaseFormDetailService {
	
	private IPurchaseFormDetailDao purchaseFormDetailDaoImpl;
	
	public PurchaseFormDetailServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<PurchaseFormDetail, String> purchaseFormDetailDaoImpl) {
		this.hibernateGenericDaoImpl = purchaseFormDetailDaoImpl;
		this.purchaseFormDetailDaoImpl = (IPurchaseFormDetailDao) purchaseFormDetailDaoImpl;
	}

	public IPurchaseFormDetailDao getPurchaseFormDetailDaoImpl() {
		return purchaseFormDetailDaoImpl;
	}

}
