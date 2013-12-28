package com.vteba.service.context.spring;

import javax.inject.Named;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Spring ApplicationContext Holder。
 * @author yinlei 
 * date 2012-8-13 下午9:30:18
 */
@Named
public class ApplicationContextHolder implements ApplicationContextAware {

	private static ApplicationContext context;
	
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		ApplicationContextHolder.context = context;
	}

	/**
	 * 获取Spring ApplicationContext。
	 * @author yinlei
	 * date 2012-8-13 下午9:30:38
	 */
	public static ApplicationContext getApplicationContext() {
		return context;
	}

}
