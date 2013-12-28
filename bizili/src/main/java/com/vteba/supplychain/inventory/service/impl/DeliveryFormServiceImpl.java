package com.vteba.supplychain.inventory.service.impl;

import javax.inject.Named;

import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;
import com.vteba.supplychain.inventory.dao.IDeliveryFormDao;
import com.vteba.supplychain.inventory.model.DeliveryForm;
import com.vteba.supplychain.inventory.service.IDeliveryFormService;

/**
 * 出库单service实现
 * @author yinlei 
 * date 2012-8-8 下午5:56:57
 */
@Named
public class DeliveryFormServiceImpl extends GenericServiceImpl<DeliveryForm, String>
		implements IDeliveryFormService {
	
	private IDeliveryFormDao deliveryFormDaoImpl;
	
	public DeliveryFormServiceImpl() {
		super();
	}

	@Override
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<DeliveryForm, String> deliveryFormDaoImpl) {
		this.hibernateGenericDaoImpl = deliveryFormDaoImpl;
		this.deliveryFormDaoImpl = (IDeliveryFormDao) deliveryFormDaoImpl;
	}

	public IDeliveryFormDao getDeliveryFormDaoImpl() {
		return deliveryFormDaoImpl;
	}

}
