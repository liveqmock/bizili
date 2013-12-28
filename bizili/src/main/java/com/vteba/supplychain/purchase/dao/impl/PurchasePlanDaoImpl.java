package com.vteba.supplychain.purchase.dao.impl;

import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.opensymphony.xwork2.inject.Inject;
import com.vteba.persister.hibernate.impl.HibernateGenericDaoImpl;
import com.vteba.service.multitenant.annotation.Schema;
import com.vteba.supplychain.purchase.dao.IPurchasePlanDao;
import com.vteba.supplychain.purchase.model.PurchasePlan;

/**
 * 采购计划DAO实现
 * @author yinlei 
 * date 2012-8-25 下午3:55:38
 */
@Named
@Schema(schemaName = "supplychain")
public class PurchasePlanDaoImpl extends HibernateGenericDaoImpl<PurchasePlan, String>
		implements IPurchasePlanDao {

	public PurchasePlanDaoImpl() {
		super();
	}

	public PurchasePlanDaoImpl(Class<PurchasePlan> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory supplychainSessionFactory) {
		this.sessionFactory = supplychainSessionFactory;
	}

}
