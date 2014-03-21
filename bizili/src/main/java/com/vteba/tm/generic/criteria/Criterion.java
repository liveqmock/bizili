package com.vteba.tm.generic.criteria;

import java.util.Map;

public interface Criterion {
	public String getExpression();
	public Map<String, Object> getMaps();
}
