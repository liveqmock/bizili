package com.vteba.finance.payables.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.finance.payables.dao.IPayablesDetailDao;
import com.vteba.finance.payables.model.PayablesDetail;
import com.vteba.finance.payables.service.IPayablesDetailService;
import com.vteba.tx.hibernate.BaseGenericDao;
import com.vteba.service.generic.impl.BaseServiceImpl;

/**
 * 应付明细表service实现
 * @author yinlei 
 * date 2012-8-1 下午3:51:01
 */
@Named
public class PayablesDetailServiceImpl extends BaseServiceImpl<PayablesDetail, String>
		implements IPayablesDetailService {

	private IPayablesDetailDao payablesDetailDaoImpl;
	
	public PayablesDetailServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setBaseGenericDaoImpl(
			BaseGenericDao<PayablesDetail, String> payablesDetailDaoImpl) {
		this.baseGenericDaoImpl = payablesDetailDaoImpl;
		this.payablesDetailDaoImpl = (IPayablesDetailDao) payablesDetailDaoImpl;
	}

	public IPayablesDetailDao getPayablesDetailDaoImpl() {
		return payablesDetailDaoImpl;
	}

}
