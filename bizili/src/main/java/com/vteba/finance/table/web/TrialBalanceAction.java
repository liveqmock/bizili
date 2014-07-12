package com.vteba.finance.table.web;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vteba.finance.table.model.TrialBalance;
import com.vteba.finance.table.service.ITrialBalanceService;
import com.vteba.service.generic.BaseService;
import com.vteba.utils.reflection.ReflectUtils;
import com.vteba.web.action.BaseAction;

/**
 * 试算平衡表action
 * @author yinlei 
 * date 2012-7-22 下午4:43:22
 */
@Controller
@RequestMapping("/table")
public class TrialBalanceAction extends BaseAction<TrialBalance> {
	private ITrialBalanceService trialBalanceServiceImpl;
	
	@RequestMapping("/trialbalance-initial")
	public String initial(TrialBalance model, Map<String, Object> maps) throws Exception {
		ReflectUtils.emptyToNull(model);
		Map<String, String> param = new LinkedHashMap<String, String>();
		param.put("subjectCode", "asc");
		
		listResult = trialBalanceServiceImpl.getEntityList(model, param);
		maps.put("listResult", listResult);
		return "table/trialbalance/trialbalance-initial-success";
	}
	
	@Inject
	public void setTrialBalanceServiceImpl(
			ITrialBalanceService trialBalanceServiceImpl) {
		this.trialBalanceServiceImpl = trialBalanceServiceImpl;
	}

	@Override
	public void setBaseServiceImpl(
			BaseService<TrialBalance, ? extends Serializable> BaseServiceImpl) {
		// TODO Auto-generated method stub
		
	}

}
