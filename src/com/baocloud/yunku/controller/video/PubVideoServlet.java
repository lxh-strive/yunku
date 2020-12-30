package com.baocloud.yunku.controller.video;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baocloud.yunku.controller.BaseServlet;
import com.baocloud.yunku.pojo.Video;
import com.baocloud.yunku.service.VideoService;
import com.baocloud.yunku.service.VideoServiceImpl;

@WebServlet(name = "AddVideoServlet", value = "/video/pub_video")
public class PubVideoServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5797139592290287206L;
	private VideoService videoService;

	public PubVideoServlet() {
		super();
		videoService = new VideoServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		Video video = new Video();
		video.setDownPoints(getInteger(req, "downPoints"));
		video.setMark(getString(req, "mark"));
		video.setName(getString(req, "name"));
		video.setParentId(getInteger(req, "parentId"));
		video.setPic(getString(req, "pic"));
		video.setSize(getInteger(req, "size"));
		video.setSubId(getInteger(req, "subId"));
		video.setUserId(getUserId(req));
		video.setVideoDesc(getString(req, "videoDesc"));
		video.setSrc(getString(req, "src"));
		int state = videoService.addVideo(video);
		// 重定向到结果页面
		resp.sendRedirect(getBasePath(req) + "video/video_pub_result.jsp?state="
				+ state);

	}

}
