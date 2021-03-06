package com.vteba.finance.account.web;

import java.io.Serializable;
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
import com.vteba.finance.account.service.ISubjectService;
import com.vteba.service.generic.BaseService;
import com.vteba.tx.generic.Page;
import com.vteba.utils.reflection.ReflectUtils;
import com.vteba.web.action.BasicAction;
import com.vteba.web.action.PageBean;

/**
 * 会计凭证action
 * @author yinlei 
 * date 2012-6-24 下午11:08:36
 */
@Controller
@RequestMapping("/account")
public class AccountCertAction extends BasicAction<CertTotal> {
	
	private ICertTotalService certTotalServiceImpl;
	@Inject
	private ISubjectService subjectServiceImpl;
	
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
		ReflectUtils.emptyToNulls(model);
		page = pageBean.getPage();
		pages = certTotalServiceImpl.queryForPage(page, model);
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
	public String input(CertTotal model, CertTotalForm certTotalForm, Map<String, Object> maps) throws Exception {
		if (isInit()) {
			setTokenValue();
			String nodes = subjectServiceImpl.getSubjectJson();
			maps.put("subjectTree", nodes);
			return "account/certificate/certificate-input-success";
		}
		if (isTokenValueOK()) {
			certTotalServiceImpl.saveCertAndDetail(model, certTotalForm.getCertList());//保存凭证汇总和凭证明细
		}
		return "account/certificate/certificate-input-success";
	}

	@Override
	public void setBaseServiceImpl(
			BaseService<CertTotal, ? extends Serializable> BaseServiceImpl) {
		// TODO Auto-generated method stub
		
	}

}
