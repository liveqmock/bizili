package com.vteba.finance.payables.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.finance.payables.dao.IPayablesDetailDao;
import com.vteba.finance.payables.model.PayablesDetail;
import com.vteba.persister.hibernate.impl.HibernateGenericDaoImpl;

/**
 * 应付明细表DAO实现
 * @author yinlei 
 * date 2012-8-1 下午3:42:30
 */
@Named
public class PayablesDetailDaoImpl extends HibernateGenericDaoImpl<PayablesDetail, String>
		implements IPayablesDetailDao {

	public PayablesDetailDaoImpl() {
		super();
	}

	public PayablesDetailDaoImpl(Class<PayablesDetail> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory biziliSessionFactory) {
		this.sessionFactory = biziliSessionFactory;
	}

}
