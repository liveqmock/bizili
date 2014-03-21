package com.vteba.finance.report.web;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
@Controller
@RequestMapping("/report")
public class ProfitAction extends BaseAction<Profit> {
	private IProfitService profitServiceImpl;
	private IAccountPeriodService accountPeriodServiceImpl;

	@RequestMapping("/profit-initial")
	public String initial(Profit model, Map<String, Object> maps) throws Exception {
		ReflectUtils.emptyToNulls(model);
		if (model.getAccountPeriod() == null) {//默认查当前会计期间
			String accountPeriod = accountPeriodServiceImpl.getCurrentPeriod();
			model.setAccountPeriod(accountPeriod);
		}
		Map<String, String> param = new LinkedHashMap<String, String>();
		param.put("rowNumber", "asc");
		listResult = profitServiceImpl.getListByCriteria(Profit.class, model, param);
		maps.put("listResult", listResult);
		return "report/profit/profit-initial-success";
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
