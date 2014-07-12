package com.vteba.finance.setting.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.finance.setting.dao.ICodeRuleDao;
import com.vteba.finance.setting.model.CodeRule;
import com.vteba.finance.setting.service.ICodeRuleService;
import com.vteba.tx.hibernate.BaseGenericDao;
import com.vteba.service.generic.impl.BaseServiceImpl;

/**
 * 基础数据编码规则service实现
 * @author yinlei 
 * date 2012-6-29 下午11:19:25
 */
@Named
public class CodeRuleServiceImpl extends BaseServiceImpl<CodeRule, String>
		implements ICodeRuleService {

	private ICodeRuleDao codeRuleDaoImpl;
	
	public CodeRuleServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setBaseGenericDaoImpl(
			BaseGenericDao<CodeRule, String> codeRuleDaoImpl) {
		this.baseGenericDaoImpl = codeRuleDaoImpl;
		this.codeRuleDaoImpl = (ICodeRuleDao) codeRuleDaoImpl;
	}

	public ICodeRuleDao getCodeRuleDaoImpl() {
		return codeRuleDaoImpl;
	}

}
