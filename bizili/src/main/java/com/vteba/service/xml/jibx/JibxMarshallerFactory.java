package com.vteba.service.xml.jibx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.oxm.jibx.JibxMarshaller;

/**
 * JibxMarshaller Factory。
 * @author yinlei
 * date 2013-8-1 下午8:10:45
 */
public class JibxMarshallerFactory implements InitializingBean {
	private static final Logger logger = LoggerFactory.getLogger(JibxMarshallerFactory.class);
	private static final String BINDING_NAME = "Binding";
	
	private String encoding;
	private List<Class<?>> targetClassList;
	private Map<Class<?>, JibxMarshaller> jibxCache = new HashMap<Class<?>, JibxMarshaller>();

	@Override
	public void afterPropertiesSet() throws Exception {
		if (this.targetClassList != null) {
			for (Class<?> clazz : this.targetClassList) {
				JibxMarshaller jibxMarshaller = new JibxMarshaller();
				jibxMarshaller.setTargetClass(clazz);
				jibxMarshaller.setBindingName(BINDING_NAME);
				jibxMarshaller.setEncoding(this.encoding);
				jibxMarshaller.afterPropertiesSet();
				jibxCache.put(clazz, jibxMarshaller);
			}
		} else {
			if (logger.isInfoEnabled()) {
				logger.info("JiBX映射目标类没有设置。运行时将无法获得JibxMarshaller实例。");
			}
		}
	}

	public List<Class<?>> getTargetClassList() {
		return targetClassList;
	}

	public void setTargetClassList(List<Class<?>> targetClassList) {
		this.targetClassList = targetClassList;
	}

	public JibxMarshaller getJibxMarshaller(Class<?> targetClass) {
		return jibxCache.get(targetClass);
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getEncoding() {
		return encoding;
	}
	
	public boolean support(Class<?> clazz) {
		return jibxCache.containsKey(clazz);
	}
}
