package com.vteba.user.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "auth_resource", catalog = "skmbw")
public class AuthResource implements java.io.Serializable {

	private static final long serialVersionUID = -3410747018225959845L;
	
	private Long id;
	private Long authId;
	private String resourceName;
	private String resourceType;
	private String resourceUrl;
	private Boolean enable;//资源是否可用
	private Integer defaults;//是否是某一权限的默认url,1是，2否

	public AuthResource() {
	}

	public AuthResource(Long id, Long authId, String resourceName,
			String resourceType, String resourceUrl, Boolean enable,
			Integer defaults) {
		super();
		this.id = id;
		this.authId = authId;
		this.resourceName = resourceName;
		this.resourceType = resourceType;
		this.resourceUrl = resourceUrl;
		this.enable = enable;
		this.defaults = defaults;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="auth_id")
	public Long getAuthId() {
		return authId;
	}

	public void setAuthId(Long authId) {
		this.authId = authId;
	}

	@Column(name = "resource_name", length = 100)
	public String getResourceName() {
		return this.resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	@Column(name = "resource_type", nullable = false, length = 50)
	public String getResourceType() {
		return this.resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	@Column(name = "resource_url", nullable = false, length = 200)
	public String getResourceUrl() {
		return this.resourceUrl;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}
	
	@Column(name="enable")
	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	@Column
	public Integer getDefaults() {
		return defaults;
	}

	public void setDefaults(Integer defaults) {
		this.defaults = defaults;
	}

}
