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

@WebServlet(name = "ViewVideoServlet", value = "/video/view")
public class ViewVideoServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private VideoService videoService;

	public ViewVideoServlet() {
		super();
		videoService = new VideoServiceImpl();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Integer videoId = getInteger(request, "video_id");
		Video video = videoService.getVideo(videoId, true);
		request.setAttribute("video", video);
		request.getRequestDispatcher("/video/video_view.jsp").forward(request,
				response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
