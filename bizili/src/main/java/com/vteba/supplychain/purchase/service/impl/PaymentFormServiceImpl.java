package com.vteba.supplychain.purchase.service.impl;

import javax.inject.Named;

import com.opensymphony.xwork2.inject.Inject;
import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;
import com.vteba.supplychain.purchase.dao.IPaymentFormDao;
import com.vteba.supplychain.purchase.model.PaymentForm;
import com.vteba.supplychain.purchase.service.IPaymentFormService;

/**
 * 付款单service实现
 * @author yinlei 
 * date 2012-8-25 下午4:13:43
 */
@Named
public class PaymentFormServiceImpl extends GenericServiceImpl<PaymentForm, String> implements
		IPaymentFormService {
	
	private IPaymentFormDao paymentFormDaoImpl;
	
	public PaymentFormServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<PaymentForm, String> paymentFormDaoImpl) {
		this.hibernateGenericDaoImpl = paymentFormDaoImpl;
		this.paymentFormDaoImpl = (IPaymentFormDao) paymentFormDaoImpl;
	}

	public IPaymentFormDao getPaymentFormDaoImpl() {
		return paymentFormDaoImpl;
	}

}
