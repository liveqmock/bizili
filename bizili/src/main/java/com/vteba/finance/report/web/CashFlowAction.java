package com.vteba.finance.report.web;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vteba.finance.report.model.CashFlow;
import com.vteba.finance.report.service.ICashFlowService;
import com.vteba.util.reflection.ReflectUtils;
import com.vteba.web.action.BaseAction;

/**
 * 现金流量表action
 * @author yinlei 
 * date 2012-7-27 下午1:09:11
 */
@Controller
@RequestMapping("/cashflow")
public class CashFlowAction extends BaseAction<CashFlow> {
	
	private CashFlow model = new CashFlow();
	private ICashFlowService cashFlowServiceImpl;
	
	public CashFlow getModel() {
		return model;
	}

	public void setModel(CashFlow model) {
		this.model = model;
	}

	@RequestMapping("/initial")
	@Override
	public String initial() throws Exception {
		ReflectUtils.emptyToNull(model);
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		param.put("orders", "asc");
		listResult = cashFlowServiceImpl.getListByPropertyEqual(CashFlow.class, model, param);
		return "cashflow/initial";
	}
	
	@Inject
	public void setCashFlowServiceImpl(ICashFlowService cashFlowServiceImpl) {
		this.cashFlowServiceImpl = cashFlowServiceImpl;
	}

}
