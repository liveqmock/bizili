package com.vteba.finance.payables.model;

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
 * 应付明细表
 * @author yinlei 
 * date 2012-8-1 下午3:06:23
 */
@Entity
@Table(name = "payables_detail", catalog = "bizili")
public class PayablesDetail implements AstModel {

	private static final long serialVersionUID = 6769538042297163644L;
	private String id;
	private String accountPeriod;
	private String supplierName;
	private String supplierId;
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

	public PayablesDetail() {
	}

	public PayablesDetail(String id) {
		this.id = id;
	}

	public PayablesDetail(String id, String accountPeriod, String supplierName,
			String supplierId, String subjectCode, String subjectName,
			String currency, Date createDate, String codeNo, String summary,
			BigDecimal debit, BigDecimal credit, String direction,
			BigDecimal balance) {
		this.id = id;
		this.accountPeriod = accountPeriod;
		this.supplierName = supplierName;
		this.supplierId = supplierId;
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

	@Column(name = "supplier_name", length = 120)
	public String getSupplierName() {
		return this.supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(name = "supplier_id", length = 45)
	public String getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
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
