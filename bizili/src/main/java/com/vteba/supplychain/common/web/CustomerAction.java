package com.vteba.supplychain.common.web;

import com.vteba.supplychain.common.model.Customer;
import com.vteba.web.action.BaseAction;

/**
 * 客户action
 * @author yinlei 
 * date 2012-8-8 下午6:00:36
 */
public class CustomerAction extends BaseAction<Customer> {
	
	private static final long serialVersionUID = 4242693313750431062L;
	private Customer model = new Customer();
	
	@Override
	public Customer getModel() {
		return model;
	}

	@Override
	public String initial() throws Exception {
		return null;
	}

}
