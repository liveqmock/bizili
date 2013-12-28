package com.vteba.supplychain.inventory.dao;

import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.supplychain.inventory.model.Warehouse;

/**
 * 仓库/存货地点DAO
 * @author yinlei 
 * date 2012-8-8 下午5:26:21
 */
public interface IWarehouseDao extends IHibernateGenericDao<Warehouse, String> {

}
