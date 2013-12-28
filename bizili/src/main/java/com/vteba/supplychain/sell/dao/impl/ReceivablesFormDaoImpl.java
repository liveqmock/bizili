package com.vteba.supplychain.sell.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.persister.hibernate.impl.HibernateGenericDaoImpl;
import com.vteba.supplychain.sell.dao.IReceivablesFormDao;
import com.vteba.supplychain.sell.model.ReceivablesForm;

/**
 * 收款单DAO实现
 * @author yinlei 
 * date 2012-8-12 上午11:40:16
 */
@Named
public class ReceivablesFormDaoImpl extends HibernateGenericDaoImpl<ReceivablesForm, String>
		implements IReceivablesFormDao {

	public ReceivablesFormDaoImpl() {
		super();
	}

	public ReceivablesFormDaoImpl(Class<ReceivablesForm> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory supplychainSessionFactory) {
		this.sessionFactory = supplychainSessionFactory;
	}

}
