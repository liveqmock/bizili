package com.vteba.finance.assets.web;

import com.vteba.finance.assets.model.AssetsType;
import com.vteba.web.action.BaseAction;

/**
 * 资产分类action
 * @author yinlei
 * date 2012-9-1
 */
public class AssetsTypeAction extends BaseAction<AssetsType> {
	
	private static final long serialVersionUID = 4891486026620377842L;
	private AssetsType model = new AssetsType();
	
	@Override
	public AssetsType getModel() {
		return model;
	}

	@Override
	public String initial() throws Exception {
		
		return SUCCESS;
	}

}
