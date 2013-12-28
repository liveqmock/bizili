package com.vteba.supplychain.purchase.dao.impl;

import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.opensymphony.xwork2.inject.Inject;
import com.vteba.persister.hibernate.impl.HibernateGenericDaoImpl;
import com.vteba.service.multitenant.annotation.Schema;
import com.vteba.supplychain.purchase.dao.IPurchaseReturnedDao;
import com.vteba.supplychain.purchase.model.PurchaseReturned;

/**
 * 采购退货单DAO实现
 * @author yinlei 
 * date 2012-8-25 下午3:57:51
 */
@Named
@Schema(schemaName = "supplychain")
public class PurchaseReturnedDaoImpl extends HibernateGenericDaoImpl<PurchaseReturned, String>
		implements IPurchaseReturnedDao {

	public PurchaseReturnedDaoImpl() {
		super();
	}

	public PurchaseReturnedDaoImpl(Class<PurchaseReturned> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory supplychainSessionFactory) {
		this.sessionFactory = supplychainSessionFactory;
	}

}
