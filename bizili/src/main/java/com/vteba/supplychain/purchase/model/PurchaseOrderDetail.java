package com.vteba.supplychain.purchase.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.vteba.common.model.AstModel;

/**
 * 采购订单明细
 * @author yinlei 
 * date 2012-8-7 下午5:27:54
 */
@Entity
@Table(name = "purchase_order_detail", catalog = "supplychain")
public class PurchaseOrderDetail implements AstModel {

	private static final long serialVersionUID = 1244876692677846483L;
	private String id;
	private String orderId;
	private String stockId;
	private String stockName;
	private String gg;
	private String warehouseName;
	private String warehouseId;
	private Double quantity;
	private BigDecimal price;
	private String priceType;
	private Double discount;
	private Double discountAmount;
	private BigDecimal purchases;
	private BigDecimal amount;
	private Double taxRate;
	private BigDecimal taxAmount;
	private String remark;

	public PurchaseOrderDetail() {
	}

	public PurchaseOrderDetail(String id) {
		this.id = id;
	}

	public PurchaseOrderDetail(String id, String orderId, String stockId,
			String stockName, String gg, String warehouseName,
			String warehouseId, Double quantity, BigDecimal price,
			String priceType, Double discount, Double discountAmount,
			BigDecimal purchases, BigDecimal amount, Double taxRate,
			BigDecimal taxAmount, String remark) {
		this.id = id;
		this.orderId = orderId;
		this.stockId = stockId;
		this.stockName = stockName;
		this.gg = gg;
		this.warehouseName = warehouseName;
		this.warehouseId = warehouseId;
		this.quantity = quantity;
		this.price = price;
		this.priceType = priceType;
		this.discount = discount;
		this.discountAmount = discountAmount;
		this.purchases = purchases;
		this.amount = amount;
		this.taxRate = taxRate;
		this.taxAmount = taxAmount;
		this.remark = remark;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 45)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "order_id", length = 45)
	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Column(name = "stock_id", length = 45)
	public String getStockId() {
		return this.stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	@Column(name = "stock_name", length = 100)
	public String getStockName() {
		return this.stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	@Column(name = "gg", length = 100)
	public String getGg() {
		return this.gg;
	}

	public void setGg(String gg) {
		this.gg = gg;
	}

	@Column(name = "warehouse_name", length = 100)
	public String getWarehouseName() {
		return this.warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	@Column(name = "warehouse_id", length = 45)
	public String getWarehouseId() {
		return this.warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	@Column(name = "quantity", precision = 12)
	public Double getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	@Column(name = "price", precision = 32, scale = 6)
	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Column(name = "price_type", length = 30)
	public String getPriceType() {
		return this.priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	@Column(name = "discount", precision = 10)
	public Double getDiscount() {
		return this.discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	@Column(name = "discount_amount", precision = 12)
	public Double getDiscountAmount() {
		return this.discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	@Column(name = "purchases", precision = 32, scale = 6)
	public BigDecimal getPurchases() {
		return this.purchases;
	}

	public void setPurchases(BigDecimal purchases) {
		this.purchases = purchases;
	}

	@Column(name = "amount", precision = 32, scale = 6)
	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Column(name = "tax_rate", precision = 10, scale = 4)
	public Double getTaxRate() {
		return this.taxRate;
	}

	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}

	@Column(name = "tax_amount", precision = 32, scale = 6)
	public BigDecimal getTaxAmount() {
		return this.taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	@Column(name = "remark", length = 100)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
