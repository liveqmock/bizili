package com.vteba.finance.account.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.finance.account.dao.ICertificateDao;
import com.vteba.finance.account.model.Certificate;
import com.vteba.tx.hibernate.impl.HibernateGenericDaoImpl;

@Named
public class CertificateDaoImpl extends HibernateGenericDaoImpl<Certificate, String>
		implements ICertificateDao {

	public CertificateDaoImpl() {
		super();
	}

	public CertificateDaoImpl(Class<Certificate> entityClass) {
		super(entityClass);
	}

	@Inject
	public void setSessionFactory(SessionFactory biziliSessionFactory) {
		this.sessionFactory = biziliSessionFactory;
	}

}
