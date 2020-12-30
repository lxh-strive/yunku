<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://www.baocloud.com/javaee/jstl/core"%>
<html>
<head>
<meta charset="utf-8" />
<title>视频检索列表</title>
<link type="text/css" href="${applicationScope.basePath}css/yunku.css"
	rel="stylesheet" />
<link type="text/css" href="${applicationScope.basePath}css/ui.css"
	rel="stylesheet" />
</head>
<body>
	<div class="base">
		<div class="head_bg">
			<c:choose>
				<c:when test="${not empty sessionScope.user }">
					<ul style="float: right; margin-right: 30px;">
						<li><span><a href="${applicationScope.basePath }">云酷首页</a></span></li>
						<li style="width: 30px; text-align: center;">|</li>
						<li>欢迎回来!<a
							href="${applicationScope.basePath }user/user_base_info.jsp">${sessionScope.user.nickname}</a></li>
						<li style="width: 30px; text-align: center;">|</li>
						<li><a href="${applicationScope.basePath }user/exit">退出</a></li>
						<li style="width: 30px; text-align: center;">|</li>
						<li>APP下载</li>
						<li style="width: 30px; text-align: center;">|</li>
						<li>PC客户端</li>
					</ul>
				</c:when>
				<c:otherwise>
					<ul style="float: right; margin-right: 30px;">
						<li><span><a href="${applicationScope.basePath }">云酷首页</a></span></li>
						<li style="width: 30px; text-align: center;">|</li>
						<li><a href="${applicationScope.basePath }login.jsp">登录</a></li>
						<li style="width: 30px; text-align: center;">|</li>
						<li><a href="${applicationScope.basePath }register.jsp">注册</a></li>
						<li style="width: 30px; text-align: center;">|</li>
						<li>APP下载</li>
						<li style="width: 30px; text-align: center;">|</li>
						<li>PC客户端</li>
					</ul>
				</c:otherwise>
			</c:choose>
		</div>
		<div style="margin-top: 10px;">
			<div class="logo"></div>
			<div class="search_form">
				<form action="${applicationScope.basePath}videos/search_kw"
					method="post">
					<input type="search" id="keyword" name="keyword"
						placeholder="娱乐/新闻/体育/电影..." value="${requestScope.keyword }" />
					<input type="submit" class="search" value="" />
				</form>
			</div>
		</div>
		<div class="middle">
			<div class="v_row_first"></div>
			<c:choose>
				<c:when test="${not empty requestScope.videos }">
					<c:forEach items="${ requestScope.videos}" var="video"
						varStatus="s">
						<div class="v_conext">
							<div class="v_bg_img" title="${video.name}">
								<div class="v_front_img" data-id="${video.videoId }"
									data-img="${applicationScope.rootPath}${video.minPic}"></div>
								<div class="v_name_bg"></div>
								<span class="v_name">${video.name }</span>
							</div>
							<div style="height: 30px; line-height: 30px;">
								<ul style="padding-left: 0px;">
									<li style="width: 150px"><img
										src="${applicationScope.basePath}img/p.png" title="播放总数量" /><span>${video.playNumber }</span></li>
									<li style="width: 150px"><img
										src="${applicationScope.basePath}img/u.png" title="发布者" /><span>${video.user.nickname }</span></li>
								</ul>
							</div>
						</div>
						<c:if test="${0==s.count mod 4 }">
						<div class="v_row_next"></div>
						</c:if>
					</c:forEach>
					<script type="text/javascript">
						window.onload = function() {
							var divs = document.getElementsByTagName("DIV");
							var len = divs.length;
							for (var index = 0; index < len; index++) {
								var div = divs[index];
								var className = div.className;
								if ("v_front_img" == className) {
									var img = div.dataset.img;
									div.parentNode.style.backgroundImage = "url("
											+ img + ")";
									div.onclick = function() {
										var videoId = this.dataset.id;
										var url = "${applicationScope.basePath}video/view?video_id="
												+ videoId;
										window.open(url, "video_view");
									};
								} else {

								}
							}
						}
					</script>
				</c:when>
				<c:otherwise>
           没有搜索到任何到视频
         </c:otherwise>
			</c:choose>
			<div class="v_row_next"></div>
		</div>
		<div class="bottom">
			Copyright<span class="copy">&copy;</span>2016 baocloud
		</div>
	</div>
</body>
</html>