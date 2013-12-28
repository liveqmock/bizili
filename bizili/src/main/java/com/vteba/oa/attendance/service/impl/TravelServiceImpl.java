package com.vteba.oa.attendance.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.oa.attendance.dao.ITravelDao;
import com.vteba.oa.attendance.model.Travel;
import com.vteba.oa.attendance.service.ITravelService;
import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;

/**
 * 出差单service实现
 * @author yinlei 
 * date 2012-7-6 下午9:29:00
 */
@Named
public class TravelServiceImpl extends GenericServiceImpl<Travel, String>
		implements ITravelService {
	
	private ITravelDao travelDaoImpl;
	
	public TravelServiceImpl(){
		super();
	}
	
	@Inject
	@Override
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<Travel, String> travelDaoImpl) {
		this.hibernateGenericDaoImpl = travelDaoImpl;
		this.travelDaoImpl = (ITravelDao) travelDaoImpl;
		
	}

	public ITravelDao getTravelDaoImpl() {
		return travelDaoImpl;
	}

}
