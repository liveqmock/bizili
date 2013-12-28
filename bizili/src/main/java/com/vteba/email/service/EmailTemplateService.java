package com.vteba.email.service;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.vteba.common.exception.BusinessException;

import freemarker.template.Template;

/**
 * 邮件模板service实现
 * yinlei date 2012-08-30 23:23
 */
@Named
public class EmailTemplateService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	public String getEmailHtmlText() {
		String htmlText = "";
		try {
			Template template = freeMarkerConfigurer.getConfiguration().getTemplate("freemarker.ftl");
			Map<String, Object> root = new HashMap<String, Object>();
			
			htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(template, root);
		} catch (Exception e) {
			logger.error("处理邮件模板失败。", e);
			throw new BusinessException("处理邮件模板失败。");
		}
		
		return htmlText;
	}
}
