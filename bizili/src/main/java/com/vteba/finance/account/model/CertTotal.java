package com.vteba.finance.account.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * 凭证明细汇总
 * @author yinlei 
 * date 2012-6-25 下午10:46:03
 */
@Entity
@Table(name = "cert_total", catalog = "bizili")
public class CertTotal implements java.io.Serializable {

	private static final long serialVersionUID = 7598058185267320109L;
	private String id;
	private Double debitAmount;//借方金额合计
	private Double creditAmount;//贷方金额合计
	private Date createDate;//制单日期
	private String codeNo;//凭证编号
	private String summary;//摘要
	private String createEmp;//制单人
	private Date auditDate;//审核日期
	private String auditEmp;//审核人
	private String remark;//备注
	private String system;//所属的系统
	private Integer state;//凭证状态,需要审核等，1待审核，2已审核
	private Integer attacheSheet;//附单据张数
	private String accountPeriod;//会计期间
	private List<Certificate> childCerts = new ArrayList<Certificate>();//凭证明细
	/**
	 * 1、新增，待审核
	 */
	public static final Integer STATE_NEW = 1;
	/**
	 * 2、出纳审核
	 */
	public static final Integer STATE_CASHER = 2;
	/**
	 * 2、审核通过
	 */
	public static final Integer STATE_AUDIT = 3;
	
	public CertTotal() {
	}

	public CertTotal(String id, Double debitAmount,
			Double creditAmount, Date createDate, String codeNo,
			String summary, String createEmp, Date auditDate, String auditEmp,
			String remark, String system, Integer state, Integer attacheSheet,
			String accountPeriod, List<Certificate> childCerts) {
		super();
		this.id = id;
		this.debitAmount = debitAmount;
		this.creditAmount = creditAmount;
		this.createDate = createDate;
		this.codeNo = codeNo;
		this.summary = summary;
		this.createEmp = createEmp;
		this.auditDate = auditDate;
		this.auditEmp = auditEmp;
		this.remark = remark;
		this.system = system;
		this.state = state;
		this.attacheSheet = attacheSheet;
		this.accountPeriod = accountPeriod;
		this.childCerts = childCerts;
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

	@Column(name = "debit_amount", precision = 32, scale = 6)
	public Double getDebitAmount() {
		return this.debitAmount;
	}

	public void setDebitAmount(Double debitAmount) {
		this.debitAmount = debitAmount;
	}

	@Column(name = "credit_amount", precision = 32, scale = 6)
	public Double getCreditAmount() {
		return this.creditAmount;
	}

	public void setCreditAmount(Double creditAmount) {
		this.creditAmount = creditAmount;
	}

	@DateTimeFormat(iso = ISO.DATE)
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

	@Column(name = "create_emp", length = 60)
	public String getCreateEmp() {
		return this.createEmp;
	}

	public void setCreateEmp(String createEmp) {
		this.createEmp = createEmp;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "audit_date", length = 10)
	public Date getAuditDate() {
		return this.auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	@Column(name = "audit_emp", length = 60)
	public String getAuditEmp() {
		return this.auditEmp;
	}

	public void setAuditEmp(String auditEmp) {
		this.auditEmp = auditEmp;
	}

	@Column(name = "remark", length = 100)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "system", length = 100)
	public String getSystem() {
		return this.system;
	}

	public void setSystem(String system) {
		this.system = system;
	}
	
	@Column(name = "state")
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	@Column(name="attache_sheet")
	public Integer getAttacheSheet() {
		return attacheSheet;
	}

	public void setAttacheSheet(Integer attacheSheet) {
		this.attacheSheet = attacheSheet;
	}
	
	@Column(name = "account_period", nullable = false, length = 10)
	public String getAccountPeriod() {
		return accountPeriod;
	}

	public void setAccountPeriod(String accountPeriod) {
		this.accountPeriod = accountPeriod;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parentCert")
	@Fetch(value = FetchMode.SELECT)
	@Cascade(CascadeType.SAVE_UPDATE)
	@LazyCollection(LazyCollectionOption.EXTRA)
	public List<Certificate> getChildCerts() {
		return childCerts;
	}

	public void setChildCerts(List<Certificate> childCerts) {
		this.childCerts = childCerts;
	}

}
