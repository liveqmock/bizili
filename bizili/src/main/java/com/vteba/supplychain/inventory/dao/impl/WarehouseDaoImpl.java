package com.vteba.supplychain.inventory.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.persister.hibernate.impl.HibernateGenericDaoImpl;
import com.vteba.supplychain.inventory.dao.IWarehouseDao;
import com.vteba.supplychain.inventory.model.Warehouse;

/**
 * 仓库/存货地点DAO实现
 * @author yinlei 
 * date 2012-8-8 下午5:49:20
 */
@Named
public class WarehouseDaoImpl extends HibernateGenericDaoImpl<Warehouse, String> implements
		IWarehouseDao {

	public WarehouseDaoImpl() {
		super();
	}

	public WarehouseDaoImpl(Class<Warehouse> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory supplychainSessionFactory) {
		this.sessionFactory = supplychainSessionFactory;
	}

}
