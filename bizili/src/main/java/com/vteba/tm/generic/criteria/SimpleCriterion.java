package com.vteba.tm.generic.criteria;

import java.util.HashMap;
import java.util.Map;

public class SimpleCriterion implements Criterion {
	private String expression;
	private String label;
	private Object value;

	public SimpleCriterion() {
		super();
	}

	public SimpleCriterion(String expression) {
		super();
		this.expression = expression;
	}
	
	public SimpleCriterion(String expression, String label, Object value) {
		super();
		this.expression = expression;
		this.label = label;
		this.value = value;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public Map<String, Object> getMaps() {
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put(":" + label, value);
		return maps;
	}
	
}
