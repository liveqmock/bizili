package com.vteba.oa.cost.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.oa.cost.dao.ITravelFeeDao;
import com.vteba.oa.cost.model.TravelFee;
import com.vteba.oa.cost.service.ITravelFeeService;
import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;

/**
 * 旅费service实现
 * @author yinlei 
 * date 2012-7-6 下午10:15:49
 */
@Named
public class TravelFeeServiceImpl extends GenericServiceImpl<TravelFee, String> implements
		ITravelFeeService {
	
	private ITravelFeeDao travelFeeDaoImpl;
	
	public TravelFeeServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<TravelFee, String> travelFeeDaoImpl) {
		this.hibernateGenericDaoImpl = travelFeeDaoImpl;
		this.travelFeeDaoImpl = (ITravelFeeDao) travelFeeDaoImpl;
	}

	public ITravelFeeDao getTravelFeeDaoImpl() {
		return travelFeeDaoImpl;
	}

}
