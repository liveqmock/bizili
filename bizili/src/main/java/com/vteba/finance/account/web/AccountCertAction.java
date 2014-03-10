package com.vteba.finance.account.web;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vteba.common.constant.CommonConst;
import com.vteba.finance.account.form.CertTotalForm;
import com.vteba.finance.account.model.CertTotal;
import com.vteba.finance.account.service.ICertTotalService;
import com.vteba.persister.generic.Page;
import com.vteba.util.reflection.ReflectUtils;
import com.vteba.web.action.BaseAction;
import com.vteba.web.action.FormBean;

/**
 * 会计凭证action
 * @author yinlei 
 * date 2012-6-24 下午11:08:36
 */
@Controller
@RequestMapping("/account")
public class AccountCertAction extends BaseAction<CertTotal> {
	
	private ICertTotalService certTotalServiceImpl;
	
	@Inject
	public void setCertTotalServiceImpl(ICertTotalService certTotalServiceImpl) {
		this.certTotalServiceImpl = certTotalServiceImpl;
	}

	@RequestMapping("/certificate-initial")
	public String initial(CertTotal model, FormBean<CertTotal> formBean) throws Exception {
		Page<CertTotal> pages = new Page<CertTotal>();
		ReflectUtils.emptyToNull(model);
		page = formBean.getPage();
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
	public String input(CertTotal model, @RequestParam(value = "init", required = false)boolean init, CertTotalForm certTotalForm) throws Exception {
		if (init) {
			setTokenValue();
			return "account/certificate/certificate-input-success";
		}
		if (isTokenValueOK()) {
			certTotalServiceImpl.saveCertAndDetail(model, certTotalForm.getCertList());//保存凭证汇总和凭证明细
		}
		return "account/certificate/certificate-input-success";
	}

}
