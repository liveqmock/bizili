package com.vteba.web.action;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;

import com.vteba.common.constant.CommonConst;
import com.vteba.service.context.RequestContextHolder;
import com.vteba.service.generic.IGenericService;
import com.vteba.tx.generic.Page;
import com.vteba.utils.json.FastJsonUtils;
import com.vteba.utils.reflection.ReflectUtils;
import com.vteba.web.editer.DoubleEditor;
import com.vteba.web.editer.FloatEditor;
import com.vteba.web.editer.IntegerEditor;
import com.vteba.web.editer.LongEditor;

/**
 * 公共action，提供一些常用方法及变量定义。
 * @author yinlei 
 * date 2012-5-5 下午9:37:30
 */
public abstract class  BaseAction<T> {
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	
	public static final String DETAIL = "detail";
	public static final String LIST = "list";
	
	public final static String MSG = "msg";
	
	public static final String HQL = "hqlQuery";
	
	private static final Random RANDOM = new Random();
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected IGenericService<T, ? extends Serializable> genericServiceImpl;
	
//	protected T entity;
//	/**
//	 * 存放任意对象list到view中
//	 */
//	protected List<?> list;
	/**
	 * 存放action的泛型参数的对象list到view中
	 */
	protected List<T> listResult;
//	/**
//	 * 封装页面的数据，即view到action中
//	 */
//	protected List<T> formList = new ArrayList<T>();
	/**
	 * 存放多个对象到view中
	 */
	protected Map<String, Object> map;
	/**
	 * 存放多个list到view中
	 */
	protected Map<String, List<T>> mapResult;
//	/**
//	 * 存放对象数组到view中
//	 */
//	protected Object[] objectResult;
	/**
	 * 包含action泛型参数的page分页
	 */
	protected Page<T> page = new Page<T>();
//	/**
//	 * 一般page分页
//	 */
//	protected Page<?> pageResult;
	/**
	 * 当前action路径
	 */
	protected String currentActionPath;// = RequestContextHolder.getRequest().getServletPath();
//	/**
//	 * 初始化，减少action method 数目，有利于控制跳转
//	 */
//	private boolean init;
//	/**
//	 * 防止表单重复提交，不使用struts2自带的
//	 */
//	protected String tokenName;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
		binder.registerCustomEditor(int.class, new IntegerEditor());
		binder.registerCustomEditor(long.class, new LongEditor());
		binder.registerCustomEditor(double.class, new DoubleEditor());
		binder.registerCustomEditor(float.class, new FloatEditor());
	}
	
