package com.vteba.finance.payables.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.finance.payables.dao.IPayablesSummaryDao;
import com.vteba.finance.payables.model.PayablesSummary;
import com.vteba.persister.hibernate.impl.HibernateGenericDaoImpl;

/**
 * 应付汇总表DAO实现
 * @author yinlei 
 * date 2012-8-1 下午3:37:49
 */
@Named
public class PayablesSummaryDaoImpl extends HibernateGenericDaoImpl<PayablesSummary, String>
		implements IPayablesSummaryDao {

	public PayablesSummaryDaoImpl() {
		super();
	}

	public PayablesSummaryDaoImpl(Class<PayablesSummary> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory biziliSessionFactory) {
		this.sessionFactory = biziliSessionFactory;
	}

}
