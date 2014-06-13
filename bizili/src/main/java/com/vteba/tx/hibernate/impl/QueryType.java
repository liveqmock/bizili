package com.vteba.tx.hibernate.impl;

/**
 * Hibernate查询类型分类，指明具体的查询类型，查询代码中不再检查，提升性能。
 * @author yinlei
 * @date 2014-6-11 17:46
 */
public enum QueryType {
	/**查询单实体PO，返回List&lt;T&gt;，T为实体类型*/
	PO, 
	/**使用select new语法查询JavaBean（VO/PO），返回List&lt;T&gt;，T为JavaBean类型*/
	NEW, 
	/**直接查询栏位，不转换，返回List&lt;Object[]&gt;*/
	NONE,
	/**根据栏位别名转换结果集，返回List&lt;T&gt;，T为JavaBean类型*/
	ALIAS
}
