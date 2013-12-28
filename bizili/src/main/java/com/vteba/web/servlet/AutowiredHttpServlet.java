package com.vteba.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 在Servle初始化时，将当前对象设为自动装配，使得在Servlet中可以获取Spring托管的Bean。
 * @author yinlei
 * date 2013-5-11 下午4:59:41
 */
public abstract class AutowiredHttpServlet extends HttpServlet {

	private static final long serialVersionUID = -2000909174467653847L;

	public void init() throws ServletException {
		super.init();
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		AutowireCapableBeanFactory factory = ctx.getAutowireCapableBeanFactory();
		factory.autowireBean(this);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		servlet(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		servlet(request, response);
	}
	
	/**
	 * 子类实现该方法，进行Servlet Post和Get请求服务。
	 * @param request HttpServletRequest实例
	 * @param response HttpServletResponse实例
	 * @throws IOException
	 * @throws ServletException
	 * @author yinlei
	 * date 2013-8-9 下午7:15:41
	 */
	public abstract void servlet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
}
