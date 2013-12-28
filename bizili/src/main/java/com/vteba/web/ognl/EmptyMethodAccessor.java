package com.vteba.web.ognl;

import java.util.Map;

import ognl.MethodAccessor;
import ognl.MethodFailedException;

/**
 * OGNL空方法访问器。
 * @author yinlei
 * date 2013-6-16 下午7:41:25
 */
public class EmptyMethodAccessor implements MethodAccessor {

	/**
	 * 构造OGNL空方法访问器
	 */
	public EmptyMethodAccessor() {
		super();
	}

	/**
     * Calls the static method named with the arguments given on the class given.
     * @param context     expression context in which the method should be called
     * @param targetClass the object in which the method exists
     * @param methodName  the name of the method
     * @param args        the arguments to the method
     *
     * @return            result of calling the method
     * @exception MethodFailedException if there is an error calling the method
     */
	@Override
	@SuppressWarnings("rawtypes")
	public Object callStaticMethod(Map context, Class targetClass,
			String methodName, Object[] args) throws MethodFailedException {
		
		return null;
	}

	/**
     * Calls the method named with the arguments given.
     * @param context     expression context in which the method should be called
     * @param target      the object in which the method exists
     * @param methodName  the name of the method
     * @param args        the arguments to the method
     * @return            result of calling the method
     * @exception MethodFailedException if there is an error calling the method
     */
	@Override
	@SuppressWarnings("rawtypes")
	public Object callMethod(Map context, Object target, String methodName,
			Object[] args) throws MethodFailedException {
		return null;
	}

}
