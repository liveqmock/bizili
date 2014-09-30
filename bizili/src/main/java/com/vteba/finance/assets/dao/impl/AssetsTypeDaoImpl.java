package com.vteba.finance.assets.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.common.constant.DataSourceConst;
import com.vteba.finance.assets.dao.IAssetsTypeDao;
import com.vteba.finance.assets.model.AssetsType;
import com.vteba.tx.hibernate.impl.BaseGenericDaoImpl;
import com.vteba.service.tenant.annotation.Schema;

/**
 * 固定资产分类DAO实现
 * @author yinlei
 * date 2012-9-4 下午9:32:34
 */
@Named
@Schema(schemaName = DataSourceConst.BIZILI)
public class AssetsTypeDaoImpl extends
		BaseGenericDaoImpl<AssetsType, String> implements IAssetsTypeDao {

	public AssetsTypeDaoImpl() {
		super();
	}

	public AssetsTypeDaoImpl(Class<AssetsType> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory biziliSessionFactory) {
		this.sessionFactory = biziliSessionFactory;
	}

}
