package com.vteba.finance.assets.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.finance.assets.dao.IDepreSummaryDao;
import com.vteba.finance.assets.model.DepreSummary;
import com.vteba.finance.assets.service.IDepreSummaryService;
import com.vteba.persister.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;

/**
 * 折旧汇总表service实现
 * @author yinlei
 * date 2012-9-4 下午10:02:25
 */
@Named
public class DepreSummaryServiceImpl extends GenericServiceImpl<DepreSummary, String>
		implements IDepreSummaryService {
	private IDepreSummaryDao depreSummaryDaoImpl;
	
	public DepreSummaryServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<DepreSummary, String> depreSummaryDaoImpl) {
		this.hibernateGenericDaoImpl = depreSummaryDaoImpl;
		this.depreSummaryDaoImpl = (IDepreSummaryDao) depreSummaryDaoImpl;
	}

	public IDepreSummaryDao getDepreSummaryDaoImpl() {
		return depreSummaryDaoImpl;
	}

}
