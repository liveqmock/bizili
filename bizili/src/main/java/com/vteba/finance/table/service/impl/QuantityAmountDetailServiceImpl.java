package com.vteba.finance.table.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.finance.table.dao.IQuantityAmountDetailDao;
import com.vteba.finance.table.model.QuantityAmountDetail;
import com.vteba.finance.table.service.IQuantityAmountDetailService;
import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;

/**
 * 数量金额明细账service实现
 * @author yinlei 
 * date 2012-7-6 下午11:05:43
 */
@Named
public class QuantityAmountDetailServiceImpl extends GenericServiceImpl<QuantityAmountDetail, String>
		implements IQuantityAmountDetailService {
	
	private IQuantityAmountDetailDao quantityAmountDetailDaoImpl;
	
	public QuantityAmountDetailServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<QuantityAmountDetail, String> quantityAmountDetailDaoImpl) {
		this.hibernateGenericDaoImpl = quantityAmountDetailDaoImpl;
		this.quantityAmountDetailDaoImpl = (IQuantityAmountDetailDao) quantityAmountDetailDaoImpl;
	}

	public IQuantityAmountDetailDao getQuantityAmountDetailDaoImpl() {
		return quantityAmountDetailDaoImpl;
	}

}
