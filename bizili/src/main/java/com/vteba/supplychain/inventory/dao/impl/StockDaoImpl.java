package com.vteba.supplychain.inventory.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.persister.hibernate.impl.HibernateGenericDaoImpl;
import com.vteba.persister.jdbc.spring.SpringJdbcTemplate;
import com.vteba.supplychain.inventory.dao.IStockDao;
import com.vteba.supplychain.inventory.model.Stock;

/**
 * 库存商品DAO实现
 * @author yinlei 
 * date 2012-8-8 下午5:37:10
 */
@Named
public class StockDaoImpl extends HibernateGenericDaoImpl<Stock, String> implements
		IStockDao {

	public StockDaoImpl() {
		super();
	}

	public StockDaoImpl(Class<Stock> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory supplychainSessionFactory) {
		this.sessionFactory = supplychainSessionFactory;
	}
	
	@Override
	@Inject
	public void setSpringJdbcTemplate(SpringJdbcTemplate supplychainJdbcTemplate){
		this.springJdbcTemplate = supplychainJdbcTemplate;
	}
}
