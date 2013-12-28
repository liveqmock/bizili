package com.vteba.config.logger;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * 动态设置Log4j的日志级别。
 * @author yinlei
 * date 2013-6-30 上午11:09:19
 */
public class DynamicLoggerLevel {
	
	/**
	 * 设置包的日志级别。
	 * @param packageName 包名
	 * @author yinlei
	 * date 2013-6-30 上午11:09:57
	 */
	public void packageLoggerLevel(String packageName) {
		Level  level = Level.toLevel(Level.ERROR_INT);
		Logger logger = LogManager.getLogger(packageName);
		logger.setLevel(level);
	}
	
	/**
	 * 设置全局(root)日志级别。
	 * @author yinlei
	 * date 2013-6-30 上午11:10:33
	 */
	public void rootLoggerLevel() {
		Level level = Level.toLevel(Level.ERROR_INT);
		LogManager.getRootLogger().setLevel(level);
	}
}
