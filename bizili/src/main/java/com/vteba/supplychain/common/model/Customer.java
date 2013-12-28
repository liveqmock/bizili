package com.vteba.supplychain.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.vteba.common.model.AstModel;

/**
 * 客户档案
 * @author yinlei 
 * date 2012-8-7 上午10:42:37
 */
@Entity
@Table(name = "customer", catalog = "supplychain", uniqueConstraints = @UniqueConstraint(columnNames = "customer_id"))
public class Customer implements AstModel {

	private static final long serialVersionUID = 416891249155958560L;
	private String id;
	private String customerId;
	private String customerName;
	private String contact;
	private String phoneNumber;
	private String mobilNumber;
	private String salesman;
	private String category;
	private String region;
	private String state;

	public Customer() {
	}

	public Customer(String id, String customerId, String customerName) {
		this.id = id;
		this.customerId = customerId;
		this.customerName = customerName;
	}

	public Customer(String id, String customerId, String customerName,
			String contact, String phoneNumber, String mobilNumber,
			String salesman, String category, String region, String state) {
		this.id = id;
		this.customerId = customerId;
		this.customerName = customerName;
		this.contact = contact;
		this.phoneNumber = phoneNumber;
		this.mobilNumber = mobilNumber;
		this.salesman = salesman;
		this.category = category;
		this.region = region;
		this.state = state;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 45)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "customer_id", unique = true, nullable = false, length = 45)
	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@Column(name = "customer_name", nullable = false, length = 100)
	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "contact", length = 45)
	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Column(name = "phone_number", length = 12)
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Column(name = "mobil_number", length = 12)
	public String getMobilNumber() {
		return this.mobilNumber;
	}

	public void setMobilNumber(String mobilNumber) {
		this.mobilNumber = mobilNumber;
	}

	@Column(name = "salesman", length = 45)
	public String getSalesman() {
		return this.salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}

	@Column(name = "category", length = 45)
	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Column(name = "region", length = 100)
	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	@Column(name = "state", length = 24)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
