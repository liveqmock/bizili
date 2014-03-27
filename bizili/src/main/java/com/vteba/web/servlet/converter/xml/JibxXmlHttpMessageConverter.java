package com.vteba.web.servlet.converter.xml;

import java.io.IOException;

import javax.xml.transform.Result;
import javax.xml.transform.Source;

import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.xml.AbstractXmlHttpMessageConverter;

import com.vteba.service.context.spring.ApplicationContextHolder;
import com.vteba.service.xml.XmlServiceImpl;

/**
 * 基于JiBX的XML HttpMessageConverter
 * @author yinlei
 * @date 2013年10月12日下午7:49:06
 */
public class JibxXmlHttpMessageConverter extends AbstractXmlHttpMessageConverter<Object> {
	private XmlServiceImpl xmlServiceImpl;
	
	public JibxXmlHttpMessageConverter() {
		super();
		xmlServiceImpl = ApplicationContextHolder.getBean("xmlServiceImpl", XmlServiceImpl.class);
	}

	@Override
	protected Object readFromSource(Class<? extends Object> clazz, HttpHeaders headers, Source source) throws IOException {
		return xmlServiceImpl.xmlToObject(source, clazz);
	}

	@Override
	protected void writeToResult(Object t, HttpHeaders headers, Result result) throws IOException {
		xmlServiceImpl.objectToXml(t, result);
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		if (xmlServiceImpl != null) {
			return xmlServiceImpl.canSerialize(clazz);
		}
		return false;
	}

	public void setXmlServiceImpl(XmlServiceImpl xmlServiceImpl) {
		this.xmlServiceImpl = xmlServiceImpl;
	}

}
