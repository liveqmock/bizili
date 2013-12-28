package com.vteba.finance.table.service;

import com.vteba.finance.table.model.TrialBalance;
import com.vteba.service.generic.IGenericService;

/**
 * 试算平衡表Service接口
 * @author yinlei 
 * date 2012-6-30 下午12:43:45
 */
public interface ITrialBalanceService extends IGenericService<TrialBalance, String> {
	
	/**
	 * 自动产生试算平衡表，定时任务调用
	 * @author yinlei
	 * date 2012-7-22 下午5:17:49
	 */
	public void autoGenerateTrialBalanceTask();
	
	/**
	 * 创建某一会计期间的试算平衡表
	 * @param period 会计期间
	 * @author yinlei
	 * date 2012-7-22 下午6:10:22
	 */
	public void createTrialBalance(String period);
}
