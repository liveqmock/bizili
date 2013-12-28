package com.vteba.finance.report.webservice.impl;

import javax.jws.WebService;

import com.vteba.finance.report.webservice.ReportWebService;

/**
 * 报表web服务实现
 * @author yinlei
 * date 2012-10-3 下午4:17:21
 */
//serviceName指明WSDL中<wsdl:service>与<wsdl:binding>、wsdl:definitions元素的名称, endpointInterface属性指向Interface类全称
@WebService(serviceName = "reportWebService", endpointInterface = "com.vteba.finance.report.webservice.ReportWebService", targetNamespace = "http://webservice.report.finance.vteba.com/")
public class ReportWebServiceImpl implements ReportWebService {
	//http://localhost:8080/bizili/services/reportWebService?wsdl
	@Override
	public String hello() {
		System.out.println("OK");
		return "success";
	}

}
