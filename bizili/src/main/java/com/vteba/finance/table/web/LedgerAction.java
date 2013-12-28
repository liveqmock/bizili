package com.vteba.finance.table.web;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

import com.vteba.finance.table.model.Ledger;
import com.vteba.finance.table.service.ILedgerService;
import com.vteba.util.reflection.ReflectUtils;
import com.vteba.web.action.BaseAction;

/**
 * 总账action
 * @author yinlei 
 * date 2012-7-16 下午8:09:20
 */
public class LedgerAction extends BaseAction<Ledger> {
	private static final long serialVersionUID = 4573100017972310047L;
	private Ledger model = new Ledger();
	private ILedgerService ledgerServiceImpl;
	
	@Override
	public Ledger getModel() {
		return model;
	}
	
	@Inject
	public void setLedgerServiceImpl(ILedgerService ledgerServiceImpl) {
		this.ledgerServiceImpl = ledgerServiceImpl;
	}

	@Override
	public String initial() throws Exception {
		ReflectUtils.emptyToNull(model);
		if (model.getLevel() == null) {
			model.setLevel(1);
		}
		Map<String, String> order = new LinkedHashMap<String, String>();
		order.put("subjectCode", "asc");
		order.put("createDate", "asc");
		order.put("summary", "desc");
		listResult = ledgerServiceImpl.getListByPropertyEqual(Ledger.class, model, order);
		return SUCCESS;
	}

}
