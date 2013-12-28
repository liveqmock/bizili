package com.vteba.supplychain.inventory.dao;

import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.supplychain.inventory.model.Stock;

/**
 * 存货/库存商品DAO
 * @author yinlei 
 * date 2012-8-8 下午5:25:06
 */
public interface IStockDao extends IHibernateGenericDao<Stock, String> {

}
