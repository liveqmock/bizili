package com.vteba.finance.account.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.vteba.common.constant.Sequence;
import com.vteba.finance.account.dao.ICertTotalDao;
import com.vteba.finance.account.model.CertTotal;
import com.vteba.finance.account.model.Certificate;
import com.vteba.finance.account.service.ICertTotalService;
import com.vteba.finance.account.service.ICertificateService;
import com.vteba.finance.currency.model.Currency;
import com.vteba.tx.hibernate.BaseGenericDao;
import com.vteba.security.spring.SecurityContextHolderUtils;
import com.vteba.service.generic.impl.BaseServiceImpl;
import com.vteba.user.model.EmpUser;
import com.vteba.utils.common.ObjectUtils;
import com.vteba.utils.date.DateUtils;

/**
 * 凭证明细汇总service实现
 * @author yinlei 
 * date 2012-6-14 下午2:44:26
 */
@Named
public class CertTotalServiceImpl extends BaseServiceImpl<CertTotal, String> implements
		ICertTotalService {
	
	private ICertTotalDao certTotalDaoImpl;
	private ICertificateService certificateServiceImpl;
	//private ISubjectService subjectServiceImpl;
	
	public CertTotalServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setBaseGenericDaoImpl(
			BaseGenericDao<CertTotal, String> certTotalDaoImpl) {
		this.baseGenericDaoImpl = certTotalDaoImpl;
		this.certTotalDaoImpl = (ICertTotalDao) certTotalDaoImpl;
	}
	
	@Inject
	public void setCertificateServiceImpl(ICertificateService certificateServiceImpl) {
		this.certificateServiceImpl = certificateServiceImpl;
	}
	
	//@Inject
	//public void setSubjectServiceImpl(ISubjectService subjectServiceImpl) {
		//this.subjectServiceImpl = subjectServiceImpl;
	//}

	public boolean saveCertAndDetail(CertTotal certTotal, List<Certificate> certList) {
		boolean ret = false;
		EmpUser user = (EmpUser) SecurityContextHolderUtils.getCurrentUserInfo();
		certTotal.setCreateEmp(user.getName());//创建人
		certTotal.setState(CertTotal.STATE_NEW);//凭证状态
		Long code = certTotalDaoImpl.getSequenceLongValue(Sequence.SEQ_CERT);
		certTotal.setCodeNo(certTotal.getCodeNo() + "-" + ObjectUtils.fillLeft(code.toString(), "0", 5));//凭证字号
		//会计期间
		String accountPeriod = DateUtils.toDateString("yyyy-MM");
		certTotal.setAccountPeriod(accountPeriod);//会计期间
		certTotalDaoImpl.save(certTotal);
		for (Certificate model : certList) {
			if (model != null) {
				//摘要、科目不能为空；借方金额、贷方金额至少一个不能为空
				if (StringUtils.isNotEmpty(model.getSummary())
						&& StringUtils.isNotEmpty(model.getSubjectId())
						&& (model.getDebitAmount() != 0 || model.getCreditAmount() != 0)) {
					model.setCreateTime(new Date());//创建时间
					model.setAccountPeriod(accountPeriod);//会计期间
					if (StringUtils.isEmpty(model.getCurrency())) {//币别
						model.setCurrency(Currency.CUR_TYPE_CN);
					}
					model.setEmpId(user.getName());//制单人
					model.setParentCert(certTotal);//明细汇总对象
					if (model.getSubjectId().length() == 4) {
						model.setOneLevel(model.getSubjectId());
					} else if (model.getSubjectId().length() == 6) {
						model.setOneLevel(model.getSubjectId().substring(0, 4));
						model.setTwoLevel(model.getSubjectId());
					} else if (model.getSubjectId().length() == 8) {
						model.setOneLevel(model.getSubjectId().substring(0, 4));
						model.setTwoLevel(model.getSubjectId().substring(0, 6));
						model.setThreeLevel(model.getSubjectId());
					}
					//Subject sub = subjectServiceImpl.getUniqueResultByProperty(Subject.class, "subjectCode", model.getSubjectId());
					certificateServiceImpl.save(model);
				}
			}
		}
		ret = true;
		return ret;
	}
}
