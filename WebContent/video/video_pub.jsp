<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String rootPath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ "/";
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>视频上传</title>
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
<script language="javascript" src="<%=basePath%>js/jquery-1.8.3.min.js"></script>
<script src="<%=basePath%>js/uploadFile.js" type="text/javascript"></script>
<script type="text/javascript">
     var timeoutId=-1;//计时器ID
     var times=0;//计时器执行的时间(单位毫秒)
     var maxTimes=15000;//计时器执行最大时间
    /**
     *上传视频图片成功后调用
     */
	function uploadImgSuccess(imgPath) {
		$("#videoImg").attr("src", "<%=rootPath%>" + imgPath);
		$("#pic").val(imgPath);
	}
	/*
	 *上传视屏图片
	 */
	function uploadVideoImg() {
		var basePath="<%=basePath%>";
		var uploadFile = new UploadFile("upload_video_img", basePath, 4, 1,
				uploadImgSuccess);
		uploadFile.fileUpload();
	}
	/**
	 *上传视频
	 */
	function uploadVideoSrc() {
		var basePath="<%=basePath%>";
		var uploadFile = new UploadFile("upload_video_src", basePath, 2048, 2,
				uploadVideoSuccess);
		uploadFile.fileUpload();
	}
	/**
	 *上传视频成功后调用
	 */
	function uploadVideoSuccess(videoPath) {
		$("#src").val(videoPath);
		var yunku_video = document.getElementById("yunku_video");
		yunku_video.src = "<%=rootPath%>" + videoPath;
		yunku_video.load();
		yunku_video.play();
		//获取视频的长度
		timeoutId = window.setTimeout("getViedoLenth()", 200);
	}

	/**
	 * 获取视频长度值,单位为秒
	 */
	function getViedoLenth() {
		times += 200;
		if (times > maxTimes) {
			clearInterval(times);
			timeoutId = -1;
			times = 0;
			return;
		}
		var video = document.getElementById("yunku_video");
		if (video.readyState > 0) {
			//获取视频长度
			$("#size").val(Math.floor(Number(video.duration)));
			clearInterval(times);
			timeoutId = -1;
			times = 0;
		} else {
			timeoutId = window.setTimeout("getViedoLenth()", 200);
		}
	}
	/**
	 * 视频加载失败
	 */
	function videoLoadFail() {
		alert("视频url地址错误或视频格式错误");
		if (-1 != timeoutId) {
			clearInterval(timeoutId);
			timeoutId = -1;
			times = 0;
		}
	}
</script>
</head>
<body>
	<div id="pub_video_context">
		<table width="850" border="1"   cellpadding="5" cellspacing="0">
			<tr>
				<td width="120">视频名称</td>
				<td><input type="text" name="name" /></td>
			</tr>
			<tr>
				<td>关键字/标签</td>
				<td><input type="text" name="mark" placeholder="关键字以分号隔开" /></td>
			</tr>
			<tr>
				<td>视频图片</td>
				<td align="left">
					<div id="upload_video_img">
						<iframe id="target_upload_img" name="target_upload_img" src=''
							style="display: none"></iframe>
						<form action="<%=basePath%>upload/video_img"
							enctype="multipart/form-data" method="post"
							target="target_upload_img">
							<input type="file" name="videoimg" onchange="uploadVideoImg()" />
							<img src="<%=basePath%>img/video_img.gif" id="videoImg"
								width="200" height="150" />
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
				</td>
			</tr>
			<tr>
				<td>视频分类</td>
				<td>一级分类 <select id="parentType" name="parentId"
					style="width: 120px; height: 30px" onchange="loadVideoSubType(this.value)"></select> &nbsp;&nbsp; 二级分类 <select
					id="subType" name="subId" style="width: 120px; height: 30px"></select>
				</td>
			</tr>
			<tr>
				<td>视频地址</td>
				<td>
					<div id="upload_video_src">
						<iframe id="target_upload_src" name="target_upload_src" src=''
							style="display: none"></iframe>
						<form action="<%=basePath%>upload/video_src"
							enctype="multipart/form-data" method="post"
							target="target_upload_src">
							<input type="file" name="videosrc" onchange="uploadVideoSrc()" />
							<video id="yunku_video" poster="<%=basePath%>img/start_img.jpg"
								width="400" height="300" onerror="videoLoadFail()"></video>
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

				</td>
			</tr>
			<tr>
				<td>视频长度</td>
				<td><input type="text" name="size" id="size" value="0"
					readonly="readonly" style="width: 60px" /> <span>(单位:秒)</span></td>
			</tr>
			<tr>
				<td>下载积分</td>
				<td><input type="number" name="downPoints" min="1" max="500"
					value="0" /></td>
			</tr>
			<tr>
				<td>视频描述</td>
				<td><textarea name="videoDesc" rows="10" cols="80"></textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="button"
					id="pub_video_but" value="发布视屏" /> <input type="hidden" id="pic"
					name="pic" /> <input type="hidden" id="src" name="src" /></td>
			</tr>
		</table>
	</div>
	<form id="pub_viedo_form" action="<%=basePath%>video/pub_video"
		method="post"></form>
	<script type="text/javascript">
		$(function() {
			$("#pub_video_but").click(function() {
				if (window.confirm("确定要发布视频吗?")) {
					$("#upload_video_src").remove();
					$("#upload_video_img").remove();
					$("#pub_video_context").appendTo($("#pub_viedo_form"));
					$("#pub_viedo_form").submit();
				}
			});
			loadVideoParentType();
		});
	</script>
	    <script  type="text/javascript" src="<%=basePath %>js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript">
    //加载视频父分类数据
    function loadVideoParentType()
    {
      $.ajax({
       type:"GET",
       url:"<%=basePath%>video/get_type_data",
       cache: false,
       dataType:"xml",
       success:function(data, textStatus, jqXHR)
       {
	       $(data).find("video-type").each(function(i){
	       var id=$(this).find("id").text();
	       var name=$(this).find("name").text();
	       var optionHtml="<option value=\"";
	       optionHtml+=id;
	       optionHtml+="\">"
	       optionHtml+=name;
	       optionHtml+="</option>";
	       $("#parentType").append(optionHtml); 
	       });
       },
       error:function(XMLHttpRequest, textStatus, errorThrown)
       {
        alert(textStatus);
       }
      });
    }
      //加载视频子分类数据
     function loadVideoSubType(parentId)
     {
      $.ajax({
	       type:"post",
	       url:"<%=basePath%>video/get_type_data", 
	       cache: false,
	       dataType:"xml",
	       data:"parentId="+parentId,
	       success:function(data, textStatus, jqXHR){
	       $("#subType").empty();
	       $(data).find("video-type").each(function(i){
	       var id=$(this).find("id").text();
	       var name=$(this).find("name").text();
	       var optionHtml="<option value=\"";
	       optionHtml+=id;
	       optionHtml+="\">"
	       optionHtml+=name;
	       optionHtml+="</option>";
	       $("#subType").append(optionHtml); 
	       });
	       },
	       error:function(XMLHttpRequest, textStatus, errorThrown)
	       {
	        alert(textStatus);
	       }
      });
     }
    </script>
	  
</body>
</html>