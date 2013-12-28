package com.vteba.finance.account.dao;

import com.vteba.finance.account.model.Subject;
import com.vteba.persister.jdbc.mybatis.mapper.BaseMapper;

/**
 * 会计科目MyBatis Dao Mapper
 * @author yinlei
 * date 2013-7-21 下午5:02:52
 */
public interface SubjectDao extends BaseMapper {
	
	/**
	 * 根据科目代码获取会计科目信息
	 * @param subjectCode 科目代码
	 * @return 会计科目
	 * @author yinlei
	 * date 2013-7-21 下午4:40:50
	 */
	public Subject getSubject(String subjectCode);
}
