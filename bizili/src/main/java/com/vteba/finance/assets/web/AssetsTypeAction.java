package com.vteba.finance.assets.web;

import java.io.Serializable;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vteba.finance.assets.model.AssetsType;
import com.vteba.service.generic.BaseService;
import com.vteba.web.action.BasicAction;

/**
 * 资产分类action
 * @author yinlei
 * date 2012-9-1
 */
@Controller
@RequestMapping("/assets")
public class AssetsTypeAction extends BasicAction<AssetsType> {

	@RequestMapping("/assetstype-initial")
	public String initial() throws Exception {
		
		return "assets/assetstype/assetstype-initial-success";
	}

	@Override
	public void setBaseServiceImpl(
			BaseService<AssetsType, ? extends Serializable> BaseServiceImpl) {
		// TODO Auto-generated method stub
		
	}

}
