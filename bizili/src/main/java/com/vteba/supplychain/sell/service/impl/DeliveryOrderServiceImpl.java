package com.vteba.supplychain.sell.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;
import com.vteba.supplychain.sell.dao.IDeliveryOrderDao;
import com.vteba.supplychain.sell.model.DeliveryOrder;
import com.vteba.supplychain.sell.service.IDeliveryOrderService;

/**
 * 发货单service实现
 * @author yinlei 
 * date 2012-8-8 下午8:12:18
 */
@Named
public class DeliveryOrderServiceImpl extends GenericServiceImpl<DeliveryOrder, String>
		implements IDeliveryOrderService {
	
	private IDeliveryOrderDao deliveryOrderDaoImpl;
	
	@Override
	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<DeliveryOrder, String> deliveryOrderDaoImpl) {
		this.hibernateGenericDaoImpl = deliveryOrderDaoImpl;
		this.deliveryOrderDaoImpl = (IDeliveryOrderDao) deliveryOrderDaoImpl;
	}

	public IDeliveryOrderDao getDeliveryOrderDaoImpl() {
		return deliveryOrderDaoImpl;
	}

}
