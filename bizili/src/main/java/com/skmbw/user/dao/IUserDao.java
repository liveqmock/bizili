package com.skmbw.user.dao;

import com.skmbw.user.model.User;
import com.vteba.tx.jdbc.spring.spi.SpringGenericDao;

public interface IUserDao extends SpringGenericDao<User, Long> {

}
