package com.vteba.finance.receivables.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.finance.receivables.dao.IReceivablesDetailDao;
import com.vteba.finance.receivables.model.ReceivablesDetail;
import com.vteba.tm.hibernate.impl.HibernateGenericDaoImpl;

/**
 * 应收明细表DAO实现
 * @author yinlei 
 * date 2012-8-1 下午4:11:58
 */
@Named
public class ReceivablesDetailDaoImpl extends HibernateGenericDaoImpl<ReceivablesDetail, String>
		implements IReceivablesDetailDao {

	public ReceivablesDetailDaoImpl() {
		super();
	}

	public ReceivablesDetailDaoImpl(Class<ReceivablesDetail> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory biziliSessionFactory) {
		this.sessionFactory = biziliSessionFactory;
	}

}
