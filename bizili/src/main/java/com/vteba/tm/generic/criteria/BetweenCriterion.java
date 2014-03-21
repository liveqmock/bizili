package com.vteba.tm.generic.criteria;

import java.util.HashMap;
import java.util.Map;

public class BetweenCriterion implements Criterion {
	private String expression;
	private String label;
	private Object lowValue;
	private Object highValue;

	public BetweenCriterion() {
		super();
	}

	public BetweenCriterion(String expression, String label, Object lowValue, Object highValue) {
		super();
		this.expression = expression;
		this.label = label;
		this.lowValue = lowValue;
		this.highValue = highValue;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Object getLowValue() {
		return lowValue;
	}

	public void setLowValue(Object lowValue) {
		this.lowValue = lowValue;
	}

	public Object getHighValue() {
		return highValue;
	}

	public void setHighValue(Object highValue) {
		this.highValue = highValue;
	}

	public Map<String, Object> getMaps() {
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put(":l" + label, lowValue);
		maps.put(":h" + label, highValue);
		return maps;
	}

}
