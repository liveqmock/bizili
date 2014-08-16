package com.vteba.finance.table.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vteba.finance.currency.model.Currency;
import com.vteba.finance.table.model.AccountSummary;
import com.vteba.finance.table.service.IAccountSummaryService;
import com.vteba.service.generic.BaseService;
import com.vteba.utils.common.BigDecimalUtils;
import com.vteba.utils.date.DateUtils;
import com.vteba.web.action.BasicAction;

/**
 * 科目汇总表action
 * @author yinlei 
 * date 2012-7-12 上午11:29:47
 */
@Controller
@RequestMapping("/table")
public class AccountSummaryAction extends BasicAction<AccountSummary> {
	private IAccountSummaryService accountSummaryServiceImpl;
	
	@RequestMapping("/accountsummary-initial")
	public String initial(AccountSummary model, Map<String, Object> maps) throws Exception {
		String currentPeriod = DateUtils.toDateString("yyyy-MM");
		Map<String, Object> param = new HashMap<String, Object>();
		String equalQuery = "select a from AccountSummary a where a.accountPeriod between :accountPeriod and :endPeriod and level = :level and currency = :currency order by a.subjectCode asc";
		if (model.getAccountPeriod() == null) {//默认查询当前会计期间
			model.setAccountPeriod(currentPeriod);
		}
		if (model.getEndPeriod() == null) {
			model.setEndPeriod(currentPeriod);
		}
		if (model.getCurrency() == null) {
			model.setCurrency(Currency.CUR_TYPE_CN);//默认查CNY
		}
		if (model.getLevel() == null) {
			model.setLevel(1);//默认查一级科目
			
			listResult = accountSummaryServiceImpl.getListByHql(equalQuery, model);
		} else if (model.getLevel() == 0) {
			List<Integer> level = new ArrayList<Integer>();
			level.add(1);
			level.add(2);
			level.add(3);
			
			param.put("levels", level);
			String query = "select a from AccountSummary a where a.accountPeriod between :accountPeriod and :endPeriod and level in (:levels) and currency = :currency order by a.subjectCode asc";
			listResult = accountSummaryServiceImpl.getListByHql(query, param, model);
		} else {
			listResult = accountSummaryServiceImpl.getListByHql(equalQuery, model);
		}
		maps.put("listResult", listResult);
		
		//汇总借方和贷方
		Double debit = 0D;
		Double credit = 0D;
		for (AccountSummary bean : listResult) {
			if (bean.getSubjectCode().length() == 4) {//只需要汇总一级科目就OK
				debit = BigDecimalUtils.add(debit, bean.getDebit());
				credit = BigDecimalUtils.add(credit, bean.getCredit());
			}
		}
		setAttributeToRequest("debit", debit);
		setAttributeToRequest("credit", credit);
		return "/table/accountsummary/accountsummary-initial-success";
	}

	@Inject
	public void setAccountSummaryServiceImpl(
			IAccountSummaryService accountSummaryServiceImpl) {
		this.accountSummaryServiceImpl = accountSummaryServiceImpl;
	}

	@Override
	public void setBaseServiceImpl(
			BaseService<AccountSummary, ? extends Serializable> BaseServiceImpl) {
		// TODO Auto-generated method stub
		
	}
	
}
