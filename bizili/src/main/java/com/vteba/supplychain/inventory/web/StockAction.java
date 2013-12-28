package com.vteba.supplychain.inventory.web;

import com.vteba.supplychain.inventory.model.Stock;
import com.vteba.web.action.BaseAction;

/**
 * 库存商品action
 * @author yinlei 
 * date 2012-8-8 下午8:23:16
 */
public class StockAction extends BaseAction<Stock> {

	private static final long serialVersionUID = -4273109180219293975L;
	private Stock model = new Stock();
	
	@Override
	public Stock getModel() {
		return model;
	}

	@Override
	public String initial() throws Exception {
		return SUCCESS;
	}

}
