package com.vteba.supplychain.inventory.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;
import com.vteba.supplychain.inventory.dao.IRequisitionDao;
import com.vteba.supplychain.inventory.model.Requisition;
import com.vteba.supplychain.inventory.service.IRequisitionService;

/**
 * 调拨单service实现
 * @author yinlei 
 * date 2012-8-8 下午6:07:38
 */
@Named
public class RequisitionServiceImpl extends GenericServiceImpl<Requisition, String> implements
		IRequisitionService {
	
	private IRequisitionDao requisitionDaoImpl;
	
	public RequisitionServiceImpl() {
		super();
	}
	
	@Override
	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<Requisition, String> requisitionDaoImpl) {
		this.hibernateGenericDaoImpl = requisitionDaoImpl;
		this.requisitionDaoImpl = (IRequisitionDao) requisitionDaoImpl;
	}

	public IRequisitionDao getRequisitionDaoImpl() {
		return requisitionDaoImpl;
	}

}
