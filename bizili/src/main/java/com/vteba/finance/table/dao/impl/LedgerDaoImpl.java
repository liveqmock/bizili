package com.vteba.finance.table.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.finance.table.dao.ILedgerDao;
import com.vteba.finance.table.model.Ledger;
import com.vteba.tm.hibernate.impl.HibernateGenericDaoImpl;

/**
 * 总账DAO实现
 * @author yinlei 
 * date 2012-7-6 下午10:41:21
 */
@Named
public class LedgerDaoImpl extends HibernateGenericDaoImpl<Ledger, String> implements
		ILedgerDao {

	public LedgerDaoImpl() {
		super();
	}

	public LedgerDaoImpl(Class<Ledger> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory biziliSessionFactory) {
		this.sessionFactory = biziliSessionFactory;
	}

}
