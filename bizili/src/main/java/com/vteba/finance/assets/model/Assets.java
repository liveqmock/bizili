package com.vteba.finance.assets.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import com.vteba.common.model.AstModel;

/**
 * 固定资产
 * @author yinlei 
 * date 2012-6-6 下午6:13:20
 */
@Entity
@Table(name = "assets", catalog = "bizili", uniqueConstraints = @UniqueConstraint(columnNames = "assets_code"))
public class Assets implements AstModel {

	private static final long serialVersionUID = -2237612031375737518L;
	private String id;
	private String assetsCode;//资产代码
	private String assetsName;//资产名
	private Date createTime;//创建时间
	private String createEmp;//创建人
	private String depreciationMethod;//折旧方法
	private Integer useYearLimit;//折旧年限/预计使用年限
	private Double netSalvage;//预计净残值
	private String subjectName;//折旧科目名
	private String subjectCode;//折旧科目代码
	private String remarks;//备注
	
	private String typeName;//资产类别名
	private String typeCode;//资产类别代码
	private String departUser;//使用部门
	private Date startDate;//开始使用日期
	private Double useCount;//使用数量
	private String accountPeriod;//录入会计期间
	private String depreFeeSubject;//折旧费用科目
	private Double sourceValue;//原值
	private Double netSalvagePercent;//残值率
	private Integer alreadyDepreAccount;//已经折旧会计期间
	private Double startDepreSum;//期初折旧累计
	private Double prepareImpairment;//减值准备
	private Double netValue;//净值
	private Double deprePerMonth;//月折旧
	
	public Assets() {
	}

	public Assets(String id) {
		this.id = id;
	}

	public Assets(String id, String assetsCode, String assetsName,
			Date createTime, String createEmp, String depreciationMethod,
			Integer useYearLimit, Double netSalvage, String subjectName,
			String subjectCode, String remarks, String typeName,
			String typeCode, String departUser, Date startDate,
			Double useCount, String accountPeriod, String depreFeeSubject,
			Double sourceValue, Double netSalvagePercent,
			Integer alreadyDepreAccount, Double startDepreSum,
			Double prepareImpairment, Double netValue,
			Double deprePerMonth) {
		this.id = id;
		this.assetsCode = assetsCode;
		this.assetsName = assetsName;
		this.createTime = createTime;
		this.createEmp = createEmp;
		this.depreciationMethod = depreciationMethod;
		this.useYearLimit = useYearLimit;
		this.netSalvage = netSalvage;
		this.subjectName = subjectName;
		this.subjectCode = subjectCode;
		this.remarks = remarks;
		this.typeName = typeName;
		this.typeCode = typeCode;
		this.departUser = departUser;
		this.startDate = startDate;
		this.useCount = useCount;
		this.accountPeriod = accountPeriod;
		this.depreFeeSubject = depreFeeSubject;
		this.sourceValue = sourceValue;
		this.netSalvagePercent = netSalvagePercent;
		this.alreadyDepreAccount = alreadyDepreAccount;
		this.startDepreSum = startDepreSum;
		this.prepareImpairment = prepareImpairment;
		this.netValue = netValue;
		this.deprePerMonth = deprePerMonth;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 45)
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid", strategy = "org.hibernate.id.UUIDGenerator" )
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "assets_code", unique = true, length = 45)
	public String getAssetsCode() {
		return this.assetsCode;
	}

	public void setAssetsCode(String assetsCode) {
		this.assetsCode = assetsCode;
	}

	@Column(name = "assets_name", length = 150)
	public String getAssetsName() {
		return this.assetsName;
	}

	public void setAssetsName(String assetsName) {
		this.assetsName = assetsName;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "create_emp", length = 45)
	public String getCreateEmp() {
		return this.createEmp;
	}

	public void setCreateEmp(String createEmp) {
		this.createEmp = createEmp;
	}

	@Column(name = "depreciation_method", length = 100)
	public String getDepreciationMethod() {
		return this.depreciationMethod;
	}

	public void setDepreciationMethod(String depreciationMethod) {
		this.depreciationMethod = depreciationMethod;
	}

	@Column(name = "use_year_limit")
	public Integer getUseYearLimit() {
		return this.useYearLimit;
	}

	public void setUseYearLimit(Integer useYearLimit) {
		this.useYearLimit = useYearLimit;
	}

	@Column(name = "net_salvage", precision = 22, scale = 0)
	public Double getNetSalvage() {
		return this.netSalvage;
	}

	public void setNetSalvage(Double netSalvage) {
		this.netSalvage = netSalvage;
	}

	@Column(name = "subject_name", length = 45)
	public String getSubjectName() {
		return this.subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	@Column(name = "subject_code", length = 45)
	public String getSubjectCode() {
		return this.subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	@Column(name = "remarks", length = 150)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "type_name", length = 100)
	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Column(name = "type_code", length = 45)
	public String getTypeCode() {
		return this.typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	@Column(name = "depart_user", length = 45)
	public String getDepartUser() {
		return this.departUser;
	}

	public void setDepartUser(String departUser) {
		this.departUser = departUser;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "start_date", length = 10)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "use_count", precision = 22, scale = 0)
	public Double getUseCount() {
		return this.useCount;
	}

	public void setUseCount(Double useCount) {
		this.useCount = useCount;
	}

	@Column(name = "account_period", length = 12)
	public String getAccountPeriod() {
		return this.accountPeriod;
	}

	public void setAccountPeriod(String accountPeriod) {
		this.accountPeriod = accountPeriod;
	}

	@Column(name = "depre_fee_subject", length = 45)
	public String getDepreFeeSubject() {
		return this.depreFeeSubject;
	}

	public void setDepreFeeSubject(String depreFeeSubject) {
		this.depreFeeSubject = depreFeeSubject;
	}

	@Column(name = "source_value", precision = 32, scale = 6)
	public Double getSourceValue() {
		return this.sourceValue;
	}

	public void setSourceValue(Double sourceValue) {
		this.sourceValue = sourceValue;
	}

	@Column(name = "net_salvage_percent", precision = 12, scale = 6)
	public Double getNetSalvagePercent() {
		return this.netSalvagePercent;
	}

	public void setNetSalvagePercent(Double netSalvagePercent) {
		this.netSalvagePercent = netSalvagePercent;
	}

	@Column(name = "already_depre_account")
	public Integer getAlreadyDepreAccount() {
		return this.alreadyDepreAccount;
	}

	public void setAlreadyDepreAccount(Integer alreadyDepreAccount) {
		this.alreadyDepreAccount = alreadyDepreAccount;
	}

	@Column(name = "start_depre_sum", precision = 32, scale = 6)
	public Double getStartDepreSum() {
		return this.startDepreSum;
	}

	public void setStartDepreSum(Double startDepreSum) {
		this.startDepreSum = startDepreSum;
	}

	@Column(name = "prepare_impairment", precision = 32, scale = 6)
	public Double getPrepareImpairment() {
		return this.prepareImpairment;
	}

	public void setPrepareImpairment(Double prepareImpairment) {
		this.prepareImpairment = prepareImpairment;
	}

	@Column(name = "net_value", precision = 32, scale = 6)
	public Double getNetValue() {
		return this.netValue;
	}

	public void setNetValue(Double netValue) {
		this.netValue = netValue;
	}

	@Column(name = "depre_per_month", precision = 32, scale = 6)
	public Double getDeprePerMonth() {
		return this.deprePerMonth;
	}

	public void setDeprePerMonth(Double deprePerMonth) {
		this.deprePerMonth = deprePerMonth;
	}

}
