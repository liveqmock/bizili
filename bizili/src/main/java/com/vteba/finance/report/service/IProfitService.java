package com.vteba.finance.report.service;

import com.vteba.finance.report.model.Profit;
import com.vteba.service.generic.IGenericService;

/**
 * 利润表service
 * @author yinlei 
 * date 2012-7-23 上午10:36:01
 */
public interface IProfitService extends IGenericService<Profit, String> {
	
	/**
	 * 自动产生利润表，定时任务调用
	 * @author yinlei
	 * date 2012-7-31 下午3:58:15
	 */
	public void autoGenerateProfitTask();
	
	/**
	 * 创建利润表
	 * @author yinlei
	 * date 2012-7-31 下午3:59:41
	 */
	public void createProfit();
	
	/**
	 * 更新利润表
	 * @author yinlei
	 * date 2012-7-31 下午4:00:13
	 */
	public void updateProfit();
}
