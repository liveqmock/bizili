package com.vteba.supplychain.purchase.dao.impl;

import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.opensymphony.xwork2.inject.Inject;
import com.vteba.persister.hibernate.impl.HibernateGenericDaoImpl;
import com.vteba.service.multitenant.annotation.Schema;
import com.vteba.supplychain.purchase.dao.IPurchaseFormDao;
import com.vteba.supplychain.purchase.model.PurchaseForm;

/**
 * 采购单DAO实现
 * @author yinlei 
 * date 2012-8-25 下午3:46:28
 */
@Named
@Schema(schemaName = "supplychain")
public class PurchaseFormDaoImpl extends HibernateGenericDaoImpl<PurchaseForm, String>
		implements IPurchaseFormDao {

	public PurchaseFormDaoImpl() {
		super();
	}

	public PurchaseFormDaoImpl(Class<PurchaseForm> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory supplychainSessionFactory) {
		this.sessionFactory = supplychainSessionFactory;
	}

}
