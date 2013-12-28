package com.vteba.finance.table.web;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

import com.vteba.finance.table.model.AccountBalance;
import com.vteba.finance.table.service.IAccountBalanceService;
import com.vteba.user.dao.UserDao;
import com.vteba.user.model.EmpUser;
import com.vteba.util.common.BigDecimalUtils;
import com.vteba.util.reflection.ReflectUtils;
import com.vteba.web.action.BaseAction;

/**
 * 科目余额表action，相关的科目余额查询等
 * @author yinlei 
 * date 2012-7-2 下午5:45:17
 */
public class AccountBalanceAction extends BaseAction<AccountBalance> {
	private static final long serialVersionUID = -2550367439805944712L;
	
	private AccountBalance model = new AccountBalance();
	private IAccountBalanceService accountBalanceServiceImpl;
	
	public void setModel(AccountBalance model) {
		this.model = model;
	}
	
	@Inject//test
	private UserDao userDao;
	
	@Inject
	public void setAccountBalanceServiceImpl(
			IAccountBalanceService accountBalanceServiceImpl) {
		this.accountBalanceServiceImpl = accountBalanceServiceImpl;
	}

	@Override
	public AccountBalance getModel() {
		return model;
	}

	@Override
	public String initial() throws Exception {
		System.out.println(getHttpServletRequest().getParameter("name"));
		long s = System.currentTimeMillis();
		@SuppressWarnings("unused")
		EmpUser user = userDao.get(123);
		long e = System.currentTimeMillis();
		System.out.println(e-s);
		
		ReflectUtils.emptyToNull(model);
		Map<String, String> param = new LinkedHashMap<String, String>();
		param.put("subjectCode", "asc");
		listResult = accountBalanceServiceImpl.getListByPropertyEqual(AccountBalance.class, model, param);
		
		//String hql = "select sum(ab.startBalanceDebit),sum(ab.startBalanceCredit) from AccountBalance ab";
		//可以使用hql汇总来做，但是where条件
		Double startDebit = 0D;//期初借方
		Double startCredit = 0D;//期初贷方
		Double currentDebit = 0D;//本期借方
		Double currentCredit = 0D;//本期贷方
		Double yearDebit = 0D;//本年借方
		Double yearCredit = 0D;//本年贷方
		Double endDebit = 0D;//期末借方
		Double endCredit = 0D;//期末贷方
		for (AccountBalance bean : listResult) {
			if (bean.getSubjectCode().length() == 4){
				startDebit = BigDecimalUtils.add(startDebit, bean.getStartBalanceDebit());
				startCredit = BigDecimalUtils.add(startCredit, bean.getStartBalanceCredit());
				currentDebit = BigDecimalUtils.add(currentDebit, bean.getCurrenceBalanceDebit());
				currentCredit = BigDecimalUtils.add(currentCredit, bean.getCurrenceBalanceCredit());
				yearDebit = BigDecimalUtils.add(yearDebit, bean.getYearSumBalanceDebit());
				yearCredit = BigDecimalUtils.add(yearCredit, bean.getYearSumBalanceCredit());
				endDebit = BigDecimalUtils.add(endDebit, bean.getEndBalanceDebit());
				endCredit = BigDecimalUtils.add(endCredit, bean.getEndBalanceCredit());
			}
		}
		setAttributeToRequest("startDebit", startDebit);
		setAttributeToRequest("startCredit", startCredit);
		setAttributeToRequest("currentDebit", currentDebit);
		setAttributeToRequest("currentCredit", currentCredit);
		setAttributeToRequest("yearDebit", yearDebit);
		setAttributeToRequest("yearCredit", yearCredit);
		setAttributeToRequest("endDebit", endDebit);
		setAttributeToRequest("endCredit", endCredit);
		return SUCCESS;
	}

}
