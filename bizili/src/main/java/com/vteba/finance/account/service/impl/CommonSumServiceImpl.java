package com.vteba.finance.account.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.finance.account.dao.ICommonSumDao;
import com.vteba.finance.account.model.CommonSum;
import com.vteba.tx.hibernate.BaseGenericDao;
import com.vteba.service.generic.impl.BaseServiceImpl;

/**
 * 常用摘要service实现
 * @author yinlei 
 * date 2012-7-5 下午11:10:16
 */
@Named
public class CommonSumServiceImpl extends BaseServiceImpl<CommonSum, String> {
	
	private ICommonSumDao commonSumDaoImpl;
	
	public CommonSumServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setBaseGenericDaoImpl(
			BaseGenericDao<CommonSum, String> commonSumDaoImpl) {
		this.baseGenericDaoImpl = commonSumDaoImpl;
		this.commonSumDaoImpl = (ICommonSumDao) commonSumDaoImpl;
	}

	public ICommonSumDao getCommonSumDaoImpl() {
		return commonSumDaoImpl;
	}

}
