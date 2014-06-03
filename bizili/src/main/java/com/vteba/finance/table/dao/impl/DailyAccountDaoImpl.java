package com.vteba.finance.table.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.finance.table.dao.IDailyAccountDao;
import com.vteba.finance.table.model.DailyAccount;
import com.vteba.tx.hibernate.impl.HibernateGenericDaoImpl;

/**
 * 日记账DAO实现
 * @author yinlei 
 * date 2012-7-6 下午10:36:19
 */
@Named
public class DailyAccountDaoImpl extends HibernateGenericDaoImpl<DailyAccount, String>
		implements IDailyAccountDao {

	public DailyAccountDaoImpl() {
		super();
	}

	public DailyAccountDaoImpl(Class<DailyAccount> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory biziliSessionFactory) {
		sessionFactory = biziliSessionFactory;
	}

}
