package com.vteba.finance.report.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.vteba.finance.account.model.Subject;
import com.vteba.finance.account.service.IAccountPeriodService;
import com.vteba.finance.report.dao.IAssetsLiabilitiesDao;
import com.vteba.finance.report.model.AssetsLiabilities;
import com.vteba.finance.report.service.IAssetsLiabilitiesService;
import com.vteba.finance.table.model.AccountBalance;
import com.vteba.finance.table.service.IAccountBalanceService;
import com.vteba.tx.hibernate.BaseGenericDao;
import com.vteba.service.generic.impl.BaseServiceImpl;
import com.vteba.utils.common.BigDecimalUtils;
import com.vteba.utils.ofbiz.LangUtils;

/**
 * 资产负债表service实现
 * @author yinlei 
 * date 2012-6-23 上午11:31:52
 */
@Named
public class AssetsLiabilitiesServiceImpl extends BaseServiceImpl<AssetsLiabilities, String>
		implements IAssetsLiabilitiesService {

	private IAssetsLiabilitiesDao assetsLiabilitiesDaoImpl;
	private IAccountBalanceService accountBalanceServiceImpl;
	private IAccountPeriodService accountPeriodServiceImpl;
	
	public AssetsLiabilitiesServiceImpl() {
		super();
	}
	
	public void autoGenerateAssetsLiabilitiesTask() {
		//createAssetsLiabilities();
		updateAssetsLiabilities();
	}
	
	/**
	 * 确定是更新资产负债表还是创建
	 * @return true or false
	 * @author yinlei
	 * date 2012-7-29 下午10:03:46
	 */
	public boolean decideUpdateAssetsLiabOrNot() {
//		StringBuilder hql = new StringBuilder(" select al from AssetsLiabilities al ");
//		hql.append(" where al.industry = :industry and al.accountPeriod = :period ");
		String period = accountPeriodServiceImpl.getCurrentPeriod();//会计期间
		Map<String, Object> param = new HashMap<String, Object>();//传递参数
		param.put("industry", "small_business");
		param.put("accountPeriod", period);
		
		//获得资产负债表
		List<AssetsLiabilities> assetsLiabList = assetsLiabilitiesDaoImpl.getEntityList(param);
		if (assetsLiabList.size() > 0) {//已有资产负债表，更新
			return true;
		}
		return false;
	}
	
	public void createAssetsLiabilities() {
//		StringBuilder hql = new StringBuilder(" select al from AssetsLiabilities al ");
//		hql.append(" where al.industry = :industry and al.accountPeriod = :period ");
		Map<String, Object> param = new HashMap<String, Object>();//传递参数
		param.put("accountPeriod", "period");
		param.put("industry", "small_business");
		
		//获得资产负债表模板
		List<AssetsLiabilities> assetsLiabList = assetsLiabilitiesDaoImpl.getEntityList(param);
		Map<String, Double> cacheMap = new HashMap<String, Double>();//用于缓存计算的结果，供后续计算使用
		String period = accountPeriodServiceImpl.getCurrentPeriod();//会计期间
		
		//处理取余额的及无需计算的项
		for(AssetsLiabilities entity : assetsLiabList) {
			if (entity.getAccountMethod() == null) {//标题行，不需计算，直接保存
				AssetsLiabilities model = new AssetsLiabilities();
				copyEntityProperty(entity, model);
				model.setAccountPeriod(period);
				assetsLiabilitiesDaoImpl.saveOrUpdate(model);
				continue;
			} else if (entity.getAccountMethod().equals("BalanceAdd")) {//余额相加或相减
				String itemCode = entity.getItemCode();
				if (StringUtils.isBlank(itemCode)) {//有些不需要计算，是空行
					AssetsLiabilities model = new AssetsLiabilities();
					copyEntityProperty(entity, model);
					model.setAccountPeriod(period);
					assetsLiabilitiesDaoImpl.saveOrUpdate(model);
					continue;
				}
				
				Double balance = 0D;//该项目的金额，初始为0
				balance = accountBalanceServiceImpl.calculateItemBalance(period, itemCode);
				
				cacheMap.put(entity.getRowNumber().toString(), balance);//将值放入map，后面统计时使用
				
				//要保存的实体，在同一个session上下文，直接保存有问题，所有要拷贝
				AssetsLiabilities model = new AssetsLiabilities();
				copyEntityProperty(entity, model);
				
				model.setAccountPeriod(period);
				model.setEndingCount(balance);
				
				calculateYearBeginCount(model, entity.getItemCode());
				
				assetsLiabilitiesDaoImpl.saveOrUpdate(model);
			} else if (entity.getAccountMethod().equals("DebitBalance")) {//借方余额，在贷方以“-”号显示
				Double balance = 0D;
				balance = getItemBalance(entity, period, cacheMap, Subject.DIR_DEBIT);
				
				AssetsLiabilities model = new AssetsLiabilities();
				copyEntityProperty(entity, model);
				
				model.setAccountPeriod(period);
				model.setEndingCount(balance);
				//计算年初数
				calculateYearBeginCount(model, entity.getItemCode());
				assetsLiabilitiesDaoImpl.saveOrUpdate(model);
				
			} else if (entity.getAccountMethod().equals("CreditBalance")) {//贷方余额，在借方以“-”号显示
				Double balance = 0D;//余额
				//获得项目余额
				balance = getItemBalance(entity, period, cacheMap, Subject.DIR_CREDIT);
				AssetsLiabilities model = new AssetsLiabilities();
				
				copyEntityProperty(entity, model);//拷贝不变属性
				model.setAccountPeriod(period);
				model.setEndingCount(balance);
				//计算年初数
				calculateYearBeginCount(model, entity.getItemCode());
				assetsLiabilitiesDaoImpl.saveOrUpdate(model);
			}
		}
		
		//处理应收，应付。只计算不保存，后面再保存(因为必须全部计算出来，知道方向后，才知道放在哪个科目上)
		handelReceivablePayable(assetsLiabList, cacheMap, period);
		
		//保存应收应付，处理合计项
		for (AssetsLiabilities entity : assetsLiabList) {
			if (entity.getAccountMethod() == null) {
				continue;
			}
			if (entity.getAccountMethod().equals("BalanceOrReverse")) {//保存应收应付
				String[] codes = StringUtils.split(entity.getItemCode(), "#");
				String code = codes[0];//该项目要取的科目
				String subjectCode = code.substring(1);//科目代码
				
				Double balance = 0D;
				balance = cacheMap.get(subjectCode);
				
				AssetsLiabilities model = new AssetsLiabilities();
				copyEntityProperty(entity, model);
				
				model.setAccountPeriod(period);
				model.setEndingCount(balance);
				
				calculateYearBeginCount(model, entity.getItemCode());
				
				assetsLiabilitiesDaoImpl.saveOrUpdate(model);
			} else if (entity.getAccountMethod().equals("ItemSummary")) {//合计项
				String[] codes = StringUtils.split(entity.getItemCode(), "#");
				Double thisPeriodCount = 0D;
				for (String code : codes) {
					//如果Map中没有数据，默认为零
					Double cache = cacheMap.get(code) == null ? 0D : cacheMap.get(code);
					thisPeriodCount = BigDecimalUtils.add(thisPeriodCount, cache);
				}
				AssetsLiabilities model = new AssetsLiabilities();
				copyEntityProperty(entity, model);
				
				model.setEndingCount(thisPeriodCount);
				model.setAccountPeriod(period);
				calculateYearBeginCount(model, entity.getItemCode());
				
				assetsLiabilitiesDaoImpl.saveOrUpdate(model);
			}
		}
	}
	
	public void updateAssetsLiabilities() {
		
		String period = accountPeriodServiceImpl.getCurrentPeriod();//会计期间
		
//		StringBuilder hql = new StringBuilder(" select al from AssetsLiabilities al ");
//		hql.append(" where al.industry = :industry and al.accountPeriod = :period ");
		
		Map<String, Object> param = new HashMap<String, Object>();//传递参数
		param.put("accountPeriod", period);
		param.put("industry", "small_business");
		
		//获得资产负债表
		List<AssetsLiabilities> assetsLiabList = assetsLiabilitiesDaoImpl.getEntityList(param);
		Map<String, Double> cacheMap = new HashMap<String, Double>();//用于缓存计算的结果
		
		for(AssetsLiabilities entity : assetsLiabList) {
			if (entity.getAccountMethod() == null) {//标题行，不需计算
				continue;
			} else if (entity.getAccountMethod().equals("BalanceAdd")) {//余额相加或相减
				String itemCode = entity.getItemCode();
				if (StringUtils.isBlank(itemCode)) {//有些不需要计算，是空行
					continue;
				}
				
				Double balance = 0D;//该项目的金额，初始为0
				balance = accountBalanceServiceImpl.calculateItemBalance(period, itemCode);
				cacheMap.put(entity.getRowNumber().toString(), balance);//将值放入map，后面统计时使用
				
				entity.setEndingCount(balance);
				assetsLiabilitiesDaoImpl.saveOrUpdate(entity);
			} else if (entity.getAccountMethod().equals("DebitBalance")) {//借方余额，在贷方以“-”号显示
				Double balance = 0D;
				balance = getItemBalance(entity, period, cacheMap, Subject.DIR_DEBIT);
				entity.setEndingCount(balance);
				assetsLiabilitiesDaoImpl.saveOrUpdate(entity);
				
			} else if (entity.getAccountMethod().equals("CreditBalance")) {//贷方余额，在借方以“-”号显示
				Double balance = 0D;
				balance = getItemBalance(entity, period, cacheMap, Subject.DIR_CREDIT);
				entity.setEndingCount(balance);
				assetsLiabilitiesDaoImpl.saveOrUpdate(entity);
			}
		}
		
		//处理应收，应付。只计算不保存，后面再保存(因为必须全部计算出来，知道方向后，才知道放在哪个科目上)
		handelReceivablePayable(assetsLiabList, cacheMap, period);
		
		//保存应收应付，处理合计项
		for (AssetsLiabilities entity : assetsLiabList) {
			if (entity.getAccountMethod() == null) {
				continue;
			}
			if (entity.getAccountMethod().equals("BalanceOrReverse")) {//保存应收应付
				String[] codes = StringUtils.split(entity.getItemCode(), "#");
				String code = codes[0];//该项目要取的科目
				String subjectCode = code.substring(1);//科目代码
				
				Double balance = 0D;
				balance = cacheMap.get(subjectCode);
				
				entity.setEndingCount(balance);
				assetsLiabilitiesDaoImpl.saveOrUpdate(entity);
			} else if (entity.getAccountMethod().equals("ItemSummary")) {//合计项
				String[] codes = StringUtils.split(entity.getItemCode(), "#");
				Double thisPeriodCount = 0D;
				for (String code : codes) {
					//如果Map中没有数据，默认为零
					Double cache = cacheMap.get(code) == null ? 0D : cacheMap.get(code);
					thisPeriodCount = BigDecimalUtils.add(thisPeriodCount, cache);
				}
				entity.setEndingCount(thisPeriodCount);
				assetsLiabilitiesDaoImpl.saveOrUpdate(entity);
			}
		}		
	}
	
	/**
	 * 计算资产负债表中项目的年初数
	 * @author yinlei
	 * date 2012-7-27 下午3:41:24
	 */
	protected void calculateYearBeginCount(AssetsLiabilities model, String itemCode) {
		//当前会计期间
		String period = accountPeriodServiceImpl.getCurrentPeriod();
		//String lastYear = (Integer.valueOf(period.substring(0, 4)).intValue()-1) + "-12";//去年最后一期
		//计算年初数hql
		//String yearBeginCountHql = "select a.yearBeginCount from AssetsLiabilities a where a.accountPeriod =?1 and a.itemCode = ?2 ";
		Map<String, ?> params = LangUtils.toMap("accountPeriod", period, "itemCode", itemCode);
		List<Double> doubleList = assetsLiabilitiesDaoImpl.queryPrimitiveList("yearBeginCount", Double.class, params);
		Double yearBeginCount = 0D;
		if (doubleList.size() == 1) {
			yearBeginCount = BigDecimalUtils.add(yearBeginCount, doubleList.get(0));
		}
		model.setYearBeginCount(yearBeginCount);
		
	}
	
	/**
	 * 拷贝实体属性
	 * @param source 源实体
	 * @param toBean 目标实体
	 * @author yinlei
	 * date 2012-7-27 下午4:34:09
	 */
	protected void copyEntityProperty(AssetsLiabilities source, AssetsLiabilities toBean) {
		toBean.setAccountMethod(source.getAccountMethod());
		toBean.setCategory(source.getCategory());
		toBean.setCreateDate(new Date());
		toBean.setIndustry(source.getIndustry());
		toBean.setItemCode(source.getItemCode());
		toBean.setItemName(source.getItemName());
		toBean.setItemType(source.getItemType());
		toBean.setOrders(source.getOrders());
		toBean.setRowNumber(source.getRowNumber());
	}
	
	/**
	 * 获得项目余额
	 * @param entity AssetsLiabilities
	 * @param period 会计期间
	 * @param cacheMap 计算结果的缓存map
	 * @param orient 取余额的方向
	 * @return 该项目的余额
	 * @author yinlei
	 * date 2012-7-29 上午11:47:38
	 */
	protected Double getItemBalance(AssetsLiabilities entity, String period, Map<String, Double> cacheMap, String orient) {
		String[] codes = StringUtils.split(entity.getItemCode(), "#");
		//String balanceHql = "select ab from AccountBalance ab where ab.accountPeriod = ?1 and ab.subjectCode = ?2 ";
		
		String code = codes[0];
		String subjectCode = code.substring(1);//科目代码
		AccountBalance balanceBean = accountBalanceServiceImpl.uniqueResult(LangUtils.toMap("accountPeriod", period, "subjectCode", subjectCode));
		
		Double balance = 0D;
		if (balanceBean != null) {
			if (orient.equals(Subject.DIR_DEBIT)) {
				cacheMap.put(entity.getRowNumber().toString(), balanceBean.getEndBalanceDebit());//将值放入map，后面统计时使用
				balance = balanceBean.getEndBalanceCredit();
			} else if (orient.equals(Subject.DIR_CREDIT)) {
				cacheMap.put(entity.getRowNumber().toString(), balanceBean.getEndBalanceDebit());//将值放入map，后面统计时使用
				balance = balanceBean.getEndBalanceCredit();
			}
		}
		return balance;
	}
	
	/**
	 * 处理计算应收和应付
	 * @param assetsLiabList 资产负债表
	 * @param cacheMap 缓存计算结果Map
	 * @param period 会计期间
	 * @author yinlei
	 * date 2012-7-29 下午9:41:07
	 */
	protected void handelReceivablePayable (List<AssetsLiabilities> assetsLiabList, Map<String, Double> cacheMap, String period) {
		for (AssetsLiabilities entity : assetsLiabList) {
			if (entity.getAccountMethod() == null) {
				continue;
			}
			if (entity.getAccountMethod().equals("BalanceOrReverse")) {//取余额，不在本方向，则取反方余额
				String[] codes = StringUtils.split(entity.getItemCode(), "#");
				//String balanceHql = "select ab from AccountBalance ab where ab.accountPeriod = ?1 and ab.subjectCode = ?2 ";
				
				String code = codes[0];//该项目要取的科目
				String symbol = code.substring(0, 1);//符号
				String subjectCode = code.substring(1);//科目代码
				String reverseCode = codes[1];//反方科目
				AccountBalance balanceBean = accountBalanceServiceImpl.uniqueResult(LangUtils.toMap("accountPeriod", period, "subjectCode", subjectCode));
				
				if (balanceBean != null) {
					Double balance = 0D;
					if (symbol.equals("+")) {//取借方余额
						if (balanceBean.getBalanceDirection().equals(Subject.DIR_DEBIT)) {//余额在借方，直接取
							balance = cacheMap.get(subjectCode) == null ? 0D : cacheMap.get(subjectCode);
							balance = BigDecimalUtils.add(balance, balanceBean.getEndBalanceDebit());
							cacheMap.put(subjectCode, balance);
						} else if (balanceBean.getBalanceDirection().equals(Subject.DIR_CREDIT)) {//余额在贷方，放到反方科目上去
							balance = cacheMap.get(reverseCode) == null ? 0D : cacheMap.get(reverseCode);
							balance = BigDecimalUtils.add(balance, balanceBean.getEndBalanceDebit());
							cacheMap.put(reverseCode, balanceBean.getEndBalanceDebit());
						}
					} else if (symbol.equals("-")) {//取贷方余额
						if (balanceBean.getBalanceDirection().equals(Subject.DIR_DEBIT)) {//余额在借方，放到反方科目上去
							balance = cacheMap.get(reverseCode) == null ? 0D : cacheMap.get(reverseCode);
							balance = BigDecimalUtils.add(balance, balanceBean.getEndBalanceCredit());
							cacheMap.put(reverseCode, balance);
						} else if (balanceBean.getBalanceDirection().equals(Subject.DIR_CREDIT)) {//余额在贷方，直接取
							balance = cacheMap.get(subjectCode) == null ? 0D : cacheMap.get(subjectCode);
							balance = BigDecimalUtils.add(balance, balanceBean.getEndBalanceCredit());
							cacheMap.put(subjectCode, balance);
						}
					}
				}
			}
		}
	}
	
	@Inject
	public void setBaseGenericDaoImpl(
			BaseGenericDao<AssetsLiabilities, String> assetsLiabilitiesDaoImpl) {
		this.baseGenericDaoImpl = assetsLiabilitiesDaoImpl;
		this.assetsLiabilitiesDaoImpl = (IAssetsLiabilitiesDao) assetsLiabilitiesDaoImpl;
	}
	
	@Inject
	public void setAccountBalanceServiceImpl(
			IAccountBalanceService accountBalanceServiceImpl) {
		this.accountBalanceServiceImpl = accountBalanceServiceImpl;
	}
	
	@Inject
	public void setAccountPeriodServiceImpl(
			IAccountPeriodService accountPeriodServiceImpl) {
		this.accountPeriodServiceImpl = accountPeriodServiceImpl;
	}
}
