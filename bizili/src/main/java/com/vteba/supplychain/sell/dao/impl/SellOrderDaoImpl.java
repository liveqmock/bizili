package com.vteba.supplychain.sell.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.persister.hibernate.impl.HibernateGenericDaoImpl;
import com.vteba.supplychain.sell.dao.ISellOrderDao;
import com.vteba.supplychain.sell.model.SellOrder;

/**
 * 销售订单DAO实现
 * @author yinlei 
 * date 2012-8-12 上午11:42:03
 */
@Named
public class SellOrderDaoImpl extends HibernateGenericDaoImpl<SellOrder, String> implements
		ISellOrderDao {

	public SellOrderDaoImpl() {
		super();
	}

	public SellOrderDaoImpl(Class<SellOrder> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory supplychainSessionFactory) {
		this.sessionFactory = supplychainSessionFactory;
	}

}
