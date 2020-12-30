package com.baocloud.yunku.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * 设置web资源基础路径
 * 
 * @author wzr
 *
 */
@WebFilter(filterName = "WebBasePathFilter", value = "*.jsp")
public class WebBasePathFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		ServletContext webContext = request.getSession().getServletContext();
		if (null == webContext.getAttribute("basePath")
				|| null == webContext.getAttribute("rootPath")) {
			String path = request.getContextPath();
			String rootPath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ "/";
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			webContext.setAttribute("rootPath", rootPath);
			webContext.setAttribute("basePath", basePath);
		}
		chain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
