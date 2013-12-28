package com.vteba.supplychain.purchase.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.persister.hibernate.impl.HibernateGenericDaoImpl;
import com.vteba.service.multitenant.annotation.Schema;
import com.vteba.supplychain.purchase.dao.IPurchaseOrderDao;
import com.vteba.supplychain.purchase.model.PurchaseOrder;

/**
 * 采购订单DAO实现
 * @author yinlei 
 * date 2012-8-7 下午5:39:43
 */
@Named
@Schema(schemaName = "supplychain")
public class PurchaseOrderDaoImpl extends HibernateGenericDaoImpl<PurchaseOrder, String>
		implements IPurchaseOrderDao {

	public PurchaseOrderDaoImpl() {
		super();
	}

	public PurchaseOrderDaoImpl(Class<PurchaseOrder> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory supplychainSessionFactory) {
		this.sessionFactory = supplychainSessionFactory;
	}


}
