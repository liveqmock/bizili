package com.vteba.util.json;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 使用Jackson进行JSON和对象的相互转换。单例模式
 * @author skmbw
 * @date 2013-3-29 下午9:48:06
 */
public class JacksonJson {
	private static final ObjectMapper objectMapper = new ObjectMapper();
	private static JacksonJson instance = new JacksonJson();

	private JacksonJson() {
	}

	/**
	 * 获得JacksonJsonFactory单例
	 * @return JacksonJsonFactory Instance
	 */
	public static JacksonJson getInstance() {
		return instance;
	}

	/**
	 * 将对象转化为JSON String
	 * 
	 * @param object 被转换的对象
	 * @return JSON String
	 */
	public String toJson(Object object) {
		if (object == null) {
			return null;
		}
		StringWriter writer = new StringWriter();
		try {
			JsonGenerator generator = new JsonFactory().createGenerator(writer);
			objectMapper.writeValue(generator, object);
			return writer.toString();
		} catch (IOException e) {

		}
		return null;
	}

	/**
	 * 将JSON字符串转换为对象
	 * 
	 * @param json 被转换的JSON String
	 * @param clazz 要转换的对象类型
	 * @return 转换后的对象
	 */
	public <T> T fromJson(String json, Class<T> clazz) {
		if (json == null || json.equals("")) {
			return null;
		}
		try {
			JsonParser jsonParser = new JsonFactory().createParser(json);
			return objectMapper.readValue(jsonParser, clazz);
		} catch (Exception e) {

		} 
		return null;
	}
}
