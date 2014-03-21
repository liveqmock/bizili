package com.vteba.finance.table.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.finance.table.dao.IAccountBalanceDao;
import com.vteba.finance.table.model.AccountBalance;
import com.vteba.tm.hibernate.impl.HibernateGenericDaoImpl;

/**
 * 科目余额表DAO实现
 * @author yinlei 
 * date 2012-7-1 下午10:49:13
 */
@Named
public class AccountBalanceDaoImpl extends HibernateGenericDaoImpl<AccountBalance, String>
		implements IAccountBalanceDao {

	public AccountBalanceDaoImpl() {
		super();
	}

	public AccountBalanceDaoImpl(Class<AccountBalance> entityClass) {
		super(entityClass);
	}

	@Inject
	public void setSessionFactory(SessionFactory biziliSessionFactory) {
		this.sessionFactory = biziliSessionFactory;
	}
	
}
