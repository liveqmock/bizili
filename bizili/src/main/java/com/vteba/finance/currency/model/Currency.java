package com.vteba.finance.currency.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 货币
 */
@Entity
@Table(name = "currency", catalog = "bizili")
public class Currency implements java.io.Serializable {

	private static final long serialVersionUID = -6090376141864882322L;
	private String id;
	private String currencyCode;//币别代码
	private String currencyName;//货币名
	private double exchangeRate;//汇率
	private String flag;//是否使用
	private String remarks;//备注
	
	public static final String CUR_TYPE_CN = "CNY";
	public static final String CUR_TYPE_US = "USD";
	public static final String CUR_TYPE_HK = "HKD";
	
	public Currency() {
	}

	public Currency(String id, String currencyCode, String currencyName,
			double exchangeRate, String flag, String remarks) {
		this.id = id;
		this.currencyCode = currencyCode;
		this.currencyName = currencyName;
		this.exchangeRate = exchangeRate;
		this.flag = flag;
		this.remarks = remarks;
	}

	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid", strategy = "org.hibernate.id.UUIDGenerator" )
	@Column(name = "id", unique = true, nullable = false, length = 45)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "currency_code", nullable = false, length = 45)
	public String getCurrencyCode() {
		return this.currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	@Column(name = "currency_name", nullable = false, length = 55)
	public String getCurrencyName() {
		return this.currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	@Column(name = "exchange_rate", nullable = false, precision = 22, scale = 0)
	public double getExchangeRate() {
		return this.exchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	@Column(name = "flag", nullable = false, length = 45)
	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Column(name = "remarks", nullable = false, length = 45)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
