package com.vteba.finance.receivables.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.finance.receivables.dao.IReceivablesSummaryDao;
import com.vteba.finance.receivables.model.ReceivablesSummary;
import com.vteba.finance.receivables.service.IReceivablesSummaryService;
import com.vteba.tx.hibernate.BaseGenericDao;
import com.vteba.service.generic.impl.BaseServiceImpl;

/**
 * 应收汇总表service实现
 * @author yinlei 
 * date 2012-8-1 下午4:21:35
 */
@Named
public class ReceivablesSummaryServiceImpl extends BaseServiceImpl<ReceivablesSummary, String>
		implements IReceivablesSummaryService {
	
	private IReceivablesSummaryDao receivablesSummaryDaoImpl;
	
	@Override
	@Inject
	public void setBaseGenericDaoImpl(
			BaseGenericDao<ReceivablesSummary, String> receivablesSummaryDaoImpl) {
		this.baseGenericDaoImpl = receivablesSummaryDaoImpl;
		this.receivablesSummaryDaoImpl = (IReceivablesSummaryDao) receivablesSummaryDaoImpl;
	}

	public IReceivablesSummaryDao getReceivablesSummaryDaoImpl() {
		return receivablesSummaryDaoImpl;
	}

}
