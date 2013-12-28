package com.vteba.finance.receivables.model;

import java.math.BigDecimal;
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
 * 应收汇总表
 * @author yinlei 
 * date 2012-8-1 下午3:08:16
 */
@Entity
@Table(name = "receivables_summary", catalog = "bizili")
public class ReceivablesSummary implements AstModel {

	private static final long serialVersionUID = 4022567112862284477L;
	private String id;
	private String accountPeriod;//会计期间
	private String customerId;//客户id
	private String customerName;//客户名称
	private String currency;//币别
	private String subjectCode;//科目代码
	private String subjectName;//科目名称
	private BigDecimal startBalance;//期初余额
	private BigDecimal thisPeriodDebit;//本期借方
	private BigDecimal thisPeriodCredit;//本期贷方
	private BigDecimal yearSumDebit;//本年借方累计
	private BigDecimal yearSumCredit;//本年贷方累计
	private BigDecimal endBalance;//期末余额
	private String category;//分类
	private String region;//区域
	private String salesman;//业务员
	private String remark;//备注
	private Date createDate;//创建日期

	public ReceivablesSummary() {
	}

	public ReceivablesSummary(String id) {
		this.id = id;
	}

	public ReceivablesSummary(String id, String accountPeriod,
			String customerId, String customerName, String currency,
			String subjectCode, String subjectName, BigDecimal startBalance,
			BigDecimal thisPeriodDebit, BigDecimal thisPeriodCredit,
			BigDecimal yearSumDebit, BigDecimal yearSumCredit,
			BigDecimal endBalance, String category, String region,
			String salesman, String remark, Date createDate) {
		this.id = id;
		this.accountPeriod = accountPeriod;
		this.customerId = customerId;
		this.customerName = customerName;
		this.currency = currency;
		this.subjectCode = subjectCode;
		this.subjectName = subjectName;
		this.startBalance = startBalance;
		this.thisPeriodDebit = thisPeriodDebit;
		this.thisPeriodCredit = thisPeriodCredit;
		this.yearSumDebit = yearSumDebit;
		this.yearSumCredit = yearSumCredit;
		this.endBalance = endBalance;
		this.category = category;
		this.region = region;
		this.salesman = salesman;
		this.remark = remark;
		this.createDate = createDate;
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

	@Column(name = "account_period", length = 10)
	public String getAccountPeriod() {
		return this.accountPeriod;
	}

	public void setAccountPeriod(String accountPeriod) {
		this.accountPeriod = accountPeriod;
	}

	@Column(name = "customer_id", length = 45)
	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@Column(name = "customer_name", length = 100)
	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "currency", length = 15)
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "subject_code", length = 45)
	public String getSubjectCode() {
		return this.subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	@Column(name = "subject_name", length = 100)
	public String getSubjectName() {
		return this.subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	@Column(name = "start_balance", precision = 32, scale = 6)
	public BigDecimal getStartBalance() {
		return this.startBalance;
	}

	public void setStartBalance(BigDecimal startBalance) {
		this.startBalance = startBalance;
	}

	@Column(name = "this_period_debit", precision = 32, scale = 6)
	public BigDecimal getThisPeriodDebit() {
		return this.thisPeriodDebit;
	}

	public void setThisPeriodDebit(BigDecimal thisPeriodDebit) {
		this.thisPeriodDebit = thisPeriodDebit;
	}

	@Column(name = "this_period_credit", precision = 32, scale = 6)
	public BigDecimal getThisPeriodCredit() {
		return this.thisPeriodCredit;
	}

	public void setThisPeriodCredit(BigDecimal thisPeriodCredit) {
		this.thisPeriodCredit = thisPeriodCredit;
	}

	@Column(name = "year_sum_debit", precision = 32, scale = 6)
	public BigDecimal getYearSumDebit() {
		return this.yearSumDebit;
	}

	public void setYearSumDebit(BigDecimal yearSumDebit) {
		this.yearSumDebit = yearSumDebit;
	}

	@Column(name = "year_sum_credit", precision = 32, scale = 6)
	public BigDecimal getYearSumCredit() {
		return this.yearSumCredit;
	}

	public void setYearSumCredit(BigDecimal yearSumCredit) {
		this.yearSumCredit = yearSumCredit;
	}

	@Column(name = "end_balance", precision = 32, scale = 6)
	public BigDecimal getEndBalance() {
		return this.endBalance;
	}

	public void setEndBalance(BigDecimal endBalance) {
		this.endBalance = endBalance;
	}

	@Column(name = "category", length = 45)
	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Column(name = "region", length = 45)
	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	@Column(name = "salesman", length = 45)
	public String getSalesman() {
		return this.salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}

	@Column(name = "remark", length = 50)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "create_date", length = 10)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
