package com.vteba.config.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 应用中需要初始化，都在此Listener中初始化。
 * @author yinlei
 * date 2013-6-20 下午9:42:51
 */
public class ApplicationInitializeListener implements
		ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {

	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {

	}

	

}
