package com.vteba.user.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "authorities", catalog = "skmbw")
public class Authorities implements java.io.Serializable {

	private static final long serialVersionUID = -7617690178463797548L;
	
	private Long authId;
	private String authName;
	private String authDesc;
	private Integer enabled;
	private Long userId;
	private String moduleId;
	private String action;//该权限默认的url
	private String urls;//该权限下所有的url
	private Integer orders;//显示顺序
	private Set<String> resUrls;//该权限下的资源url
	//private Set<Resources> resourceSet = new HashSet<Resources>();
	
	public Authorities() {
	}

	public Authorities(Long authId, String authName, String authDesc,
			Integer enabled, Long userId, String moduleId, String action,
			String urls, Integer orders, Set<String> resUrls) {
		super();
		this.authId = authId;
		this.authName = authName;
		this.authDesc = authDesc;
		this.enabled = enabled;
		this.userId = userId;
		this.moduleId = moduleId;
		this.action = action;
		this.urls = urls;
		this.orders = orders;
		this.resUrls = resUrls;
		//this.resourceSet = resourceSet;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "auth_id", unique = true, nullable = false)
	public Long getAuthId() {
		return this.authId;
	}

	public void setAuthId(Long authId) {
		this.authId = authId;
	}

	@Column(name = "auth_name", nullable = false, length = 100)
	public String getAuthName() {
		return this.authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	@Column(name = "auth_desc", length = 150)
	public String getAuthDesc() {
		return this.authDesc;
	}

	public void setAuthDesc(String authDesc) {
		this.authDesc = authDesc;
	}

	@Column(name = "enabled")
	public Integer getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	@Column(name = "user_id")
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	@Column(name = "module_id")
	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	
	@Column(name = "action")
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	@Column
	public String getUrls() {
		return urls;
	}

	public void setUrls(String urls) {
		this.urls = urls;
	}
	
	@Column
	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}

	@Transient
	public Set<String> getResUrls() {
		return resUrls;
	}

	public void setResUrls(Set<String> resUrls) {
		this.resUrls = resUrls;
	}

//	@ManyToMany
//	@JoinTable(name="auth_resource",
//	joinColumns = {@JoinColumn(name="",referencedColumnName="")})
//	public Set<Resources> getResourceSet() {
//		return resourceSet;
//	}
//
//	public void setResourceSet(Set<Resources> resourceSet) {
//		this.resourceSet = resourceSet;
//	}
	
}
