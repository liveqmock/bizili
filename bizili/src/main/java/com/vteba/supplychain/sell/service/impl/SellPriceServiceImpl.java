package com.vteba.supplychain.sell.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;
import com.vteba.supplychain.sell.dao.ISellPriceDao;
import com.vteba.supplychain.sell.model.SellPrice;
import com.vteba.supplychain.sell.service.ISellPriceService;

/**
 * 存货销售价格列表service实现
 * @author yinlei 
 * date 2012-8-12 下午12:02:43
 */
@Named
public class SellPriceServiceImpl extends GenericServiceImpl<SellPrice, String> implements
		ISellPriceService {
	
	private ISellPriceDao sellPriceDaoImpl;
	
	public SellPriceServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<SellPrice, String> sellPriceDaoImpl) {
		this.hibernateGenericDaoImpl = sellPriceDaoImpl;
		this.sellPriceDaoImpl = (ISellPriceDao) sellPriceDaoImpl;
	}

	public ISellPriceDao getSellPriceDaoImpl() {
		return sellPriceDaoImpl;
	}

}
