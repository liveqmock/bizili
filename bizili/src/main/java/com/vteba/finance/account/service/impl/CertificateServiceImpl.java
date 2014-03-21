package com.vteba.finance.account.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.finance.account.dao.ICertificateDao;
import com.vteba.finance.account.model.Certificate;
import com.vteba.finance.account.service.ICertificateService;
import com.vteba.tm.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;

@Named
public class CertificateServiceImpl extends
		GenericServiceImpl<Certificate, String> implements ICertificateService {

	private ICertificateDao certificateDaoImpl;

	public CertificateServiceImpl() {
		super();
	}

	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<Certificate, String> certificateDaoImpl) {
		this.hibernateGenericDaoImpl = certificateDaoImpl;
		this.certificateDaoImpl = (ICertificateDao) certificateDaoImpl;
	}

	public ICertificateDao getCertificateDaoImpl() {
		return certificateDaoImpl;
	}
	
	public List<Certificate> getAllList(){
		return certificateDaoImpl.getAll(Certificate.class);
	}
}
