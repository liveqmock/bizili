package com.vteba.finance.assets.service.impl;

import javax.inject.Named;

import com.vteba.finance.assets.dao.IAssetsDao;
import com.vteba.finance.assets.model.Assets;
import com.vteba.finance.assets.service.IAssetsService;
import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;

/**
 * 资产service实现
 * @author yinlei 
 * date 2012-6-29 下午11:23:25
 */
@Named
public class AssetsServiceImpl extends GenericServiceImpl<Assets, String> implements
		IAssetsService {

	private IAssetsDao assetsDaoImpl;
		
	public AssetsServiceImpl() {
		super();
	}

	@Override
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<Assets, String> assetsDaoImpl) {
		this.hibernateGenericDaoImpl = assetsDaoImpl;
		this.assetsDaoImpl = (IAssetsDao) assetsDaoImpl;
	}

	public IAssetsDao getAssetsDaoImpl() {
		return assetsDaoImpl;
	}
	
}
