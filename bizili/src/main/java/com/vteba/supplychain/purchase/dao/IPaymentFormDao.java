package com.vteba.supplychain.purchase.dao;

import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.supplychain.purchase.model.PaymentForm;

/**
 * 付款单DAO
 * @author yinlei 
 * date 2012-8-25 下午3:31:24
 */
public interface IPaymentFormDao extends IHibernateGenericDao<PaymentForm, String> {

}
