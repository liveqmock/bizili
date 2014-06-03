package com.vteba.user.dao;

import com.vteba.tx.jdbc.mybatis.annotation.DaoMapper;
import com.vteba.user.model.EmpUser;

/**
 * UserDao Mapper接口。
 * @author yinlei
 * date 2012-9-30 下午2:29:14
 */
@DaoMapper
public interface UserMapper {
	public EmpUser get(int id);
	
	public void delete(int id);
}
