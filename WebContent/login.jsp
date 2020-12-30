<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://www.baocloud.com/javaee/jstl/core"%>
<!DOCTYPE>
<html>
<c:if test="${1==param.state}">
	<script type="text/javascript">
		alert("密码修改成功后,请重新登录系统!");
	</script>
</c:if>
<c:if test="${12306==param.state}">
  <script type="text/javascript">
    alert("请登录后再访问!");
  </script>
</c:if>
<c:if test="${not empty sessionScope.user }">
  <c:redirect url="${applicationScope.basePath }user/user_base_info.jsp?state=1"/>
</c:if>
<head>
<meta charset="UTF-8" />
<title>用户登录</title>
<link type="text/css" href="${applicationScope.basePath }css/yunku.css" rel="stylesheet" />
<style type="text/css">
.input1 {
	width: 240px;
	height: 30px;
}

.input2 {
	width: 100px;
	vertical-align: middle;
	height: 30px;
	margin-right: 20px;
}

.but1 {
	width: 120px;
	height: 36px;
	vertical-align: middle;
	cursor: pointer;
}

.but2 {
	width: 244px;
	height: 36px;
	vertical-align: middle;
	text-align: center;
	font-size: 20px;
	color: #FFFFFF;
	background-color: #25A3F3;
	border: 1px solid #0C8ADE;
	cursor: pointer;
}
</style>
<script type="text/javascript" src="${applicationScope.basePath }js/ajaxFrame.js"></script>
</head>

<body>
	<div>
		<div style="margin-top: 10px;">
			<div class="logo2"></div>
			<span><a href="${applicationScope.basePath }">云酷首页</a></span>
		</div>
		<div style="margin-top: 60px;"></div>
		<div style="text-align: center;">
			<div style="width: 260px; margin: 0 auto;">
				<form action="${applicationScope.basePath }user/login" method="post">
					<ul style="list-style: none; padding-left: 0px;">
						<li class="li3"></li>
						<li class="li2"><span>登录云酷网</span>&nbsp;&nbsp;&nbsp;&nbsp;<a
							href="${applicationScope.basePath }register.jsp">免费注册</a></li>
						<li class="li3"></li>
						<li class="li2"><input type="text" id="email" name="email"
							class="input1" placeholder="电子邮件" /></li>
						<li class="li3"><span id="e_message"
							style="color: #FF0000; font-size: 12px;"></span></li>
						<li class="li2"><input type="password" class="input1"
							placeholder="密码为6-18个非空白字符" name="password" id="password" /></li>
						<li class="li3"><span id="pwd_message"
							style="color: #FF0000; font-size: 12px;"></span></li>
						<li class="li2"><img id="codes_img" src="${applicationScope.basePath }common/getcodes" />
						</li>
						<li class="li3"></li>
						<li class="li2"><input type="text" id="codes" class="input2"
							placeholder="验证码" /> <input type="button" class="but1"
							value="重新获取" onclick="bulidNewCodes()" /></li>
						<li class="li3"><span id="code_message"
							style="color: #FF0000; font-size: 12px;"></span></li>
						<li class="li2"><input type="button" class="but2" value="登录"
							onclick="checkFormDatas()" /></li>
						<li class="li3"><span style="color: #ADADAD;">没有帐号</span><a
							href="${applicationScope.basePath }register.jsp">免费注册</a> <a href="${applicationScope.basePath }find_pwd.jsp">忘记密码</a></li>
					</ul>
				</form>
			</div>
		</div>
		<div style="text-align: center; margin-top: 200px;">
			<span
				style="color: #666666; font-style: oblique; font-size: 18px; margin: 0 auto;">Copyright&copy;2016
				baocloud</span>
		</div>
	</div>
	<script type="text/javascript">
		//产生新的验证码
		function bulidNewCodes() {
			document.getElementById("codes_img").src = "${applicationScope.basePath}common/getcodes?time="
					+ new Date();
		}
	</script>
	<script type="text/javascript">
		//检查登录表单数据
		function checkFormDatas() {
			//检查email
			var email = document.getElementById("email").value;
			if ("" == email) {
				document.getElementById("e_message").innerText = "请输入电子邮件/用户名";
				return;
			}
			//验证email正则表达式
			var regex = /^(?:\w+\.?)*\w+@(?:\w+\.)*\w+$/;
			if (!regex.test(email)) {
				document.getElementById("e_message").innerText = "电子邮件格式错误";
				return;
			}
			//检查密码
			var pwd = document.getElementById("password").value;
			if ("" == pwd) {
				document.getElementById("pwd_message").innerText = "请输入密码";
				return;
			}
			var regex = /^[^\s]{6,18}$/;
			if (!regex.test(pwd)) {
				document.getElementById("pwd_message").innerText = "密码为6-18个非空白字符";
				return;
			}
			//检查验证码        
			var codes = document.getElementById("codes").value;
			if ("" == codes) {
				document.getElementById("code_message").innerText = "请输入验证码!";
				return;
			}
			//Ajax验证验证码是否正确
			var xmlHttpRequest = createXmlHttpRequest();
			var codes = document.getElementById("codes").value;
			if (xmlHttpRequest) {
				var url = "${applicationScope.basePath }user/check_codes?codes=" + codes;
				xmlHttpRequest.open("GET", url, true);
				xmlHttpRequest.onreadystatechange = function() {
					if (200 == xmlHttpRequest.status
							&& 4 == xmlHttpRequest.readyState) {
						var state = parseInt(xmlHttpRequest.responseText);
						if (1 == state) {
							//验证码正确,提交表单
							document.forms[0].submit();
						} else if (0 == state) {
							document.getElementById("code_message").innerText = "验证码错误";
							bulidNewCodes();
						} else if (-1 == state) {
							document.getElementById("code_message").innerText = "请重新输入验证码";
							bulidNewCodes();
						} else {
							document.getElementById("code_message").innerText = "客户端请求未传验证码";
						}
					}
				};
				xmlHttpRequest.send(); //发送Ajax请求
			}
		}
	</script>
</body>
</html>