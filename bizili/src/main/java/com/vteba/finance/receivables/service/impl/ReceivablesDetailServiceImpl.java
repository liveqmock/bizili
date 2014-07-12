package com.vteba.finance.receivables.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.finance.receivables.dao.IReceivablesDetailDao;
import com.vteba.finance.receivables.model.ReceivablesDetail;
import com.vteba.finance.receivables.service.IReceivablesDetailService;
import com.vteba.tx.hibernate.BaseGenericDao;
import com.vteba.service.generic.impl.BaseServiceImpl;

/**
 * 应收明细表service实现
 * @author yinlei 
 * date 2012-8-1 下午4:24:20
 */
@Named
public class ReceivablesDetailServiceImpl extends BaseServiceImpl<ReceivablesDetail, String>
		implements IReceivablesDetailService {

	private IReceivablesDetailDao receivablesDetailDaoImpl;
	
	@Override
	@Inject
	public void setBaseGenericDaoImpl(
			BaseGenericDao<ReceivablesDetail, String> receivablesDetailDaoImpl) {
		this.baseGenericDaoImpl = receivablesDetailDaoImpl;
		this.receivablesDetailDaoImpl = (IReceivablesDetailDao) receivablesDetailDaoImpl;
	}

	public IReceivablesDetailDao getReceivablesDetailDaoImpl() {
		return receivablesDetailDaoImpl;
	}

}
