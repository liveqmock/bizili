package com.vteba.finance.table.service;

import com.vteba.finance.table.model.AccountBalance;
import com.vteba.service.generic.IGenericService;

/**
 * 科目余额表service接口
 * @author yinlei 
 * date 2012-6-3 下午9:15:02
 */
public interface IAccountBalanceService extends IGenericService<AccountBalance, String> {
	
	/**
	 * 定时任务执行科目余额汇总
	 * @author yinlei
	 * date 2012-7-13 下午3:14:07
	 */
	public void autoGenerateAccountBalanceTask();
	
	/**
	 * 产生科目余额数据
	 * @param type 数据产生的来源
	 * @author yinlei
	 * date 2012-7-12 下午3:57:06
	 */
	public void createAccountBalance(int type);
	
	/**
	 * 根据科目代码查询当期的科目余额
	 * @param subjectCode 科目代码
	 * @return 科目余额
	 * @author yinlei
	 * date 2012-7-17 下午12:03:09
	 */
	public double queryAccountBalanceByCode(String subjectCode);
	
	/**
	 * 计算项目余额(多个科目相加)
	 * @param period 会计期间
	 * @param itemCode 科目字符创
	 * @return 项目余额
	 * @author yinlei
	 * date 2012-7-29 下午9:50:23
	 */
	public Double calculateItemBalance(String period, String itemCode);
}
