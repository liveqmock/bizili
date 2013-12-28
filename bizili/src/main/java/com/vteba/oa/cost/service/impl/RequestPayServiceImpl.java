package com.vteba.oa.cost.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.oa.cost.dao.IRequestPayDao;
import com.vteba.oa.cost.model.RequestPay;
import com.vteba.oa.cost.service.IRequestPayService;
import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;

/**
 * 请款单service实现
 * @author yinlei 
 * date 2012-7-6 下午10:11:42
 */
@Named
public class RequestPayServiceImpl extends GenericServiceImpl<RequestPay, String> implements
		IRequestPayService {
	
	private IRequestPayDao requestPayDaoImpl;
	
	public RequestPayServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<RequestPay, String> requestPayDaoImpl) {
		this.hibernateGenericDaoImpl = requestPayDaoImpl;
		this.requestPayDaoImpl = (IRequestPayDao) requestPayDaoImpl;
	}

	public IRequestPayDao getRequestPayDaoImpl() {
		return requestPayDaoImpl;
	}

}
