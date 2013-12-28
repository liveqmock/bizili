package com.vteba.cache.hibernate;

/**
 * 缓存异常
 * @author yinlei
 * date 2012-10-4 下午11:02:38
 */
public class CacheException extends RuntimeException {

	private static final long serialVersionUID = -3012875645186129021L;

	public CacheException() {
	}

	public CacheException(String message) {
		super(message);
	}

	public CacheException(String message, Throwable cause) {
		super(message, cause);
	}

	public CacheException(Throwable cause) {
		super(cause);
	}

}