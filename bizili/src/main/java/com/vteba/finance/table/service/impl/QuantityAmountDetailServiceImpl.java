package com.vteba.finance.table.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.finance.table.dao.IQuantityAmountDetailDao;
import com.vteba.finance.table.model.QuantityAmountDetail;
import com.vteba.finance.table.service.IQuantityAmountDetailService;
import com.vteba.service.generic.impl.BaseServiceImpl;
import com.vteba.tx.hibernate.BaseGenericDao;

/**
 * 数量金额明细账service实现
 * @author yinlei 
 * date 2012-7-6 下午11:05:43
 */
@Named
public class QuantityAmountDetailServiceImpl extends BaseServiceImpl<QuantityAmountDetail, String>
		implements IQuantityAmountDetailService {
	
	private IQuantityAmountDetailDao quantityAmountDetailDaoImpl;
	
	public QuantityAmountDetailServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setBaseGenericDaoImpl(
			BaseGenericDao<QuantityAmountDetail, String> quantityAmountDetailDaoImpl) {
		this.baseGenericDaoImpl = quantityAmountDetailDaoImpl;
		this.quantityAmountDetailDaoImpl = (IQuantityAmountDetailDao) quantityAmountDetailDaoImpl;
	}

	public IQuantityAmountDetailDao getQuantityAmountDetailDaoImpl() {
		return quantityAmountDetailDaoImpl;
	}

}
