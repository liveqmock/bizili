package com.vteba.finance.report.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.finance.report.dao.ICashFlowDao;
import com.vteba.finance.report.model.CashFlow;
import com.vteba.tm.hibernate.impl.HibernateGenericDaoImpl;

/**
 * 现金流量表DAO实现
 * @author yinlei 
 * date 2012-6-29 下午11:22:13
 */
@Named
public class CashFlowDaoImpl extends HibernateGenericDaoImpl<CashFlow, String> implements
		ICashFlowDao {

	public CashFlowDaoImpl() {
		super();
	}

	public CashFlowDaoImpl(Class<CashFlow> entityClass) {
		super(entityClass);
	}

	@Inject
	public void setSessionFactory(SessionFactory biziliSessionFactory) {
		this.sessionFactory = biziliSessionFactory;
	}

}
