package com.vteba.hr.wage.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.hr.wage.dao.ISalaryDao;
import com.vteba.hr.wage.model.Salary;
import com.vteba.persister.hibernate.impl.HibernateGenericDaoImpl;

/**
 * salary dao implementation
 * @author yinlei 
 * date 2012-7-7 上午10:37:50
 */
@Named
public class SalaryDaoImpl extends HibernateGenericDaoImpl<Salary, String> implements
		ISalaryDao {

	public SalaryDaoImpl() {
		super();
	}

	public SalaryDaoImpl(Class<Salary> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory skmbwSessionFactory) {
		this.sessionFactory = skmbwSessionFactory;
	}

}
