package com.vteba.finance.table.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.finance.table.dao.IDetailAccountDao;
import com.vteba.finance.table.model.DetailAccount;
import com.vteba.tm.hibernate.impl.HibernateGenericDaoImpl;

/**
 * 明细账DAO实现
 * @author yinlei 
 * date 2012-7-6 下午10:38:28
 */
@Named
public class DetailAccountDaoImpl extends HibernateGenericDaoImpl<DetailAccount, String>
		implements IDetailAccountDao {

	public DetailAccountDaoImpl() {
		super();
	}

	public DetailAccountDaoImpl(Class<DetailAccount> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory biziliSessionFactory) {
		this.sessionFactory = biziliSessionFactory;
	}

}
