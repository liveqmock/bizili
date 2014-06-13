package com.vteba.finance.table.web;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vteba.finance.table.model.DetailAccount;
import com.vteba.finance.table.service.IDetailAccountService;
import com.vteba.service.generic.IGenericService;
import com.vteba.util.reflection.ReflectUtils;
import com.vteba.web.action.BaseAction;

/**
 * 明细账action
 * @author yinlei 
 * date 2012-7-16 下午8:09:32
 */
@Controller
@RequestMapping("/table")
public class DetailAccountAction extends BaseAction<DetailAccount> {
	private IDetailAccountService detailAccountServiceImpl;
	
	@Inject
	public void setDetailAccountServiceImpl(
			IDetailAccountService detailAccountServiceImpl) {
		this.detailAccountServiceImpl = detailAccountServiceImpl;
	}

	@RequestMapping("/detailaccount-initial")
	public String initial(DetailAccount model, Map<String, Object> maps) throws Exception {
		ReflectUtils.emptyToNull(model);
		model.setSubjectCode("2711");
		Map<String, String> param = new LinkedHashMap<String, String>();
		param.put("createDate", "asc");//指定排序的顺序
		param.put("codeNo", "asc");
		param.put("subjectCode", "asc");
		param.put("summary", "desc");
		listResult = detailAccountServiceImpl.getListByCriteria(DetailAccount.class, model, param);
		maps.put("listResult", listResult);
		return "table/detailaccount/detailaccount-initial-success";
	}

	@Override
	public void setGenericServiceImpl(
			IGenericService<DetailAccount, ? extends Serializable> genericServiceImpl) {
		// TODO Auto-generated method stub
		
	}

}
