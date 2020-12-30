<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://www.baocloud.com/javaee/jstl/core"%>
<c:if test="${not empty sessionScope.user }">
	<c:redirect
		url="${applicationScope.basePath }user/user_base_info.jsp?state=1" />
</c:if>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户注册</title>
<link type="text/css" href="${applicationScope.basePath }css/yunku.css"
	rel="stylesheet" />
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
</head>
<body>
	<div>
		<div style="margin-top: 10px;">
			<div class="logo2"></div>
			<span><a href="${applicationScope.basePath }">云酷首页</a></span>
		</div>
		<div style="margin-top:60px; ">
		</div>
		<div style="text-align: center;">
			<div style="width: 260px; margin: 0 auto;">
				<form action="${applicationScope.basePath }user/reg" method="post">
					<ul style="list-style: none; padding-left: 0px;">
						<li class="li3"></li>
						<li class="li2"><span>欢迎注册云库用户</span></li>
						<li class="li3"></li>
						<li class="li2"><input type="email" id="email" name="email"
							class="input1" placeholder="电子邮件" onfocus="setEmail(this)"
							onchange="checkEmail(this)" /></li>
						<li class="li3"><span id="e_message"
							style="color: #FF0000; font-size: 12px;"></span></li>
						<li class="li2"><input type="text" class="input2"
							placeholder="邮件验证码" id="codes" /><input type="button" id="but"
							class="but1" value="获取验证码" onclick="sentEmailCodes()"
							disabled="disabled" /></li>
						<li class="li3"></li>
						<li class="li2"><input type="password" id="password"
							class="input1" placeholder="密码6到18个字符" name="password"
							onchange="checkPassword(this)" onfocus="setPassword(this)" /></li>
						<li class="li3"><span id="pwd_message"
							style="color: #FF0000; font-size: 12px;"></span></li>
						<li class="li2"><input type="password" class="input1"
							placeholder="确认密码" id="password" name="password2"
							onchange="checkPassword2(this)" onfocus="setPassword2(this)" /></li>
						<li class="li3"><span id="pwd2_message"
							style="color: #FF0000; font-size: 12px;"></span></li>
						<li class="li2"><input type="button" class="but2" value="注册"
							onclick="checkCodes()" /></li>
						<li class="li3"><span style="color: #ADADAD;">已有帐号</span><a
							href="login.jsp">直接登录</a></li>
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
		/**
		 * 验证电子邮件
		 * 
		 */
		function checkEmail(e) {
			var email = e.value;
			//验证email正则表达式
			var regex = /^(?:\w+\.?)*\w+@(?:\w+\.)*\w+$/;
			if (!regex.test(email)) {
				document.getElementById("e_message").innerText = "电子邮件格式错误";
				document.getElementById("but").disabled = true;
				return;
			}
			//验证email是否已经被注册(Ajax) 代码目前无需看
			var xmlHttpRequest = createXmlHttpRequest();
			if (xmlHttpRequest) {
				var url = "${applicationScope.basePath }user/check_email?email="
						+ email;
				xmlHttpRequest.open("GET", url, true);
				xmlHttpRequest.onreadystatechange = function() {
					if (200 == xmlHttpRequest.status
							&& 4 == xmlHttpRequest.readyState) {
						//
						var state = parseInt(xmlHttpRequest.responseText);
						if (state > 0) {
							document.getElementById("e_message").innerText = "电子邮件已被注册!";
							e.value = "";
							document.getElementById("but").disabled = true;
						} else if (-1 == state) {
							document.getElementById("e_message").innerText = "检测发生异常!";
							document.getElementById("but").disabled = true;
						} else {
							document.getElementById("but").disabled = false;
						}
					}
				};
				xmlHttpRequest.send(); //发送Ajax请求
			}
		}
		/**
		 * 
		 * 当获得焦点的时候,如果邮件地址错误就清除email,并且去除错误提示信息
		 * 格式正确无需处理
		 */
		function setEmail(e) {
			var email = e.value;
			var regex = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
			if (!regex.test(email)) {
				document.getElementById("e_message").innerText = "";
				e.value = "";
			}
		}
	</script>
	<script type="text/javascript">
		/**
		 * 创建Ajax对象
		 */
		function createXmlHttpRequest() {
			var xmlHttpRequest = false;
			var supportAjax = true;
			if (window.XMLHttpRequest) {
				//IE7+、Opera、Chrome、Firefox、Safari等
				xmlHttpRequest = new XMLHttpRequest();
			} else if (window.ActiveXObject) {
				//IE5、IE6
				var versions = [ "Microsoft.XMLHTTP", "MSXML.XMLHTTP",
						"Msxml2.XMLHTTP.6.0", "Msxml2.XMLHTTP.5.0",
						"Msxml2.XMLHTTP.4.0", "MSXML2.XMLHTTP.3.0",
						"MSXML2.XMLHTTP" ];
				var length = versions.length;
				for (var v = 0; v < length; v++) {
					try {
						xmlHttpRequest = new ActiveXObject(versions[v]);
						break;
					} catch (e) {
						continue;
					}
				}
			} else {
				alert("当前浏览器不支持使用AJAX");
				supportAjax = false;
			}
			if (supportAjax) {
				if (!xmlHttpRequest) {
					alert("创建XMLHttpRequest失败");
				}
			}
			return xmlHttpRequest;
		}
		//发送邮件验证码
		function sentEmailCodes() {
			var email = document.getElementById("email").value;
			var xmlHttpRequest = createXmlHttpRequest();
			if (xmlHttpRequest) {
				document.getElementById("but").disabled = true;
				//Ajax请求服务器发送email
				var url = "user/sent_email_codes?email=" + email;
				xmlHttpRequest.open("GET", url, true);
				xmlHttpRequest.onreadystatechange = function() {
					if (200 == xmlHttpRequest.status
							&& 4 == xmlHttpRequest.readyState) {
						var state = parseInt(xmlHttpRequest.responseText);
						if (1 == state) {
							alert("电子邮件已经发送");
							//设置60秒以后重新发送
							t = 60;
							intervalID = window.setTimeout("countdown()", 1000);
						} else {
							alert("邮件发送失败,请稍后再试!");
							document.getElementById("but").disabled = false;
						}
					}
				};
				xmlHttpRequest.send();
			}
		}
		//验证码发送倒计时
		function countdown() {
			document.getElementById("but").value = "验证码已发送(" + t + ")";
			t--;
			if (t < 0) {
				window.clearTimeout(intervalID);
				document.getElementById("but").disabled = false;
				document.getElementById("but").value = "获取验证码"
			} else {
				intervalID = window.setTimeout("countdown()", 1000)
			}
		}
		//验证密码格式
		function checkPassword(p) {
			var pwd = p.value;
			if ("" == pwd) {
				document.getElementById("pwd_message").innerText = "请输入密码";
			} else {
				var regex = /^[^\s]{6,18}$/;
				if (!regex.test(pwd)) {
					document.getElementById("pwd_message").innerText = "密码为6-18个非空白字符";
				}
			}
		}
		//重新设置密码
		function setPassword(p) {
			document.getElementById("pwd_message").innerText = "";
		}

		function checkPassword2(p) {
			var pwd2 = p.value;
			if ("" == pwd2) {
				document.getElementById("pwd2_message").innerText = "请输入确认密码";
			} else {
				var pwd = document.getElementById("password").value;
				if (pwd != pwd2) {
					document.getElementById("pwd2_message").innerText = "两次输入密码不一致!";
				}
			}
		}
		//重新设置确认密码
		function setPassword2(p) {
			document.getElementById("pwd2_message").innerText = "";
		}
		//检测邮件验证码
		function checkCodes() {
			var codes = document.getElementById("codes").value;
			if ("" == codes) {
				alert("请输入邮件验证码");
				return;
			}
			//Ajax验证验证码是否正确
			var xmlHttpRequest = createXmlHttpRequest();
			var codes = document.getElementById("codes").value;
			if (xmlHttpRequest) {
				var url = "user/check_codes?codes=" + codes;
				xmlHttpRequest.open("GET", url, true);
				xmlHttpRequest.onreadystatechange = function() {
					if (200 == xmlHttpRequest.status
							&& 4 == xmlHttpRequest.readyState) {
						//
						var state = parseInt(xmlHttpRequest.responseText);
						if (1 == state) {
							//验证码正确,提交表单
							document.forms[0].submit();
						} else if (0 == state) {
							alert("验证码错误");
						} else if (-1 == state) {
							alert("请重新发送邮件验证码");
						} else {
							alert("客户端请求未传验证码");
						}
					}
				};
				xmlHttpRequest.send(); //发送Ajax请求
			}
		}
	</script>
</body>
</html>