package com.vteba.tm.generic.criteria;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InCriterion implements Criterion {
	private String expression;
	private String label;
	private List<?> value;

	public InCriterion() {
		super();
	}

	public InCriterion(String expression, String label, List<?> value) {
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

	public List<?> getValue() {
		return value;
	}

	public void setValue(List<?> value) {
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
