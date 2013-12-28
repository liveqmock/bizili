package com.vteba.common.exception;

/**
 * 非事务异常。
 * @author yinlei
 * date 2012-08-31
 */
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = -4971709041482821303L;

	public BaseException() {
		super();
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}
	
}
