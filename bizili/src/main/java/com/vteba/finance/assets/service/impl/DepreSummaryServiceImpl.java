package com.vteba.finance.assets.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.finance.assets.dao.IDepreSummaryDao;
import com.vteba.finance.assets.model.DepreSummary;
import com.vteba.finance.assets.service.IDepreSummaryService;
import com.vteba.tx.hibernate.BaseGenericDao;
import com.vteba.service.generic.impl.BaseServiceImpl;

/**
 * 折旧汇总表service实现
 * @author yinlei
 * date 2012-9-4 下午10:02:25
 */
@Named
public class DepreSummaryServiceImpl extends BaseServiceImpl<DepreSummary, String>
		implements IDepreSummaryService {
	private IDepreSummaryDao depreSummaryDaoImpl;
	
	public DepreSummaryServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setBaseGenericDaoImpl(
			BaseGenericDao<DepreSummary, String> depreSummaryDaoImpl) {
		this.baseGenericDaoImpl = depreSummaryDaoImpl;
		this.depreSummaryDaoImpl = (IDepreSummaryDao) depreSummaryDaoImpl;
	}

	public IDepreSummaryDao getDepreSummaryDaoImpl() {
		return depreSummaryDaoImpl;
	}

}
