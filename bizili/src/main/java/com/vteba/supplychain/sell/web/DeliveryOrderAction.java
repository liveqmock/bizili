package com.vteba.supplychain.sell.web;

import com.vteba.supplychain.sell.model.DeliveryOrder;
import com.vteba.web.action.BaseAction;

/**
 * 发货单action
 * @author yinlei 
 * date 2012-8-8 下午8:18:01
 */
public class DeliveryOrderAction extends BaseAction<DeliveryOrder> {

	private static final long serialVersionUID = 1222452907939830966L;
	private DeliveryOrder model = new DeliveryOrder();
	
	@Override
	public DeliveryOrder getModel() {
		return model;
	}

	@Override
	public String initial() throws Exception {
		return SUCCESS;
	}

}
