package com.vteba.finance.account.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.finance.account.dao.ICommonSumDao;
import com.vteba.finance.account.model.CommonSum;
import com.vteba.tm.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;

/**
 * 常用摘要service实现
 * @author yinlei 
 * date 2012-7-5 下午11:10:16
 */
@Named
public class CommonSumServiceImpl extends GenericServiceImpl<CommonSum, String> {
	
	private ICommonSumDao commonSumDaoImpl;
	
	public CommonSumServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<CommonSum, String> commonSumDaoImpl) {
		this.hibernateGenericDaoImpl = commonSumDaoImpl;
		this.commonSumDaoImpl = (ICommonSumDao) commonSumDaoImpl;
	}

	public ICommonSumDao getCommonSumDaoImpl() {
		return commonSumDaoImpl;
	}

}
