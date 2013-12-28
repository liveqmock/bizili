package com.vteba.service.multitenant.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.vteba.service.multitenant.SchemaContextHolder;
import com.vteba.service.multitenant.annotation.Schema;

/**
 * Parse annotation schema, get the jta schema, and then put it into the current ThreadLocal.
 * @author yinlei
 * date 2012-8-16 下午9:33:59
 */
public class DetermineSchemaInterceptor implements MethodInterceptor{

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object object = invocation.getThis();//获得被拦截的目标对象
		boolean isSchema = object.getClass().isAnnotationPresent(Schema.class);
		if (isSchema) {
			Schema schema = object.getClass().getAnnotation(Schema.class);
			String schemaName = schema.schemaName();
			//System.out.println("拦截器打印的，Schema是：" + schemaName + "，线程是：" + Thread.currentThread());
			SchemaContextHolder.putSchema(schemaName);
		}
		return invocation.proceed();
	}

}
