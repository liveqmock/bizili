package com.vteba.supplychain.purchase.dao.impl;

import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.opensymphony.xwork2.inject.Inject;
import com.vteba.persister.hibernate.impl.HibernateGenericDaoImpl;
import com.vteba.service.multitenant.annotation.Schema;
import com.vteba.supplychain.purchase.dao.IExpensesDao;
import com.vteba.supplychain.purchase.model.Expenses;

@Named
@Schema(schemaName = "supplychain")
public class ExpensesDaoImpl extends HibernateGenericDaoImpl<Expenses, String> implements
		IExpensesDao {

	public ExpensesDaoImpl() {
		super();
	}

	public ExpensesDaoImpl(Class<Expenses> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory supplychainSessionFactory) {
		this.sessionFactory = supplychainSessionFactory;
	}

}
