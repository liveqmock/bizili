package com.vteba.test;

import javax.inject.Inject;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.JiBXException;
import org.springframework.oxm.jibx.JibxMarshaller;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class JibxTest {
	
	@Inject
	private static JibxMarshaller userJibxMarshaller;
	
	/**
	 * @param args
	 * @author yinlei
	 * date 2012-9-22 下午1:47:12
	 */
	public static void main(String[] args) {
		IBindingFactory factory = null;
		try {
			factory = BindingDirectory.getFactory(Customer.class);
		} catch (JiBXException e) {
			e.printStackTrace();
		}
		IMarshallingContext marshallingContext = null;
		try {
			marshallingContext = factory.createMarshallingContext();
			marshallingContext.marshalDocument(null);
		} catch (JiBXException e) {
			e.printStackTrace();
		}
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		userJibxMarshaller = context.getBean("userJibxMarshaller", JibxMarshaller.class);
		System.out.println(userJibxMarshaller);
	}
	
	public JibxMarshaller getUserJibxMarshaller() {
		return userJibxMarshaller;
	}
	
	public void setUserJibxMarshaller(JibxMarshaller userJibxMarshaller) {
		JibxTest.userJibxMarshaller = userJibxMarshaller;
	}
	
	
}
