package com.vteba.finance.table.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.finance.table.dao.IQuantityAmountLedgerDao;
import com.vteba.finance.table.model.QuantityAmountLedger;
import com.vteba.finance.table.service.IQuantityAccountLedgerService;
import com.vteba.service.generic.impl.BaseServiceImpl;
import com.vteba.tx.hibernate.BaseGenericDao;

/**
 * 数量金额总账service实现
 * @author yinlei 
 * date 2012-7-6 下午11:04:05
 */
@Named
public class QuantityAccountLedgerServiceImpl extends BaseServiceImpl<QuantityAmountLedger, String>
		implements IQuantityAccountLedgerService {
	
	private IQuantityAmountLedgerDao quantityAmountLedgerDaoImpl;
	
	public QuantityAccountLedgerServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setBaseGenericDaoImpl(
			BaseGenericDao<QuantityAmountLedger, String> quantityAmountLedgerDaoImpl) {
		this.baseGenericDaoImpl = quantityAmountLedgerDaoImpl;
		this.quantityAmountLedgerDaoImpl = (IQuantityAmountLedgerDao) quantityAmountLedgerDaoImpl;
	}

	public IQuantityAmountLedgerDao getQuantityAmountLedgerDaoImpl() {
		return quantityAmountLedgerDaoImpl;
	}

}
