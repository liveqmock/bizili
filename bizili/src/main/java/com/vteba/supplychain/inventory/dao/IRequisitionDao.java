package com.vteba.supplychain.inventory.dao;

import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.supplychain.inventory.model.Requisition;

/**
 * 调拨单DAO
 * @author yinlei 
 * date 2012-8-8 下午5:29:48
 */
public interface IRequisitionDao extends IHibernateGenericDao<Requisition, String> {

}
