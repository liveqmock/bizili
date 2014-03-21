package com.vteba.finance.account.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.finance.account.dao.IAccountPeriodDao;
import com.vteba.finance.account.model.AccountPeriod;
import com.vteba.finance.account.service.IAccountPeriodService;
import com.vteba.tm.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;

/**
 * 会计期间service实现
 * @author yinlei 
 * date 2012-7-27 下午4:21:19
 */
@Named
public class AccountPeriodServiceImpl extends GenericServiceImpl<AccountPeriod, String>
		implements IAccountPeriodService {
	
	private IAccountPeriodDao accountPeriodDaoImpl;
	
	public AccountPeriodServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<AccountPeriod, String> accountPeriodDaoImpl) {
		this.hibernateGenericDaoImpl = accountPeriodDaoImpl;
		this.accountPeriodDaoImpl = (IAccountPeriodDao) accountPeriodDaoImpl;
	}

	public IAccountPeriodDao getAccountPeriodDaoImpl() {
		return accountPeriodDaoImpl;
	}
	
	public String getCurrentPeriod() {
		//没有结账，当前会计期间
		String hql = "select a from AccountPeriod a where a.checkout = false and a.currentPeriod = true";
		AccountPeriod model = accountPeriodDaoImpl.uniqueResultByHql(hql);
		return model.getPeriod();
	}
	
}
