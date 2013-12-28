package com.vteba.supplychain.purchase.dao.impl;

import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.opensymphony.xwork2.inject.Inject;
import com.vteba.persister.hibernate.impl.HibernateGenericDaoImpl;
import com.vteba.service.multitenant.annotation.Schema;
import com.vteba.supplychain.purchase.dao.IPurchaseOrderDetailDao;
import com.vteba.supplychain.purchase.model.PurchaseOrderDetail;

/**
 * 采购订单明细DAO实现
 * @author yinlei 
 * date 2012-8-25 下午3:52:23
 */
@Named
@Schema(schemaName = "supplychain")
public class PurchaseOrderDetailDaoImpl extends HibernateGenericDaoImpl<PurchaseOrderDetail, String>
		implements IPurchaseOrderDetailDao {

	@Override
	@Inject
	public void setSessionFactory(SessionFactory supplychainSessionFactory) {
		this.sessionFactory = supplychainSessionFactory;
	}

}
