package com.vteba.supplychain.purchase.service.impl;

import javax.inject.Named;

import com.opensymphony.xwork2.inject.Inject;
import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;
import com.vteba.supplychain.purchase.dao.IExpensesDao;
import com.vteba.supplychain.purchase.model.Expenses;
import com.vteba.supplychain.purchase.service.IExpensesService;

/**
 * 费用支出单service实现
 * @author yinlei 
 * date 2012-8-25 下午4:10:48
 */
@Named
public class ExpensesServiceImpl extends GenericServiceImpl<Expenses, String> implements
		IExpensesService {
	
	private IExpensesDao expensesDaoImpl;
	
	public ExpensesServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<Expenses, String> expensesDaoImpl) {
		this.hibernateGenericDaoImpl = expensesDaoImpl;
		this.expensesDaoImpl = (IExpensesDao) expensesDaoImpl;
	}

	public IExpensesDao getExpensesDaoImpl() {
		return expensesDaoImpl;
	}

}
