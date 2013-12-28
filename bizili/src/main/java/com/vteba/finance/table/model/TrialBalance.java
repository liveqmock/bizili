package com.vteba.finance.table.model;

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
 * 试算平衡表
 * @author yinlei 
 * date 2012-6-30 下午12:30:19
 */
@Entity
@Table(name = "trial_balance", catalog = "bizili")
public class TrialBalance implements AstModel {

	private static final long serialVersionUID = 2294562321907775303L;
	private String id;
	private String accountPeriod;//会计期间
	private Integer level;//科目级别
	private Date createDate;//创建时间
	private String subjectCode;//科目代码
	private String subjectName;//科目名
	private Double startBalanceDebit;//期初借方余额
	private Double startBalanceCredit;//期初贷方余额
	private Double thisPeriodDebit;//本期借方发生额
	private Double thisPeriodCredit;//本期贷方发生额
	private Double endBalanceDebit;//期末借方余额
	private Double endBalanceCredit;//期末贷方余额

	public TrialBalance() {
	}

	public TrialBalance(String accountPeriod, Integer level, Date createDate,
			String subjectCode, String subjectName, Double startBalanceDebit,
			Double startBalanceCredit, Double thisPeriodDebit,
			Double thisPeriodCredit, Double endBalanceDebit,
			Double endBalanceCredit) {
		super();
		this.accountPeriod = accountPeriod;
		this.level = level;
		this.createDate = createDate;
		this.subjectCode = subjectCode;
		this.subjectName = subjectName;
		this.startBalanceDebit = startBalanceDebit;
		this.startBalanceCredit = startBalanceCredit;
		this.thisPeriodDebit = thisPeriodDebit;
		this.thisPeriodCredit = thisPeriodCredit;
		this.endBalanceDebit = endBalanceDebit;
		this.endBalanceCredit = endBalanceCredit;
	}

	public TrialBalance(String id, String accountPeriod, Integer level,
			Date createDate, String subjectCode, String subjectName,
			Double startBalanceDebit, Double startBalanceCredit,
			Double thisPeriodDebit, Double thisPeriodCredit,
			Double endBalanceDebit, Double endBalanceCredit) {
		this.id = id;
		this.accountPeriod = accountPeriod;
		this.level = level;
		this.createDate = createDate;
		this.subjectCode = subjectCode;
		this.subjectName = subjectName;
		this.startBalanceDebit = startBalanceDebit;
		this.startBalanceCredit = startBalanceCredit;
		this.thisPeriodDebit = thisPeriodDebit;
		this.thisPeriodCredit = thisPeriodCredit;
		this.endBalanceDebit = endBalanceDebit;
		this.endBalanceCredit = endBalanceCredit;
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

	@Column(name = "level")
	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "create_date", length = 10)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "subject_code", nullable = false, length = 45)
	public String getSubjectCode() {
		return this.subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	@Column(name = "subject_name", nullable = false, length = 60)
	public String getSubjectName() {
		return this.subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
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

	@Column(name = "this_period_debit", precision = 32, scale = 6)
	public Double getThisPeriodDebit() {
		return this.thisPeriodDebit;
	}

	public void setThisPeriodDebit(Double thisPeriodDebit) {
		this.thisPeriodDebit = thisPeriodDebit;
	}

	@Column(name = "this_period_credit", precision = 32, scale = 6)
	public Double getThisPeriodCredit() {
		return this.thisPeriodCredit;
	}

	public void setThisPeriodCredit(Double thisPeriodCredit) {
		this.thisPeriodCredit = thisPeriodCredit;
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

}
