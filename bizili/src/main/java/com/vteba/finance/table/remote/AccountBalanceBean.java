package com.vteba.finance.table.remote;

import javax.inject.Inject;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.spring.SpringCreator;

import com.vteba.finance.table.service.IAccountBalanceService;

/**
 * 科目余额表 dwr Bean
 * @author yinlei 
 * date 2012-7-5 下午4:39:11
 */
@RemoteProxy(creator = SpringCreator.class)
public class AccountBalanceBean {
	private IAccountBalanceService accountBalanceServiceImpl;
	
	@Inject
	public void setAccountBalanceServiceImpl(
			IAccountBalanceService accountBalanceServiceImpl) {
		this.accountBalanceServiceImpl = accountBalanceServiceImpl;
	}

	/**
	 * 根据科目ID查询该科目的余额，参数错误返回-1
	 * @param subjectCode 科目代码
	 * @return 余额
	 * @author yinlei
	 * date 2012-7-2 下午5:54:19
	 */
	@RemoteMethod
	public double queryAccountBalanceByCode(String subjectCode){
		return accountBalanceServiceImpl.queryAccountBalanceByCode(subjectCode);
	}
}
