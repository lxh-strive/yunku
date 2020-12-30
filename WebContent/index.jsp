<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://www.baocloud.com/javaee/jstl/core"%>
<!-- 跳转首页 -->
<%
	String path = request.getContextPath();
	String rootPath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ "/";
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	ServletContext webContext = request.getSession()
			.getServletContext();
	application.setAttribute("rootPath", rootPath);
	application.setAttribute("basePath", basePath);
%>
<c:redirect url="${applicationScope.basePath}ui/index" />