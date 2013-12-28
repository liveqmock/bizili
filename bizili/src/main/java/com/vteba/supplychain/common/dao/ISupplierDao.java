package com.vteba.supplychain.common.dao;

import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.supplychain.common.model.Supplier;

/**
 * 供应商DAO
 * @author yinlei 
 * date 2012-8-8 下午4:59:24
 */
public interface ISupplierDao extends IHibernateGenericDao<Supplier, String> {

}
