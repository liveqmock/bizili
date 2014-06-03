package com.vteba.tx.hibernate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.hibernate.type.BigDecimalType;
import org.hibernate.type.BigIntegerType;
import org.hibernate.type.BooleanType;
import org.hibernate.type.ByteType;
import org.hibernate.type.CharacterType;
import org.hibernate.type.DateType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.FloatType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.ObjectType;
import org.hibernate.type.ShortType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;

/**
 * 类型匹配和转换工具方法。
 * @author yinlei
 * date 2013-6-10 下午10:22:03
 */
public class MatchType {
	
	/**
	 * 匹配clazz类型到Hibernate的对应的类型。
	 * @param clazz 要匹配的类型
	 * @return Hibernate类型
	 * @author yinlei
	 * date 2013-6-11 下午10:20:21
	 */
	public static Type matchResultType(Class<?> clazz) {
		if (clazz == Integer.class || clazz == Integer.TYPE) {
			return IntegerType.INSTANCE;
		} else if (clazz == Long.class || clazz == Long.TYPE) {
			return LongType.INSTANCE;
		} else if (clazz == String.class) {
			return StringType.INSTANCE;
		} else if (clazz == Double.class || clazz == Double.TYPE) {
			return DoubleType.INSTANCE;
		} else if (clazz == Date.class || clazz == java.sql.Date.class) {
			return DateType.INSTANCE;
		} else if (clazz == Boolean.class || clazz == Boolean.TYPE) {
			return BooleanType.INSTANCE;
		} else if (clazz == BigInteger.class) {
			return BigIntegerType.INSTANCE;
		} else if (clazz == BigDecimal.class) {
			return BigDecimalType.INSTANCE;
		} else if (clazz == Float.class || clazz == Float.TYPE) {
			return FloatType.INSTANCE;
		} else if (clazz == Short.class || clazz == Short.TYPE) {
			return ShortType.INSTANCE;
		} else if (clazz == Byte.class || clazz == Byte.TYPE) {
			return ByteType.INSTANCE;
		} else if (clazz == Character.class || clazz == Character.TYPE) {
			return CharacterType.INSTANCE;
		}
		return ObjectType.INSTANCE;
	}
	
	/**
	 * 判断类是否是基本类型及其封转类，增加String，Date，java.sql.Date，BigInteger，BigInteger的判断。
	 * @param clazz 要判断的类型
	 * @return 是true否false
	 * @author yinlei
	 * date 2013-6-11 上午12:03:34
	 */
	public static boolean isPrimitive(Class<?> clazz) {
		if (clazz.isPrimitive()) {
			return true;
		} else if (clazz == Integer.class) {
			return true;
		} else if (clazz == Long.class) {
			return true;
		} else if (clazz == String.class) {
			return true;
		} else if (clazz == Double.class) {
			return true;
		} else if (clazz == Date.class || clazz == java.sql.Date.class) {
			return true;
		} else if (clazz == Boolean.class) {
			return true;
		} else if (clazz == BigInteger.class) {
			return true;
		} else if (clazz == BigInteger.class) {
			return true;
		} else if (clazz == Float.class) {
			return true;
		} else if (clazz == Short.class) {
			return true;
		} else if (clazz == Byte.class) {
			return true;
		} else if (clazz == Character.class) {
			return true;
		}
		return false;
	}
	
}
