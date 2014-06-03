package com.vteba.finance.account.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.finance.account.dao.ICertTotalDao;
import com.vteba.finance.account.model.CertTotal;
import com.vteba.tx.hibernate.impl.HibernateGenericDaoImpl;

/**
 * 凭证汇总DAO实现
 * @author yinlei 
 * date 2012-6-25 下午11:07:13
 */
@Named
public class CertTotalDaoImpl extends HibernateGenericDaoImpl<CertTotal, String> implements
		ICertTotalDao {
	
	public CertTotalDaoImpl() {
		super();
	}

	public CertTotalDaoImpl(Class<CertTotal> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory biziliSessionFactory) {
		this.sessionFactory = biziliSessionFactory;
	}

	

}
