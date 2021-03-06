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
 * 应收明细表
 * @author yinlei 
 * date 2012-8-1 下午3:07:49
 */
@Entity
@Table(name = "receivables_detail", catalog = "bizili")
public class ReceivablesDetail implements AstModel {

	private static final long serialVersionUID = -1079680627683871160L;
	private String id;
	private String accountPeriod;
	private String customerName;
	private String customerId;
	private String subjectCode;
	private String subjectName;
	private String currency;
	private Date createDate;
	private String codeNo;
	private String summary;
	private BigDecimal debit;
	private BigDecimal credit;
	private String direction;
	private BigDecimal balance;

	public ReceivablesDetail() {
	}

	public ReceivablesDetail(String id) {
		this.id = id;
	}

	public ReceivablesDetail(String id, String accountPeriod,
			String customerName, String customerId, String subjectCode,
			String subjectName, String currency, Date createDate,
			String codeNo, String summary, BigDecimal debit, BigDecimal credit,
			String direction, BigDecimal balance) {
		this.id = id;
		this.accountPeriod = accountPeriod;
		this.customerName = customerName;
		this.customerId = customerId;
		this.subjectCode = subjectCode;
		this.subjectName = subjectName;
		this.currency = currency;
		this.createDate = createDate;
		this.codeNo = codeNo;
		this.summary = summary;
		this.debit = debit;
		this.credit = credit;
		this.direction = direction;
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

	@Column(name = "account_period", length = 10)
	public String getAccountPeriod() {
		return this.accountPeriod;
	}

	public void setAccountPeriod(String accountPeriod) {
		this.accountPeriod = accountPeriod;
	}

	@Column(name = "customer_name", length = 120)
	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "customer_id", length = 45)
	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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

	@Column(name = "currency", length = 15)
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "create_date", length = 10)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "code_no", length = 60)
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

	@Column(name = "debit", precision = 32, scale = 6)
	public BigDecimal getDebit() {
		return this.debit;
	}

	public void setDebit(BigDecimal debit) {
		this.debit = debit;
	}

	@Column(name = "credit", precision = 32, scale = 6)
	public BigDecimal getCredit() {
		return this.credit;
	}

	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}

	@Column(name = "direction", length = 10)
	public String getDirection() {
		return this.direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	@Column(name = "balance", precision = 32, scale = 6)
	public BigDecimal getBalance() {
		return this.balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

}
