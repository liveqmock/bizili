package com.vteba.finance.currency.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.finance.currency.dao.ICurrencyDao;
import com.vteba.finance.currency.model.Currency;
import com.vteba.finance.currency.service.ICurrencyService;
import com.vteba.tx.hibernate.BaseGenericDao;
import com.vteba.service.generic.impl.BaseServiceImpl;

/**
 * 货币币别Service实现
 * @author yinlei 
 * date 2012-6-29 下午11:25:26
 */
@Named
public class CurrencyServiceImpl extends BaseServiceImpl<Currency, String> implements
		ICurrencyService {

	private ICurrencyDao currencyDaoImpl;
	
	public CurrencyServiceImpl() {
		super();
	}
	
	@Override
	@Inject
	public void setBaseGenericDaoImpl(
			BaseGenericDao<Currency, String> currencyDaoImpl) {
		this.baseGenericDaoImpl = currencyDaoImpl;
		this.currencyDaoImpl = (ICurrencyDao) currencyDaoImpl;
	}

	public ICurrencyDao getCurrencyDaoImpl() {
		return currencyDaoImpl;
	}

}
