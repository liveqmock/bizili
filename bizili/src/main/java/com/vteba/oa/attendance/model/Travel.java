package com.vteba.oa.attendance.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 出差单
 * @author yinlei 
 * date 2012-7-6 下午6:57:52
 */
//@Entity
//@Table(name = "travel", catalog = "skmbw")
public class Travel implements java.io.Serializable {
	
	private static final long serialVersionUID = -9213839265063609622L;
	private String id;
	private Date startTime;
	private Date endTime;
	private String empId;
	private double fee;
	private String empName;
	private String deptId;
	private String deptName;
	private String reason;
	private String projectId;
	private String projectName;
	private String flag;
	private String description;

	public Travel() {
	}

	public Travel(String id, Date startTime, Date endTime, String empId,
			double fee, String empName, String deptId, String deptName,
			String reason, String projectId, String projectName, String flag,
			String description) {
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.empId = empId;
		this.fee = fee;
		this.empName = empName;
		this.deptId = deptId;
		this.deptName = deptName;
		this.reason = reason;
		this.projectId = projectId;
		this.projectName = projectName;
		this.flag = flag;
		this.description = description;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_time", nullable = false, length = 19)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_time", nullable = false, length = 19)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "emp_id", nullable = false, length = 45)
	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	@Column(name = "fee", nullable = false, precision = 22, scale = 0)
	public double getFee() {
		return this.fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	@Column(name = "emp_name", nullable = false, length = 45)
	public String getEmpName() {
		return this.empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	@Column(name = "dept_id", nullable = false, length = 45)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "dept_name", nullable = false, length = 100)
	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "reason", nullable = false, length = 200)
	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Column(name = "project_id", nullable = false, length = 45)
	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(name = "project_name", nullable = false, length = 100)
	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Column(name = "flag", nullable = false, length = 45)
	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Column(name = "description", nullable = false, length = 45)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}