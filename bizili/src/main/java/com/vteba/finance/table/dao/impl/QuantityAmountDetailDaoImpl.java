package com.vteba.finance.table.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.finance.table.dao.IQuantityAmountDetailDao;
import com.vteba.finance.table.model.QuantityAmountDetail;
import com.vteba.persister.hibernate.impl.HibernateGenericDaoImpl;

/**
 * 数量金额明细账DAO实现
 * @author yinlei 
 * date 2012-7-6 下午10:47:06
 */
@Named
public class QuantityAmountDetailDaoImpl extends HibernateGenericDaoImpl<QuantityAmountDetail, String>
		implements IQuantityAmountDetailDao {

	public QuantityAmountDetailDaoImpl() {
		super();
	}

	public QuantityAmountDetailDaoImpl(Class<QuantityAmountDetail> entityClass) {
		super(entityClass);
	}

	@Override
	@Inject
	public void setSessionFactory(SessionFactory biziliSessionFactory) {
		this.sessionFactory = biziliSessionFactory;
	}

}
