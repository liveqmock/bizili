package com.vteba.finance.account.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/**
 * 常用凭证摘要
 * @author yinlei 
 * date 2012-6-29 下午11:03:02
 */
@Entity
@Table(name = "common_sum", catalog = "bizili")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "com.vteba.finance.account.model.CommonSum")
public class CommonSum implements java.io.Serializable {

	private static final long serialVersionUID = -6829304469942006522L;
	private String id;
	private String sumName;//摘要名
	private Boolean enable;//是否可用

	public CommonSum() {
	}

	public CommonSum(String id, String sumName, Boolean enable) {
		this.id = id;
		this.sumName = sumName;
		this.enable = enable;
	}

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy="org.hibernate.id.UUIDGenerator")
	@Column(name = "id", unique = true, nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "sum_name", length = 100)
	public String getSumName() {
		return this.sumName;
	}

	public void setSumName(String sumName) {
		this.sumName = sumName;
	}

	@Column(name = "enable")
	public Boolean getEnable() {
		return this.enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

}
