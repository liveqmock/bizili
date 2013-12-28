package com.vteba.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 字符编码过滤器。默认UTF-8。
 * @author yinlei
 * date 2013-8-9 下午7:09:30
 */
public class CharacterEncodingFilter implements Filter {
	private static final String CHARSET = "charset";
	private static final String FORCE_ENCODING = "forceEncoding";
	private static final String DEFAULT_CHARSET = "UTF-8";
	
	private String encoding;
	private boolean forceEncoding;
	
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		
		if (this.encoding != null && (this.forceEncoding || request.getCharacterEncoding() == null)) {
			request.setCharacterEncoding(this.encoding);
			if (this.forceEncoding) {
				response.setCharacterEncoding(this.encoding);
			}
		}
		filterChain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.encoding = filterConfig.getInitParameter(CHARSET);
		if (this.encoding == null) {
			this.encoding = DEFAULT_CHARSET;
		}
		String force = filterConfig.getInitParameter(FORCE_ENCODING);
		if (force == null || !force.equals("true")) {
			force = "false";
		}
		this.forceEncoding = Boolean.parseBoolean(force);
	}

}
