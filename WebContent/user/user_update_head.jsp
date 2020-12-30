<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://www.baocloud.com/javaee/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户中心-修改头像</title>
<link type="text/css" href="${applicationScope.basePath }css/yunku.css"
	rel="stylesheet" />
<link type="text/css" href="${applicationScope.basePath }css/user.css"
	rel="stylesheet" />
<style type="text/css">
.pro_bg {
	height: 30px;
	width: 300px;
	border: 2px solid #0000FF;
	padding-left: 0px;
	border-radius: 15px;
	-moz-border-radius: 15px;
	-webkit-border-radius: 15px;
	float: left;
}

.pro_bar {
	height: 30px;
	width: 0px;
	background-color: #8888FF;
	border-radius: 13px;
	-moz-border-radius: 13px;
	-webkit-border-radius: 13px;
}

.pro_info {
	height: 30px;
	line-height: 30px;
	margin-left: 10px;
	color: #868686;
	font-size: 15px;
}

.progress {
	position: relative;
	display: none;
}
</style>
<script language="javascript"
	src="${applicationScope.basePath }js/jquery-1.8.3.min.js"></script>
<script src="${applicationScope.basePath }js/uploadFile.js"
	type="text/javascript"></script>
<script type="text/javascript">
	/**
	 *上传视频图片成功后调用
	 */
	function uploadImgSuccess(imgPath) {
		$("#headImg").attr("src", "${applicationScope.rootPath}" + imgPath);
		$("#pic").val(imgPath);
	}
	/*
	 *上传视屏图片
	 */
	function uploadUserHead() {
		var basePath = "${applicationScope.basePath }";
		var uploadFile = new UploadFile("upload_head_img", basePath, 1, 1,
				uploadImgSuccess);
		uploadFile.fileUpload();
	}
</script>
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
					<li class="tag_select" data-url="${applicationScope.basePath }user/user_videos.jsp">修改头像</li>
					<li class="tag"
						data-url="${applicationScope.basePath }user/user_set_mobile.jsp">绑定手机</li>
					<li class="tag" data-url="#">我的视频</li>
					<li class="tag" data-url="#">视频收藏</li>
					<li class="tag" data-url="#">观看记录</li>
					<li class="tag" data-url="#">我的评论</li>
				</ul>
			</div>
		</div>
		<div class="middle">
			<div id="upload_head_img">
				<iframe id="target_upload_head" name="target_upload_head" src=''
					style="display: none"></iframe>
				<form action="${applicationScope.basePath }upload/user_head"
					enctype="multipart/form-data" method="post"
					target="target_upload_head">
					<input type="file" id="userhead" name="userhead"
						onchange="uploadUserHead()" />
					<c:choose>
						<c:when test="${empty sessionScope.user.pic }">
							<img src="${applicationScope.basePath }img/head_bg.jpg"
								id="headImg" />
						</c:when>
						<c:otherwise>
							<img src="${applicationScope.rootPath }${sessionScope.user.pic}"
								id="headImg" />
						</c:otherwise>
					</c:choose>
				</form>
				<div class="progress">
					<div class="pro_bg">
						<div class="pro_bar"></div>
					</div>
					<div>
						<span class="pro_info"></span>
					</div>
				</div>
			</div>
			<div style="margin-top: 60px">
				<form>
					<input type="hidden" id="pic" name="pic" /> <input type="button"
						value="设置头像" onClick="updatePic()" />
				</form>
			</div>
			<script type="text/javascript">
				function updatePic() {
					var pic = $("#pic").val();
					if ("" == pic) {
						alert("你未设置或更新头像！");
						return;
					}
					$.ajax({
						type : "post",
						url : "${applicationScope.basePath }user/update_head",
						cache : false,
						dataType : "text",
						data : "pic=" + pic,
						success : function(data, textStatus, jqXHR) {
							if (1 == data) {
								alert("头像修改成功!");
								$("#pic").val("");
								$("#userhead").val();
							} else {
								alert("修改头像失败,返回状态码[" + data + "]");
							}

						},
						error : function(XMLHttpRequest, textStatus,
								errorThrown) {
							alert("修改头像失败,返回状态码[" + textStatus + "]");
						}
					});
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