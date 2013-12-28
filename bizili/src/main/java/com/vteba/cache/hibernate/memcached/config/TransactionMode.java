package com.vteba.cache.hibernate.memcached.config;

/**
 * 事务模式
 * @author yinlei
 * date 2013-4-4 下午12:47:48
 */
public enum TransactionMode {
	/**不使用事务*/
	NONE,
	
	/**本地事务*/
	LOCAL,
	
	/**本地jta事务*/
	JTA,
	
	/**严格两阶段提交xa事务*/
	XA
}
