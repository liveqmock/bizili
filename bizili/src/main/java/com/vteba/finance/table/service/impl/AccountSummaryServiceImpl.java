package com.vteba.finance.table.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.common.constant.CommonConst;
import com.vteba.finance.account.model.Certificate;
import com.vteba.finance.account.model.Subject;
import com.vteba.finance.account.service.ICertificateService;
import com.vteba.finance.account.service.ISubjectService;
import com.vteba.finance.currency.model.Currency;
import com.vteba.finance.table.dao.IAccountSummaryDao;
import com.vteba.finance.table.model.AccountSummary;
import com.vteba.finance.table.service.IAccountSummaryService;
import com.vteba.service.generic.impl.GenericServiceImpl;
import com.vteba.tx.hibernate.IHibernateGenericDao;
import com.vteba.utils.date.DateUtils;
import com.vteba.utils.ofbiz.LangUtils;

/**
 * 凭证汇总表service实现
 * @author yinlei 
 * date 2012-7-9 下午9:09:25
 */
@Named
public class AccountSummaryServiceImpl extends GenericServiceImpl<AccountSummary, String>
		implements IAccountSummaryService {
	
	private IAccountSummaryDao accountSummaryDaoImpl;
	private ICertificateService certificateServiceImpl;
	private ISubjectService subjectServiceImpl;
	
	public AccountSummaryServiceImpl() {
		super();
	}

	@Override
	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<AccountSummary, String> accountSummaryDaoImpl) {
		this.hibernateGenericDaoImpl = accountSummaryDaoImpl;
		this.accountSummaryDaoImpl = (IAccountSummaryDao) accountSummaryDaoImpl;
	}

	public void autoGenerateAccountSummaryTask() {
		createAccountSummary();
	}
	
	public void createAccountSummary() {
		//因为hql不支持union，又不想使用sql(跨平台)
		String onehql = "select oneLevel, sum(c.creditAmount), sum(c.debitAmount),1 from Certificate c where c.accountPeriod = ?1 group by oneLevel ";
		String twohql = "select twoLevel, sum(c.creditAmount), sum(c.debitAmount),2 from Certificate c where c.accountPeriod = ?1 and c.twoLevel is not null group by twoLevel ";
		String thrhql = "select threeLevel,sum(c.creditAmount),sum(c.debitAmount),3 from Certificate c where c.accountPeriod = ?1 and c.threeLevel is not null group by threeLevel";
		
		String period = DateUtils.toDateString("yyyy-MM");//会计期间
		List<Object[]> oneList = certificateServiceImpl.hqlQueryForObject(onehql, false, period);
		List<Object[]> twoList = certificateServiceImpl.hqlQueryForObject(twohql, false, period);
		List<Object[]> thrList = certificateServiceImpl.hqlQueryForObject(thrhql, false, period);
		
		//将三次的查询结果放在一起
		List<Object[]> list = new ArrayList<Object[]>();
		list.addAll(oneList);
		list.addAll(twoList);
		list.addAll(thrList);
		//String hql = "select s from AccountSummary s where s.subjectCode = ?1 and s.accountPeriod = ?2 ";
		for (Object[] obj : list) {
			String code = (String)obj[0];
			Double debit = (Double)obj[2];
			Double credit = (Double)obj[1];
			Integer level = (Integer)obj[3];//科目级别
			
			AccountSummary summary = null;
			summary = accountSummaryDaoImpl.uniqueResult(LangUtils.toMap("accountPeriod", period, "subjectCode", code));
			if (summary != null) {//如果已经有了，更新借方和贷方金额即可
				summary.setCredit(credit);
				summary.setDebit(debit);
				accountSummaryDaoImpl.saveOrUpdate(summary);
			} else {//还没有，创建
				summary = new AccountSummary();
				//获得科目信息
				Subject subject = subjectServiceImpl.uniqueResultByHql("subject.querySubByCode" , true, code);
				summary.setSubjectCode(subject.getSubjectCode());
				summary.setSubjectName(subject.getSubjectName());
				summary.setAccountPeriod(period);
				summary.setCredit(credit);
				summary.setDebit(debit);
				summary.setCreateDate(new Date());
				summary.setCreateUser(CommonConst.SYSTEM);
				summary.setCurrency(Currency.CUR_TYPE_CN);
				summary.setLevel(level);
				accountSummaryDaoImpl.persist(summary);
			}
		}				
	}
	
	public boolean updateAccountSummary(List<Certificate> certList) {
		
		return true;
	}
	
	@Inject
	public void setCertificateServiceImpl(ICertificateService certificateServiceImpl) {
		this.certificateServiceImpl = certificateServiceImpl;
	}
	
	@Inject
	public void setSubjectServiceImpl(ISubjectService subjectServiceImpl) {
		this.subjectServiceImpl = subjectServiceImpl;
	}
}
