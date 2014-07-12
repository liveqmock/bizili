package com.vteba.finance.account.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.finance.account.dao.ICertificateDao;
import com.vteba.finance.account.model.Certificate;
import com.vteba.finance.account.service.ICertificateService;
import com.vteba.tx.hibernate.BaseGenericDao;
import com.vteba.service.generic.impl.BaseServiceImpl;

@Named
public class CertificateServiceImpl extends
		BaseServiceImpl<Certificate, String> implements ICertificateService {

	private ICertificateDao certificateDaoImpl;

	public CertificateServiceImpl() {
		super();
	}

	@Inject
	public void setBaseGenericDaoImpl(
			BaseGenericDao<Certificate, String> certificateDaoImpl) {
		this.baseGenericDaoImpl = certificateDaoImpl;
		this.certificateDaoImpl = (ICertificateDao) certificateDaoImpl;
	}

	public ICertificateDao getCertificateDaoImpl() {
		return certificateDaoImpl;
	}
	
	public List<Certificate> getAllList(){
		return certificateDaoImpl.getAll();
	}
}
