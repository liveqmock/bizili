package com.vteba.util.common;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * JavaBean和Map的转换，建议使用BeanCopierFactory
 * @author yinlei
 * date 2012-4-13 下午11:31:43
 */
@Deprecated
public class ObjectMapUtils {

	/**
	 * 将对象属性名和对应的属性值转化为Map键值对
	 * @param obj 要转化的对象
	 * @return 转化后的map对象
	 */
	public static <T> Map<String, Object> objectToMap(T obj) throws RuntimeException {
		if (obj == null) {
			throw new RuntimeException("object is null.");
		}

		Class<?> clazz = obj.getClass();
		Map<String, Object> map = new HashMap<String, Object>();
		convertObjectToMap(clazz, map, obj);
		//Map<String, Object> newMap = convertHashMap(map);
		//return newMap;
		return map;
	}

	/**
	 * 将对象中的值，放到map中
	 * @param clazz
	 * @param map
	 * @param obj
	 * @throws Exception
	 * 2012-6-11下午03:49:22
	 * @author: yinlei
	 */
	private static void convertObjectToMap(Class<?> clazz, Map<String, Object> map,
			Object obj) throws RuntimeException {
		if (clazz.getSimpleName().equals("Object")) {
			return;
		}

		Field[] fields = clazz.getDeclaredFields();
		if (fields == null || fields.length <= 0) {
			throw new RuntimeException("object have nothing fields.");
		}
		for (int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);
			String name = fields[i].getName();
			if (name.equals("serialVersionUID")) {
				continue;
			}
			Object value = null;
			try {
				value = fields[i].get(obj);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			if (value != null) {
				map.put(name, value);
			}

		}
		Class<?> superClzz = clazz.getSuperclass();
		convertObjectToMap(superClzz, map, obj);
	}

	/**
	 * 将value格式化为string
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> convertHashMap(Map<String, Object> map)
			throws RuntimeException {

		Map<String, Object> newMap = new HashMap<String, Object>();
		Set<String> keys = map.keySet();
		Iterator<String> it = keys.iterator();
		while (it.hasNext()) {
			String key = it.next();
			convertToString(map.get(key), newMap, key);
		}
		return newMap;
	}

	/**
	 * 将value转换为String
	 * @param value
	 * @param newMap
	 * @param key
	 */
	private static void convertToString(Object value,
			Map<String, Object> newMap, String key) {
		if (value != null) {
			Class<?> clazz = value.getClass();
			if (isBaseType(clazz)) {
				newMap.put(key, value.toString());
			} else if (clazz == String.class) {
				newMap.put(key, value.toString());
			} else if (clazz == Date.class) {
				Date date = (Date) value;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				newMap.put(key, sdf.format(date));
			} else if (clazz == Timestamp.class) {
				Timestamp timestamp = (Timestamp) value;
				long times = timestamp.getTime();
				Date date = new Date(times);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				newMap.put(key, sdf.format(date));
			} else if (clazz == java.sql.Date.class) {
				java.sql.Date sqlDate = (java.sql.Date) value;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				newMap.put(key, sdf.format(sqlDate));
			} else {
				newMap.put(key, value);
			}
		} else {
			newMap.put(key, value);
		}

	}

	/**
	 * 是基本类型
	 * @param clazz
	 * @return
	 */
	private static boolean isBaseType(Class<?> clazz) {

		if (clazz == Integer.class) {
			return true;
		}
		if (clazz == Long.class) {
			return true;
		}
		if (clazz == Double.class) {
			return true;
		}
		if (clazz == Byte.class) {
			return true;
		}
		if (clazz == Float.class) {
			return true;
		}
		if (clazz == Short.class) {
			return true;
		}
		if (clazz == Boolean.class) {
			return true;
		}
		return false;
	}

	/**
	 * 测试
	 * @param args
	 
	public static void main(String[] args) throws Exception {
		MenuItem item = new MenuItem();
		item.setAuthId("ssd123");
		item.setId("sdfjkl12321");
		item.setMenuName("jklsd");
		item.setModuleId("dfgasd");

		Map<String, Object> map = ObjectMapUtils.objectToMap(item);
		Set<Entry<String, Object>> entrys = map.entrySet();
		Iterator<Entry<String, Object>> it = entrys.iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
	}*/

}
