package com.vteba.finance.setting.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.finance.setting.dao.ICodeRuleDao;
import com.vteba.finance.setting.model.CodeRule;
import com.vteba.tx.hibernate.impl.BaseGenericDaoImpl;

/**
 * 基础数据编码规则DAO实现
 * @author yinlei 
 * date 2012-6-29 下午11:14:23
 */
@Named
public class CodeRuleDaoImpl extends BaseGenericDaoImpl<CodeRule, String> implements
		ICodeRuleDao {
	
	
	public CodeRuleDaoImpl() {
		super();
	}

	public CodeRuleDaoImpl(Class<CodeRule> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory biziliSessionFactory) {
		this.sessionFactory = biziliSessionFactory;
	}

}
