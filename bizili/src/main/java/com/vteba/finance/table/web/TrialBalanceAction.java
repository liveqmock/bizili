package com.vteba.finance.table.web;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

import com.vteba.finance.table.model.TrialBalance;
import com.vteba.finance.table.service.ITrialBalanceService;
import com.vteba.util.reflection.ReflectUtils;
import com.vteba.web.action.BaseAction;

/**
 * 试算平衡表action
 * @author yinlei 
 * date 2012-7-22 下午4:43:22
 */
public class TrialBalanceAction extends BaseAction<TrialBalance> {
	private static final long serialVersionUID = 8040692829885509407L;
	private TrialBalance model = new TrialBalance();
	private ITrialBalanceService trialBalanceServiceImpl;
	
	@Override
	public TrialBalance getModel() {
		return model;
	}

	@Override
	public String initial() throws Exception {
		ReflectUtils.emptyToNull(model);
		Map<String, String> param = new LinkedHashMap<String, String>();
		param.put("subjectCode", "asc");
		//addActionMessage("sdfjl");
		listResult = trialBalanceServiceImpl.getListByPropertyEqual(TrialBalance.class, model, param);
		return SUCCESS;
	}
	
	@Inject
	public void setTrialBalanceServiceImpl(
			ITrialBalanceService trialBalanceServiceImpl) {
		this.trialBalanceServiceImpl = trialBalanceServiceImpl;
	}

}
