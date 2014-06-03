package com.vteba.finance.currency.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.finance.currency.dao.ICurrencyDao;
import com.vteba.finance.currency.model.Currency;
import com.vteba.tx.hibernate.impl.HibernateGenericDaoImpl;

/**
 * 货币币别DAO实现
 * @author yinlei 
 * date 2012-6-29 下午11:24:47
 */
@Named
public class CurrencyDaoImpl extends HibernateGenericDaoImpl<Currency, String> implements
		ICurrencyDao {
	
	public CurrencyDaoImpl() {
		super();
	}

	public CurrencyDaoImpl(Class<Currency> entityClass) {
		super(entityClass);
	}

	@Inject
	public void setSessionFactory(SessionFactory biziliSessionFactory) {
		this.sessionFactory = biziliSessionFactory;
	}

}
