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
 * 日记账：银行存款、现金
 * @author yinlei 
 * date 2012-7-6 下午6:28:26
 */
@Entity
@Table(name = "daily_account", catalog = "bizili")
public class DailyAccount implements AstModel {
	private static final long serialVersionUID = -6278087264526509002L;
	private String id;
	private String codeNo;//凭证字号
	private Date createDate;//创建日期
	private String createUser;//制单人
	private String subjectCode;//科目代码
	private String subjectName;//科目名
	private String accountPeriod;//会计期间
	private String type;//日记账类型，银行存款、现金
	private String summary;//摘要
	private Double debit;//借方金额
	private Double credit;//贷方金额
	private Double balance;//余额
	private String balanceDirection;//余额方向
	private Integer orders;//显示顺序

	public DailyAccount() {
	}

	public DailyAccount(String codeNo, Date createDate, String subjectCode,
			String subjectName, String accountPeriod, String summary,
			Double debit, Double credit) {
		super();
		this.codeNo = codeNo;
		this.createDate = createDate;
		this.subjectCode = subjectCode;
		this.subjectName = subjectName;
		this.accountPeriod = accountPeriod;
		this.summary = summary;
		this.debit = debit;
		this.credit = credit;
	}

	public DailyAccount(String id, String codeNo, Date createDate,
			String createUser, String subjectCode, String subjectName,
			String accountPeriod, String type, String summary,
			Double debit, Double credit, Double balance,
			String balanceDirection, Integer orders) {
		this.id = id;
		this.codeNo = codeNo;
		this.createDate = createDate;
		this.createUser = createUser;
		this.subjectCode = subjectCode;
		this.subjectName = subjectName;
		this.accountPeriod = accountPeriod;
		this.type = type;
		this.summary = summary;
		this.debit = debit;
		this.credit = credit;
		this.balance = balance;
		this.balanceDirection = balanceDirection;
		this.orders = orders;
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

	@Column(name = "code_no", length = 20)
	public String getCodeNo() {
		return this.codeNo;
	}

	public void setCodeNo(String codeNo) {
		this.codeNo = codeNo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "create_date", length = 10)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "create_user", length = 45)
	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
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

	@Column(name = "account_period", length = 12)
	public String getAccountPeriod() {
		return this.accountPeriod;
	}

	public void setAccountPeriod(String accountPeriod) {
		this.accountPeriod = accountPeriod;
	}

	@Column(name = "type", length = 45)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "summary", length = 100)
	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Column(name = "debit", precision = 32, scale = 6)
	public Double getDebit() {
		return this.debit;
	}

	public void setDebit(Double debit) {
		this.debit = debit;
	}

	@Column(name = "credit", precision = 32, scale = 6)
	public Double getCredit() {
		return this.credit;
	}

	public void setCredit(Double credit) {
		this.credit = credit;
	}

	@Column(name = "balance", precision = 32, scale = 6)
	public Double getBalance() {
		return this.balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	@Column(name = "balance_direction", length = 10)
	public String getBalanceDirection() {
		return this.balanceDirection;
	}

	public void setBalanceDirection(String balanceDirection) {
		this.balanceDirection = balanceDirection;
	}
	
	@Column(name = "orders")
	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}

}
