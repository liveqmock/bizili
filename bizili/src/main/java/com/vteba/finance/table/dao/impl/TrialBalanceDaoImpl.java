package com.vteba.finance.table.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.finance.table.dao.ITrialBalanceDao;
import com.vteba.finance.table.model.TrialBalance;
import com.vteba.tx.hibernate.impl.HibernateGenericDaoImpl;

/**
 * 试算平衡表DAO实现
 * @author yinlei 
 * date 2012-6-30 下午12:41:26
 */
@Named
public class TrialBalanceDaoImpl extends HibernateGenericDaoImpl<TrialBalance, String>
		implements ITrialBalanceDao {

	public TrialBalanceDaoImpl() {
		super();
	}

	public TrialBalanceDaoImpl(Class<TrialBalance> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory biziliSessionFactory) {
		this.sessionFactory = biziliSessionFactory;
	}

}
