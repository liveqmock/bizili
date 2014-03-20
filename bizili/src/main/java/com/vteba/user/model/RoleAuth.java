package com.vteba.user.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.vteba.common.model.AstModel;

@Entity
@Table(name = "role_auth", catalog = "skmbw")
public class RoleAuth implements AstModel {

	private static final long serialVersionUID = 4105064581070071692L;
	private Long id;
	//private Roles roles;
	private Long roleId;
	private Long authId;
	private String authName;
	private String authDesc;
	private Integer flag;
	private String description;

	public RoleAuth() {
	}

	public RoleAuth(Long id, Long roleId, Long authId, String authName,
			String authDesc, Integer flag, String description) {
		super();
		this.id = id;
		this.roleId = roleId;
		this.authId = authId;
		this.authName = authName;
		this.authDesc = authDesc;
		this.flag = flag;
		this.description = description;
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

	//@ManyToOne(fetch = FetchType.LAZY)
	//@JoinColumn(name = "role_id", nullable = false)
	//public Roles getRoles() {
		//return this.roles;
	//}
	
	@Column(name="role_id")
	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	@Column(name = "auth_id", nullable = false)
	public Long getAuthId() {
		return this.authId;
	}

	public void setAuthId(Long authId) {
		this.authId = authId;
	}

	@Column(name = "auth_name", length = 100)
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

	@Column(name = "flag")
	public Integer getFlag() {
		return this.flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	@Column(name = "description", length = 100)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
