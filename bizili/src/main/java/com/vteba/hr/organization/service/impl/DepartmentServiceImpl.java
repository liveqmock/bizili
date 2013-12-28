package com.vteba.hr.organization.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.hr.organization.dao.IDepartmentDao;
import com.vteba.hr.organization.model.Department;
import com.vteba.hr.organization.service.IDepartmentService;
import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;

/**
 * 部门组织service实现
 * @author yinlei 
 * date 2012-7-6 下午11:24:51
 */
@Named
public class DepartmentServiceImpl extends GenericServiceImpl<Department, String> implements
		IDepartmentService {
	
	private IDepartmentDao departmentDaoImpl;
	
	public DepartmentServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<Department, String> departmentDaoImpl) {
		this.hibernateGenericDaoImpl = departmentDaoImpl;
		this.departmentDaoImpl = (IDepartmentDao) departmentDaoImpl;
	}

	public IDepartmentDao getDepartmentDaoImpl() {
		return departmentDaoImpl;
	}

}
