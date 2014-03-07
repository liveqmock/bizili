package com.vteba.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import com.vteba.service.context.RequestContextHolder;

/**
 * Servlet Filter that exposes the request/response to the current thread,
 * through {@link RequestContextHolder}. To be registered as filter in {@code web.xml}.
 * @author yinlei
 * @date 2013-8-30
 */
public class RequestContextFilter extends OncePerRequestFilter {

	/**
	 * Returns "false" so that the filter may set up the request context in each
	 * asynchronously dispatched thread.
	 */
	@Override
	protected boolean shouldNotFilterAsyncDispatch() {
		return false;
	}

	/**
	 * Returns "false" so that the filter may set up the request context in an
	 * error dispatch.
	 */
	@Override
	protected boolean shouldNotFilterErrorDispatch() {
		return false;
	}

	@Override
	protected void doFilterInternal(
			HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		ServletWebRequest servletWebRequest = new ServletWebRequest(request, response);
		initContextHolders(request, servletWebRequest);
		
		try {
			filterChain.doFilter(request, response);
		}
		finally {
			resetContextHolders();
			servletWebRequest.requestCompleted();
		}
	}

	private void initContextHolders(HttpServletRequest request, ServletWebRequest servletWebRequest) {
		RequestContextHolder.setServletWebRequest(servletWebRequest);
	}

	private void resetContextHolders() {
		RequestContextHolder.resetServletWebRequest();
	}

}

