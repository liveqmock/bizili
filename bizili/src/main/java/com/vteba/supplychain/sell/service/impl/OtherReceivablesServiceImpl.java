package com.vteba.supplychain.sell.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;
import com.vteba.supplychain.sell.dao.IOtherReceivablesDao;
import com.vteba.supplychain.sell.model.OtherReceivables;
import com.vteba.supplychain.sell.service.IOtherReceivablesService;

/**
 * 其他应收单Service实现
 * @author yinlei 
 * date 2012-8-12 上午11:53:44
 */
@Named
public class OtherReceivablesServiceImpl extends GenericServiceImpl<OtherReceivables, String>
		implements IOtherReceivablesService {
	
	private IOtherReceivablesDao otherReceivablesDaoImpl;
	
	public OtherReceivablesServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<OtherReceivables, String> otherReceivablesDaoImpl) {
		this.hibernateGenericDaoImpl = otherReceivablesDaoImpl;
		this.otherReceivablesDaoImpl = (IOtherReceivablesDao) otherReceivablesDaoImpl;
	}

	public IOtherReceivablesDao getOtherReceivablesDaoImpl() {
		return otherReceivablesDaoImpl;
	}

}
