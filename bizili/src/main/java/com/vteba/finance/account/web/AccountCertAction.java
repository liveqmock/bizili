package com.vteba.finance.account.web;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vteba.common.constant.CommonConst;
import com.vteba.finance.account.model.CertTotal;
import com.vteba.finance.account.model.Certificate;
import com.vteba.finance.account.service.ICertTotalService;
import com.vteba.persister.generic.Page;
import com.vteba.util.reflection.ReflectUtils;
import com.vteba.web.action.BaseAction;

/**
 * 会计凭证action
 * @author yinlei 
 * date 2012-6-24 下午11:08:36
 */
@Controller
@RequestMapping("/account")
public class AccountCertAction extends BaseAction<CertTotal> {
	/**
	 * 封装凭证明细
	 */
	private List<Certificate> certList = new ArrayList<Certificate>();
	
	private ICertTotalService certTotalServiceImpl;
	
	@Inject
	public void setCertTotalServiceImpl(ICertTotalService certTotalServiceImpl) {
		this.certTotalServiceImpl = certTotalServiceImpl;
	}

	@RequestMapping("/certificate-initial")
	public String initial(CertTotal model) throws Exception {
		Page<CertTotal> pages = new Page<CertTotal>();
		ReflectUtils.emptyToNull(model);
		pages = certTotalServiceImpl.queryForPageByModel(page, model);
		listResult = pages.getResult();
		setAttributeToRequest(CommonConst.PAGE_NAME, pages);
		return "/account/certificate/certificate-initial-success";
	}
	
	/**
	 * 保存凭证汇总和凭证明细
	 * @throws Exception
	 * @author yinlei
	 * date 2012-7-5 下午9:07:05
	 */
	@RequestMapping("/certificate-input")
	public String input(CertTotal model) throws Exception {
		if (isInit()) {
			setTokenValue();
			return "account/certificate/certificate-input-success";
		}
		if (isTokenValueOK()) {
			certTotalServiceImpl.saveCertAndDetail(model, certList);//保存凭证汇总和凭证明细
		}
		return "account/certificate/certificate-input-success";
	}

	public List<Certificate> getCertList() {
		return certList;
	}

	public void setCertList(List<Certificate> certList) {
		this.certList = certList;
	}

}
