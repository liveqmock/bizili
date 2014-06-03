package com.vteba.service.xml;

/**
 * 用来封装&lt;field name="userName"&gt;yinlei&lt;field&gt;这样的xml.
 * @author yinlei
 * @date 2013年12月1日 下午8:35:55
 */
public class Field {
	private String name;
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
