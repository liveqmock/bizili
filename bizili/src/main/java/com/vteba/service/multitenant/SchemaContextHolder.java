package com.vteba.service.multitenant;

/**
 * Schema Context Holder。
 * @author yinlei
 * date 2012-08-15
 */
public class SchemaContextHolder {
	
	private static ThreadLocal<String> schemaLocal = new ThreadLocal<String>();
	//private static VicariousThreadLocal<String> schemaLocal = new VicariousThreadLocal<String>();
	
	public static void putSchema(String schema) {
		schemaLocal.set(schema);
	}
	
	public static String getSchema() {
		return schemaLocal.get();
	}
}
