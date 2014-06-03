package com.vteba.finance.assets.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.finance.assets.dao.IDepreDetailsDao;
import com.vteba.finance.assets.model.DepreDetails;
import com.vteba.finance.assets.service.IDepreDetailsService;
import com.vteba.tx.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;

/**
 * 折旧明细表service实现
 * @author yinlei
 * date 2012-9-4 下午9:59:40
 */
@Named
public class DepreDetailsServiceImpl extends GenericServiceImpl<DepreDetails, String>
		implements IDepreDetailsService {
	private IDepreDetailsDao depreDetailsDaoImpl;
	
	public DepreDetailsServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<DepreDetails, String> depreDetailsDaoImpl) {
		this.hibernateGenericDaoImpl = depreDetailsDaoImpl;
		this.depreDetailsDaoImpl = (IDepreDetailsDao) depreDetailsDaoImpl;
	}

	public IDepreDetailsDao getDepreDetailsDaoImpl() {
		return depreDetailsDaoImpl;
	}

}
