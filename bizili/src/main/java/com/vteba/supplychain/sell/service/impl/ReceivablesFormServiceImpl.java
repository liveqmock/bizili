package com.vteba.supplychain.sell.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;
import com.vteba.supplychain.sell.dao.IReceivablesFormDao;
import com.vteba.supplychain.sell.model.ReceivablesForm;
import com.vteba.supplychain.sell.service.IReceivablesFormService;

/**
 * 收款单service实现
 * @author yinlei 
 * date 2012-8-12 上午11:56:17
 */
@Named
public class ReceivablesFormServiceImpl extends GenericServiceImpl<ReceivablesForm, String>
		implements IReceivablesFormService {
	
	private IReceivablesFormDao receivablesFormDaoImpl;
	
	public ReceivablesFormServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<ReceivablesForm, String> receivablesFormDaoImpl) {
		this.hibernateGenericDaoImpl = receivablesFormDaoImpl;
		this.receivablesFormDaoImpl = (IReceivablesFormDao) receivablesFormDaoImpl;
	}

	public IReceivablesFormDao getReceivablesFormDaoImpl() {
		return receivablesFormDaoImpl;
	}

}
