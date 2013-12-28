package com.vteba.finance.report.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.finance.report.dao.IProfitDao;
import com.vteba.finance.report.model.Profit;
import com.vteba.persister.hibernate.impl.HibernateGenericDaoImpl;

/**
 * 利润表DAO实现
 * @author yinlei 
 * date 2012-6-29 下午11:22:32
 */
@Named
public class ProfitDaoImpl extends HibernateGenericDaoImpl<Profit, String> implements
		IProfitDao {

	public ProfitDaoImpl() {
		super();
	}

	public ProfitDaoImpl(Class<Profit> entityClass) {
		super(entityClass);
	}

	@Inject
	public void setSessionFactory(SessionFactory biziliSessionFactory) {
		this.sessionFactory = biziliSessionFactory;
	}

}
