package com.vteba.finance.report.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.vteba.common.model.AstModel;

/**
 * 利润表
 * @author yinlei 
 * date 2012-6-30 上午12:10:32
 */
@Entity
@Table(name = "profit", catalog = "bizili")
public class Profit implements AstModel {

	private static final long serialVersionUID = 6222624036424301852L;
	private String id;
	private String accountPeriod;//会计期间
	private Date createDate;//创建时间
	private String itemName;//项目名/科目名称
	private String itemCode;//项目代码/科目代码
	private Integer rowNumber;//行次
	private Double thisPeriodCount;//本月金额&新版
	private Double thisYearSum;//本年累计金额&新版
	
	private Double lastPeriodCount;//上期数&老版
	private Double lastYearSum;//上年累计数&老版
	private String industry;//是哪个行业的利润表
	private String accountMethod;//该项目的核算方法
	
	/**
	 * 核算方法，余额相加
	 */
	public static final String ACCOUNT_METHOD_BALANCE_ADD = "BalanceAdd";
	/**
	 * 核算方法，细项合计
	 */
	public static final String ACCOUNT_METHOD_ITEM_SUMMARY = "ItemSummary";
	
	public Profit() {
	}

	public Profit(String id, String accountPeriod, Date createDate,
			String itemName, String itemCode, Integer rowNumber,
			Double thisPeriodCount, Double lastPeriodCount, Double thisYearSum,
			Double lastYearSum, String industry, String accountMethod) {
		super();
		this.id = id;
		this.accountPeriod = accountPeriod;
		this.createDate = createDate;
		this.itemName = itemName;
		this.itemCode = itemCode;
		this.rowNumber = rowNumber;
		this.thisPeriodCount = thisPeriodCount;
		this.lastPeriodCount = lastPeriodCount;
		this.thisYearSum = thisYearSum;
		this.lastYearSum = lastYearSum;
		this.industry = industry;
		this.accountMethod = accountMethod;
	}

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", unique = true, nullable = false, length = 45)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "account_period", nullable = false, length = 10)
	public String getAccountPeriod() {
		return this.accountPeriod;
	}

	public void setAccountPeriod(String accountPeriod) {
		this.accountPeriod = accountPeriod;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "item_name", length = 150)
	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column(name = "item_code", length = 60)
	public String getItemCode() {
		return this.itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	@Column(name = "row_number")
	public Integer getRowNumber() {
		return this.rowNumber;
	}

	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}

	@Column(name = "this_period_count", precision = 32, scale = 6)
	public Double getThisPeriodCount() {
		return this.thisPeriodCount;
	}

	public void setThisPeriodCount(Double thisPeriodCount) {
		this.thisPeriodCount = thisPeriodCount;
	}

	@Column(name = "last_period_count", precision = 32, scale = 6)
	public Double getLastPeriodCount() {
		return this.lastPeriodCount;
	}

	public void setLastPeriodCount(Double lastPeriodCount) {
		this.lastPeriodCount = lastPeriodCount;
	}

	@Column(name = "this_year_sum", precision = 32, scale = 6)
	public Double getThisYearSum() {
		return this.thisYearSum;
	}

	public void setThisYearSum(Double thisYearSum) {
		this.thisYearSum = thisYearSum;
	}

	@Column(name = "last_year_sum", precision = 32, scale = 6)
	public Double getLastYearSum() {
		return this.lastYearSum;
	}

	public void setLastYearSum(Double lastYearSum) {
		this.lastYearSum = lastYearSum;
	}
	
	@Column(name = "industry", length = 30)
	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}
	
	@Column(name = "account_method", length = 45)
	public String getAccountMethod() {
		return accountMethod;
	}

	public void setAccountMethod(String accountMethod) {
		this.accountMethod = accountMethod;
	}
	
}
