package com.vteba.finance.receivables.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.finance.receivables.dao.IReceivablesSummaryDao;
import com.vteba.finance.receivables.model.ReceivablesSummary;
import com.vteba.finance.receivables.service.IReceivablesSummaryService;
import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;

/**
 * 应收汇总表service实现
 * @author yinlei 
 * date 2012-8-1 下午4:21:35
 */
@Named
public class ReceivablesSummaryServiceImpl extends GenericServiceImpl<ReceivablesSummary, String>
		implements IReceivablesSummaryService {
	
	private IReceivablesSummaryDao receivablesSummaryDaoImpl;
	
	@Override
	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<ReceivablesSummary, String> receivablesSummaryDaoImpl) {
		this.hibernateGenericDaoImpl = receivablesSummaryDaoImpl;
		this.receivablesSummaryDaoImpl = (IReceivablesSummaryDao) receivablesSummaryDaoImpl;
	}

	public IReceivablesSummaryDao getReceivablesSummaryDaoImpl() {
		return receivablesSummaryDaoImpl;
	}

}
