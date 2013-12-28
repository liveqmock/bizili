package com.vteba.supplychain.purchase.dao.impl;

import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.opensymphony.xwork2.inject.Inject;
import com.vteba.persister.hibernate.impl.HibernateGenericDaoImpl;
import com.vteba.service.multitenant.annotation.Schema;
import com.vteba.supplychain.purchase.dao.IPurchaseFormDetailDao;
import com.vteba.supplychain.purchase.model.PurchaseFormDetail;

/**
 * 采购单明细DAO实现
 * @author yinlei 
 * date 2012-8-25 下午3:49:09
 */
@Named
@Schema(schemaName = "supplychain")
public class PurchaseFormDetailDaoImpl extends HibernateGenericDaoImpl<PurchaseFormDetail, String>
		implements IPurchaseFormDetailDao {

	public PurchaseFormDetailDaoImpl() {
		super();
	}

	public PurchaseFormDetailDaoImpl(Class<PurchaseFormDetail> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory supplychainSessionFactory) {
		this.sessionFactory = supplychainSessionFactory;
	}

}
