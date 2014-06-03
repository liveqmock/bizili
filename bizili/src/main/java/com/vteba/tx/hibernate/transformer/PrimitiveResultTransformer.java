package com.vteba.tx.hibernate.transformer;

import org.hibernate.transform.BasicTransformerAdapter;

import com.vteba.util.common.TypeConverter;

/**
 * 原生类型及其封转类，String，大数据，日期，结果集转化器
 * @author yinlei
 * date 2013-6-11 下午4:59:21
 */
public class PrimitiveResultTransformer extends BasicTransformerAdapter {
	private static final long serialVersionUID = 1L;
	private Class<?> primitiveClass;
	
	/**
	 * 构造原生类型转换器
	 * @param primitiveClass 原生类型类
	 */
	public PrimitiveResultTransformer(Class<?> primitiveClass) {
		this.primitiveClass = primitiveClass;
	}
	
	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		return TypeConverter.convertValue(tuple[0], primitiveClass);
	}
}
