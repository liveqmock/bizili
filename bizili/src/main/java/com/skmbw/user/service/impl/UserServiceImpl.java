package com.skmbw.user.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.skmbw.user.dao.IUserDao;
import com.skmbw.user.model.User;
import com.skmbw.user.service.UserServcie;
import com.vteba.service.generic.impl.CommonServiceImpl;
import com.vteba.tx.jdbc.spring.spi.SpringGenericDao;

@Named
public class UserServiceImpl extends CommonServiceImpl<User, Long> implements UserServcie {

	protected IUserDao userDaoImpl;
	
	public UserServiceImpl() {
		
	}

	@Inject
	@Override
	public void setSpringGenericDaoImpl(
			SpringGenericDao<User, Long> userDaoImpl) {
		this.springGenericDaoImpl = userDaoImpl;
		this.userDaoImpl = (IUserDao) userDaoImpl;
	}

}
