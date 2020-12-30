<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://www.baocloud.com/javaee/jstl/core"%>
<!DOCTYPE>
<head>
<meta charset="utf-8" />
<title>云酷专业视频网站</title>
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
						placeholder="娱乐/新闻/体育/电影..." /> <input type="submit"
						class="search" value="" />
				</form>
			</div>
		</div>
		<div class="middle">
			<div>
				<c:choose>
					<c:when test="${null== requestScope.videoTypes }">
						<span style="color: #FF0000;">加载视频分类数据失败,服务器异常</span>
					</c:when>
					<c:when test="${not empty requestScope.videoTypes}">
						<c:forEach items="${requestScope.videoTypes }" var="parentType">
							<ul class="video_types">
								<li class="parent_type" data-id="${parentType.parentId}">${parentType.name}(${parentType.counts})</li>
								<c:if test="${not empty parentType.subTypes }">
									<c:forEach items="${parentType.subTypes }" var="subType">
										<li class="sub_type" data-id="${subType.subId}">${subType.name }(${subType.counts })</li>
									</c:forEach>
								</c:if>
							</ul>
						</c:forEach>
						<script type="text/javascript">
							window.onload = function() {
								var videoTypeLis = document
										.getElementsByTagName("LI");
								var len = videoTypeLis.length;
								for (var index = 0; index < len; index++) {
									var videoTypeLi = videoTypeLis[index];
									var className = videoTypeLi.className;
									if ("parent_type" == className) {
										videoTypeLi.onclick = function() {
											var parent_id = this.dataset.id;
											window.location.href = "${applicationScope.basePath}videos/search_parent?parent_id="
													+ parent_id;
										};
									} else if ("sub_type" == className) {
										videoTypeLi.onclick = function() {
											var sub_id = this.dataset.id;
											window.location.href = "${applicationScope.basePath}videos/search_sub?sub_id="
													+ sub_id;
										};
									} else {

									}
								}
							}
						</script>
					</c:when>
					<c:otherwise>
						<span style="color: #FF0000;">无视频分类数据</span>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="bottom">
			Copyright${applicationScope.basePath}<span class="copy">&copy;</span>2016
			baocloud
		</div>
	</div>
</body>