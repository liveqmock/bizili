package com.vteba.oa.cost.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.oa.cost.dao.IPrepaymentDao;
import com.vteba.oa.cost.model.Prepayment;
import com.vteba.oa.cost.service.IPrepaymentService;
import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;

/**
 * 预付款service实现
 * @author yinlei 
 * date 2012-7-6 下午10:08:45
 */
@Named
public class PrepaymentServiceImpl extends GenericServiceImpl<Prepayment, String> implements
		IPrepaymentService {
	
	private IPrepaymentDao prepaymentDaoImpl;

	public PrepaymentServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<Prepayment, String> prepaymentDaoImpl) {
		this.hibernateGenericDaoImpl = prepaymentDaoImpl;
		this.prepaymentDaoImpl = (IPrepaymentDao) prepaymentDaoImpl;
	}

	public IPrepaymentDao getPrepaymentDaoImpl() {
		return prepaymentDaoImpl;
	}

}
