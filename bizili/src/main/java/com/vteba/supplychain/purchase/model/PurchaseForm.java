package com.vteba.supplychain.purchase.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.vteba.common.model.AstModel;

/**
 * 采购单
 * @author yinlei 
 * date 2012-8-7 上午10:24:52
 */
@Entity
@Table(name = "purchase_form", catalog = "supplychain")
public class PurchaseForm implements AstModel {

	private static final long serialVersionUID = 21002193568013244L;
	private String orderId;
	private String empNo;
	private String empName;
	private Date purchaseDate;
	private String projectId;
	private String projectName;
	private String supplierId;
	private String supplierName;
	private String currency;
	private Double rate;
	private String address;
	private String remark;
	private Date deliveryDate;
	private String supplierInvoice;
	private Double discount;
	private BigDecimal discountAmount;
	private String payment;
	private String payTerms;
	private Integer accountLength;
	private Date deadline;

	public PurchaseForm() {
	}

	public PurchaseForm(String orderId) {
		this.orderId = orderId;
	}

	public PurchaseForm(String orderId, String empNo, String empName,
			Date purchaseDate, String projectId, String projectName,
			String supplierId, String supplierName, String currency,
			Double rate, String address, String remark, Date deliveryDate,
			String supplierInvoice, Double discount, BigDecimal discountAmount,
			String payment, String payTerms, Integer accountLength,
			Date deadline) {
		this.orderId = orderId;
		this.empNo = empNo;
		this.empName = empName;
		this.purchaseDate = purchaseDate;
		this.projectId = projectId;
		this.projectName = projectName;
		this.supplierId = supplierId;
		this.supplierName = supplierName;
		this.currency = currency;
		this.rate = rate;
		this.address = address;
		this.remark = remark;
		this.deliveryDate = deliveryDate;
		this.supplierInvoice = supplierInvoice;
		this.discount = discount;
		this.discountAmount = discountAmount;
		this.payment = payment;
		this.payTerms = payTerms;
		this.accountLength = accountLength;
		this.deadline = deadline;
	}

	@Id
	@Column(name = "order_id", unique = true, nullable = false, length = 45)
	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Column(name = "emp_no", length = 45)
	public String getEmpNo() {
		return this.empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	@Column(name = "emp_name", length = 45)
	public String getEmpName() {
		return this.empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "purchase_date", length = 10)
	public Date getPurchaseDate() {
		return this.purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	@Column(name = "project_id", length = 45)
	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(name = "project_name", length = 60)
	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Column(name = "supplier_id", length = 45)
	public String getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	@Column(name = "supplier_name", length = 90)
	public String getSupplierName() {
		return this.supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(name = "currency", length = 15)
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "rate", precision = 10, scale = 6)
	public Double getRate() {
		return this.rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	@Column(name = "address", length = 100)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "remark", length = 50)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "delivery_date", length = 10)
	public Date getDeliveryDate() {
		return this.deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	@Column(name = "supplier_invoice", length = 60)
	public String getSupplierInvoice() {
		return this.supplierInvoice;
	}

	public void setSupplierInvoice(String supplierInvoice) {
		this.supplierInvoice = supplierInvoice;
	}

	@Column(name = "discount", precision = 10, scale = 6)
	public Double getDiscount() {
		return this.discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	@Column(name = "discount_amount", precision = 32, scale = 6)
	public BigDecimal getDiscountAmount() {
		return this.discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	@Column(name = "payment", length = 45)
	public String getPayment() {
		return this.payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	@Column(name = "pay_terms", length = 45)
	public String getPayTerms() {
		return this.payTerms;
	}

	public void setPayTerms(String payTerms) {
		this.payTerms = payTerms;
	}

	@Column(name = "account_length")
	public Integer getAccountLength() {
		return this.accountLength;
	}

	public void setAccountLength(Integer accountLength) {
		this.accountLength = accountLength;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "deadline", length = 10)
	public Date getDeadline() {
		return this.deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

}
