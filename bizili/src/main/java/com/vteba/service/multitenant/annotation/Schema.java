package com.vteba.service.multitenant.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * multi-tenant use, based schema
 * @author yinlei
 * date 2012-8-20
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Schema {
	
	/**
	 * Database Schema Name.
	 * @return schema name
	 */
	public String schemaName() default "skmbw";
	
	/**
	 * tenant id
	 * @return tenant id
	 */
	//public abstract String tenantIdentifier();
}
