package com.vteba.finance.account.web;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vteba.finance.account.model.CommonSum;
import com.vteba.finance.account.service.ICommonSumService;
import com.vteba.web.action.BaseAction;

/**
 * 常用摘要action
 * @author yinlei 
 * date 2012-7-5 下午11:02:32
 */
@Controller
@RequestMapping("/commonSum")
public class CommonSumAction extends BaseAction<CommonSum> {
	private CommonSum model = new CommonSum();
	private ICommonSumService commonSumServiceImpl;
	
	public ICommonSumService getCommonSumServiceImpl() {
		return commonSumServiceImpl;
	}
	
	@Inject
	public void setCommonSumServiceImpl(ICommonSumService commonSumServiceImpl) {
		this.commonSumServiceImpl = commonSumServiceImpl;
	}

	public CommonSum getModel() {
		return model;
	}

	@Override
	public String initial() throws Exception {
		return "";
	}

}
