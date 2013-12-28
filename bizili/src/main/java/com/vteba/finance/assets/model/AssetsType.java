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
 * 固定资产分类
 * @author yinlei 
 * date 2012-7-6 下午6:13:20
 */
@Entity
@Table(name = "assets_type", catalog = "bizili", uniqueConstraints = @UniqueConstraint(columnNames = "type_code"))
public class AssetsType implements AstModel {

	private static final long serialVersionUID = 126908282748448585L;
	private String id;
	private String typeCode;//固定资产类别代码
	private String typeName;//类别名
	private String depreciationMethod;//折旧方法
	private Integer useYearLimit;//使用年限
	private Double netSalvagePercentage;//净残值率
	private String subjectName;//折旧科目名
	private String subjectCode;//折旧科目代码
	private Integer state;//状态
	private String createUser;//创建人
	private Date createDate;//创建日期
	private String remark;//备注

	public AssetsType() {
	}

	public AssetsType(String id, String typeCode, String typeName,
			String depreciationMethod, Integer useYearLimit,
			Double netSalvagePercentage, String subjectName,
			String subjectCode, Integer state, String createUser,
			Date createDate, String remark) {
		this.id = id;
		this.typeCode = typeCode;
		this.typeName = typeName;
		this.depreciationMethod = depreciationMethod;
		this.useYearLimit = useYearLimit;
		this.netSalvagePercentage = netSalvagePercentage;
		this.subjectName = subjectName;
		this.subjectCode = subjectCode;
		this.state = state;
		this.createUser = createUser;
		this.createDate = createDate;
		this.remark = remark;
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

	@Column(name = "type_code", unique = true, length = 45)
	public String getTypeCode() {
		return this.typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	@Column(name = "type_name", length = 100)
	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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

	@Column(name = "net_salvage_percentage", precision = 12, scale = 6)
	public Double getNetSalvagePercentage() {
		return this.netSalvagePercentage;
	}

	public void setNetSalvagePercentage(Double netSalvagePercentage) {
		this.netSalvagePercentage = netSalvagePercentage;
	}

	@Column(name = "subject_name", length = 60)
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

	@Column(name = "state")
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(name = "create_user", length = 45)
	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "create_date", length = 10)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "remark", length = 100)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
