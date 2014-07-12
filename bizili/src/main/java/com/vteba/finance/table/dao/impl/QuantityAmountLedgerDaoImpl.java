package com.vteba.finance.table.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.finance.table.dao.IQuantityAmountLedgerDao;
import com.vteba.finance.table.model.QuantityAmountLedger;
import com.vteba.tx.hibernate.impl.BaseGenericDaoImpl;

/**
 * 数量金额总账DAO实现
 * @author yinlei 
 * date 2012-7-6 下午10:49:28
 */
@Named
public class QuantityAmountLedgerDaoImpl extends BaseGenericDaoImpl<QuantityAmountLedger, String>
		implements IQuantityAmountLedgerDao {

	public QuantityAmountLedgerDaoImpl() {
		super();
	}

	public QuantityAmountLedgerDaoImpl(Class<QuantityAmountLedger> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory biziliSessionFactory) {
		this.sessionFactory = biziliSessionFactory;
	}

}
