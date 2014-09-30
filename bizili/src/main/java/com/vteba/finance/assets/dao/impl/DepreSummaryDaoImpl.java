package com.vteba.finance.assets.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.common.constant.DataSourceConst;
import com.vteba.finance.assets.dao.IDepreSummaryDao;
import com.vteba.finance.assets.model.DepreSummary;
import com.vteba.tx.hibernate.impl.BaseGenericDaoImpl;
import com.vteba.service.tenant.annotation.Schema;

/**
 * 折旧汇总表DAO实现
 * @author yinlei
 * date 2012-9-4 下午9:45:43
 */
@Named
@Schema(schemaName = DataSourceConst.BIZILI)
public class DepreSummaryDaoImpl extends
		BaseGenericDaoImpl<DepreSummary, String> implements IDepreSummaryDao {

	public DepreSummaryDaoImpl() {
		super();
	}

	public DepreSummaryDaoImpl(Class<DepreSummary> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory biziliSessionFactory) {
		this.sessionFactory = biziliSessionFactory;
	}

}
