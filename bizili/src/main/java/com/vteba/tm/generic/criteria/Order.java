package com.vteba.tm.generic.criteria;

public class Order {
	private String orderBy;
	
	protected Order() {
		
	}
	
	protected Order(String orderBy) {
		this.orderBy = orderBy;
	}
	
	public static Order asc(String name) {
		return new Order(name + " asc ");
	} 
	
	public static Order desc(String name) {
		return new Order(name + " desc ");
	}
	
	public String getOrderBy() {
		return orderBy;
	}
	
	
}
