package com.vteba.tm.generic.criteria;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vteba.user.model.EmpUser;

public class DetachedCriteria {
	private String clazzName;
	private List<Criterion> criterionList = new ArrayList<Criterion>();
	private boolean distinct;
	private List<String> orderBy = new ArrayList<String>();
	
	private Map<String, Object> maps = new HashMap<String, Object>();
	
	protected DetachedCriteria() {
		super();
	}
	
	protected DetachedCriteria(Class<?> clazz) {
		super();
		this.clazzName = clazz.getName();
	}
	
	public static DetachedCriteria forClass(Class<?> clazz) {
		return new DetachedCriteria(clazz);
	}
	
	public DetachedCriteria add(Criterion criterion) {
		criterionList.add(criterion);
		return this;
	}

	public DetachedCriteria and(Criterion... criterions) {
		Conjunction conjunction = new Conjunction(criterions);
		criterionList.add(conjunction);
		return this;
	}
	
	public DetachedCriteria or(Criterion... criterions) {
		Disjunction disjunction = new Disjunction(criterions);
		criterionList.add(disjunction);
		return this;
	}
	
	public DetachedCriteria orderBy(Order order) {
		orderBy.add(order.getOrderBy());
		return this;
	}
	
	public String getClazzName() {
		return clazzName;
	}

	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}

	public List<Criterion> getCriterionList() {
		return criterionList;
	}

	public void setCriterionList(List<Criterion> criterionList) {
		this.criterionList = criterionList;
	}

	public boolean isDistinct() {
		return distinct;
	}

	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	public List<String> getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy.add(orderBy);
	}

	@Override
	public String toString() {
		return "DetachedCriteria [clazzName=" + clazzName + ", criterionList="
				+ criterionList + ", distinct=" + distinct + ", orderBy="
				+ orderBy + "]";
	}
	
	public String toQuery() {
		StringBuilder sb = new StringBuilder("select u from ");
		sb.append(clazzName);
		
		if (criterionList.size() > 0) {
			sb.append(" u where");
			for (Criterion criterion : criterionList) {
				if (!maps.isEmpty()) {
					sb.append(" and ");
				}
				sb.append(" ").append(criterion.getExpression()).append(" ");
				maps.putAll(criterion.getMaps());
//				if (criterion instanceof SimpleCriterion) {
//					SimpleCriterion c = (SimpleCriterion)criterion;
//					if (!maps.isEmpty()) {
//						sb.append(" and ");
//					}
//					sb.append(" ").append(c.getExpression()).append(" ");
//					maps.put(c.getLabel(), c.getValue());
//				} else if (criterion instanceof InCriterion) {
//					InCriterion c = (InCriterion)criterion;
//					if (!maps.isEmpty()) {
//						sb.append(" and ");
//					}
//					sb.append(" ").append(c.getExpression()).append(" ");
//					maps.put(c.getLabel(), c.getValue());
//				} else if (criterion instanceof BetweenCriterion) {
//					BetweenCriterion c = (BetweenCriterion)criterion;
//					if (!maps.isEmpty()) {
//						sb.append(" and ");
//					}
//					sb.append(" ").append(c.getExpression()).append(" ");
//					maps.put("l" + c.getLabel(), c.getLowValue());
//					maps.put("h" + c.getLabel(), c.getHighValue());
//				} else if (criterion instanceof Conjunction) {
//					Conjunction c = (Conjunction)criterion;
//					if (!maps.isEmpty()) {
//						sb.append(" and ");
//					}
//					sb.append(c.getExpression());
//					maps.putAll(c.getMaps());
//				} else if (criterion instanceof Disjunction) {
//					Disjunction c = (Disjunction)criterion;
//					if (!maps.isEmpty()) {
//						sb.append(" and ");
//					}
//					sb.append(c.getExpression());
//					maps.putAll(c.getMaps());
//				}
			}
		}
		
		
		return sb.toString();
	}
	
	public static void main(String[] a) {
		List<Integer> countList = new ArrayList<Integer>();
		countList.add(22);
		countList.add(33);
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(EmpUser.class);
		detachedCriteria.add(Restrictions.eq("userName", "yinlei"))
			.add(Restrictions.between("createDate", new Date(), new Date()))
			.add(Restrictions.gt("age", 19))
			.add(Restrictions.in("count", countList))
			.add(Restrictions.like("userAccount", "tongku2008@126.com"))
			.or(Restrictions.eq("email", "12312@qq.com"), Restrictions.le("salary", 19000), Restrictions.isNotNull("state"))
			.add(Restrictions.ne("status", 3D));
		
		String ss = detachedCriteria.toQuery();
		System.out.println(ss);
		
	}
}
