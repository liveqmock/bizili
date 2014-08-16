package com.vteba.finance.setting.web;

import java.io.Serializable;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vteba.finance.setting.model.CodeRule;
import com.vteba.service.generic.BaseService;
import com.vteba.web.action.BasicAction;

/**
 * 系统编码规则action
 * @author yinlei 
 * date 2012-7-20 下午9:51:11
 */
@Controller
@RequestMapping("/setting")
public class CodeRuleAction extends BasicAction<CodeRule> {

	@RequestMapping("/coderule-initial")
	public String initial() throws Exception {
		return "setting/coderule/coderule-initial-success";
	}

	@Override
	public void setBaseServiceImpl(
			BaseService<CodeRule, ? extends Serializable> BaseServiceImpl) {
		// TODO Auto-generated method stub
		
	}

}
