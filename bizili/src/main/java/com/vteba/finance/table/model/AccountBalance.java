package com.vteba.finance.table.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.vteba.common.model.AstModel;

/**
 * 科目余额
 */
@Entity
@Table(name = "account_balance", catalog = "bizili")
public class AccountBalance implements AstModel {

	private static final long serialVersionUID = 6330466722378528930L;
	private String id;
	private String accountPeriod;//会计期间
	private String subjectCode;//科目代码
	private String subjectName;//科目名称
	private Integer level;//科目级别
	private String balanceDirection;//余额方向
	private Double startBalanceDebit;//期初借方余额
	private Double startBalanceCredit;//期初贷方余额
	private Double currenceBalanceDebit;//本期借方余额
	private Double currenceBalanceCredit;//本期贷方余额
	private Double yearSumBalanceDebit;//本年汇总借方余额
	private Double yearSumBalanceCredit;//本年汇总贷方余额
	private Double endBalanceDebit;//期末借方余额
	private Double endBalanceCredit;//期末贷方余额
	private Integer state;//1手工初始化的，2定时服务产生的，3每月汇总的，4年度汇总的
	
	/**
	 * 手工初始化期初余额
	 */
	public static final Integer STATE_HAND = 1;
	/**
	 * 每天定时任务科目余额
	 */
	public static final Integer STATE_TASK = 2;
	/**
	 * 每月结账产生科目期初余额
	 */
	public static final Integer STATE_MONTH = 3;
	/**
	 * 年度汇总科目余额
	 */
	public static final Integer STATE_YEAR = 4;
	
	public AccountBalance() {
	}

	public AccountBalance(String id, String accountPeriod, String subjectCode,
			String subjectName, Integer level, String balanceDirection,
			Double startBalanceDebit, Double startBalanceCredit,
			Double currenceBalanceDebit, Double currenceBalanceCredit,
			Double yearSumBalanceDebit, Double yearSumBalanceCredit,
			Double endBalanceDebit, Double endBalanceCredit,
			Integer state) {
		super();
		this.id = id;
		this.accountPeriod = accountPeriod;
		this.subjectCode = subjectCode;
		this.subjectName = subjectName;
		this.level = level;
		this.balanceDirection = balanceDirection;
		this.startBalanceDebit = startBalanceDebit;
		this.startBalanceCredit = startBalanceCredit;
		this.currenceBalanceDebit = currenceBalanceDebit;
		this.currenceBalanceCredit = currenceBalanceCredit;
		this.yearSumBalanceDebit = yearSumBalanceDebit;
		this.yearSumBalanceCredit = yearSumBalanceCredit;
		this.endBalanceDebit = endBalanceDebit;
		this.endBalanceCredit = endBalanceCredit;
		this.state = state;
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

	@Column(name = "subject_code", nullable = false, length = 45)
	public String getSubjectCode() {
		return this.subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	@Column(name = "subject_name", length = 45)
	public String getSubjectName() {
		return this.subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	@Column(name = "level")
	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	@Column(name = "balance_direction", length = 10)
	public String getBalanceDirection() {
		return balanceDirection;
	}

	public void setBalanceDirection(String balanceDirection) {
		this.balanceDirection = balanceDirection;
	}

	@Column(name = "start_balance_debit", precision = 32, scale = 6)
	public Double getStartBalanceDebit() {
		return this.startBalanceDebit;
	}

	public void setStartBalanceDebit(Double startBalanceDebit) {
		this.startBalanceDebit = startBalanceDebit;
	}

	@Column(name = "start_balance_credit", precision = 32, scale = 6)
	public Double getStartBalanceCredit() {
		return this.startBalanceCredit;
	}

	public void setStartBalanceCredit(Double startBalanceCredit) {
		this.startBalanceCredit = startBalanceCredit;
	}

	@Column(name = "currence_balance_debit", precision = 32, scale = 6)
	public Double getCurrenceBalanceDebit() {
		return this.currenceBalanceDebit;
	}

	public void setCurrenceBalanceDebit(Double currenceBalanceDebit) {
		this.currenceBalanceDebit = currenceBalanceDebit;
	}

	@Column(name = "currence_balance_credit", precision = 32, scale = 6)
	public Double getCurrenceBalanceCredit() {
		return this.currenceBalanceCredit;
	}

	public void setCurrenceBalanceCredit(Double currenceBalanceCredit) {
		this.currenceBalanceCredit = currenceBalanceCredit;
	}

	@Column(name = "year_sum_balance_debit", precision = 32, scale = 6)
	public Double getYearSumBalanceDebit() {
		return this.yearSumBalanceDebit;
	}

	public void setYearSumBalanceDebit(Double yearSumBalanceDebit) {
		this.yearSumBalanceDebit = yearSumBalanceDebit;
	}

	@Column(name = "year_sum_balance_credit", precision = 32, scale = 6)
	public Double getYearSumBalanceCredit() {
		return this.yearSumBalanceCredit;
	}

	public void setYearSumBalanceCredit(Double yearSumBalanceCredit) {
		this.yearSumBalanceCredit = yearSumBalanceCredit;
	}

	@Column(name = "end_balance_debit", precision = 32, scale = 6)
	public Double getEndBalanceDebit() {
		return this.endBalanceDebit;
	}

	public void setEndBalanceDebit(Double endBalanceDebit) {
		this.endBalanceDebit = endBalanceDebit;
	}

	@Column(name = "end_balance_credit", precision = 32, scale = 6)
	public Double getEndBalanceCredit() {
		return this.endBalanceCredit;
	}

	public void setEndBalanceCredit(Double endBalanceCredit) {
		this.endBalanceCredit = endBalanceCredit;
	}

	@Column(name = "state")
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}
