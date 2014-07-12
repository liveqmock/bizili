package com.vteba.finance.table.service;

import java.util.List;

import com.vteba.finance.account.model.Certificate;
import com.vteba.finance.table.model.AccountSummary;
import com.vteba.service.generic.BaseService;

/**
 * 凭证汇总表service 接口
 * @author yinlei 
 * date 2012-7-9 下午9:08:00
 */
public interface IAccountSummaryService extends BaseService<AccountSummary, String> {
	
	/**
	 * 自动产生一次凭证汇总，定时任务调用
	 * @author yinlei
	 * date 2012-7-9 下午9:47:00
	 */
	public void autoGenerateAccountSummaryTask();
	
	/**
	 * 创建accountSummary
	 * @author yinlei
	 * date 2012-7-24 上午10:58:50
	 */
	public void createAccountSummary();
	
	/**
	 * 当修改或者新增凭证时，触发该凭证对应的科目进行凭证汇总
	 * @param certList 修改或者新增的凭证明细列表
	 * @return true or false
	 * @author yinlei
	 * date 2012-7-24 上午11:00:38
	 */
	public boolean updateAccountSummary(List<Certificate> certList);
}
