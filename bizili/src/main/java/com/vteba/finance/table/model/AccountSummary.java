package com.vteba.finance.table.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.vteba.common.model.AstModel;

/**
 * 科目汇总表
 * @author yinlei 
 * date 2012-7-9 下午3:39:27
 */
@Entity
@Table(name = "account_summary", catalog = "bizili")
@NamedQueries(value = {
		@NamedQuery(name = "accountSummary.queryAccountSumByEq", query = "select a from AccountSummary a where a.accountPeriod between :accountPeriod and :endPeriod and level = :level and currency = :currency order by a.subjectCode asc"),
		@NamedQuery(name = "accountSummary.queryAccountSumByIn", query = "select a from AccountSummary a where a.accountPeriod between :accountPeriod and :endPeriod and level in (:levels) and currency = :currency order by a.subjectCode asc") })
public class AccountSummary implements AstModel {
	private static final long serialVersionUID = -5021823466301330627L;
	private String id;
	private String subjectCode;//科目代码
	private String subjectName;//科目名称
	private String accountPeriod;//会计期间
	private Double debit;//借方金额
	private Double credit;//贷方金额
	private Date createDate;//创建日期
	private String currency;//货币单位
	private String createUser;//制单人
	private Integer level;//科目级别
	//transient field
	private String endPeriod;//会计期间，按范围查询，表结束值

	public AccountSummary() {
	}

	public AccountSummary(String id, String subjectCode, String subjectName,
			String accountPeriod, Double debit, Double credit, Date createDate,
			String currency, String createUser, Integer level) {
		super();
		this.id = id;
		this.subjectCode = subjectCode;
		this.subjectName = subjectName;
		this.accountPeriod = accountPeriod;
		this.debit = debit;
		this.credit = credit;
		this.createDate = createDate;
		this.currency = currency;
		this.createUser = createUser;
		this.level = level;
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
	
	@Column(name = "account_period", length = 45)
	public String getAccountPeriod() {
		return accountPeriod;
	}

	public void setAccountPeriod(String accountPeriod) {
		this.accountPeriod = accountPeriod;
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

	@Temporal(TemporalType.DATE)
	@Column(name = "create_date", length = 10)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "currency", length = 15)
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "create_user", length = 45)
	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	@Column(name = "level")
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	@Transient
	public String getEndPeriod() {
		return endPeriod;
	}

	public void setEndPeriod(String endPeriod) {
		this.endPeriod = endPeriod;
	}
	
}
