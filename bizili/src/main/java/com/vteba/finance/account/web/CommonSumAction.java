package com.vteba.finance.account.web;

import java.io.Serializable;

import javax.inject.Inject;

import com.vteba.finance.account.model.CommonSum;
import com.vteba.finance.account.service.ICommonSumService;
import com.vteba.service.generic.BaseService;
import com.vteba.web.action.BasicAction;

/**
 * 常用摘要action
 * @author yinlei 
 * date 2012-7-5 下午11:02:32
 */
public class CommonSumAction extends BasicAction<CommonSum> {
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

	public String initial() throws Exception {
		return "";
	}

	@Override
	public void setBaseServiceImpl(
			BaseService<CommonSum, ? extends Serializable> BaseServiceImpl) {
		// TODO Auto-generated method stub
		
	}

}
