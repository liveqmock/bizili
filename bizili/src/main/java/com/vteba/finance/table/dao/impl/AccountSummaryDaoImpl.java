package com.vteba.finance.table.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.finance.table.dao.IAccountSummaryDao;
import com.vteba.finance.table.model.AccountSummary;
import com.vteba.persister.hibernate.impl.HibernateGenericDaoImpl;
import com.vteba.persister.jdbc.spring.SpringJdbcTemplate;

/**
 * 科目汇总表DAO implementation
 * @author yinlei 
 * date 2012-7-9 下午9:05:55
 */
@Named
public class AccountSummaryDaoImpl extends HibernateGenericDaoImpl<AccountSummary, String>
		implements IAccountSummaryDao {
	
	public AccountSummaryDaoImpl() {
		super();
	}

	public AccountSummaryDaoImpl(Class<AccountSummary> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory biziliSessionFactory) {
		this.sessionFactory = biziliSessionFactory;
	}
	
	@Inject
	public void setSpringJdbcTemplate(SpringJdbcTemplate biziliJdbcTemplate) {
		this.springJdbcTemplate = biziliJdbcTemplate;
	}
}
