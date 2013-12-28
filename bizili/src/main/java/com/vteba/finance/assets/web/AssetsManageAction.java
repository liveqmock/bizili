package com.vteba.finance.assets.web;

import com.vteba.finance.assets.model.Assets;
import com.vteba.web.action.BaseAction;

/**
 * 资产管理action
 * @author Administrator
 * 2012-9-1
 */
public class AssetsManageAction extends BaseAction<Assets> {

	private static final long serialVersionUID = -1353038216652135199L;
	private Assets model  = new Assets();
	
	@Override
	public Assets getModel() {
		return model;
	}

	@Override
	public String initial() throws Exception {
		
		return SUCCESS;
	}

}
