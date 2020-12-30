package com.baocloud.yunku.web;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckUnloginFilter implements Filter {

	public CheckUnloginFilter() {

	}

	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		// 检查又没有登录,没有登录,重定向到login.jsp
		HttpServletRequest request = (HttpServletRequest) req;
		if (null == request.getSession().getAttribute("user")) {
			// 未登录
			HttpServletResponse response = (HttpServletResponse) resp;
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			response.sendRedirect(basePath + "login.jsp?state=12306");
		} else {
			chain.doFilter(req, resp);
		}
	}

	public void init(FilterConfig config) throws ServletException {

	}

}
