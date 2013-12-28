package com.vteba.cache.memcached.manager;

import java.lang.reflect.Method;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 缓存键封装
 * @author yinlei
 * date 2012-1-15 下午9:24:21
 */
public class CacheKey extends AbstractCacheObject {

	private static final long serialVersionUID = 7079339102457461509L;
	private String className;
	private String methodName;
	private Class<?>[] methodParameterType;
	private Object[] parameter;
	private int hashCode;
	
	/**
	 * 构造缓存键
	 * @param cacheClass 缓存类
	 * @param cacheMethod 缓存的方法
	 * @param args 方法的参数
	 */
	public CacheKey(Class<?> cacheClass, Method cacheMethod, Object[] args) {
		this.className = ( cacheClass == null ) ? CacheKey.class.getName() : cacheClass.getName();
		this.methodName = ( cacheMethod != null ) ? cacheMethod.getName() : "null";
		this.methodParameterType = ( cacheMethod != null ) ? cacheMethod.getParameterTypes() : null;
		
		if (methodParameterType != null && methodParameterType.length != 0) {
			for (Class<?> clazz : methodParameterType) {
				if (clazz == long.class) {
                    clazz = Long.class;
                } else if (clazz == int.class) {
                	clazz = Integer.class;
                } else if (clazz == short.class) {
                	clazz = Short.class;
                } else if (clazz == byte.class) {
                	clazz = Byte.class;
                } else if (clazz == double.class) {
                	clazz = Double.class;
                } else if (clazz == float.class) {
                	clazz = Float.class;
                } else if (clazz == boolean.class) {
                	clazz = Boolean.class;
                }
			}
		}
		
		if ( args != null ) {
			parameter = new Object[args.length];
			for ( int i = 0; i < args.length; i++ ) {
				if (args[i] != null) {
					parameter[i] = deepOrCloneCopy(args[i]).getValue();
				} else {
					parameter[i] = "";
				}
			}
		}
		
		this.hashCode = new HashCodeBuilder(2112222238, 78967567)
				.append(className).append(methodName)
				.append(methodParameterType).append(parameter).toHashCode();
	}

	@Override
	public int hashCode() {
		return this.hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CacheKey)) {
			return false;
		}
		CacheKey key = (CacheKey)obj;
		if (new EqualsBuilder().append(this.className, key.className)
				.append(this.methodName, key.methodName)
				.append(this.methodParameterType, key.methodParameterType)
				.isEquals()) {
			if (this.parameter == null && key.parameter == null) {
				return true;
			} else {
				return new EqualsBuilder().append(this.parameter, key.parameter).isEquals();
			}
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append(className).append(methodName)
				.append(methodParameterType).append(parameter).toString();
	}
	
	
}
