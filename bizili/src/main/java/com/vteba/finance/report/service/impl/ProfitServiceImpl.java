package com.vteba.finance.report.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.vteba.common.constant.ReportConst;
import com.vteba.finance.account.service.IAccountPeriodService;
import com.vteba.finance.report.dao.IProfitDao;
import com.vteba.finance.report.model.Profit;
import com.vteba.finance.report.service.IProfitService;
import com.vteba.finance.table.service.IAccountBalanceService;
import com.vteba.tx.hibernate.IHibernateGenericDao;
import com.vteba.service.generic.impl.GenericServiceImpl;
import com.vteba.util.common.BigDecimalUtils;
import com.vteba.util.date.JodaTimeUtils;

/**
 * 利润表service实现
 * @author yinlei 
 * date 2012-7-23 上午10:36:20
 */
@Named
public class ProfitServiceImpl extends GenericServiceImpl<Profit, String> implements
		IProfitService {

	private IProfitDao profitDaoImpl;
	private IAccountPeriodService accountPeriodServiceImpl;
	private IAccountBalanceService accountBalanceServiceImpl;
	
	public ProfitServiceImpl() {
		super();
	}

	public void autoGenerateProfitTask() {
		//createProfit();
		updateProfit();
	}
	
	public void createProfit() {
		
		//新建，取得利润表模板
		StringBuilder hql = new StringBuilder("select p from Profit p ");
		hql.append(" where p.accountPeriod = ?1 and p.industry = ?2 order by p.rowNumber asc ");
		String period = accountPeriodServiceImpl.getCurrentPeriod();
		List<Profit> profitList = profitDaoImpl.getEntityListByHql(hql.toString(), "period", ReportConst.COMPANY_TYPE_SMALL);
		
		//缓存临时计算的结果，供后续合计项计算使用
		Map<String, Double> cacheMap = new HashMap<String, Double>();
		
		for (Profit profit : profitList) {
			//余额相加
			if (profit.getAccountMethod().equals(Profit.ACCOUNT_METHOD_BALANCE_ADD)) {
				Profit entity = new Profit();
				//本期数
				Double thisPeriodCount = accountBalanceServiceImpl.calculateItemBalance(period, profit.getItemCode());
				entity.setThisPeriodCount(thisPeriodCount);
				cacheMap.put(profit.getRowNumber().toString(), thisPeriodCount);
				
				entity.setAccountPeriod(period);
				copyEntityProperty(profit, entity);
				//今年累计数，要先计算本期数哦
				calculateThisYearCount(entity, profit.getItemCode(), period);
				profitDaoImpl.saveOrUpdate(entity);
				
			//细项合计
			} else if (profit.getAccountMethod().equals(Profit.ACCOUNT_METHOD_ITEM_SUMMARY)) {
				String[] codes = StringUtils.split(profit.getItemCode(), "#");
				Double thisPeriodCount = 0D;
				for (String code : codes) {
					//如果Map中没有数据，默认为零
					Double cache = cacheMap.get(code) == null ? 0D : cacheMap.get(code);
					thisPeriodCount = BigDecimalUtils.add(thisPeriodCount, cache);
				}
				Profit entity = new Profit();
				entity.setThisPeriodCount(thisPeriodCount);
				
				entity.setAccountPeriod(period);
				copyEntityProperty(profit, entity);
				//今年累计数，要先计算本期数哦
				calculateThisYearCount(entity, profit.getItemCode(), period);
				
				profitDaoImpl.saveOrUpdate(entity);
			}
		}
	}
	
	public void updateProfit() {
		//因为新建，取得利润表模板
		StringBuilder hql = new StringBuilder("select p from Profit p ");
		hql.append(" where p.accountPeriod = ?1 and p.industry = ?2 order by p.rowNumber asc ");
		String period = accountPeriodServiceImpl.getCurrentPeriod();
		List<Profit> profitList = profitDaoImpl.getEntityListByHql(hql.toString(), period, ReportConst.COMPANY_TYPE_SMALL);
		
		//缓存临时计算的结果，供后续合计项计算使用
		Map<String, Double> cacheMap = new HashMap<String, Double>();
		
		for (Profit profit : profitList) {
			//余额相加
			if (profit.getAccountMethod().equals(Profit.ACCOUNT_METHOD_BALANCE_ADD)) {
				//本期数
				Double thisPeriodCount = accountBalanceServiceImpl.calculateItemBalance(period, profit.getItemCode());
				profit.setThisPeriodCount(thisPeriodCount);
				cacheMap.put(profit.getRowNumber().toString(), thisPeriodCount);
				
				profit.setAccountPeriod(period);
				profit.setCreateDate(new Date());
				//copyEntityProperty(profit, entity);
				//今年累计数，要先计算本期数哦
				calculateThisYearCount(profit, profit.getItemCode(), period);
				profitDaoImpl.saveOrUpdate(profit);
				
			//细项合计
			} else if (profit.getAccountMethod().equals(Profit.ACCOUNT_METHOD_ITEM_SUMMARY)) {
				String[] codes = StringUtils.split(profit.getItemCode(), "#");
				Double thisPeriodCount = 0D;
				for (String code : codes) {
					//如果Map中没有数据，默认为零
					Double cache = cacheMap.get(code) == null ? 0D : cacheMap.get(code);
					thisPeriodCount = BigDecimalUtils.add(thisPeriodCount, cache);
				}
				
				profit.setThisPeriodCount(thisPeriodCount);
				profit.setAccountPeriod(period);
				profit.setCreateDate(new Date());
				//今年累计数，要先计算本期数哦
				calculateThisYearCount(profit, profit.getItemCode(), period);
				
				profitDaoImpl.saveOrUpdate(profit);
			}
		}		
	}
	
	/**
	 * 拷贝实体属性
	 * @param source 源实体
	 * @param toBean 目标实体
	 * @author yinlei
	 * date 2012-7-31 下午5:03:49
	 */
	protected void copyEntityProperty(Profit source, Profit toBean) {
		toBean.setAccountMethod(source.getAccountMethod());
		toBean.setCreateDate(new Date());
		toBean.setIndustry(source.getIndustry());
		toBean.setItemCode(source.getItemCode());
		toBean.setItemName(source.getItemName());
		toBean.setRowNumber(source.getRowNumber());
	}
	
	/**
	 * 计算本年累计数，上期的本年累计数+本期数(要先计算)
	 * @param model
	 * @param itemCode
	 * @param period
	 * @author yinlei
	 * date 2012-7-31 下午5:18:07
	 */
	protected void calculateThisYearCount(Profit model, String itemCode, String period) {
		String lastPeriod = JodaTimeUtils.getLastPeriod(period);
		//计算年初数hql
		String thisYearCountHql = "select a.thisYearSum from Profit a where a.accountPeriod =?1 and a.itemCode = ?2 ";
		
		List<Double> doubleList = profitDaoImpl.hqlQueryForList(thisYearCountHql, Double.class, lastPeriod, itemCode);
		Double thisYearSum = 0D;
		if (doubleList.size() == 1) {
			thisYearSum = BigDecimalUtils.add(thisYearSum, doubleList.get(0));
		}
		thisYearSum = BigDecimalUtils.add(thisYearSum, model.getThisPeriodCount());
		model.setThisYearSum(thisYearSum);
	}
	
	@Inject
	public void setHibernateGenericDaoImpl(
			IHibernateGenericDao<Profit, String> profitDaoImpl) {
		this.hibernateGenericDaoImpl = profitDaoImpl;
		this.profitDaoImpl = (IProfitDao) profitDaoImpl;
	}
	
	@Inject
	public void setAccountPeriodServiceImpl(
			IAccountPeriodService accountPeriodServiceImpl) {
		this.accountPeriodServiceImpl = accountPeriodServiceImpl;
	}
	
	@Inject
	public void setAccountBalanceServiceImpl(
			IAccountBalanceService accountBalanceServiceImpl) {
		this.accountBalanceServiceImpl = accountBalanceServiceImpl;
	}
	
}
