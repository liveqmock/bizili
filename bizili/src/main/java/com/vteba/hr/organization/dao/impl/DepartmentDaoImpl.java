package com.vteba.hr.organization.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.hr.organization.dao.IDepartmentDao;
import com.vteba.hr.organization.model.Department;
import com.vteba.persister.hibernate.impl.HibernateGenericDaoImpl;

/**
 * 部门组织DAO实现
 * @author yinlei 
 * date 2012-7-6 下午11:21:44
 */
@Named
public class DepartmentDaoImpl extends HibernateGenericDaoImpl<Department, String> implements
		IDepartmentDao {

	public DepartmentDaoImpl() {
		super();
	}

	public DepartmentDaoImpl(Class<Department> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory skmbwSessionFactory) {
		this.sessionFactory = skmbwSessionFactory;
	}

}
