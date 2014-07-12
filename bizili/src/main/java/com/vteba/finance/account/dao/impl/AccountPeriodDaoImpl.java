package com.vteba.finance.account.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.finance.account.dao.IAccountPeriodDao;
import com.vteba.finance.account.model.AccountPeriod;
import com.vteba.tx.hibernate.impl.BaseGenericDaoImpl;

/**
 * 会计期间DAO实现
 * @author yinlei 
 * date 2012-7-27 下午4:15:52
 */
@Named
public class AccountPeriodDaoImpl extends BaseGenericDaoImpl<AccountPeriod, String>
		implements IAccountPeriodDao {
	private SessionFactory biziliSessionFactory;
	
	public AccountPeriodDaoImpl() {
		super();
	}

	public AccountPeriodDaoImpl(Class<AccountPeriod> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory biziliSessionFactory) {
		this.sessionFactory = biziliSessionFactory;
	}

	public SessionFactory getBiziliSessionFactory() {
		return biziliSessionFactory;
	}

}
