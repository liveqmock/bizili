package com.vteba.finance.assets.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.finance.assets.dao.IAssetsTypeDao;
import com.vteba.finance.assets.model.AssetsType;
import com.vteba.finance.assets.service.IAssetsTypeService;
import com.vteba.tx.hibernate.BaseGenericDao;
import com.vteba.service.generic.impl.BaseServiceImpl;

/**
 * 固定资产分类service实现
 * @author yinlei
 * date 2012-9-4 下午9:54:12
 */
@Named
public class AssetsTypeServiceImpl extends
		BaseServiceImpl<AssetsType, String> implements IAssetsTypeService {
	private IAssetsTypeDao assetsTypeDaoImpl;
	
	public AssetsTypeServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setBaseGenericDaoImpl(
			BaseGenericDao<AssetsType, String> assetsTypeDaoImpl) {
		this.baseGenericDaoImpl = assetsTypeDaoImpl;
		this.assetsTypeDaoImpl = (IAssetsTypeDao) assetsTypeDaoImpl;
	}

	public IAssetsTypeDao getAssetsTypeDaoImpl() {
		return assetsTypeDaoImpl;
	}

}
