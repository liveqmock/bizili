package com.vteba.finance.table.service;

import java.util.List;

import com.vteba.finance.table.model.Ledger;
import com.vteba.service.generic.IGenericService;

/**
 * 总账service接口
 * @author yinlei 
 * date 2012-7-6 下午10:55:00
 */
public interface ILedgerService extends IGenericService<Ledger, String> {
	
	/**
	 * 自动产生总账，定时任务调用
	 * @author yinlei
	 * date 2012-7-18 上午11:29:44
	 */
	public void autoGenerateLedgerTask();
	
	/**
	 * 创建某一会计期间，某一科目，某一科目级别的总账
	 * @param subjeList 科目列表
	 * @param period 会计期间
	 * @param level 科目级别
	 * @author yinlei
	 * date 2012-7-18 下午3:40:46
	 */
	public void createLedger(List<Ledger> subjectList, String period, int level);
}
