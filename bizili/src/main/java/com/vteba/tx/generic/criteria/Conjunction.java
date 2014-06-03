package com.vteba.tx.generic.criteria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Conjunction implements Criterion {
	private List<Criterion> junctionList = new ArrayList<Criterion>();
	private Map<String, Object> maps = new HashMap<String, Object>();
	
	public Conjunction() {
		super();
	}
	
	protected Conjunction(Criterion... criterions) {
		Collections.addAll(junctionList, criterions);
	}
	
	public String getExpression() {
		StringBuilder sb = new StringBuilder("(");
		for (Criterion criterion : junctionList) {
			if (criterion instanceof SimpleCriterion) {
				SimpleCriterion c = (SimpleCriterion)criterion;
				if (sb.length() > 1) {
					sb.append(" and ");
				}
				sb.append(" ").append(c.getExpression()).append(" ");
				maps.put(c.getLabel(), c.getValue());
			} else if (criterion instanceof InCriterion) {
				InCriterion c = (InCriterion)criterion;
				if (sb.length() > 1) {
					sb.append(" and ");
				}
				sb.append(" ").append(c.getExpression()).append(" ");
				maps.put(c.getLabel(), c.getValue());
			} else if (criterion instanceof BetweenCriterion) {
				BetweenCriterion c = (BetweenCriterion)criterion;
				if (sb.length() > 1) {
					sb.append(" and ");
				}
				sb.append(" ").append(c.getExpression()).append(" ");
				maps.put("l" + c.getLabel(), c.getLowValue());
				maps.put("h" + c.getLabel(), c.getHighValue());
			}
		}
		return sb.append(")").toString();
	}

	public Map<String, Object> getMaps() {
		return maps;
	}
	
}
