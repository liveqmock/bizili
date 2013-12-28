package com.vteba.finance.report.webservice;

import javax.jws.WebService;

/**
 * 报表web服务
 * @author yinlei
 * date 2012-10-3 下午4:17:31
 */
//name 指明wsdl中<wsdl:portType>元素的名称
@WebService(name = "ReportWebService", targetNamespace = "http://webservice.report.finance.vteba.com/")
public interface ReportWebService {
	public String hello();
}
