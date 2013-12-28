package com.vteba.web.action;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.vteba.common.constant.CommonConst;
import com.vteba.persister.generic.Page;

/**
 * 公共action，提供一些常用方法及变量定义。
 * @author yinlei 
 * date 2012-5-5 下午9:37:30
 */
public abstract class  BaseAction<T> extends ActionSupport implements ModelDriven<T> {
	private static final long serialVersionUID = 1740766986180297938L;
	private static final Random RANDOM = new Random();
	protected Logger logger = LoggerFactory.getLogger(getClass());
	protected T entity;
	/**
	 * 存放任意对象list到view中
	 */
	protected List<?> list;
	/**
	 * 存放action的泛型参数的对象list到view中
	 */
	protected List<T> listResult;
	/**
	 * 封装页面的数据，即view到action中
	 */
	protected List<T> formList = new ArrayList<T>();
	/**
	 * 存放多个对象到view中
	 */
	protected Map<String, Object> map;
	/**
	 * 存放多个list到view中
	 */
	protected Map<String, List<T>> mapResult;
	/**
	 * 存放对象数组到view中
	 */
	protected Object[] objectResult;
	/**
	 * 包含action泛型参数的page分页
	 */
	protected Page<T> page = new Page<T>();
	/**
	 * 一般page分页
	 */
	protected Page<?> pageResult;
	/**
	 * 当前action路径
	 */
	protected String currentActionPath = ServletActionContext.getRequest().getServletPath();
	/**
	 * 初始化，减少action method 数目，有利于控制跳转
	 */
	private boolean init;
	/**
	 * 防止表单重复提交，不使用struts2自带的
	 */
	protected String tokenName;
	
	public static final String DETAIL = "detail";
	public static final String LIST = "list";
	
	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public List<T> getListResult() {
		return listResult;
	}

	public void setListResult(List<T> listResult) {
		this.listResult = listResult;
	}

	public List<T> getFormList() {
		return formList;
	}

	public void setFormList(List<T> formList) {
		this.formList = formList;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public Map<String, List<T>> getMapResult() {
		return mapResult;
	}

	public void setMapResult(Map<String, List<T>> mapResult) {
		this.mapResult = mapResult;
	}

	public Object[] getObjectResult() {
		return objectResult;
	}

	public void setObjectResult(Object[] objectResult) {
		this.objectResult = objectResult;
	}

	public Page<T> getPage() {
		return page;
	}

	public void setPage(Page<T> page) {
		this.page = page;
	}

	public Page<?> getPageResult() {
		return pageResult;
	}

	public void setPageResult(Page<?> pageResult) {
		this.pageResult = pageResult;
	}
	
	public String getCurrentActionPath() {
		return currentActionPath;
	}

	public void setCurrentActionPath(String currentActionPath) {
		this.currentActionPath = currentActionPath;
	}
	
	/**
	 * the jsp page initial。
	 */
	public boolean isInit() {
		return init;
	}

	public void setInit(boolean init) {
		this.init = init;
	}

	public String getTokenName() {
		return tokenName;
	}

	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	public String execute() throws Exception{
		return this.initial();
	}
	
	/**
	 * action default method，常作为查询使用，或初始化。
	 */
	public abstract String initial() throws Exception;
	
	/**
	 * 将value保存到request。
	 * @param name
	 * @param value
	 * @author yinlei
	 * date 2012-7-5 下午9:38:26
	 */
	protected void setAttributeToRequest(String name, Object value) {
		ActionContext ctx = ActionContext.getContext();
		ctx.put(name, value);
	}
	
	/**
	 * 取得request中参数值。
	 * @param name
	 * @author yinlei
	 * date 2012-7-5 下午10:19:33
	 */
	protected Object getRequestParamater(String name) {
		ActionContext ctx = ActionContext.getContext();
		return ctx.get(name);
	}
	
	/**
	 * 将value保存到session。
	 * @param name
	 * @param value
	 * @author yinlei
	 * date 2012-7-5 下午10:05:32
	 */
	protected void setAttributeToSession(String name, Object value) {
		ActionContext ctx = ActionContext.getContext();
		Map<String, Object> session = ctx.getSession();
		session.put(name, value);
	}
	
	/**
	 * 取得session中参数值。
	 * @param name
	 * @author yinlei
	 * date 2012-7-5 下午10:21:44
	 */
	protected Object getSessionParameter(String name) {
		Map<String, Object> session = ActionContext.getContext().getSession();
		return session.get(name);
	}
	
	/**
	 * 产生全局唯一的ID。
	 * @author yinlei
	 * date 2012-7-5 下午9:35:37
	 */
	protected String generateGUID(){
        return (new BigInteger(165, RANDOM)).toString(36).toUpperCase();
    }
	
	/**
	 * 设置 token value to session scope，表单重复提交时使用
	 * @author yinlei
	 * date 2012-7-5 下午8:38:40
	 */
	protected void setTokenValue() {
        String token = generateGUID();
        setAttributeToSession(CommonConst.TOKEN_NAME, token);
	}
	
	/**
	 * 如果token合法，移除session中旧的token，再重新产生一个新token，防止表单重复提交。
	 * @author yinlei
	 * date 2012-7-5 下午8:39:35
	 */
	protected boolean isTokenValueOK(){
        Map<String, Object> session = ActionContext.getContext().getSession();
        String token = (String) session.get(CommonConst.TOKEN_NAME);
        if (tokenName != null && tokenName.equals(token)) {
            session.remove(CommonConst.TOKEN_NAME);//移除旧的session值
            setTokenValue();//添加新的session值，供下次form提交使用
            return true;
        }
        return false;
	}
	
	public HttpServletResponse getHttpServletResponse() {
		return ServletActionContext.getResponse();
	}
	
	public HttpServletRequest getHttpServletRequest() {
		return ServletActionContext.getRequest();
	}
	
	public HttpSession getHttpSession() {
		return ServletActionContext.getRequest().getSession();
	}
	
	/**
	 * 向客户端输出指定格式的文档。
	 * @param response 		响应
	 * @param text   		要发的内容
	 * @param contentType   发送的格式
	 */
	public void response(HttpServletResponse response, String text, String contentType) {
		try {
			response.setContentType(contentType);
			response.getWriter().write(text);
		} catch (IOException e) {
			logger.info("向客户端输出时发生异常 。content=[" + text + "]", e);
		}
	}

	/**
	 * 直接输出json。
	 */
	public void renderJson(String json) {
		response(ServletActionContext.getResponse(), json, "application/json;charset=UTF-8");
	}

	/**
	 * 直接输出纯HTML。
	 */
	public void renderHtml(String text) {
		response(ServletActionContext.getResponse(), text, "text/html;charset=UTF-8");
	}

	/**
	 * 直接输出纯字符串。约定：返回1，刷新页面；返回文本，alert。
	 */
	public void renderText(String text) {
		response(ServletActionContext.getResponse(), text, "application/plain;charset=UTF-8");
	}
}
