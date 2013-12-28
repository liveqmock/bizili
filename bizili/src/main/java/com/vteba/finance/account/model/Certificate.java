package com.vteba.finance.account.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

/**
 * 会计凭证明细
 */
@Entity
@Table(name = "certificate", catalog = "bizili")
public class Certificate implements java.io.Serializable {

	private static final long serialVersionUID = -5633997183463621997L;
	private String id;
	private String summary;//摘要
	private String subjectId;//会计科目
	private String subjectName;//科目名
	private Long quantity;//数量
	private Double unitPrice;//单价
	private String units;//计量单位
	private Double sourceAmount;//原币数量
	private String currency;//汇率
	private double debitAmount;//借方金额
	private double creditAmount;//贷方金额
	private String fourLevel;//四级科目
	private Date createTime;//制单时间
	private String accountPeriod;//会计期间
	private String empId;//制单人id
	private CertTotal parentCert;//凭证汇总父类
	private String oneLevel;
	private String twoLevel;
	private String threeLevel;
	
	/******辅助核算项目*******/
	private String ticketNumber;//票号
	private Date ticketDate;//票号日期
	private String projectId;//项目
	private String customId;//客户
	private String deptId;//部门
	private String salesman;//业务员
	private String personal;//个人
	
	public Certificate() {
	}

	public Certificate(String id, String summary, String subjectId,
			String subjectName, Long quantity, Double unitPrice, String units,
			Double sourceAmount, String currency, double debitAmount,
			double creditAmount, String fourLevel, Date createTime,
			String accountPeriod, String empId, CertTotal parentCert,
			String oneLevel, String twoLevel, String threeLevel,
			String ticketNumber, Date ticketDate, String projectId,
			String customId, String deptId, String salesman, String personal) {
		super();
		this.id = id;
		this.summary = summary;
		this.subjectId = subjectId;
		this.subjectName = subjectName;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.units = units;
		this.sourceAmount = sourceAmount;
		this.currency = currency;
		this.debitAmount = debitAmount;
		this.creditAmount = creditAmount;
		this.fourLevel = fourLevel;
		this.createTime = createTime;
		this.accountPeriod = accountPeriod;
		this.empId = empId;
		this.parentCert = parentCert;
		this.oneLevel = oneLevel;
		this.twoLevel = twoLevel;
		this.threeLevel = threeLevel;
		this.ticketNumber = ticketNumber;
		this.ticketDate = ticketDate;
		this.projectId = projectId;
		this.customId = customId;
		this.deptId = deptId;
		this.salesman = salesman;
		this.personal = personal;
	}

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", unique = true, nullable = false, length = 50)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "summary", nullable = false, length = 200)
	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Column(name = "subject_id", nullable = false, length = 45)
	public String getSubjectId() {
		return this.subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	
	@Column(name = "subject_name", nullable = true, length = 100)
	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	@Column(name = "quantity")
	public Long getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	@Column(name = "unit_price", precision = 32, scale = 6)
	public Double getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	@Column(name = "units", length = 45)
	public String getUnits() {
		return this.units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	@Column(name = "source_amount", precision = 32, scale = 6)
	public Double getSourceAmount() {
		return this.sourceAmount;
	}

	public void setSourceAmount(Double sourceAmount) {
		this.sourceAmount = sourceAmount;
	}

	@Column(name = "currency", length = 100)
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "debit_amount", precision = 32, scale = 6)
	public double getDebitAmount() {
		return this.debitAmount;
	}

	public void setDebitAmount(double debitAmount) {
		this.debitAmount = debitAmount;
	}

	@Column(name = "credit_amount", precision = 32, scale = 6)
	public double getCreditAmount() {
		return this.creditAmount;
	}

	public void setCreditAmount(double creditAmount) {
		this.creditAmount = creditAmount;
	}

	@Column(name = "four_level", length = 45)
	public String getFourLevel() {
		return this.fourLevel;
	}

	public void setFourLevel(String fourLevel) {
		this.fourLevel = fourLevel;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "create_time", nullable = false)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "account_period", nullable = false, length = 10)
	public String getAccountPeriod() {
		return this.accountPeriod;
	}

	public void setAccountPeriod(String accountPeriod) {
		this.accountPeriod = accountPeriod;
	}

	@Column(name = "emp_id", length = 45)
	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cert_id", nullable = false)
	@LazyToOne(LazyToOneOption.PROXY)
	public CertTotal getParentCert() {
		return parentCert;
	}

	public void setParentCert(CertTotal parentCert) {
		this.parentCert = parentCert;
	}
	
	@Column(name = "ticket_number", length = 60)
	public String getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
	
	@Temporal(value = TemporalType.DATE)
	@Column(name = "ticket_date")
	public Date getTicketDate() {
		return ticketDate;
	}

	public void setTicketDate(Date ticketDate) {
		this.ticketDate = ticketDate;
	}
	
	@Column(name = "project_id", length = 60)
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	@Column(name = "custom_id", length = 60)
	public String getCustomId() {
		return customId;
	}

	public void setCustomId(String customId) {
		this.customId = customId;
	}
	
	@Column(name = "dept_id", length = 60)
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	@Column(name = "salesman", length = 30)
	public String getSalesman() {
		return salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}
	
	@Column(name = "personal", length = 30)
	public String getPersonal() {
		return personal;
	}

	public void setPersonal(String personal) {
		this.personal = personal;
	}
	
	@Column(name = "one_level", length = 45)
	public String getOneLevel() {
		return oneLevel;
	}

	public void setOneLevel(String oneLevel) {
		this.oneLevel = oneLevel;
	}

	@Column(name = "two_level", length = 45)
	public String getTwoLevel() {
		return twoLevel;
	}

	public void setTwoLevel(String twoLevel) {
		this.twoLevel = twoLevel;
	}

	@Column(name = "three_level", length = 45)
	public String getThreeLevel() {
		return threeLevel;
	}

	public void setThreeLevel(String threeLevel) {
		this.threeLevel = threeLevel;
	}

}
