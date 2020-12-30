<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://www.baocloud.com/javaee/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户中心-设置帐号</title>
<link type="text/css" href="${applicationScope.basePath }css/yunku.css"
	rel="stylesheet" />
<link type="text/css" href="${applicationScope.basePath }css/user.css"
	rel="stylesheet" />
</head>
<body>
	<div class="base">
		<div class="head">
			<div class="logo"></div>
			<div>
				<span class="span_index"><a
					href="${applicationScope.basePath }">云酷首页</a></span>
				<c:choose>
					<c:when test="${ not empty sessionScope.user }">
						<span class="span_sys">欢迎回来!${sessionScope.user.nickname }&nbsp;&nbsp;<a
							href="${applicationScope.basePath }user/exit">退出系统</a></span>
					</c:when>
					<c:otherwise>
						<span class="span_sys">未登录</span>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div style="height: 25px;"></div>
		<div>
			<div class="user_logo">
				<span class="user_center">用户中心 </span>
			</div>
			<div>
				<ul class="user_navi" id="user_navi">
					<li class="tag"
						data-url="${applicationScope.basePath }user/user_base_info.jsp">基本信息</li>
					<li class="tag"
						data-url="${applicationScope.basePath }user/get_sign_state">每日签到</li>
					<li class="tag"
						data-url="${applicationScope.basePath }user/query_inte">我的积分</li>
					<li class="tag_select" data-url="#">云酷帐号</li>
					<li class="tag"
						data-url="${applicationScope.basePath }user/user_update_pwd.jsp">修改密码</li>
					<li class="tag"
						data-url="${applicationScope.basePath }user/user_update_head.jsp">修改头像</li>
					<li class="tag"
						data-url="${applicationScope.basePath }user/user_set_mobile.jsp">绑定手机</li>
					<li class="tag"
						data-url="${applicationScope.basePath }user/user_videos.jsp">我的视频</li>
					<li class="tag" data-url="#">视频收藏</li>
					<li class="tag" data-url="#">观看记录</li>
					<li class="tag" data-url="#">我的评论</li>
				</ul>
			</div>
		</div>
		<div class="middle">
			<c:choose>
				<c:when test="${not empty sessionScope.user }">
					<c:choose>
						<c:when test="${not empty sessionScope.user.username }">
							<div>
								<span>您的云酷账户:${sessionScope.user.username }</span>
							</div>
							<div>
								<span style="color: #FF0000; margin-top: 20px; font-size: 12px">温馨提示:用户名设置后不可以修改!</span>
							</div>
						</c:when>
						<c:otherwise>
							<form action="user/user_set_name" method="post">
								<input type="text" id="username" name="username"
									placeholder="用户名以字母开头,后跟数字、下划线.长度为4-20个字符"
									onfocus="clearMessage()" /> <input type="button" value="设置"
									onclick="setUsername()" /> <span id="check_msg"
									style="color: #FF0000"></span>
							</form>
							<script type="text/javascript"
								src="${applicationScope.basePath }js/ajaxFrame.js"></script>
							<script type="text/javascript">
								//设置用户名
								function setUsername() {
									var username = document
											.getElementById("username").value;
									if ("" == username) {
										document.getElementById("check_msg").innerText = "请输入用户名";
										return;
									}
									var regex = /^[a-zA-Z][a-zA-Z_0-9_]{3,19}$/;
									if (!regex.test(username)) {
										document.getElementById("check_msg").innerText = "用户格式错误!";
										return;
									}
									var url = "${applicationScope.basePath }user/check_user_name?username="
											+ username;
									var ajaxUtils = new AjaxUtils(url, true);
									ajaxUtils.sentGet(checkUsername);
								}
								/**
								 *检查用户名是否存在
								 */
								function checkUsername(state) {
									if (0 == state) {
										document.forms[0].submit();
									} else if (1 == state) {
										document.getElementById("check_msg").innerText = "用户名已被使用";
									} else if (-1 == state) {
										alert("检测用户名失败,服务器内部错误,请稍后再试!");
									} else {
										alert("检测用户名失败,服务器返回错误状态!");
									}

								}
								/**
								 *清除验证错误信息
								 */
								function clearMessage() {
									document.getElementById("check_msg").innerText = "";
								}
							</script>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
        请登录系统
      </c:otherwise>
			</c:choose>
		</div>
		<div class="bottom">
			Copyright<span class="copy">&copy;</span>2016 baocloud
		</div>
	</div>
	<script type="text/javascript">
		window.onload = function() {
			var user_navi_ul = document.getElementById("user_navi");
			var user_navi_lis = user_navi_ul.getElementsByTagName("LI");
			var len = user_navi_lis.length;
			for (var index = 0; index < len; index++) {
				var user_navi_li = user_navi_lis[index];
				var url = user_navi_li.dataset.url;
				if ("#" != url) {
					user_navi_li.onclick = function() {
						window.location.href = this.dataset.url;
					};
				}

			}
		}
	</script>
</body>
</body>
</html>