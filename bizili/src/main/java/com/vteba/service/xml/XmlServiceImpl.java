package com.vteba.service.xml;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.xstream.XStreamMarshaller;

/**
 * XML和对象序列化和反序列化服务。
 * @author yinlei
 * date 2013-7-28 下午8:17:01
 */
//@Named
public class XmlServiceImpl {
	private static Logger logger = LoggerFactory.getLogger(XmlServiceImpl.class);
	
//	@Inject
	private JibxMarshallerFactory jibxMarshallerFactory;
	
//	@Inject
	private XStreamMarshaller xstreamMarshaller;
	
	/**
	 * 将XML字符串反序列化成对应的对象。底层使用XStream，并且该XML字符串是XStream产生的。<br>
	 * 因为各XML绑定框架生成的XML字符串不一定相同。
	 * @param xml 要被反序列化的XML字符串
	 * @return 反序列化后的对象
	 * @author yinlei
	 * date 2013-7-28 下午8:17:05
	 */
	@Deprecated
	public Object fromXml(String xml) {
		StringReader reader = new StringReader(xml);
		return xstreamMarshaller.getXStream().fromXML(reader);
	}
	
	/**
	 * 将对象序列化成XML字符串。底层使用XStream。
	 * @param object 要被序列化的对象
	 * @return 序列化后的XML字符串
	 * @author yinlei
	 * date 2013-7-28 下午8:18:05
	 */
	@Deprecated
	public String toXml(Object object) {
		return xstreamMarshaller.getXStream().toXML(object);
	}
	
	/**
	 * 将XML字符串反序列化成对应的对象。
	 * @param xml 要被反序列化的XML字符串
	 * @param targetClass 要反序列化的目标对象
	 * @return 反序列化后的对象
	 * @author yinlei
	 * date 2013-7-28 下午8:17:25
	 */
	public <T> T xmlToObject(String xml, Class<T> targetClass) {
		Reader reader = new StringReader(xml);
		Source source = new StreamSource(reader);
		Unmarshaller unmarshaller = jibxMarshallerFactory.getJibxMarshaller(targetClass);
		if (unmarshaller == null) {
			if (logger.isInfoEnabled()) {
				logger.info("从JibxMarshallerFactory类没有获取类[" + targetClass.getName() + "]的Marshaller，将使用XStreamMarshaller。");
			}
			unmarshaller = xstreamMarshaller;
		}
		try {
			@SuppressWarnings("unchecked")
			T result = (T) unmarshaller.unmarshal(source);
			return result;
		} catch (IOException e) {
			logger.error("XML to Object IO 错误。", e);
		}
		return null;
	}
	
	/**
	 * 将XML Source（Stream）反序列化成对应的对象。
	 * @param source 要被反序列化的XML Source
	 * @param targetClass 要反序列化的目标对象 
	 * @return 反序列化后的对象
	 * @author yinlei
	 * date 2013-7-28 下午8:17:10
	 */
	public <T> T xmlToObject(Source source, Class<T> targetClass) {
		Unmarshaller unmarshaller = jibxMarshallerFactory.getJibxMarshaller(targetClass);
		if (unmarshaller == null) {
			unmarshaller = xstreamMarshaller;
		}
		try {
			@SuppressWarnings("unchecked")
			T object = (T) unmarshaller.unmarshal(source);
			return object;
		} catch (IOException e) {
			logger.error("XML to Object IO错误。", e);
		}
		return null;
	}
	
	/**
	 * 将对象序列化成XML Result（Stream）。
	 * @param object 要被序列化的对象
	 * @param result XML Stream Result Holder
	 * @author yinlei
	 * date 2013-7-28 下午8:20:07
	 */
	public void objectToXml(Object object, Result result) {
		Marshaller marshaller = jibxMarshallerFactory.getJibxMarshaller(object.getClass());
		if (marshaller == null) {
			marshaller = xstreamMarshaller;
		}
		try {
			marshaller.marshal(object, result);
		} catch (IOException e) {
			logger.error("XML to Object IO错误。", e);
		}
	}
	
	/**
	 * 将对象序列化成XML字符串。
	 * @param object 要被序列化的对象
	 * @return 序列化后的XML字符串
	 * @author yinlei
	 * date 2013-7-28 下午8:20:44
	 */
	public String objectToXml(Object object) {
		StringWriter writer = new StringWriter();
		Result result = new StreamResult(writer);
		
		Marshaller marshaller = jibxMarshallerFactory.getJibxMarshaller(object.getClass());
		if (marshaller == null) {
			marshaller = xstreamMarshaller;
		}
		
		try {
			marshaller.marshal(object, result);
		} catch (IOException e) {
			logger.error("XML to Object IO错误。", e);
		}
		writer.flush();
		return writer.toString();
	}

}
