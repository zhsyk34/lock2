package com.dnake.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String url = request.getRequestURL().toString();
		if (request.getSession().getAttribute("manager") != null || url.contains("login") || url.contains("logout")) {
			return true;
		}
		String header = request.getHeader("x-requested-with");
		if ("XMLHttpRequest".equalsIgnoreCase(header)) {
			response.setHeader("time", Boolean.TRUE.toString());
		} else {
			response.sendRedirect(request.getContextPath() + "/login");
		}
		return false;
	}
}
