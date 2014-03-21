package com.vteba.finance.setting.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.finance.setting.dao.ICodeRuleDao;
import com.vteba.finance.setting.model.CodeRule;
import com.vteba.finance.setting.service.ICodeRuleService;
import com.vteba.tm.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;

/**
 * 基础数据编码规则service实现
 * @author yinlei 
 * date 2012-6-29 下午11:19:25
 */
@Named
public class CodeRuleServiceImpl extends GenericServiceImpl<CodeRule, String>
		implements ICodeRuleService {

	private ICodeRuleDao codeRuleDaoImpl;
	
	public CodeRuleServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<CodeRule, String> codeRuleDaoImpl) {
		this.hibernateGenericDaoImpl = codeRuleDaoImpl;
		this.codeRuleDaoImpl = (ICodeRuleDao) codeRuleDaoImpl;
	}

	public ICodeRuleDao getCodeRuleDaoImpl() {
		return codeRuleDaoImpl;
	}

}
