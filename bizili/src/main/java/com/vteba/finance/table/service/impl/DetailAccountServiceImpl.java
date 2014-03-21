package com.vteba.finance.table.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.finance.account.model.Subject;
import com.vteba.finance.account.service.ISubjectService;
import com.vteba.finance.table.dao.IDetailAccountDao;
import com.vteba.finance.table.model.AccountBalance;
import com.vteba.finance.table.model.DetailAccount;
import com.vteba.finance.table.service.IAccountBalanceService;
import com.vteba.finance.table.service.IAccountSummaryService;
import com.vteba.finance.table.service.IDetailAccountService;
import com.vteba.tm.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;
import com.vteba.util.common.BigDecimalUtils;
import com.vteba.util.common.ObjectUtils;
import com.vteba.util.date.JodaTimeUtils;

/**
 * 明细账service实现
 * @author yinlei 
 * date 2012-7-6 下午11:14:23
 */
@Named
public class DetailAccountServiceImpl extends GenericServiceImpl<DetailAccount, String>
		implements IDetailAccountService {
	
	private IDetailAccountDao detailAccountDaoImpl;
	private IAccountBalanceService accountBalanceServiceImpl;
	private ISubjectService subjectServiceImpl;
	private IAccountSummaryService accountSummaryServiceImpl;
	
	public DetailAccountServiceImpl() {
		super();
	}

	public void autoGenerateDetailAccountTask() {
		//1、统计出所有的科目，根据每个科目按顺序查找，计算余额和方向
		//2、构造每个科目的期初余额
		//3、根据查询的结果，保存期间发生额
		//4、构造期末余额，本年累计额
		//5、对于没有审核的凭证，如果更改、删除或者作废凭证，要更新这里的数值
		
		String period = ObjectUtils.toDateString("yyyy-MM");//会计期间
		
		//删除原有数据
		String delHql = " delete from DetailAccount d where d.accountPeriod = ?1 ";
		detailAccountDaoImpl.executeHqlUpdate(delHql, false, period);
		
		//本期发生的一级会计科目，需要处理，期初，本期，期末，本年累计
		String oneHql = " select distinct c.oneLevel from Certificate c where c.accountPeriod = ?1 ";
		List<String> subjectList = detailAccountDaoImpl.hqlQueryForList(oneHql, String.class, period);
		createDetailAccount(subjectList, period, 1);
		
		//本期发生的二级会计科目
		String twoHql = " select distinct c.twoLevel from Certificate c where c.accountPeriod = ?1 and c.twoLevel is not null ";
		List<String> twoSubjectList = detailAccountDaoImpl.hqlQueryForList(twoHql, String.class, period);
		createDetailAccount(twoSubjectList, period, 2);
	}
	
	public void createDetailAccount(List<String> subjectList, String period, Integer level) {
		if (subjectList != null && subjectList.size() > 0) {
			for (String code : subjectList) {
				Subject sub = subjectServiceImpl.uniqueResultByCriteria(Subject.class, "subjectCode", code);
				//查询该科目及子科目的凭证
				StringBuilder hql = new StringBuilder(" select new DetailAccount(t.accountPeriod,c.subjectId,c.subjectName,c.currency,t.createDate,t.codeNo,c.summary,c.debitAmount,c.creditAmount) ");
				hql.append(" from CertTotal t join t.childCerts c ");
				hql.append(" where t.accountPeriod = ?1 ");
				hql.append(" and c.subjectId like ?2 ");
				hql.append(" order by t.createDate asc, t.codeNo asc, c.subjectId asc ");
				List<DetailAccount> detailAccountList = detailAccountDaoImpl.getEntityListByHql(hql.toString(), period, ObjectUtils.sqlLike(code));
				if (detailAccountList != null && detailAccountList.size() > 0) {
					//----------科目期初余额----------//
					DetailAccount account = new DetailAccount();
					account.setSummary("期初余额");
					account.setCreateDate(JodaTimeUtils.getFirstDayOfMonth(new Date()));
					double parentBalance = 0D;//期初余额
					String parentAccBalHql = " select b from AccountBalance b where b.subjectCode =?1 and b.accountPeriod = ?2 ";
					AccountBalance parentAccBal = accountBalanceServiceImpl.uniqueResultByHql(parentAccBalHql, false, code, period);//父级科目余额
					if (sub.getBalanceDirection().equals(Subject.DIR_DEBIT)) {
						parentBalance = BigDecimalUtils.subtract(parentAccBal.getStartBalanceDebit(), parentAccBal.getStartBalanceCredit());
					} else {
						parentBalance = BigDecimalUtils.subtract(parentAccBal.getStartBalanceCredit(), parentAccBal.getStartBalanceDebit());
					}
					account.setBalance(parentBalance);
					if (parentBalance == 0 || parentBalance == -1) {
						account.setBalanceDirection(Subject.DIR_PING);
					} else {
						account.setBalanceDirection(sub.getBalanceDirection());
					}
					account.setSubjectCode(code);
					account.setSubjectName(sub.getSubjectName());
					account.setAccountPeriod(period);
					account.setLevel(level);
					detailAccountDaoImpl.persist(account);
					
					double debitSum = 0D;//本期借方合计
					double creditSum = 0D;//本期贷方合计
					double balance = 0D;//用来累计合并的余额
					boolean first = true;//不用计算余额了
					for (DetailAccount bean : detailAccountList) {
						debitSum = BigDecimalUtils.add(debitSum, bean.getDebitBalance());
						creditSum = BigDecimalUtils.add(creditSum, bean.getCreditBalance());
						//会计科目，不需要具体的科目，使用父科目的方向
						//Subject subject = subjectServiceImpl.getUniqueResultByProperty(Subject.class, "subjectCode", bean.getSubjectCode());
						
						//科目期初余额，用于计算后续每个凭证发生后的余额
						if (first) {
							String accBalHql = " select b from AccountBalance b where b.subjectCode =?1 and b.accountPeriod = ?2 ";
							//AccountBalance accBal = accountBalanceServiceImpl.uniqueResultByHql(accBalHql, false, subject.getSubjectCode(),period);
							AccountBalance accBal = accountBalanceServiceImpl.uniqueResultByHql(accBalHql, false, bean.getSubjectCode(), period);
							if (sub.getBalanceDirection().equals(Subject.DIR_DEBIT)) {//余额在借方
								balance = BigDecimalUtils.subtract(accBal.getStartBalanceDebit(), accBal.getStartBalanceCredit());
							} else {//余额在贷方
								balance = BigDecimalUtils.subtract(accBal.getStartBalanceCredit(), accBal.getStartBalanceDebit());
							}
							first = false;
						}
						
						//--------期间发生额--------//
						if (sub.getBalanceDirection().equals(Subject.DIR_DEBIT)) {
							double temp = BigDecimalUtils.subtract(balance, bean.getCreditBalance());
							balance = BigDecimalUtils.add(temp, bean.getDebitBalance());
							bean.setBalance(balance);
							bean.setBalanceDirection(sub.getBalanceDirection());
						} else {
							double temp = BigDecimalUtils.subtract(balance, bean.getDebitBalance());
							balance = BigDecimalUtils.add(temp, bean.getCreditBalance());
							bean.setBalance(balance);
							bean.setBalanceDirection(sub.getBalanceDirection());
						}
						//余额为0，或者没有，方向为平
						if (balance == 0 || balance == -1) {
							bean.setBalanceDirection(Subject.DIR_PING);
						}
						
						bean.setSubjectCode(code);//重写此处的科目代码，查询时不用再like了
						bean.setLevel(level);
						detailAccountDaoImpl.persist(bean);
					}
					
					//---------科目期末合计汇总--------//
					DetailAccount thisPeriodSum = new DetailAccount();//本期合计
					thisPeriodSum.setAccountPeriod(period);
					thisPeriodSum.setSubjectName(sub.getSubjectName());
					thisPeriodSum.setSubjectCode(sub.getSubjectCode());
					thisPeriodSum.setSummary("本期合计");
					thisPeriodSum.setBalanceDirection(sub.getBalanceDirection());//子科目和父科目的余额方向是一致的，所以可以这样用
					thisPeriodSum.setCreateDate(JodaTimeUtils.getLastDayOfMonth(new Date()));
					thisPeriodSum.setCreditBalance(creditSum);
					thisPeriodSum.setDebitBalance(debitSum);
					if (sub.getBalanceDirection().equals(Subject.DIR_DEBIT)) {
						thisPeriodSum.setBalance(BigDecimalUtils.subtract(debitSum, creditSum));
					} else {
						thisPeriodSum.setBalance(BigDecimalUtils.subtract(creditSum, debitSum));
					}
					thisPeriodSum.setLevel(level);
					detailAccountDaoImpl.persist(thisPeriodSum);
					
					DetailAccount thisYearSum = new DetailAccount();//本年累计
					thisYearSum.setAccountPeriod(period);
					thisYearSum.setSubjectCode(sub.getSubjectCode());
					thisYearSum.setSubjectName(sub.getSubjectName());
					thisYearSum.setSummary("本年累计");
					thisYearSum.setBalanceDirection(sub.getBalanceDirection());
					thisYearSum.setCreateDate(JodaTimeUtils.getLastDayOfMonth(new Date()));
					
					//汇总本年度某一会计科目借方和贷方金额
					String yearSumHql = " select sum(s.debit),sum(s.credit) from AccountSummary s where s.subjectCode = :subjectCode and s.accountPeriod between :periodStart and :periodEnd ";
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("subjectCode", sub.getSubjectCode());
					param.put("periodStart", period.substring(0, 5) + "01");
					param.put("periodEnd", period.substring(0, 5) + "12");
					List<Object[]> summary = accountSummaryServiceImpl.hqlQueryForObject(yearSumHql, false, param);
					if (summary != null && summary.size() == 1) {
						Double debit = (Double) summary.get(0)[0];
						thisYearSum.setDebitBalance(debit);
						Double credit = (Double) summary.get(0)[1];
						thisYearSum.setCreditBalance(credit);
						if (sub.getBalanceDirection().equals(Subject.DIR_DEBIT)) {//余额在借方
							thisYearSum.setBalance(BigDecimalUtils.subtract(debit, credit));
						} else {//余额在贷方
							thisYearSum.setBalance(BigDecimalUtils.subtract(credit, debit));
						}
					}
					thisYearSum.setLevel(level);
					detailAccountDaoImpl.persist(thisYearSum);
				}
			}
		}
	}
	
	//---------getter and setter--------//
	
	@Override
	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<DetailAccount, String> detailAccountDaoImpl) {
		this.hibernateGenericDaoImpl = detailAccountDaoImpl;
		this.detailAccountDaoImpl = (IDetailAccountDao) detailAccountDaoImpl;
	}
	
	@Inject
	public void setAccountBalanceServiceImpl(
			IAccountBalanceService accountBalanceServiceImpl) {
		this.accountBalanceServiceImpl = accountBalanceServiceImpl;
	}
	
	@Inject
	public void setSubjectServiceImpl(ISubjectService subjectServiceImpl) {
		this.subjectServiceImpl = subjectServiceImpl;
	}
	
	@Inject
	public void setAccountSummaryServiceImpl(
			IAccountSummaryService accountSummaryServiceImpl) {
		this.accountSummaryServiceImpl = accountSummaryServiceImpl;
	}
}
