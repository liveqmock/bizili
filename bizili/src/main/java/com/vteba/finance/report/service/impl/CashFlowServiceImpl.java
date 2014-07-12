package com.vteba.finance.report.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.finance.report.dao.ICashFlowDao;
import com.vteba.finance.report.model.CashFlow;
import com.vteba.finance.report.service.ICashFlowService;
import com.vteba.tx.hibernate.BaseGenericDao;
import com.vteba.service.generic.impl.BaseServiceImpl;

/**
 * 现金流量表service实现
 * @author yinlei 
 * date 2012-7-23 上午10:34:59
 */
@Named
public class CashFlowServiceImpl extends BaseServiceImpl<CashFlow, String> implements
		ICashFlowService {

	private ICashFlowDao cashFlowDaoImpl;
	
	public CashFlowServiceImpl() {
		super();
	}

	public ICashFlowDao getCashFlowDaoImpl() {
		return cashFlowDaoImpl;
	}

	@Inject
	public void setBaseGenericDaoImpl(
			BaseGenericDao<CashFlow, String> cashFlowDaoImpl) {
		this.baseGenericDaoImpl = cashFlowDaoImpl;
		this.cashFlowDaoImpl = (ICashFlowDao) cashFlowDaoImpl;
	}

}
