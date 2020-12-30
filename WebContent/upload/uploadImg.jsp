<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<title>上传图书图片</title>
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
.progress
{
position: relative; 
display: none;
}
</style>
<script language="javascript" src="<%=basePath%>js/jquery-1.8.3.min.js"></script>
<script src="<%=basePath%>js/uploadFile.js" type="text/javascript"></script>
<script type="text/javascript">
	function uploadSuccess(uploadImgPath) {
		$("#bookImg").attr("src", "<%=basePath%>" + uploadImgPath);
		//$("#imgPath").val(uploadImgPath);
	}
	function uploadImg() {
		var uploadFile=new UploadFile("upload_pic","<%=basePath%>",2,1,uploadSuccess);
		uploadFile.fileUpload();
	}
</script>
</head>
<body>

	<div id="upload_pic">
		<iframe id="target_upload" name="target_upload" src=''
			style="display: none"></iframe>
		<form action="<%=basePath%>upload/video_img" id="uploadForm"
			enctype="multipart/form-data" method="post" target="target_upload">
			<input type="file"  name="upload" onchange="uploadImg()" />
			<img src="<%=basePath%>img/uploadBg.gif" id="bookImg" width="400"
				height="400" />
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
</body>
</html>
