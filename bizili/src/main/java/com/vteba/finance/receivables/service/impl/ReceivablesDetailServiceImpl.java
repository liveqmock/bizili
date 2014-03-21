package com.vteba.finance.receivables.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.finance.receivables.dao.IReceivablesDetailDao;
import com.vteba.finance.receivables.model.ReceivablesDetail;
import com.vteba.finance.receivables.service.IReceivablesDetailService;
import com.vteba.tm.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;

/**
 * 应收明细表service实现
 * @author yinlei 
 * date 2012-8-1 下午4:24:20
 */
@Named
public class ReceivablesDetailServiceImpl extends GenericServiceImpl<ReceivablesDetail, String>
		implements IReceivablesDetailService {

	private IReceivablesDetailDao receivablesDetailDaoImpl;
	
	@Override
	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<ReceivablesDetail, String> receivablesDetailDaoImpl) {
		this.hibernateGenericDaoImpl = receivablesDetailDaoImpl;
		this.receivablesDetailDaoImpl = (IReceivablesDetailDao) receivablesDetailDaoImpl;
	}

	public IReceivablesDetailDao getReceivablesDetailDaoImpl() {
		return receivablesDetailDaoImpl;
	}

}
