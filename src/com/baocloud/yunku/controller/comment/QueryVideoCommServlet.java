package com.baocloud.yunku.controller.comment;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.baocloud.yunku.controller.BaseServlet;
import com.baocloud.yunku.service.CommentService;
import com.baocloud.yunku.service.CommentServiceImpl;
import com.baocloud.yunku.service.VideoService;
import com.baocloud.yunku.service.VideoServiceImpl;

@WebServlet(name = "QueryVideoCommServlet", value = "/comment/query_comm")
public class QueryVideoCommServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	private CommentService commentService;
	private VideoService videoService;

	public QueryVideoCommServlet() {
		super();
	}

	@Override
	public void init() throws ServletException {
		super.init();
		commentService = new CommentServiceImpl();
		videoService = new VideoServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer videoId = Integer.valueOf(req.getParameter("videoId"));
		setRequestAttr(req, "video", videoService.getVideo(videoId));
		setRequestAttr(req, "commList", commentService.queryVideoComms(videoId));
		req.getRequestDispatcher("/video/video_comment.jsp").forward(req, resp);
	}

}
