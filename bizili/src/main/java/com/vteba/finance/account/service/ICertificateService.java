package com.vteba.finance.account.service;

import java.util.List;

import com.vteba.finance.account.model.Certificate;
import com.vteba.service.generic.IGenericService;

/**
 * 凭证明细service接口
 * @author yinlei 
 * date 2012-6-14 下午2:45:58
 */
public interface ICertificateService extends IGenericService<Certificate, String> {
	
	public List<Certificate> getAllList();
}
