package com.vteba.finance.setting.web;

import com.vteba.finance.setting.model.CodeRule;
import com.vteba.web.action.BaseAction;

/**
 * 系统编码规则action
 * @author yinlei 
 * date 2012-7-20 下午9:51:11
 */
public class CodeRuleAction extends BaseAction<CodeRule> {
	private static final long serialVersionUID = 515402985655260816L;
	private CodeRule model = new CodeRule();
	
	@Override
	public CodeRule getModel() {
		return model;
	}

	@Override
	public String initial() throws Exception {
		return SUCCESS;
	}

}
