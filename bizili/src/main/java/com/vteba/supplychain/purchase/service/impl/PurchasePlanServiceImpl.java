package com.vteba.supplychain.purchase.service.impl;

import javax.inject.Named;

import com.opensymphony.xwork2.inject.Inject;
import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;
import com.vteba.supplychain.purchase.dao.IPurchasePlanDao;
import com.vteba.supplychain.purchase.model.PurchasePlan;
import com.vteba.supplychain.purchase.service.IPurchasePlanService;

/**
 * 采购计划service实现
 * @author yinlei 
 * date 2012-8-25 下午4:26:21
 */
@Named
public class PurchasePlanServiceImpl extends GenericServiceImpl<PurchasePlan, String>
		implements IPurchasePlanService {
	
	private IPurchasePlanDao purchasePlanDaoImpl;
	
	public PurchasePlanServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<PurchasePlan, String> purchasePlanDaoImpl) {
		this.hibernateGenericDaoImpl = purchasePlanDaoImpl;
		this.purchasePlanDaoImpl = (IPurchasePlanDao) purchasePlanDaoImpl;
	}

	public IPurchasePlanDao getPurchasePlanDaoImpl() {
		return purchasePlanDaoImpl;
	}

}
