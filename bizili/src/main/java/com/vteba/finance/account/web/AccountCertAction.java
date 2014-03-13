package com.vteba.finance.account.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vteba.common.constant.CommonConst;
import com.vteba.finance.account.form.CertTotalForm;
import com.vteba.finance.account.model.CertTotal;
import com.vteba.finance.account.service.ICertTotalService;
import com.vteba.persister.generic.Page;
import com.vteba.util.reflection.ReflectUtils;
import com.vteba.web.action.BaseAction;
import com.vteba.web.action.PageBean;

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

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		super.initBinder(binder);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}
	
	@RequestMapping("/certificate-initial")
	public String initial(CertTotal model, PageBean<CertTotal> pageBean, Map<String, Object> maps) throws Exception {
		Page<CertTotal> pages = new Page<CertTotal>();
		ReflectUtils.emptyToNull(model);
		page = pageBean.getPage();
		pages = certTotalServiceImpl.queryForPageByModel(page, model);
		listResult = pages.getResult();
		maps.put("listResult", listResult);
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
	public String input(CertTotal model, CertTotalForm certTotalForm) throws Exception {
		if (isInit()) {
			setTokenValue();
			return "account/certificate/certificate-input-success";
		}
		if (isTokenValueOK()) {
			certTotalServiceImpl.saveCertAndDetail(model, certTotalForm.getCertList());//保存凭证汇总和凭证明细
		}
		return "account/certificate/certificate-input-success";
	}

}
