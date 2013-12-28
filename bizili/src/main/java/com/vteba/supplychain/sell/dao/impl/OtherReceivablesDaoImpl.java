package com.vteba.supplychain.sell.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.persister.hibernate.impl.HibernateGenericDaoImpl;
import com.vteba.supplychain.sell.dao.IOtherReceivablesDao;
import com.vteba.supplychain.sell.model.OtherReceivables;

/**
 * 其他应收单DAO实现
 * @author yinlei 
 * date 2012-8-12 上午11:38:13
 */
@Named
public class OtherReceivablesDaoImpl extends HibernateGenericDaoImpl<OtherReceivables, String>
		implements IOtherReceivablesDao {

	public OtherReceivablesDaoImpl() {
		super();
	}

	public OtherReceivablesDaoImpl(Class<OtherReceivables> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory supplychainSessionFactory) {
		this.sessionFactory = supplychainSessionFactory;
	}

}
