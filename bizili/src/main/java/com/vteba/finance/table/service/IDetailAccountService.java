package com.vteba.finance.table.service;

import java.util.List;

import com.vteba.finance.table.model.DetailAccount;
import com.vteba.service.generic.IGenericService;

/**
 * 明细账service接口
 * @author yinlei 
 * date 2012-7-6 下午10:53:52
 */
public interface IDetailAccountService extends IGenericService<DetailAccount, String> {
	
	/**
	 * 自动产生明细账，定时任务调用
	 * @author yinlei
	 * date 2012-7-16 下午8:13:47
	 */
	public void autoGenerateDetailAccountTask();
	
	/**
	 * 生成某一会计期间，某些科目，某一级别的明细账
	 * @param subjectList List<String>科目代码
	 * @param period 会计期间
	 * @param level 科目级别
	 * @author yinlei
	 * date 2012-7-17 下午11:31:02
	 */
	public void createDetailAccount(List<String> subjectList, String period, Integer level);
}
