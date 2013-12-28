package com.vteba.supplychain.purchase.dao.impl;

import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.opensymphony.xwork2.inject.Inject;
import com.vteba.persister.hibernate.impl.HibernateGenericDaoImpl;
import com.vteba.service.multitenant.annotation.Schema;
import com.vteba.supplychain.purchase.dao.IPaymentFormDao;
import com.vteba.supplychain.purchase.model.PaymentForm;

/**
 * 付款单DAO实现
 * @author yinlei 
 * date 2012-8-25 下午3:43:57
 */
@Named
@Schema(schemaName = "supplychain")
public class PaymentFormDaoImpl extends HibernateGenericDaoImpl<PaymentForm, String>
		implements IPaymentFormDao {

	public PaymentFormDaoImpl() {
		super();
	}

	public PaymentFormDaoImpl(Class<PaymentForm> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory supplychainSessionFactory) {
		this.sessionFactory = supplychainSessionFactory;
	}

}
