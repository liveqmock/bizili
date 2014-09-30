package com.vteba.finance.assets.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.common.constant.DataSourceConst;
import com.vteba.finance.assets.dao.IDepreDetailsDao;
import com.vteba.finance.assets.model.DepreDetails;
import com.vteba.tx.hibernate.impl.BaseGenericDaoImpl;
import com.vteba.service.tenant.annotation.Schema;

/**
 * 折旧明细表DAO实现
 * @author yinlei
 * date 2012-9-4 下午9:42:54
 */
@Named
@Schema(schemaName = DataSourceConst.BIZILI)
public class DepreDetailsDaoImpl extends
		BaseGenericDaoImpl<DepreDetails, String> implements IDepreDetailsDao {

	public DepreDetailsDaoImpl() {
		super();
	}

	public DepreDetailsDaoImpl(Class<DepreDetails> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory biziliSessionFactory) {
		this.sessionFactory = biziliSessionFactory;
	}

}
