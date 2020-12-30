<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://www.baocloud.com/javaee/jstl/core"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>评论和回复</title>
<style type="text/css">
li {
	word-wrap: break-word;
}

.user_head {
	border-radius: 30px;
	-moz-border-radius: 30px;
	-webkit-border-radius: 30px;
	width:60px;
	height:60px;
}

.username {
	color: blue;
	line-height: 23px;
}

.li_info {
	margin-left: 56px;
	width: 500px;
}

.li_do {
	margin-top: 10px;
	padding-left: 56px;
}

.li_hf {
	margin-top: 10px;
	padding-left: 56px;
	display: none;
}

.li_hr {
	border-top: 1px solid #C9C9C9;
	margin-top: 10px;
}

.li_hr2 {
	border-top: 1px dotted #C9C9C9;
	margin-top: 10px;
}

ul {
	list-style: none;
	width: 800px;
	padding-left: 0px;
}

ol {
	list-style: none;
	width: 444px;
	padding-left: 0px;
}

.li_hf_list {
	padding-left: 56px;
	margin-top: 10px;
}

.comm_context {
	text-align: left;
	width: 820px;
	margin: 0 auto;
}
</style>
<script type="text/javascript"
	src="${applicationScope.basePath}js/comment/comment_reply.js"></script>
<c:choose>
	<c:when test="${not empty sessionScope.user}">
		<script type="text/javascript"
			src="${applicationScope.basePath}js/ajaxFrame.js"></script>
		<script type="text/javascript"
			src="${applicationScope.basePath}js/public.js"></script>
		<script type="text/javascript"
			src="${applicationScope.basePath}js/comment/video_comment.js"></script>
	</c:when>
	<c:otherwise>
		<script type="text/javascript">
			function submitComment() {
				alert("亲,请登录后再发表评论!");
			}
			function replyComment()
			{
				alert("亲,请登录后再发表回复!");
			}
		</script>
	</c:otherwise>
</c:choose>
</head>
<body>
	<div style="text-align: center;">
		<div class="comm_context">
			<form action="${applicationScope.basePath }comment/add_comm" id="comm_form"
				method="post">
				<textarea cols="60" rows="5" name="comcon" id="comcon" placeholder="评论不少于5个字,不超过200个字"></textarea>
				<c:if test="${not empty requestScope.video }">
					<input type="hidden" name="videoId" value="${requestScope.video.videoId}" />
				</c:if>
				<input type="button" value="发表评论" onclick="submitComment()" />
			</form>
		</div>
		<c:choose>
			<c:when test="${not  empty requestScope.commList}">
				<c:forEach items="${ requestScope.commList}" var="comm">
					<div class="comm_context">
						<ul>
							<li style="float: left;"><img class="user_head"
								src="${applicationScope.rootPath}${comm.user.pic}" /></li>
							<li class="li_info"><span class="username">${ comm.user.nickname }</span>${comm.timeDesc }
								<br /> <span>${comm.comCon }</span></li>
							<li class="li_do"><input type="image" src="../img/zan.png" />(0)
								<input type="image" src="../img/cai.png" />(0) <input
								type="image" src="../img/huifu.png" /><a href="#"
								onclick="replyContext(this.parentNode)">回复</a>
								${requestScope.repCount}</li>
							<li class="li_hf"><textarea cols="60" rows="5"></textarea> <input
								type="button" value="回复"
								onclick="replyComment(this.parentNode,${comm.comId},'${applicationScope.basePath}')" /></li>
							<li class="li_hf_list"><c:if
									test="${not empty comm.commReplies }">
									<c:forEach items="${comm.commReplies }" var="reply">
										<ol>
											<li style="float: left;"><img class="user_head"
												src="${applicationScope.rootPath}${reply.user.pic}" /></li>
											<li class="li_info"><span class="username">${reply.user.nickname }</span>
												${reply.timeDesc} <br /></li>
											<li class="li_do"><input type="image"
												src="../img/zan.png" />(0) <input type="image"
												src="../img/cai.png" />(0)</li>
											<li class="li_hr2"></li>
										</ol>
									</c:forEach>
								</c:if></li>
							<li class="li_hr"></li>
						</ul>
					</div>
				</c:forEach>
			</c:when>
			<c:otherwise>
          当前没有评论
       </c:otherwise>
		</c:choose>
	</div>
</body>
</html>