package com.vteba.supplychain.inventory.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.persister.hibernate.impl.HibernateGenericDaoImpl;
import com.vteba.supplychain.inventory.dao.IRequisitionDao;
import com.vteba.supplychain.inventory.model.Requisition;

/**
 * 调拨单DAO实现
 * @author yinlei 
 * date 2012-8-8 下午5:31:40
 */
@Named
public class RequisitionDaoImpl extends HibernateGenericDaoImpl<Requisition, String>
		implements IRequisitionDao {

	public RequisitionDaoImpl() {
		super();
	}

	public RequisitionDaoImpl(Class<Requisition> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory supplychainSessionFactory) {
		this.sessionFactory = supplychainSessionFactory;
	}

}
