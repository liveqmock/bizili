package com.vteba.user.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_role", catalog = "skmbw")
public class UserRole implements java.io.Serializable {

	private static final long serialVersionUID = 6824674090246547663L;
	private Long id;
	//private EmpUser empUser;
	private Long userId;
	private String roleName;
	private Integer flag;
	private String roleDesc;
	private Long roleId;

	public UserRole() {
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

	//@ManyToOne(fetch = FetchType.LAZY)//支配端
	//@JoinColumn(name = "user_id", nullable = false)//user_id指的是这个表中的栏位
	//public EmpUser getEmpUser() {
		//return this.empUser;
	//}

	//public void setEmpUser(EmpUser empUser) {
		//this.empUser = empUser;
	//}
	@Column(name="user_id", nullable = false)
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	@Column(name = "role_name", length = 100)
	public String getRoleName() {
		return this.roleName;
	}

	@Column(name = "flag")
	public Integer getFlag() {
		return this.flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	@Column(name = "role_desc", length = 150)
	public String getRoleDesc() {
		return this.roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	@Column(name = "role_id", nullable = false)
	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}
