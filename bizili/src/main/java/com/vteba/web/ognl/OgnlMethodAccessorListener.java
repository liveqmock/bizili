package com.vteba.web.ognl;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import ognl.OgnlRuntime;

/**
 * OGNL方法访问监听器，限制OGNL对某些类的访问，消除安全隐患。
 * @author yinlei
 * date 2013-6-16 下午9:03:14
 */
public class OgnlMethodAccessorListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		initOgnl();
	}

	/**
	 * 限制OGNL对某些方法的访问，消除安全隐患。
	 * @author yinlei
	 * date 2013-6-16 下午8:54:27
	 */
	private void initOgnl() {
		OgnlRuntime.setMethodAccessor(Runtime.class, new EmptyMethodAccessor());
		OgnlRuntime.setMethodAccessor(System.class, new EmptyMethodAccessor());
		OgnlRuntime.setMethodAccessor(ProcessBuilder.class, new EmptyMethodAccessor());
		OgnlRuntime.setMethodAccessor(OgnlRuntime.class, new EmptyMethodAccessor());
	}
}
