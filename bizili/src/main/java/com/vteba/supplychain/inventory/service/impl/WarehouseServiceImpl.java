package com.vteba.supplychain.inventory.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;
import com.vteba.supplychain.inventory.dao.IWarehouseDao;
import com.vteba.supplychain.inventory.model.Warehouse;
import com.vteba.supplychain.inventory.service.IWarehouseService;

/**
 * 仓库/库存地点service实现
 * @author yinlei 
 * date 2012-8-8 下午6:17:10
 */
@Named
public class WarehouseServiceImpl extends GenericServiceImpl<Warehouse, String> implements
		IWarehouseService {
	
	private IWarehouseDao warehouseDaoImpl;
	
	public WarehouseServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<Warehouse, String> warehouseDaoImpl) {
		this.hibernateGenericDaoImpl = warehouseDaoImpl;
		this.warehouseDaoImpl = (IWarehouseDao) warehouseDaoImpl;
	}

	public IWarehouseDao getWarehouseDaoImpl() {
		return warehouseDaoImpl;
	}

}
