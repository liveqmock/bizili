package com.vteba.finance.assets.service.impl;

import javax.inject.Named;

import com.vteba.finance.assets.dao.IAssetsDao;
import com.vteba.finance.assets.model.Assets;
import com.vteba.finance.assets.service.IAssetsService;
import com.vteba.tx.hibernate.BaseGenericDao;
import com.vteba.service.generic.impl.BaseServiceImpl;

/**
 * 资产service实现
 * @author yinlei 
 * date 2012-6-29 下午11:23:25
 */
@Named
public class AssetsServiceImpl extends BaseServiceImpl<Assets, String> implements
		IAssetsService {

	private IAssetsDao assetsDaoImpl;
		
	public AssetsServiceImpl() {
		super();
	}

	@Override
	public void setBaseGenericDaoImpl(
			BaseGenericDao<Assets, String> assetsDaoImpl) {
		this.baseGenericDaoImpl = assetsDaoImpl;
		this.assetsDaoImpl = (IAssetsDao) assetsDaoImpl;
	}

	public IAssetsDao getAssetsDaoImpl() {
		return assetsDaoImpl;
	}
	
}
