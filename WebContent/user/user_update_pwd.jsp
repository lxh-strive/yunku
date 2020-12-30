<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://www.baocloud.com/javaee/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户中心-修改密码</title>
<style type="text/css">
input {
	width: 200px;
	height: 25px;
	vertical-align: middle;
}
</style>
<link type="text/css" href="${applicationScope.basePath }css/yunku.css"
	rel="stylesheet" />
<link type="text/css" href="${applicationScope.basePath }css/user.css"
	rel="stylesheet" />
<script type="text/javascript"
	src="${applicationScope.basePath }js/ajaxFrame.js"></script>
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
						<span class="span_sys">欢迎回来!${sessionScope.user.nickname}&nbsp;&nbsp;<a
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
					<li class="tag"
						data-url="${applicationScope.basePath }user/user_set_name.jsp">云酷帐号</li>
					<li class="tag_select" data-url="#">修改密码</li>
					<li class="tag"
						data-url="${applicationScope.basePath }user/user_update_head.jsp">修改头像</li>
					<li class="tag" data-url="${applicationScope.basePath }user/user_set_mobile.jsp">绑定手机</li>
					<li class="tag" data-url="${applicationScope.basePath }user/user_videos.jsp">我的视频</li>
					<li class="tag" data-url="#">视频收藏</li>
					<li class="tag" data-url="#">观看记录</li>
					<li class="tag" data-url="#">我的评论</li>
				</ul>
			</div>
		</div>
		<div class="middle">
			<form action="${applicationScope.basePath }user/update_pwd"
				method="post">
				<ul style="padding-left: 0px">
					<li style="float: none;">旧密码:&nbsp;<input type="password"
						id="oldpwd" placeholder="旧密码为6-18个非空白字符"
						onfocus="clearMessage('oldpwd_msg')" /></li>
					<li style="float: none; height: 30px"><span id="oldpwd_msg"
						style="color: #FF0000"></span></li>
					<li style="float: none">新密码:&nbsp;<input type="password"
						name="newpwd" id="newpwd" placeholder="新密码不能和旧密码相同"
						onfocus="clearMessage('newpwd_msg')" /></li>
					<li style="float: none; height: 30px"><span id="newpwd_msg"
						style="color: #FF0000"></span></li>
					<li style="float: none">确认密码:<input type="password"
						id="conpwd" onfocus="clearMessage('conpwd_msg')"
						placeholder="确认密码和新密码相同" /></li>
					<li style="float: none; height: 30px"><span id="conpwd_msg"
						style="color: #FF0000"></span></li>
					<li style="float: none"><input type="button" value="确定修改"
						onclick="updatePwd()" /></li>
				</ul>
			</form>
			<script type="text/javascript">
				var regex = /^[^\s]{6,18}$/;
				/**
				 *修改密码
				 */
				function updatePwd() {
					var oldpwd = document.getElementById("oldpwd").value;
					if ("" == oldpwd) {
						document.getElementById("oldpwd_msg").innerText = "请输入旧密码";
						return;
					}
					if (!regex.test(oldpwd)) {
						document.getElementById("oldpwd_msg").innerText = "旧密码格式不对";
						return;
					}
					var url = "${applicationScope.basePath }user/user_check_pwd";
					var ajaxUtils = new AjaxUtils(url, true);
					ajaxUtils.sentPost(checkOldpwd, "password=" + oldpwd);
				}
				/**
				 *检查旧密码是否正确
				 */
				function checkOldpwd(state) {
					if (1 == state) {
						//验证新密码
						var newpwd = document.getElementById("newpwd").value;
						if ("" == newpwd) {
							document.getElementById("newpwd_msg").innerText = "请输入新密码";
							return;
						}
						if (!regex.test(newpwd)) {
							document.getElementById("newpwd_msg").innerText = "新密码格式不对";
							return;
						}
						var oldpwd = document.getElementById("oldpwd").value;
						if (newpwd == oldpwd) {
							document.getElementById("newpwd_msg").innerText = "新密码与旧密码相同";
							return;
						}
						//验证确认密码
						var conpwd = document.getElementById("conpwd").value;
						if ("" == conpwd) {
							document.getElementById("conpwd_msg").innerText = "请输入确认密码";
							return;
						}
						if (newpwd != conpwd) {
							document.getElementById("conpwd_msg").innerText = "两次输入密码不一致";
							return;
						}
						document.forms[0].submit();

					} else if (0 == state) {
						document.getElementById("oldpwd_msg").innerText = "旧密码输入错误";
					} else if (-1 == state) {
						alert("检测旧密码失败,服务器异常!");
					} else if (-2 == state) {
						alert("未登录!");
					} else if (110 == state) {
						alert("检测旧密码失败,缺少参数!");
					} else {
						alert("检测旧密码失败,服务器返回未知状态吗!");
					}
				}
				//清除错误消息
				function clearMessage(msgId) {
					document.getElementById(msgId).innerText = "";
				}
			</script>
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