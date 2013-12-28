package com.vteba.supplychain.sell.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;
import com.vteba.supplychain.sell.dao.ISellOrderDao;
import com.vteba.supplychain.sell.model.SellOrder;
import com.vteba.supplychain.sell.service.ISellOrderService;

/**
 * 销售订单service实现
 * @author yinlei 
 * date 2012-8-12 下午12:00:28
 */
@Named
public class SellOrderServiceImpl extends GenericServiceImpl<SellOrder, String> implements
		ISellOrderService {
	
	private ISellOrderDao sellOrderDaoImpl;
	
	public SellOrderServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<SellOrder, String> sellOrderDaoImpl) {
		this.hibernateGenericDaoImpl = sellOrderDaoImpl;
		this.sellOrderDaoImpl = (ISellOrderDao) sellOrderDaoImpl;
	}

	public ISellOrderDao getSellOrderDaoImpl() {
		return sellOrderDaoImpl;
	}

}
