package com.skmbw.user.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.skmbw.user.dao.IUserDao;
import com.skmbw.user.model.User;
import com.vteba.tx.jdbc.spring.SpringJdbcTemplate;
import com.vteba.tx.jdbc.spring.impl.SpringGenericDaoImpl;

@Named
public class UserDaoImpl extends SpringGenericDaoImpl<User, Long> implements IUserDao {

	public UserDaoImpl() {
		super();
	}

	public UserDaoImpl(Class<User> entityClass) {
		super(entityClass);
	}

	@Inject
	@Override
	public void setSpringJdbcTemplate(SpringJdbcTemplate skmbwJdbcTemplate) {
		this.springJdbcTemplate = skmbwJdbcTemplate;
	}

}
