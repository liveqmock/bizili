package com.vteba.supplychain.sell.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;
import com.vteba.supplychain.sell.dao.ISellReturnedDao;
import com.vteba.supplychain.sell.model.SellReturned;
import com.vteba.supplychain.sell.service.ISellReturnedService;

/**
 * 销售退货单service实现
 * @author yinlei 
 * date 2012-8-12 下午12:05:09
 */
@Named
public class SellReturnedServiceImpl extends GenericServiceImpl<SellReturned, String>
		implements ISellReturnedService {
	
	private ISellReturnedDao sellReturnedDaoImpl;
	
	public SellReturnedServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<SellReturned, String> sellReturnedDaoImpl) {
		this.hibernateGenericDaoImpl = sellReturnedDaoImpl;
		this.sellReturnedDaoImpl = (ISellReturnedDao) sellReturnedDaoImpl;
	}

	public ISellReturnedDao getSellReturnedDaoImpl() {
		return sellReturnedDaoImpl;
	}

}
