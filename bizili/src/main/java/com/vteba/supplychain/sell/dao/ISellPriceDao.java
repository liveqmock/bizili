package com.vteba.supplychain.sell.dao;

import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.supplychain.sell.model.SellPrice;

/**
 * 存货销售价格列表DAO
 * @author yinlei 
 * date 2012-8-12 上午11:35:25
 */
public interface ISellPriceDao extends IHibernateGenericDao<SellPrice, String> {

}
