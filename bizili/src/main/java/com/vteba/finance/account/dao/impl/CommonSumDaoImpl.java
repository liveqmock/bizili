package com.vteba.finance.account.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.finance.account.dao.ICommonSumDao;
import com.vteba.finance.account.model.CommonSum;
import com.vteba.tx.hibernate.impl.HibernateGenericDaoImpl;

/**
 * 常用摘要DAO实现
 * @author yinlei 
 * date 2012-7-5 下午11:06:37
 */
@Named
public class CommonSumDaoImpl extends HibernateGenericDaoImpl<CommonSum, String> implements
		ICommonSumDao {

	public CommonSumDaoImpl() {
		super();
	}

	public CommonSumDaoImpl(Class<CommonSum> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory biziliSessionFactory) {
		this.sessionFactory = biziliSessionFactory;
	}

}
