package com.vteba.finance.assets.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vteba.finance.assets.model.Assets;
import com.vteba.web.action.BaseAction;

/**
 * 资产管理action
 * @author Administrator
 * 2012-9-1
 */
@Controller
@RequestMapping("/assetsManage")
public class AssetsManageAction extends BaseAction<Assets> {

	private Assets model;
	
	public Assets getModel() {
		return model;
	}

	public void setModel(Assets model) {
		this.model = model;
	}

	@Override
	public String initial() throws Exception {
		
		return "assetsManage/initial";
	}

}
