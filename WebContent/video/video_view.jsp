<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://www.baocloud.com/javaee/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>视频播放</title>
<style type="text/css">
.v_user {
	width: 100px;
}

.data_count {
	color: #C5C5C5;
	font-size: 12px
}
</style>
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
					href="${applicationScope.basePath }index.html">云酷首页</a></span>
				<c:choose>
					<c:when test="${ not empty sessionScope.user }">
						<span class="span_sys">欢迎回来!${sessionScope.user.nickname }&nbsp;&nbsp;
						<a href="${applicationScope.basePath }user/exit">退出系统</a></span>
					</c:when>
					<c:otherwise>
						<span class="span_sys"> <a
							href="${applicationScope.basePath }login.jsp">登录</a>&nbsp;<a
							href="${applicationScope.basePath }register.jsp">注册</a>
						</span>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div style="height: 25px;"></div>
		<div class="middle">
			<div style="text-align: center;">
				<c:choose>
					<c:when test="${empty requestScope.video }">获取视频信息失败,服务器异常 </c:when>
					<c:when test="${empty requestScope.video.videoId }">该视频不存在,或已删除</c:when>
					<c:otherwise>
						<div>
							<div>
								<span style="font-size: 20px">${requestScope.video.name}</span>
							</div>
							<video id="yunku_video"
								src="${applicationScope.rootPath}${requestScope.video.src}"
								poster="${applicationScope.rootPath }${requestScope.video.pic}"
								width="600" height="450" controls="controls"
								title="${requestScope.video.name}">
							</video>
						</div>
						<div style="width: 600px; margin: 0 auto; margin-top: 5px">
							<ul style="padding-left: 0px">
								<li class="v_user"><input type="image" id="supp" title="赞"
									src="${applicationScope.basePath }img/zan.png" /> <span
									id="suppCount" class="data_count">${requestScope.video.suppCount}</span>
								</li>
								<li class="v_user"><input type="image" id="unSupp"
									title="踩" src="${applicationScope.basePath }img/cai.png"  /> <span
									id="unSuppCount" class="data_count">${requestScope.video.unSuppCount}</span>
								</li>
								<li class="v_user"><input type="image" id="coll" title="收藏"
									src="${applicationScope.basePath }img/shoucang.png" /> <span
									id="collCount" class="data_count">${requestScope.video.collCount}</span>
								</li>
								<li class="v_user"><input type="image" id="comm" title="评论"
									src="${applicationScope.basePath }img/pinglun.PNG" /> <span
									class="data_count"><a href="${applicationScope.basePath }comment/query_comm?videoId=${requestScope.video.videoId}">${requestScope.video.commCount}</a></span></li>
								<li class="v_user"><input type="image" id="down" title="下载"
									src="${applicationScope.basePath }img/xaizai.PNG" /> <span
									id="downNumber" class="data_count">${requestScope.video.downNumber}</span>
								</li>
								<li class="v_user"><input type="image" title="播放"
									src="${applicationScope.basePath }img/play.png" /> <span
									id="playNumber" class="data_count">${requestScope.video.playNumber}</span>
								</li>
							</ul>
							<script type="text/javascript"
								src="${applicationScope.basePath }js/ajaxFrame.js"></script>
							<script type="text/javascript">
								window.onload = function() {
									document.getElementById("supp").onclick = function() {
										var url = "${applicationScope.basePath}video/add_sugges?type=1&videoId=${requestScope.video.videoId}"
										var ajaxUtils = new AjaxUtils(url,
												false);
										ajaxUtils.sentGet(suppVideoState);
									};
									document.getElementById("unSupp").onclick = function() {
										var url = "${applicationScope.basePath}video/add_sugges?type=2&videoId=${requestScope.video.videoId}"
										var ajaxUtils = new AjaxUtils(url,
												false);
										ajaxUtils.sentGet(unSuppVideoState);
									};
									document.getElementById("coll").onclick = function() {
										var url = "${applicationScope.basePath}video/add_coll?videoId=${requestScope.video.videoId}"
										var ajaxUtils = new AjaxUtils(url,
												false);
										ajaxUtils.sentGet(collVideoState);
									};
                  //视频播放结束时调用
									document.getElementById("yunku_video").onended = function() {
					                
					       };
								};
								//点赞返回的状态
								function suppVideoState(state) {
									if (1 == state) {
										var suppCount = document
												.getElementById("suppCount");
										suppCount.innerText = parseInt(suppCount.innerText) + 1;
									} else if (-2 == state) {
										alert("请登录后再点赞");
									} else if (-1 == state) {
										alert("点赞失败,服务器异常");
									} else if (2 == state) {
										alert("该视频你已经点过赞");
									} else {
										alert("点赞失败,服务器返回未知状态");
									}
								}
								//踩返回的状态
								function unSuppVideoState(state) {
									if (1 == state) {
										var suppCount = document
												.getElementById("unSuppCount");
										suppCount.innerText = parseInt(suppCount.innerText) + 1;
									} else if (-2 == state) {
										alert("请登录后再点踩");
									} else if (-1 == state) {
										alert("点踩失败,服务器异常");
									} else if (2 == state) {
										alert("该视频你被你踩过");
									} else {
										alert("点踩失败,服务器返回未知状态");
									}
								}

								//踩返回的状态
								function collVideoState(state) {
									if (1 == state) {
										var suppCount = document
												.getElementById("collCount");
										suppCount.innerText = parseInt(suppCount.innerText) + 1;
									} else if (-2 == state) {
										alert("收藏视屏需要登录");
									} else if (-1 == state) {
										alert("收藏失败,服务器异常");
									} else if (2 == state) {
										alert("该视频已收藏");
									} else {
										alert("收藏失败,服务器返回未知状态");
									}
								}
								//视屏播放结束时
								
							</script>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="bottom">
			Copyright<span class="copy">&copy;</span>2016 baocloud
		</div>
	</div>
	<script type="text/javascript">
		
	</script>
</body>
</html>