//	public List<?> getList() {
//		return list;
//	}
//
//	public void setList(List<?> list) {
//		this.list = list;
//	}

	public List<T> getListResult() {
		return listResult;
	}

	public void setListResult(List<T> listResult) {
		this.listResult = listResult;
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

//	public Object[] getObjectResult() {
//		return objectResult;
//	}
//
//	public void setObjectResult(Object[] objectResult) {
//		this.objectResult = objectResult;
//	}

	public Page<T> getPage() {
		return page;
	}

	public void setPage(Page<T> page) {
		this.page = page;
	}

//	public Page<?> getPageResult() {
//		return pageResult;
//	}
//
//	public void setPageResult(Page<?> pageResult) {
//		this.pageResult = pageResult;
//	}
	
	public String getCurrentActionPath() {
		if (currentActionPath == null) {
			currentActionPath = RequestContextHolder.getRequest().getServletPath();
		}
		return currentActionPath;
	}

	public void setCurrentActionPath(String currentActionPath) {
		this.currentActionPath = currentActionPath;
	}
	
	/**
	 * 页面是否已经初始化。
	 * @return true是，false否
	 */
	public boolean isInit() {
		String init = getHttpServletRequest().getParameter("init");
		if (init != null && init.equals("true")) {
			return true;
		}
		return false;
	}
	
	/**
	 * 是否是表单查询，如果是，需要排除空字符串。
	 * @return true是，false否
	 */
	public boolean isQuery() {
		String init = getHttpServletRequest().getParameter("query");
		if (init != null && init.equals("true")) {
			return true;
		}
		return false;
	}
//
//	public void setInit(boolean init) {
//		this.init = init;
//	}

	/**
	 * 获取IP地址
	 * @param request HttpServletRequest
	 * @return IP地址
	 */
	public String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 所有ActionMap 统一从这里获取
	 * @return
	 */
	public Map<String, Object> getRootMap() {
		Map<String, Object> rootMap = new HashMap<String, Object>();
		// 添加url到 Map中
		//rootMap.putAll(URLUtils.getUrlMap());
		return rootMap;
	}

	public ModelAndView forword(String viewName, Map<String, ?> context) {
		return new ModelAndView(viewName, context);
	}
	
	public ModelAndView error(String errMsg) {
		return new ModelAndView("error_all");
	}

	/**
	 * 提示成功信息
	 * @param message
	 */
	public void sendSuccessMessage(String message) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SUCCESS, true);
		result.put(MSG, message);
		renderJson(FastJsonUtils.toJson(result));
	}

	/**
	 * 提示失败信息
	 * @param message
	 */
	public void sendFailureMessage(String message) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SUCCESS, false);
		result.put(MSG, message);
		renderJson(FastJsonUtils.toJson(result));
	}
	
	/**
	 * 将value保存到request。
	 * @param name
	 * @param value
	 * @author yinlei
	 * date 2012-7-5 下午9:38:26
	 */
	protected void setAttributeToRequest(String name, Object value) {
		getHttpServletRequest().setAttribute(name, value);
	}
	
	/**
	 * 取得request中参数值。
	 * @param name
	 * @author yinlei
	 * date 2012-7-5 下午10:19:33
	 */
	protected Object getRequestParamater(String name) {
		return getHttpServletRequest().getAttribute(name);
	}
	
	/**
	 * 将value保存到session。
	 * @param name
	 * @param value
	 * @author yinlei
	 * date 2012-7-5 下午10:05:32
	 */
	protected void setAttributeToSession(String name, Object value) {
		getHttpSession().setAttribute(name, value);
	}
	
	/**
	 * 取得session中参数值。
	 * @param name
	 * @author yinlei
	 * date 2012-7-5 下午10:21:44
	 */
	protected Object getSessionParameter(String name) {
		return getHttpSession().getAttribute(name);
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
        String token = (String) getHttpSession().getAttribute(CommonConst.TOKEN_NAME);
        String tokenName = getHttpServletRequest().getParameter("tokenName");
        if (tokenName != null && tokenName.equals(token)) {
            getHttpSession().removeAttribute(CommonConst.TOKEN_NAME);//移除旧的session值
            setTokenValue();//添加新的session值，供下次form提交使用
            return true;
        }
        return false;
	}
	
	public HttpServletResponse getHttpServletResponse() {
		return RequestContextHolder.getResponse();
	}
	
	public HttpServletRequest getHttpServletRequest() {
		return RequestContextHolder.getRequest();
	}
	
	public HttpSession getHttpSession() {
		return RequestContextHolder.getRequest().getSession();
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
		response(getHttpServletResponse(), json, "application/json;charset=UTF-8");
	}

	/**
	 * 直接输出纯HTML。
	 */
	public void renderHtml(String text) {
		response(getHttpServletResponse(), text, "text/html;charset=UTF-8");
	}

	/**
	 * 直接输出纯字符串。
	 */
	public void renderText(String text) {
		response(getHttpServletResponse(), text, "text/plain;charset=UTF-8");
	}
	
	/**
	 * 默认方式的分页查询方式
	 * @param model 表单model数据
	 * @param pageBean 分页数据
	 * @param maps 返回给页面view的数据
	 */
	protected void queryForPage(T model, PageBean<T> pageBean, Map<String, Object> maps) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (isQuery()) {
			params = ReflectUtils.buildHql(model);
		} else {
			String hql = new StringBuilder("select tbs from ")
				.append(model.getClass().getSimpleName())
				.append(" tbs ").toString();
			params.put(HQL, hql);
		}
		page = pageBean.getPage();
		String hql = params.get(HQL).toString();
		params.remove(HQL);
		//具体查询回调
		genericServiceImpl.queryForPageByHql(page, hql, params);
		listResult = page.getResult();
		maps.put("listResult", listResult);
		setAttributeToRequest(CommonConst.PAGE_NAME, page);
	}
	
	/**
	 * 抽象分页查询方法，同时规范该Action对应的service的注入。genericServiceImpl参数名，要改成具体的service的名字。
	 * @param genericServiceImpl 延迟到子类中，注入具体的servie实现。
	 */
	public abstract void setGenericServiceImpl(IGenericService<T, ? extends Serializable> genericServiceImpl);

	/**
	 * 每一个模组的初始化方法
	 * @param model 表单模型数据
	 * @param pageBean 分页数据
	 * @param maps 返回给view的数据
	 * @return 逻辑视图名
	 * @throws Exception
	 */
	//public abstract String initial(T model, PageBean<T> pageBean, Map<String, Object> maps) throws Exception;
}
