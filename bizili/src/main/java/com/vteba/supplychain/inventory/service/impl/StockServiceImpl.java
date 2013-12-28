package com.vteba.supplychain.inventory.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;
import com.vteba.supplychain.inventory.dao.IStockDao;
import com.vteba.supplychain.inventory.model.Stock;
import com.vteba.supplychain.inventory.service.IStockService;

/**
 * 库存商品service实现
 * @author yinlei 
 * date 2012-8-8 下午6:10:38
 */
@Named
public class StockServiceImpl extends GenericServiceImpl<Stock, String> implements
		IStockService {
	
	private IStockDao stockDaoImpl;
	
	public StockServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<Stock, String> stockDaoImpl) {
		this.hibernateGenericDaoImpl = stockDaoImpl;
		this.stockDaoImpl = (IStockDao) stockDaoImpl;
	}

	public IStockDao getStockDaoImpl() {
		return stockDaoImpl;
	}

}
