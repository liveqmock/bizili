package com.vteba.tm.jdbc.spring;

import com.vteba.user.model.EmpUser;

public class UserDaoImpl extends SpringGenericDaoImpl<EmpUser, Long> {

	public UserDaoImpl() {
		super("emp_user", EmpUser.class);
	}

	public UserDaoImpl(String tableName, Class<EmpUser> entityClass) {
		super(tableName, entityClass);
	}
	
}
