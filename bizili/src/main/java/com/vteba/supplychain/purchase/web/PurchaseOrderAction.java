package com.vteba.supplychain.purchase.web;

import com.vteba.supplychain.purchase.model.PurchaseOrder;
import com.vteba.web.action.BaseAction;

/**
 * 采购订单action
 * @author yinlei 
 * date 2012-8-8 下午8:21:18
 */
public class PurchaseOrderAction extends BaseAction<PurchaseOrder> {

	private static final long serialVersionUID = 3952141885164764579L;
	private PurchaseOrder model = new PurchaseOrder();
	
	@Override
	public PurchaseOrder getModel() {
		return model;
	}

	@Override
	public String initial() throws Exception {
		return SUCCESS;
	}

}
