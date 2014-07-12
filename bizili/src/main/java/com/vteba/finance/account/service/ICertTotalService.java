package com.vteba.finance.account.service;

import java.util.List;

import com.vteba.finance.account.model.CertTotal;
import com.vteba.finance.account.model.Certificate;
import com.vteba.service.generic.BaseService;

/**
 * 凭证明细汇总service接口
 * @author yinlei 
 * date 2012-6-14 下午2:43:39
 */
public interface ICertTotalService extends BaseService<CertTotal, String> {
	
	/**
	 * 保存凭证汇总和凭证明细
	 * @param certTotal 凭证汇总
	 * @param certList 凭证明细
	 * @return true成功，false失败
	 * @author yinlei
	 * date 2012-7-4 下午2:41:51
	 */
	public boolean saveCertAndDetail(CertTotal certTotal, List<Certificate> certList);
}
