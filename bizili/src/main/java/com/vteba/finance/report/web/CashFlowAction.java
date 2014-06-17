package com.vteba.finance.report.web;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vteba.finance.report.model.CashFlow;
import com.vteba.finance.report.service.ICashFlowService;
import com.vteba.service.generic.IGenericService;
import com.vteba.utils.reflection.ReflectUtils;
import com.vteba.web.action.BaseAction;

/**
 * 现金流量表action
 * @author yinlei 
 * date 2012-7-27 下午1:09:11
 */
@Controller
@RequestMapping("/report")
public class CashFlowAction extends BaseAction<CashFlow> {
	
	private ICashFlowService cashFlowServiceImpl;
	
	@RequestMapping("/cashflow-initial")
	public String initial(CashFlow model, Map<String, Object> maps) throws Exception {
		ReflectUtils.emptyToNulls(model);
		Map<String, String> param = new LinkedHashMap<String, String>();
		param.put("orders", "asc");
		listResult = cashFlowServiceImpl.getListByCriteria(CashFlow.class, model, param);
		maps.put("listResult", listResult);
		return "report/cashflow/cashflow-initial-success";
	}
	
	@Inject
	public void setCashFlowServiceImpl(ICashFlowService cashFlowServiceImpl) {
		this.cashFlowServiceImpl = cashFlowServiceImpl;
	}

	@Override
	public void setGenericServiceImpl(
			IGenericService<CashFlow, ? extends Serializable> genericServiceImpl) {
		// TODO Auto-generated method stub
		
	}

}
