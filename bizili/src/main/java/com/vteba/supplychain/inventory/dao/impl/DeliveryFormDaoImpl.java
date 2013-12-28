package com.vteba.supplychain.inventory.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.persister.hibernate.impl.HibernateGenericDaoImpl;
import com.vteba.supplychain.inventory.dao.IDeliveryFormDao;
import com.vteba.supplychain.inventory.model.DeliveryForm;

/**
 * 出库单DAO实现
 * @author yinlei 
 * date 2012-8-8 下午5:34:13
 */
@Named
public class DeliveryFormDaoImpl extends HibernateGenericDaoImpl<DeliveryForm, String>
		implements IDeliveryFormDao {

	public DeliveryFormDaoImpl() {
		super();
	}

	public DeliveryFormDaoImpl(Class<DeliveryForm> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory supplychainSessionFactory) {
		this.sessionFactory = supplychainSessionFactory;
	}

}
