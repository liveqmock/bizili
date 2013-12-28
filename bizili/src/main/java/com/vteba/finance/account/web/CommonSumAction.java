package com.vteba.finance.account.web;

import javax.inject.Inject;

import com.vteba.finance.account.model.CommonSum;
import com.vteba.finance.account.service.ICommonSumService;
import com.vteba.web.action.BaseAction;

/**
 * 常用摘要action
 * @author yinlei 
 * date 2012-7-5 下午11:02:32
 */
public class CommonSumAction extends BaseAction<CommonSum> {
	private static final long serialVersionUID = 7417186516058078996L;
	private CommonSum model = new CommonSum();
	private ICommonSumService commonSumServiceImpl;
	
	public ICommonSumService getCommonSumServiceImpl() {
		return commonSumServiceImpl;
	}
	
	@Inject
	public void setCommonSumServiceImpl(ICommonSumService commonSumServiceImpl) {
		this.commonSumServiceImpl = commonSumServiceImpl;
	}

	@Override
	public CommonSum getModel() {
		return model;
	}

	@Override
	public String initial() throws Exception {
		return SUCCESS;
	}

}
