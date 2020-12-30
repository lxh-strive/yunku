<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://www.baocloud.com/javaee/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户中心-绑定手机</title>
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
					<li class="tag"
						data-url="${applicationScope.basePath }user/user_set_name.jsp">云酷帐号</li>
					<li class="tag"
						data-url="${applicationScope.basePath }user/user_update_pwd.jsp">修改密码</li>
					<li class="tag"
						data-url="${applicationScope.basePath }user/user_update_head.jsp">修改头像</li>
					<li class="tag_select"
						data-url="${applicationScope.basePath }user/user_set_mobile.jsp">绑定手机</li>
					<li class="tag" data-url="${applicationScope.basePath }user/user_videos.jsp">我的视频</li>
					<li class="tag" data-url="#">视频收藏</li>
					<li class="tag" data-url="#">观看记录</li>
					<li class="tag" data-url="#">我的评论</li>
				</ul>
			</div>
		</div>
		<div class="middle">
			<c:choose>
				<c:when test="${not empty sessionScope.user}">
					<form action="${applicationScope.basePath}user/set_mobile"
						method="post">
						<c:choose>
							<c:when test="${not empty sessionScope.user.mobile  }">
								<input type="text" id="mobile" name="mobile"
									placeholder="输入11位手机号码" value="${sessionScope.user.mobile}"
									onfocus="clearMessage()" />
								<input type="button" value="修改手机" onClick="bindMobile()" />
								<input type="hidden" id="old_mobile"
									value="${sessionScope.user.mobile}" />
							</c:when>
							<c:otherwise>
								<input type="text" id="mobile" name="mobile"
									placeholder="输入11位手机号码" onFocus="clearMessage()" />
								<input type="button" value="绑定手机" onClick="bindMobile()" />
							</c:otherwise>
						</c:choose>
						<span id="check_msg" style="color: #FF0000"></span>
					</form>
					<script type="text/javascript"
						src="${applicationScope.basePath }js/ajaxFrame.js"></script>
					<script type="text/javascript">
						//设置手机
						function bindMobile() {
							var mobile = document.getElementById("mobile").value;
							if ("" == mobile) {
								document.getElementById("check_msg").innerText = "请输入手机号码";
								return;
							}
							var regex = /^((1[358][0-9])|(17[678])|(14[57]))[0-9]{8}$/;
							if (!regex.test(mobile)) {
								document.getElementById("check_msg").innerText = "手机格式错误!";
								return;
							}
							var old_mobile = document
									.getElementById("old_mobile");
							if (old_mobile) {
								if (mobile == old_mobile.value) {
									alert("您未修改手机!");
									return;
								}
							}
							var url = "${applicationScope.basePath}user/check_mobile?mobile="
									+ mobile;
							var ajaxUtils = new AjaxUtils(url, true);
							ajaxUtils.sentGet(checkmobile);
						}
						/**
						 *检查手机是否存在
						 */
						function checkmobile(state) {
							if (0 == state) {
								document.forms[0].submit();
							} else if (1 == state) {
								document.getElementById("check_msg").innerText = "手机已被使用";
							} else if (-1 == state) {
								alert("检测手机失败,服务器内部错误,请稍后再试!");
							} else {
								alert("检测手机失败,服务器返回错误状态!");
							}

						}
						/**
						 *清除验证错误信息
						 */
						function clearMessage() {
							document.getElementById("check_msg").innerText = "";
						}
					</script>
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