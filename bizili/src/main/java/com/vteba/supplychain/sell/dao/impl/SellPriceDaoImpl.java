package com.vteba.supplychain.sell.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.persister.hibernate.impl.HibernateGenericDaoImpl;
import com.vteba.supplychain.sell.dao.ISellPriceDao;
import com.vteba.supplychain.sell.model.SellPrice;

/**
 * 存货销售价格列表DAO实现
 * @author yinlei 
 * date 2012-8-12 上午11:44:01
 */
@Named
public class SellPriceDaoImpl extends HibernateGenericDaoImpl<SellPrice, String> implements
		ISellPriceDao {

	public SellPriceDaoImpl() {
		super();
	}

	public SellPriceDaoImpl(Class<SellPrice> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory supplychainSessionFactory) {
		this.sessionFactory = supplychainSessionFactory;
	}

}
