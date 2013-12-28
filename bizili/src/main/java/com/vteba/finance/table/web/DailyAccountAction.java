package com.vteba.finance.table.web;

import javax.inject.Inject;

import com.vteba.finance.account.model.Subject;
import com.vteba.finance.table.model.DailyAccount;
import com.vteba.finance.table.service.IDailyAccountService;
import com.vteba.util.common.BigDecimalUtils;
import com.vteba.util.common.ObjectUtils;
import com.vteba.util.reflection.ReflectUtils;
import com.vteba.web.action.BaseAction;

/**
 * 日记账action，包括bank和cash
 * @author yinlei 
 * date 2012-7-18 下午8:17:40
 */
public class DailyAccountAction extends BaseAction<DailyAccount> {
	private static final long serialVersionUID = -4283546911874579870L;
	private DailyAccount model = new DailyAccount();
	private IDailyAccountService dailyAccountServiceImpl;
	private String types;//日记账类型
	
	@Override
	public DailyAccount getModel() {
		return model;
	}

	@Override
	public String initial() throws Exception {
		ReflectUtils.emptyToNull(model);
		if (getTypes() == null || getTypes().equals("cash")) {
			model.setType("1001");
		} else if(getTypes().equals("bank")) {
			model.setType("100201");
		}
		String period = ObjectUtils.toDateString("yyyy-MM");
		model.setAccountPeriod(period);
		String hql = " select d from DailyAccount d where d.type = :type and d.accountPeriod = :accountPeriod order by d.createDate asc,isnull(d.codeNo),d.codeNo asc,d.orders asc";
		listResult = dailyAccountServiceImpl.getEntityListByHql(hql, model);
		
		if (listResult != null && listResult.size() > 0) {
			//上日余额
			DailyAccount lastDayBalance = listResult.get(0);
			Double lastDayBal = BigDecimalUtils.add(lastDayBalance.getCredit(), lastDayBalance.getBalance());
			lastDayBal = BigDecimalUtils.subtract(lastDayBal, lastDayBalance.getDebit());
			setAttributeToRequest("lastDayBal", lastDayBal);
			
			//本期合计&本年累计
			Double debitSum = 0D;
			Double creditSum = 0D;
			int index = listResult.size();
			Double balance = listResult.get(index - 1).getBalance();;
			for (int i = 0; i < listResult.size(); i++) {
				DailyAccount bean = listResult.get(i);
				if (bean.getSubjectCode() == null && bean.getCodeNo() == null) {//把各日的合计相加即可
					debitSum = BigDecimalUtils.add(debitSum, bean.getDebit());
					creditSum = BigDecimalUtils.add(creditSum, bean.getCredit());
				}
			}
			DailyAccount periodSum = new DailyAccount();
			
			periodSum.setSummary("本期合计");
			periodSum.setBalance(balance);
			periodSum.setDebit(debitSum);
			periodSum.setCredit(creditSum);
			periodSum.setBalanceDirection(Subject.DIR_DEBIT);
			listResult.add(index, periodSum);
			
			DailyAccount yearSum = new DailyAccount();//因为是transit的，所以要声明俩
			yearSum.setSummary("本年累计");
			yearSum.setBalance(balance);
			yearSum.setDebit(debitSum);
			yearSum.setCredit(creditSum);
			yearSum.setBalanceDirection(Subject.DIR_DEBIT);
			listResult.add(index + 1, yearSum);
		}
		return SUCCESS;
	}

	@Inject
	public void setDailyAccountServiceImpl(
			IDailyAccountService dailyAccountServiceImpl) {
		this.dailyAccountServiceImpl = dailyAccountServiceImpl;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

}
