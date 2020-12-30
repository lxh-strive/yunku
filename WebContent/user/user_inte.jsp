<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://www.baocloud.com/javaee/jstl/core"%>
<%@taglib prefix="fmt" uri="http://www.baocloud.com/javaee/jstl/fmt"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户中心-用户积分</title>
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
					<li class="tag_select"
						data-url="#">我的积分</li>
					<li class="tag"
						data-url="${applicationScope.basePath }user/user_set_name.jsp">云酷帐号</li>
					<li class="tag"
						data-url="${applicationScope.basePath }user/user_update_pwd.jsp">修改密码</li>
					<li class="tag" data-url="${applicationScope.basePath }user/user_update_head.jsp">修改头像</li>
					<li class="tag"
						data-url="${applicationScope.basePath }user/user_set_mobile.jsp">绑定手机</li>
					<li class="tag" data-url="${applicationScope.basePath }user/user_videos.jsp">我的视频</li>
					<li class="tag" data-url="#">视频收藏</li>
					<li class="tag" data-url="#">观看记录</li>
					<li class="tag" data-url="#">我的评论</li>
				</ul>
			</div>
		</div>
		<div class="middle" >
			 <table width="600"  border="1" cellpadding="10" bordercolor="#B6A6F7" cellspacing="0">
		<tr align="center" bgcolor="#E0E0E0">
			<th width="70">积分</th>
			<th width="280">积分描述</th>
			<th width="180">时间</th>
			<th width="70">当前余额</th>
		</tr>
		<c:choose>
			<c:when test="${empty inteList} ">
				<tr>
					<td align="center" colspan="4" style=color:#FF0000>该用户当前积分为0</td>
				</tr>
			</c:when>
					<c:otherwise>
						<c:forEach items="${inteList}" var="inte">
							<tr>
								<td align="right">${inte.inte}</td>
								<td align="left">${inte.inteDesc}</td>
								<td align="center">
								<fmt:formatDate value="${inte.time}" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td align="right">${inte.bal}</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
	</table>
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