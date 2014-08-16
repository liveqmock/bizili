package com.vteba.finance.assets.web;

import java.io.Serializable;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vteba.finance.assets.model.Assets;
import com.vteba.service.generic.BaseService;
import com.vteba.web.action.BasicAction;

/**
 * 资产管理action
 * @author Administrator
 * 2012-9-1
 */
@Controller
@RequestMapping("/assets")
public class AssetsManageAction extends BasicAction<Assets> {

	@RequestMapping("/assetsmanage-initial")
	public String initial(Assets model) throws Exception {
		
		return "assets/assetsmanage/assetsmanage-initial-success";
	}

	@Override
	public void setBaseServiceImpl(
			BaseService<Assets, ? extends Serializable> BaseServiceImpl) {
		// TODO Auto-generated method stub
		
	}

}
