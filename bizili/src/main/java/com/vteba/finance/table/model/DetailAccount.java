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

/**
 * 明细账
 * @author yinlei 
 * date 2012-7-6 下午6:29:00
 */
@Entity
@Table(name = "detail_account", catalog = "bizili")
public class DetailAccount implements java.io.Serializable {
	private static final long serialVersionUID = 1161249073706639841L;
	private String id;
	private String accountPeriod;//会计期间
	private String subjectCode;//科目代码
	private String subjectName;//科目名
	private Integer level;//科目级别
	private String currency;//币别
	private String aidAccount;//辅助核算
	private Date createDate;//创建日期
	private String codeNo;//凭证字号
	private String summary;//摘要
	private Double debitBalance;//借方余额
	private Double creditBalance;//贷方余额
	private String balanceDirection;//余额方向
	private Double balance;//余额

	public DetailAccount() {
	}

	public DetailAccount(String accountPeriod, String subjectCode,
			String subjectName, String currency, Date createDate, String codeNo, String summary,
			Double debitBalance, Double creditBalance) {
		super();
		this.accountPeriod = accountPeriod;
		this.subjectCode = subjectCode;
		this.subjectName = subjectName;
		this.currency = currency;
		this.createDate = createDate;
		this.codeNo = codeNo;
		this.summary = summary;
		this.debitBalance = debitBalance;
		this.creditBalance = creditBalance;
	}

	public DetailAccount(String id, String accountPeriod, String subjectCode,
			String subjectName, Integer level, String currency,
			String aidAccount, Date createDate, String codeNo, String summary,
			Double debitBalance, Double creditBalance,
			String balanceDirection, Double balance) {
		this.id = id;
		this.accountPeriod = accountPeriod;
		this.subjectCode = subjectCode;
		this.subjectName = subjectName;
		this.level = level;
		this.currency = currency;
		this.aidAccount = aidAccount;
		this.createDate = createDate;
		this.codeNo = codeNo;
		this.summary = summary;
		this.debitBalance = debitBalance;
		this.creditBalance = creditBalance;
		this.balanceDirection = balanceDirection;
		this.balance = balance;
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

	@Column(name = "account_period", length = 12)
	public String getAccountPeriod() {
		return this.accountPeriod;
	}

	public void setAccountPeriod(String accountPeriod) {
		this.accountPeriod = accountPeriod;
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

	@Column(name = "level")
	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Column(name = "currency", length = 15)
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "aid_account", length = 60)
	public String getAidAccount() {
		return this.aidAccount;
	}

	public void setAidAccount(String aidAccount) {
		this.aidAccount = aidAccount;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "create_date", length = 10)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "code_no", length = 20)
	public String getCodeNo() {
		return this.codeNo;
	}

	public void setCodeNo(String codeNo) {
		this.codeNo = codeNo;
	}

	@Column(name = "summary", length = 100)
	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Column(name = "debit_balance", precision = 32, scale = 6)
	public Double getDebitBalance() {
		return this.debitBalance;
	}

	public void setDebitBalance(Double debitBalance) {
		this.debitBalance = debitBalance;
	}

	@Column(name = "credit_balance", precision = 32, scale = 6)
	public Double getCreditBalance() {
		return this.creditBalance;
	}

	public void setCreditBalance(Double creditBalance) {
		this.creditBalance = creditBalance;
	}

	@Column(name = "balance_direction", length = 10)
	public String getBalanceDirection() {
		return this.balanceDirection;
	}

	public void setBalanceDirection(String balanceDirection) {
		this.balanceDirection = balanceDirection;
	}

	@Column(name = "balance", precision = 32, scale = 6)
	public Double getBalance() {
		return this.balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

}
