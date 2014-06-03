package com.vteba.finance.receivables.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.finance.receivables.dao.IReceivablesSummaryDao;
import com.vteba.finance.receivables.model.ReceivablesSummary;
import com.vteba.tx.hibernate.impl.HibernateGenericDaoImpl;

/**
 * 应收汇总表DAO实现
 * @author yinlei 
 * date 2012-8-1 下午4:06:18
 */
@Named
public class ReceivablesSummaryDaoImpl extends HibernateGenericDaoImpl<ReceivablesSummary, String>
		implements IReceivablesSummaryDao {

	public ReceivablesSummaryDaoImpl() {
		super();
	}

	public ReceivablesSummaryDaoImpl(Class<ReceivablesSummary> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory biziliSessionFactory) {
		this.sessionFactory = biziliSessionFactory;
	}

}
