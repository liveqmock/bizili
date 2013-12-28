package com.vteba.finance.report.web;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

import com.vteba.finance.account.service.IAccountPeriodService;
import com.vteba.finance.report.model.Profit;
import com.vteba.finance.report.service.IProfitService;
import com.vteba.util.reflection.ReflectUtils;
import com.vteba.web.action.BaseAction;

/**
 * 利润表action
 * @author yinlei 
 * date 2012-7-25 下午4:41:04
 */
public class ProfitAction extends BaseAction<Profit> {
	private static final long serialVersionUID = 3089914964324259265L;
	private Profit model = new Profit();
	private IProfitService profitServiceImpl;
	private IAccountPeriodService accountPeriodServiceImpl;
	
	@Override
	public Profit getModel() {
		return model;
	}

	@Override
	public String initial() throws Exception {
		ReflectUtils.emptyToNull(model);
		if (model.getAccountPeriod() == null) {//默认查当前会计期间
			String accountPeriod = accountPeriodServiceImpl.getCurrentPeriod();
			model.setAccountPeriod(accountPeriod);
		}
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		param.put("rowNumber", "asc");
		listResult = profitServiceImpl.getListByPropertyEqual(Profit.class, model, param);
		return SUCCESS;
	}
	
	@Inject
	public void setProfitServiceImpl(IProfitService profitServiceImpl) {
		this.profitServiceImpl = profitServiceImpl;
	}
	
	@Inject
	public void setAccountPeriodServiceImpl(
			IAccountPeriodService accountPeriodServiceImpl) {
		this.accountPeriodServiceImpl = accountPeriodServiceImpl;
	}

}
