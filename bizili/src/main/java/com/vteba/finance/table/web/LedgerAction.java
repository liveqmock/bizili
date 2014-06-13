package com.vteba.finance.table.web;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vteba.finance.table.model.Ledger;
import com.vteba.finance.table.service.ILedgerService;
import com.vteba.service.generic.IGenericService;
import com.vteba.util.reflection.ReflectUtils;
import com.vteba.web.action.BaseAction;

/**
 * 总账action
 * @author yinlei 
 * date 2012-7-16 下午8:09:20
 */
@Controller
@RequestMapping("/table")
public class LedgerAction extends BaseAction<Ledger> {
	private ILedgerService ledgerServiceImpl;
	
	@Inject
	public void setLedgerServiceImpl(ILedgerService ledgerServiceImpl) {
		this.ledgerServiceImpl = ledgerServiceImpl;
	}

	@RequestMapping("/ledger-initial")
	public String initial(Ledger model, Map<String, Object> maps) throws Exception {
		ReflectUtils.emptyToNull(model);
		if (model.getLevel() == null) {
			model.setLevel(1);
		}
		Map<String, String> order = new LinkedHashMap<String, String>();
		order.put("subjectCode", "asc");
		order.put("createDate", "asc");
		order.put("summary", "desc");
		listResult = ledgerServiceImpl.getListByCriteria(Ledger.class, model, order);
		maps.put("listResult", listResult);
		return "/table/ledger/ledger-initial-success";
	}

	@Override
	public void setGenericServiceImpl(
			IGenericService<Ledger, ? extends Serializable> genericServiceImpl) {
		// TODO Auto-generated method stub
		
	}

}
