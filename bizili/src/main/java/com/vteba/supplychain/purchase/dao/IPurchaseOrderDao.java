package com.vteba.supplychain.purchase.dao;

import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.supplychain.purchase.model.PurchaseOrder;

/**
 * 采购订单DAO
 * @author yinlei 
 * date 2012-8-7 下午5:36:56
 */
public interface IPurchaseOrderDao extends IHibernateGenericDao<PurchaseOrder, String> {

}
