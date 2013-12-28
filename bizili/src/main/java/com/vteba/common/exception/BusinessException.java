package com.vteba.common.exception;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;

/**
 * Service layer throws Exception for Transaction Rollback。
 * @author yinlei 
 * date 2012-7-4 下午2:54:08
 */
public class BusinessException extends DataAccessException {

	private static final long serialVersionUID = -5721992293752829655L;
	private int flag;
	private String message;
	
	public BusinessException(String message, int flag) {
		super(message);
		this.flag = flag;
		this.message = message;
	}

	public BusinessException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public BusinessException(String msg) {
		super(msg);
	}

	/**
	 * 获得底层抛出的异常信息的内容
	 * @author yinlei
	 * date 2012-7-4 下午3:06:44
	 */
	public String getExceptionMessage(){
		String excepMessage = "";
		if (StringUtils.isNotBlank(message)) {
			return message;
		} else {
			excepMessage = "失败";
			switch (flag) {
			case 0:
				excepMessage = "";
				break;
			case 1:
				excepMessage = "";
				break;
			case 2:
				excepMessage = "";
				break;
			case 3:
				excepMessage = "";
				break;
			case 4:
				excepMessage = "";
				break;
			case 5:
				excepMessage = "";
				break;
			case 6:
				excepMessage = "";
				break;
			case 7:
				excepMessage = "";
				break;
			case 8:
				excepMessage = "";
				break;
			case 9:
				excepMessage = "";
				break;
			}
			return excepMessage;
		}
	}
}
