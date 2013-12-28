package com.vteba.finance.report.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.finance.report.dao.IAssetsLiabilitiesDao;
import com.vteba.finance.report.model.AssetsLiabilities;
import com.vteba.persister.hibernate.impl.HibernateGenericDaoImpl;

/**
 * 资产负债表DAO实现
 * @author yinlei 
 * date 2012-6-29 下午11:21:41
 */
@Named
public class AssetsLiabilitiesDaoImpl extends
		HibernateGenericDaoImpl<AssetsLiabilities, String> implements
		IAssetsLiabilitiesDao {

	public AssetsLiabilitiesDaoImpl() {
		super();
	}

	public AssetsLiabilitiesDaoImpl(Class<AssetsLiabilities> entityClass) {
		super(entityClass);
	}
	
	@Override
	@Inject
	public void setSessionFactory(SessionFactory biziliSessionFactory) {
		this.sessionFactory = biziliSessionFactory;
	}

}
