package com.vteba.finance.common;

public class TestEnum {

	/**
	 * @param args
	 * @author yinlei
	 * date 2013-3-31 下午3:24:25
	 */
	public static void main(String[] args) {
		
		for (Const method : Const.values()){
			System.out.print(method.name() + " ");
			System.out.print(method.toString() + " ");
			System.out.println(method.ordinal() + " ");
		}
		
		for (AccountMethod method : AccountMethod.values()){
			System.out.print(method.name() + " ");
			System.out.print(method.toString() + " ");
			System.out.print(method.ordinal() + " ");
			System.out.println(method.value());
		}
		System.out.println(AccountMethod.BALANCE_ADD);
		System.out.println(AccountMethod.valueOf("BALANCE_ADD"));	
		System.out.println(AccountMethod.valueOf(AccountMethod.class, "BALANCE_ADD"));
	}

}
