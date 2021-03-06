package com.vteba.finance.assets.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.finance.assets.dao.IAssetsDao;
import com.vteba.finance.assets.model.Assets;
import com.vteba.tx.hibernate.impl.BaseGenericDaoImpl;

/**
 * 资产DAO实现
 * @author yinlei 
 * date 2012-6-29 下午11:24:02
 */
@Named
public class AssetsDaoImpl extends BaseGenericDaoImpl<Assets, String> implements
		IAssetsDao {

	public AssetsDaoImpl() {
		super();
	}

	public AssetsDaoImpl(Class<Assets> entityClass) {
		super(entityClass);
	}

	@Inject
	public void setSessionFactory(SessionFactory biziliSessionFactory) {
		this.sessionFactory = biziliSessionFactory;
	}

}
