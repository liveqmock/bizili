package com.vteba.finance.account.service;

import com.vteba.finance.account.model.AccountPeriod;
import com.vteba.service.generic.IGenericService;

/**
 * 会计期间service
 * @author yinlei 
 * date 2012-7-27 下午4:17:58
 */
public interface IAccountPeriodService extends IGenericService<AccountPeriod, String> {
	
	/**
	 * 获得当前会计期间
	 * @author yinlei
	 * date 2012-7-27 下午4:26:47
	 */
	public String getCurrentPeriod();
	
}
