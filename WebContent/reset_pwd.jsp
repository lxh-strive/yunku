<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>密码重置</title>
</head>
<body>
	<form action="<%=basePath%>user/reset_pwd" method="post">
		新密码:<input type="password" name="newpwd" placeholder="密码为6-18个非空白字符" />
		确认密码:<input type="password" placeholder="确认密码与密码相同" /> <input
			type="submit" value="重置密码" /> <input type="hidden" name="userid"
			value="${ requestScope.userid}" />
	</form>
</body>
</html>