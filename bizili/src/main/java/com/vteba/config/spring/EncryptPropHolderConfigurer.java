package com.vteba.config.spring;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.vteba.util.cryption.DESUtils;

/**
 * Properties属性密文配置
 * @author yinlei 
 * date 2012-7-16 下午4:28:33
 */
public class EncryptPropHolderConfigurer extends PropertyPlaceholderConfigurer {
	private String[] encryptPropNames = { "jdbc.username", "jdbc.password" };
	
	public EncryptPropHolderConfigurer() {
		super();
	}
	
	@Override
	protected String convertProperty(String propertyName, String propertyValue) {
		if (isEncryptProp(propertyName)) {
			String decryptValue = DESUtils.getDecryptString(propertyValue);
			return decryptValue;
		} else {
			return super.convertPropertyValue(propertyValue);
		}
		
    }
	
	private boolean isEncryptProp(String propName) {
		for (String prop : encryptPropNames) {
			if (propName.equals(prop)) {
				return true;
			}
		}
		return false;
	}
}
