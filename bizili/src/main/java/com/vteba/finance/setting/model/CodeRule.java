package com.vteba.finance.setting.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 基础数据编码规则
 * @author yinlei 
 * date 2012-6-29 下午10:37:33
 */
@Entity
@Table(name = "code_rule", catalog = "bizili")
public class CodeRule implements java.io.Serializable {

	private static final long serialVersionUID = -5549272589522454916L;
	private String id;
	private String ruleCode;//规则英文代码
	private String ruleName;//规则名
	private String codePrefix;//编码前缀
	private Integer serialNumber;//流水号位数
	private Integer startSerial;//流水号起始数
	private Boolean defaults;//是否默认
	private Boolean enable;//使用可用

	public CodeRule() {
	}

	public CodeRule(String id, String ruleCode, String ruleName,
			String codePrefix, Integer serialNumber, Integer startSerial,
			Boolean defaults, Boolean enable) {
		this.id = id;
		this.ruleCode = ruleCode;
		this.ruleName = ruleName;
		this.codePrefix = codePrefix;
		this.serialNumber = serialNumber;
		this.startSerial = startSerial;
		this.defaults = defaults;
		this.enable = enable;
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

	@Column(name = "rule_code", length = 10)
	public String getRuleCode() {
		return this.ruleCode;
	}

	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}

	@Column(name = "rule_name", length = 45)
	public String getRuleName() {
		return this.ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	@Column(name = "code_prefix", length = 30)
	public String getCodePrefix() {
		return this.codePrefix;
	}

	public void setCodePrefix(String codePrefix) {
		this.codePrefix = codePrefix;
	}

	@Column(name = "serial_number")
	public Integer getSerialNumber() {
		return this.serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Column(name = "start_serial")
	public Integer getStartSerial() {
		return this.startSerial;
	}

	public void setStartSerial(Integer startSerial) {
		this.startSerial = startSerial;
	}

	@Column(name = "defaults")
	public Boolean getDefaults() {
		return this.defaults;
	}

	public void setDefaults(Boolean defaults) {
		this.defaults = defaults;
	}

	@Column(name = "enable")
	public Boolean getEnable() {
		return this.enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

}
