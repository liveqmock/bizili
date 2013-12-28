package com.vteba.supplychain.sell.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.persister.hibernate.impl.HibernateGenericDaoImpl;
import com.vteba.supplychain.sell.dao.IDeliveryOrderDao;
import com.vteba.supplychain.sell.model.DeliveryOrder;

/**
 * 发货单DAO实现
 * @author yinlei 
 * date 2012-8-8 下午8:05:31
 */
@Named
public class DeliveryOrderDaoImpl extends HibernateGenericDaoImpl<DeliveryOrder, String>
		implements IDeliveryOrderDao {

	public DeliveryOrderDaoImpl() {
		super();
	}

	public DeliveryOrderDaoImpl(Class<DeliveryOrder> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory supplychainSessionFactory) {
		this.sessionFactory = supplychainSessionFactory;
	}

}
