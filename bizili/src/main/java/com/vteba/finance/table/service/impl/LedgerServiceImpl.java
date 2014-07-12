package com.vteba.finance.table.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.finance.account.model.Subject;
import com.vteba.finance.account.service.ISubjectService;
import com.vteba.finance.table.dao.IAccountBalanceDao;
import com.vteba.finance.table.dao.IAccountSummaryDao;
import com.vteba.finance.table.dao.ILedgerDao;
import com.vteba.finance.table.model.AccountBalance;
import com.vteba.finance.table.model.Ledger;
import com.vteba.finance.table.service.ILedgerService;
import com.vteba.service.generic.impl.BaseServiceImpl;
import com.vteba.tx.hibernate.BaseGenericDao;
import com.vteba.utils.common.BigDecimalUtils;
import com.vteba.utils.date.DateUtils;
import com.vteba.utils.date.JodaTimeUtils;

/**
 * 总账service实现
 * @author yinlei 
 * date 2012-7-6 下午11:15:56
 */
@Named
public class LedgerServiceImpl extends BaseServiceImpl<Ledger, String> implements
		ILedgerService {
	
	private ILedgerDao ledgerDaoImpl;
	@Inject
	private IAccountSummaryDao accountSummaryDaoImpl;
	private ISubjectService subjectServiceImpl;
	@Inject
	private IAccountBalanceDao accountBalanceDaoImpl;
	
	public LedgerServiceImpl() {
		super();
	}

	public void autoGenerateLedgerTask() {
		String period = DateUtils.toDateString("yyyy-MM");
		//本期发生的一级会计科目，需要处理，期初，期末，本年累计
		StringBuilder oneHql = new StringBuilder("select new Ledger(c.accountPeriod,c.oneLevel,c.currency,c.summary,sum(c.debitAmount),sum(c.creditAmount)) ");
		oneHql.append(" from Certificate c where c.accountPeriod = ?1 ");
		oneHql.append(" group by c.oneLevel ");
		oneHql.append(" order by c.oneLevel asc ");
		List<Ledger> oneLedgerList = ledgerDaoImpl.getListByHql(oneHql.toString(), period);
		createLedger(oneLedgerList, period, 1);
		StringBuilder twoHql = new StringBuilder("select new Ledger(c.accountPeriod,c.twoLevel,c.currency,c.summary,sum(c.debitAmount),sum(c.creditAmount)) ");
		twoHql.append(" from Certificate c where c.accountPeriod = ?1 ");
		twoHql.append(" and c.twoLevel is not null ");
		twoHql.append(" group by c.twoLevel ");
		twoHql.append(" order by c.twoLevel asc ");
		List<Ledger> twoLedgerList = ledgerDaoImpl.getListByHql(twoHql.toString(), period);
		createLedger(twoLedgerList, period, 2);
	}
	
	public void createLedger(List<Ledger> ledgerList, String period, int level) {
		//String delHql = "delete from Ledger l where l.accountPeriod = ?1 and l.level = ?2 ";
		ledgerDaoImpl.deleteBatch("accountPeriod", period, "level", level);
		
		if (ledgerList != null && ledgerList.size() > 0) {
			for (Ledger bean : ledgerList) {
				//科目
				Subject sub = subjectServiceImpl.uniqueResult("subjectCode", bean.getSubjectCode());
				
				double startBalance = 0D;//期初余额
				//String startAccBalHql = " select b from AccountBalance b where b.subjectCode =?1 and b.accountPeriod = ?2 ";
				AccountBalance startAccBal = accountBalanceDaoImpl.uniqueResult("subjectCode", bean.getSubjectCode(), "accountPeriod", period);
				
				//----------期初余额----------//
				Ledger periodStart = new Ledger();
				periodStart.setSummary("期初余额");
				periodStart.setCreateDate(JodaTimeUtils.getFirstDayOfMonth(new Date()));
				
				if (sub.getBalanceDirection().equals(Subject.DIR_DEBIT)) {
					startBalance = BigDecimalUtils.subtract(startAccBal.getStartBalanceDebit(), startAccBal.getStartBalanceCredit());
				} else {
					startBalance = BigDecimalUtils.subtract(startAccBal.getStartBalanceCredit(), startAccBal.getStartBalanceDebit());
				}
				periodStart.setBalance(startBalance);//余额
				periodStart.setBalanceDirection(sub.getBalanceDirection());//余额方向
				if (startBalance == 0 || startBalance == -1) {
					periodStart.setBalanceDirection(Subject.DIR_PING);
				}
				periodStart.setSubjectCode(bean.getSubjectCode());
				periodStart.setSubjectName(sub.getSubjectName());
				periodStart.setAccountPeriod(period);
				periodStart.setLevel(level);
				ledgerDaoImpl.persist(periodStart);
				
				//---------本期合计--------//
				double balance = 0D;//本期余额
				if (sub.getBalanceDirection().equals(Subject.DIR_DEBIT)) {
					double temp = BigDecimalUtils.subtract(startBalance, bean.getCreditBalance());
					balance = BigDecimalUtils.add(temp, bean.getDebitBalance());
					bean.setBalance(balance);
					bean.setBalanceDirection(sub.getBalanceDirection());
				} else {
					double temp = BigDecimalUtils.subtract(startBalance, bean.getDebitBalance());
					balance = BigDecimalUtils.add(temp, bean.getCreditBalance());
					bean.setBalance(balance);
					bean.setBalanceDirection(sub.getBalanceDirection());
				}
				//余额为0，或者没有，方向为平
				if (balance == 0 || balance == -1) {
					bean.setBalanceDirection(Subject.DIR_PING);
				}
				bean.setSummary("本期合计");
				bean.setCreateDate(new Date());
				bean.setSubjectName(sub.getSubjectName());
				bean.setLevel(level);
				ledgerDaoImpl.persist(bean);
				
				//---------本年累计--------//
				Ledger thisYearSum = new Ledger();
				thisYearSum.setAccountPeriod(period);
				thisYearSum.setSubjectCode(sub.getSubjectCode());
				thisYearSum.setSubjectName(sub.getSubjectName());
				thisYearSum.setSummary("本年累计");
				thisYearSum.setBalanceDirection(sub.getBalanceDirection());
				if (JodaTimeUtils.isLastDayOfMonth(new Date())) {
					thisYearSum.setCreateDate(JodaTimeUtils.getLastDayOfMonth(new Date()));
				} else {
					thisYearSum.setCreateDate(new Date());
				}
				//汇总本年度某一会计科目借方和贷方金额
				String yearSumHql = " select sum(s.debit),sum(s.credit) from AccountSummary s where s.subjectCode = :subjectCode and s.accountPeriod between :periodStart and :periodEnd ";
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("subjectCode", sub.getSubjectCode());
				param.put("periodStart", period.substring(0, 5) + "01");
				param.put("periodEnd", period.substring(0, 5) + "12");
				List<Object[]> summary = accountSummaryDaoImpl.queryForObject(yearSumHql, param);
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
				ledgerDaoImpl.persist(thisYearSum);
			}
			
		}
		
	}

	//---------getter and setter--------//
	@Override
	@Inject
	public void setBaseGenericDaoImpl(
			BaseGenericDao<Ledger, String> ledgerDaoImpl) {
		this.baseGenericDaoImpl = ledgerDaoImpl;
		this.ledgerDaoImpl = (ILedgerDao) ledgerDaoImpl;
	}
	
	@Inject
	public void setSubjectServiceImpl(ISubjectService subjectServiceImpl) {
		this.subjectServiceImpl = subjectServiceImpl;
	}
	
}
