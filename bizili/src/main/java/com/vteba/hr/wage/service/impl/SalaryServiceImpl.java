package com.vteba.hr.wage.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.hr.wage.dao.ISalaryDao;
import com.vteba.hr.wage.model.Salary;
import com.vteba.hr.wage.service.ISalaryService;
import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;

/**
 * salary service implementation
 * @author yinlei 
 * date 2012-7-7 上午10:43:53
 */
@Named
public class SalaryServiceImpl extends GenericServiceImpl<Salary, String> implements
		ISalaryService {
	
	private ISalaryDao salaryDaoImpl;
	
	public SalaryServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<Salary, String> salaryDaoImpl) {
		this.hibernateGenericDaoImpl = salaryDaoImpl;
		this.salaryDaoImpl = (ISalaryDao) salaryDaoImpl;
	}

	public ISalaryDao getSalaryDaoImpl() {
		return salaryDaoImpl;
	}

}
