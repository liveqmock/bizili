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
@RequestMapping("/assets")
public class AssetsTypeAction extends BaseAction<AssetsType> {

	@RequestMapping("/assetstype-initial")
	public String initial() throws Exception {
		
		return "assets/assetstype/assetstype-initial-success";
	}

}
