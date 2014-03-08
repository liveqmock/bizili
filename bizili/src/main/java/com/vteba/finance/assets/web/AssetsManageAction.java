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
@RequestMapping("/assets")
public class AssetsManageAction extends BaseAction<Assets> {

	@RequestMapping("/assetsmanage-initial")
	public String initial(Assets model) throws Exception {
		
		return "assets/assetsmanage/assetsmanage-initial-success";
	}

}
