package com.vteba.finance.account.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.finance.account.dao.ISubjectDao;
import com.vteba.finance.account.model.Subject;
import com.vteba.tx.hibernate.impl.BaseGenericDaoImpl;
import com.vteba.tx.jdbc.spring.SpringJdbcTemplate;
import com.vteba.service.multitenant.annotation.Schema;

/**
 * 科目代码DAO实现
 * @author yinlei
 * date 2012-7-1 下午9:25:04
 */
@Named
@Schema(schemaName = "bizili")
public class SubjectDaoImpl extends BaseGenericDaoImpl<Subject, String> implements
		ISubjectDao {

	public SubjectDaoImpl() {
		super();
	}

	public SubjectDaoImpl(Class<Subject> entityClass) {
		super(entityClass);
	}

	@Inject
	public void setSessionFactory(SessionFactory biziliSessionFactory) {
		this.sessionFactory = biziliSessionFactory;
	}
	
	@Inject
	public void setSpringJdbcTemplate(SpringJdbcTemplate biziliJdbcTemplate) {
		this.springJdbcTemplate = biziliJdbcTemplate;
	}
}
