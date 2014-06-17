package com.vteba.finance.table.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.vteba.finance.account.model.Subject;
import com.vteba.finance.account.service.IAccountPeriodService;
import com.vteba.finance.account.service.ICertificateService;
import com.vteba.finance.account.service.ISubjectService;
import com.vteba.finance.table.dao.IAccountBalanceDao;
import com.vteba.finance.table.model.AccountBalance;
import com.vteba.finance.table.service.IAccountBalanceService;
import com.vteba.tx.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;
import com.vteba.utils.common.BigDecimalUtils;

/**
 * 科目余额表service实现
 * @author yinlei 
 * date 2012-6-12 下午3:47:42
 */
@Named
public class AccountBalanceServiceImpl extends GenericServiceImpl<AccountBalance, String>
		implements IAccountBalanceService {

	private IAccountBalanceDao accountBalanceDaoImpl;
	private ICertificateService certificateServiceImpl;
	private ISubjectService subjectServiceImpl;
	private IAccountPeriodService accountPeriodServiceImpl;
	
	public AccountBalanceServiceImpl() {
		super();
	}

	public void autoGenerateAccountBalanceTask() {
		createAccountBalance(AccountBalance.STATE_TASK);
	}
	
	public void createAccountBalance(int type) {
		StringBuilder currentSql = new StringBuilder();
		currentSql.append(" select c1.one_level, sum(c1.credit_amount),sum(c1.debit_amount) from certificate c1 where c1.account_period = ? group by c1.one_level ");
		currentSql.append(" union ");
		currentSql.append(" select c2.two_level, sum(c2.credit_amount),sum(c2.debit_amount) from certificate c2 where c2.account_period = ? and two_level is not null group by c2.two_level ");
		String period = accountPeriodServiceImpl.getCurrentPeriod();//ObjectUtils.toDateString("yyyy-MM");//会计期间
		//本期借方和贷方发生额
		List<Object[]> currentList = certificateServiceImpl.sqlQueryForObject(currentSql.toString(), period, period);
		
		StringBuilder sql = new StringBuilder();
		sql.append(" select c1.one_level, sum(c1.credit_amount),sum(c1.debit_amount) from certificate c1 where c1.account_period between ? and ? group by c1.one_level ");
		sql.append(" union ");
		sql.append(" select c2.two_level, sum(c2.credit_amount),sum(c2.debit_amount) from certificate c2 where c2.account_period between ? and ? and two_level is not null group by c2.two_level ");
		String year = period.substring(0, 4);//ObjectUtils.toDateString("yyyy");
		String start = year + "-01";
		String end = year + "-12";
		//本年借方和贷方累计发生额
		List<Object[]> yearList = certificateServiceImpl.sqlQueryForObject(sql.toString(), start, end, start, end);
		
		List<AccountBalance> balanceList = new ArrayList<AccountBalance>();
		List<String> list = new ArrayList<String>();//存放科目代码，供in()查询
		for (Object[] obj : yearList) {
			list.add(obj[0].toString());
		}
		if (list.size() > 0) {
			String hql = " select ab from AccountBalance ab where ab.subjectCode in (?1) and ab.accountPeriod = ?2 ";
			//期初借方和贷方余额
			balanceList = accountBalanceDaoImpl.getEntityListByHql(hql,list, period);
		}
		
		if (yearList.size() > 0) {
			
			for (Object[] bean : yearList) {//本年累计发生额
				String subjectCode = (String)bean[0];//科目代码
				BigDecimal currentDebit = null;//本期借方发生额
				BigDecimal currentCredit = null;//本期贷方发生额
				BigDecimal yearDebit = null;//本年累计借方发生额
				BigDecimal yearCredit = null;//本年累计贷方发生额
				
				AccountBalance balance = null;//要保存的对象
				Double endDebit = 0D;//期末借方余额
				Double endCredit = 0D;//期末贷方余额
				boolean hasStartBalance = false;//是否有期初余额，有的话，更新；没有，新建
				//期初余额
				for (AccountBalance bal : balanceList) {
					if (bal.getSubjectCode().equals(subjectCode)) {//有期初余额，更新原有的entity
						hasStartBalance = true;
						balance = bal;//将该实体，赋值给要保存的变量
					}
				}
				if (!hasStartBalance) {//没有，新建一个
					balance = new AccountBalance();
					balance.setStartBalanceCredit(0D);//初始化期初余额为0
					balance.setStartBalanceDebit(0D);
				}
				//本期发生额
				for (Object[] currentBean : currentList) {
					if (subjectCode.equals(currentBean[0])) {//同一个会计科目
						currentCredit = (BigDecimal)currentBean[1];
						currentDebit = (BigDecimal)currentBean[2];
						balance.setCurrenceBalanceCredit(currentCredit.doubleValue());//本期贷方余额
						balance.setCurrenceBalanceDebit(currentDebit.doubleValue());//本期借方余额
					}
				}
				
				//本年累计发生额
				yearCredit = (BigDecimal) bean[1];//本年贷方累计
				yearDebit = (BigDecimal) bean[2];//本年借方累计
				balance.setYearSumBalanceCredit(yearCredit.doubleValue());
				balance.setYearSumBalanceDebit(yearDebit.doubleValue());
				
				//会计科目相关
				Subject sub = subjectServiceImpl.uniqueResultByCriteria(Subject.class, "subjectCode", subjectCode);//这里有缓存，无影响
				balance.setAccountPeriod(period);
				balance.setSubjectCode(subjectCode);
				balance.setSubjectName(sub.getSubjectName());
				balance.setLevel(sub.getLevel());
				balance.setState(type);
				
				//期末余额
				if (sub.getBalanceDirection().equals(Subject.DIR_DEBIT)) {//科目余额在借方
					Double debit = BigDecimalUtils.add(balance.getStartBalanceDebit(), balance.getCurrenceBalanceDebit());
					endDebit = BigDecimalUtils.subtract(debit, balance.getCurrenceBalanceCredit());
					if (endDebit > 0) {
						balance.setBalanceDirection(Subject.DIR_DEBIT);
					} else if (endDebit < 0) {
						balance.setBalanceDirection(Subject.DIR_CREDIT);
					} else {
						balance.setBalanceDirection(Subject.DIR_PING);
					}
				} else {//科目余额在贷方
					Double credit = BigDecimalUtils.add(balance.getStartBalanceCredit(), balance.getCurrenceBalanceCredit());
					endCredit = BigDecimalUtils.subtract(credit, balance.getCurrenceBalanceDebit());
					if (endCredit < 0) {
						balance.setBalanceDirection(Subject.DIR_DEBIT);
					} else if (endCredit > 0) {
						balance.setBalanceDirection(Subject.DIR_CREDIT);
					} else {
						balance.setBalanceDirection(Subject.DIR_PING);
					}
				}
				balance.setEndBalanceCredit(endCredit);
				balance.setEndBalanceDebit(endDebit);
				
				accountBalanceDaoImpl.saveOrUpdate(balance);
			}
		}
	}
	
	public double queryAccountBalanceByCode(String subjectCode) {
		if (StringUtils.isBlank(subjectCode)) {
			return -1D;
		}
		StringBuilder hql = new StringBuilder(" select ab from AccountBalance ab ");
		hql.append(" where ab.subjectCode = :subjectCode ");
		hql.append(" and ab.accountPeriod = :accountPeriod ");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("subjectCode", subjectCode);
		String period = accountPeriodServiceImpl.getCurrentPeriod();//当前会计期间
		param.put("accountPeriod", period);
		AccountBalance bean = accountBalanceDaoImpl.uniqueResultByHql(hql.toString(), false, param);
		double balance = 0D;
		if (bean != null) {
			//借方汇总
			double debitSum = BigDecimalUtils.add(bean.getStartBalanceDebit(), bean.getCurrenceBalanceDebit());
			//贷方汇总
			double creditSum = BigDecimalUtils.add(bean.getStartBalanceCredit(), bean.getCurrenceBalanceCredit());
			if (bean.getBalanceDirection().equals(Subject.DIR_DEBIT)) {//余额方向在借方
				balance = BigDecimalUtils.subtract(debitSum, creditSum);
			} else if (bean.getBalanceDirection().equals(Subject.DIR_CREDIT)) {//余额方向在贷方
				balance = BigDecimalUtils.subtract(creditSum, debitSum);
			}
		}
		return balance;
	}
	
	public Double calculateItemBalance(String period, String itemCode) {
		String[] codes = StringUtils.split(itemCode, "#");
		Double balance = 0D;//该项目的金额，初始为0
		String balanceHql = "select ab from AccountBalance ab where ab.accountPeriod = ?1 and ab.subjectCode = ?2 ";
		
		for (String code : codes) {
			String symbol = code.substring(0, 1);//符号
			String subjectCode = code.substring(1);//科目代码
			AccountBalance model = accountBalanceDaoImpl.uniqueResultByHql(balanceHql, false, period, subjectCode);
			if (model != null) {
				if (symbol.equals("+")) {//相加
					if (model.getBalanceDirection().equals(Subject.DIR_DEBIT)) {//余额在借方
						balance = BigDecimalUtils.add(balance, model.getEndBalanceDebit());
					} else if (model.getBalanceDirection().equals(Subject.DIR_CREDIT)) {//余额在贷方
						balance = BigDecimalUtils.add(balance, model.getEndBalanceCredit());
					}
				} else if (symbol.equals("-")){//相减
					if (model.getBalanceDirection().equals(Subject.DIR_DEBIT)) {//余额在借方
						balance = BigDecimalUtils.subtract(balance, model.getEndBalanceDebit());
					} else if (model.getBalanceDirection().equals(Subject.DIR_CREDIT)) {//余额在贷方
						balance = BigDecimalUtils.subtract(balance, model.getEndBalanceCredit());
					}
				}
			}
		}
		return balance;
	}
	
	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<AccountBalance, String> accountBalanceDaoImpl) {
		this.hibernateGenericDaoImpl = accountBalanceDaoImpl;
		this.accountBalanceDaoImpl = (IAccountBalanceDao) accountBalanceDaoImpl;
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
