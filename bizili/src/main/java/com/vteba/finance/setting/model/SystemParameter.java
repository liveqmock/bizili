package com.vteba.finance.setting.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import com.vteba.common.model.AstModel;

/**
 * 系统参数设定
 */
@Entity
@Table(name = "system_parameter", catalog = "bizili")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region="com.vteba.finance.setting.model.SystemParameter")
@Immutable
public class SystemParameter implements AstModel {
	private static final long serialVersionUID = 4698816774682061571L;
	private String id;
	private String types;//类型名
	private String name;//参数名
	private String values;//参数值
	private String remark;//备注
	private String beizhu;//备用的栏位

	public SystemParameter() {
	}

	public SystemParameter(String id, String types, String name, String values,
			String remark, String beizhu) {
		this.id = id;
		this.types = types;
		this.name = name;
		this.values = values;
		this.remark = remark;
		this.beizhu = beizhu;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 45)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "types", nullable = false, length = 45)
	public String getTypes() {
		return this.types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	@Column(name = "name", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "values", nullable = false, length = 100)
	public String getValues() {
		return this.values;
	}

	public void setValues(String values) {
		this.values = values;
	}

	@Column(name = "remark", nullable = false, length = 150)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "beizhu", nullable = false, length = 45)
	public String getBeizhu() {
		return this.beizhu;
	}

	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}
}
