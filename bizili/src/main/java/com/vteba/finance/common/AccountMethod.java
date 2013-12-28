package com.vteba.finance.common;

/**
 * 资产负债表核算类型
 * @author yinlei
 * date 2013-3-31 下午3:36:55
 */
public enum AccountMethod {
	
	/**余额相加*/
	BALANCE_ADD("BalanceAdd"),
	
	/**借方余额*/
	DEBIT_BALANCE("DebitBalance"),
	
	/**贷方余额*/
	CREDIT_BALANCE("CreditBalance"),
	
	/**应收应付*/
	BALANCE_OR_REVERSE("BalanceOrReverse"),
	
	/**合计项*/
	ITEM_SUMMARY("ItemSummary");
	
	private String value;
	
	private AccountMethod(String value) {
		this.value = value;
	}
	
	public String value() {
		return this.value;
	}
	
	public String toString() {
		return value();
	}
}
