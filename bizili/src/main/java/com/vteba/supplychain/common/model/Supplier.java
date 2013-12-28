package com.vteba.supplychain.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.vteba.common.model.AstModel;

/**
 * 供应商档案
 * @author yinlei 
 * date 2012-8-7 上午10:43:02
 */
@Entity
@Table(name = "supplier", catalog = "supplychain")
public class Supplier implements AstModel {
	
	private static final long serialVersionUID = -300473588554975293L;
	private String id;
	private String supplierId;
	private String supplierName;
	private String contact;
	private String address;
	private String phoneNumber;
	private String mobilNumber;
	private String category;
	private String state;

	public Supplier() {
	}

	public Supplier(String id, String supplierId, String supplierName) {
		this.id = id;
		this.supplierId = supplierId;
		this.supplierName = supplierName;
	}

	public Supplier(String id, String supplierId, String supplierName,
			String contact, String address, String phoneNumber,
			String mobilNumber, String category, String state) {
		this.id = id;
		this.supplierId = supplierId;
		this.supplierName = supplierName;
		this.contact = contact;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.mobilNumber = mobilNumber;
		this.category = category;
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

	@Column(name = "supplier_id", nullable = false, length = 45)
	public String getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	@Column(name = "supplier_name", nullable = false, length = 120)
	public String getSupplierName() {
		return this.supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(name = "contact", length = 45)
	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Column(name = "address", length = 150)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	@Column(name = "category", length = 45)
	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Column(name = "state", length = 24)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
