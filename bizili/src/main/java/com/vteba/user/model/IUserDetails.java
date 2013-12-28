package com.vteba.user.model;

import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * 扩展spring security的userDetails接口，SpringSecurityContext返回该接口的实现
 * @author yinlei
 *
 */
public interface IUserDetails extends UserDetails {

	public Long getUserId();

	public String getName();

	public String getEmail();

	public String getPass();

	//public String getDomain();

	//public Integer getFlag();

	//public String getDescription();

	public Date getCreateDate();

	public Integer getEnable();

	//public Integer getPriority();

	public String getUserAccount();

	///public Set<UserRole> getUserRoles();
}
