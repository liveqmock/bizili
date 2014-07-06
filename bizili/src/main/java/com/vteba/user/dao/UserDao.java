package com.vteba.user.dao;

import com.vteba.user.model.EmpUser;

/**
 * UserDao接口。
 * @author yinlei
 * date 2012-9-30 下午6:31:39
 */
public interface UserDao {
	
	public EmpUser get(int id);
}
