package com.vteba.finance.report.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.vteba.common.model.AstModel;

/**
 * 资产负债表
 * @author yinlei 
 * date 2012-6-30 上午12:07:34
 */
@Entity
@Table(name = "assets_liabilities", catalog = "bizili")
public class AssetsLiabilities implements AstModel {

	private static final long serialVersionUID = 2159314651567509753L;
	private String id;
	private String accountPeriod;//会计期间
	private Date createDate;//创建时间
	private String itemName;//项目名/科目名称
	private String itemCode;//项目代码/科目代码
	private Integer rowNumber;//行次
	private Double endingCount;//期末数
	private Double yearBeginCount;//年初数
	private String itemType;//细项类型，分为流动资产-流动负债-非流动资产-非流动负债等
	private String industry;//哪个行业的利润表
	private Integer orders;//显示顺序
	private String category;//分类，是资产，负债和权益
	private String accountMethod;//计算方法
	
	public AssetsLiabilities() {
	}

	public AssetsLiabilities(String id, String accountPeriod, Date createDate,
			String itemName, String itemCode, Integer rowNumber,
			Double endingCount, Double yearBeginCount, String itemType,
			String industry, Integer orders, String category, String accountMethod) {
		super();
		this.id = id;
		this.accountPeriod = accountPeriod;
		this.createDate = createDate;
		this.itemName = itemName;
		this.itemCode = itemCode;
		this.rowNumber = rowNumber;
		this.endingCount = endingCount;
		this.yearBeginCount = yearBeginCount;
		this.itemType = itemType;
		this.industry = industry;
		this.orders = orders;
		this.category = category;
		this.accountMethod = accountMethod;
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

	@Column(name = "account_period", nullable = false, length = 10)
	public String getAccountPeriod() {
		return this.accountPeriod;
	}

	public void setAccountPeriod(String accountPeriod) {
		this.accountPeriod = accountPeriod;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@Column(name = "item_name", length = 150)
	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column(name = "item_code", length = 100)
	public String getItemCode() {
		return this.itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	@Column(name = "row_number")
	public Integer getRowNumber() {
		return this.rowNumber;
	}

	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}

	@Column(name = "ending_count", precision = 32, scale = 6)
	public Double getEndingCount() {
		return this.endingCount;
	}

	public void setEndingCount(Double endingCount) {
		this.endingCount = endingCount;
	}

	@Column(name = "year_begin_count", precision = 32, scale = 6)
	public Double getYearBeginCount() {
		return this.yearBeginCount;
	}

	public void setYearBeginCount(Double yearBeginCount) {
		this.yearBeginCount = yearBeginCount;
	}

	@Column(name = "item_type", length = 45)
	public String getItemType() {
		return this.itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	
	@Column(name = "industry", length = 30)
	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}
	
	@Column(name = "orders")
	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}
	
	@Column(name = "category", length = 45)
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	@Column(name = "account_method", length = 50)
	public String getAccountMethod() {
		return accountMethod;
	}

	public void setAccountMethod(String accountMethod) {
		this.accountMethod = accountMethod;
	}
	
}
