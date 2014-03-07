package com.vteba.hr.wage.web;

import com.vteba.hr.wage.model.Salary;
import com.vteba.web.action.BaseAction;

/**
 * 薪酬action
 * @author yinlei
 * date 2012-9-4 下午10:09:05
 */
public class SalaryAction extends BaseAction<Salary> {

	private Salary model = new Salary();
	
	public Salary getModel() {
		return model;
	}

	@Override
	public String initial() throws Exception {
		return "";
	}

}
