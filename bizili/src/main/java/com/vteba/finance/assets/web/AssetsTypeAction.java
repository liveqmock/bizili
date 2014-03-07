package com.vteba.finance.assets.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vteba.finance.assets.model.AssetsType;
import com.vteba.web.action.BaseAction;

/**
 * 资产分类action
 * @author yinlei
 * date 2012-9-1
 */
@Controller
@RequestMapping("/assetsType")
public class AssetsTypeAction extends BaseAction<AssetsType> {
	
	private AssetsType model = new AssetsType();
	
	public AssetsType getModel() {
		return model;
	}

	@RequestMapping("/initial")
	@Override
	public String initial() throws Exception {
		
		return "assetsType/initial";
	}

}
