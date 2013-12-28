package com.vteba.finance.table.web;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

import com.vteba.finance.table.model.DetailAccount;
import com.vteba.finance.table.service.IDetailAccountService;
import com.vteba.util.reflection.ReflectUtils;
import com.vteba.web.action.BaseAction;

/**
 * 明细账action
 * @author yinlei 
 * date 2012-7-16 下午8:09:32
 */
public class DetailAccountAction extends BaseAction<DetailAccount> {
	private static final long serialVersionUID = -3826789376048217133L;
	private DetailAccount model = new DetailAccount();
	private IDetailAccountService detailAccountServiceImpl;
	
	@Override
	public DetailAccount getModel() {
		return model;
	}
	
	@Inject
	public void setDetailAccountServiceImpl(
			IDetailAccountService detailAccountServiceImpl) {
		this.detailAccountServiceImpl = detailAccountServiceImpl;
	}

	@Override
	public String initial() throws Exception {
		ReflectUtils.emptyToNull(model);
		model.setSubjectCode("2711");
		Map<String, String> param = new LinkedHashMap<String, String>();
		param.put("createDate", "asc");//指定排序的顺序
		param.put("codeNo", "asc");
		param.put("subjectCode", "asc");
		param.put("summary", "desc");
		listResult = detailAccountServiceImpl.getListByPropertyEqual(DetailAccount.class, model, param);
		return SUCCESS;
	}

}
