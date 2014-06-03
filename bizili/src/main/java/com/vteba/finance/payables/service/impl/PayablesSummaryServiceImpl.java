package com.vteba.finance.payables.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.finance.payables.dao.IPayablesSummaryDao;
import com.vteba.finance.payables.model.PayablesSummary;
import com.vteba.finance.payables.service.IPayablesSummaryService;
import com.vteba.tx.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;

/**
 * 应付汇总表service实现
 * @author yinlei 
 * date 2012-8-1 下午3:47:24
 */
@Named
public class PayablesSummaryServiceImpl extends GenericServiceImpl<PayablesSummary, String>
		implements IPayablesSummaryService {

	private IPayablesSummaryDao payablesSummaryDaoImpl;
	
	public PayablesSummaryServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<PayablesSummary, String> payablesSummaryDaoImpl) {
		this.hibernateGenericDaoImpl = payablesSummaryDaoImpl;
		this.payablesSummaryDaoImpl = (IPayablesSummaryDao) payablesSummaryDaoImpl;
	}

	public IPayablesSummaryDao getPayablesSummaryDaoImpl() {
		return payablesSummaryDaoImpl;
	}

}
