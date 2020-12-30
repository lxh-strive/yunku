package com.baocloud.yunku.controller.video;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.baocloud.yunku.controller.BaseServlet;
import com.baocloud.yunku.service.VideoService;
import com.baocloud.yunku.service.VideoServiceImpl;
 
@WebServlet(name = "AddSuggesServlet", value = "/video/add_sugges")
public class AddSuggesServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5797139592290287206L;
	private VideoService videoService;

	public AddSuggesServlet() {
		super();
		videoService = new VideoServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Integer userId = getUserId(req);
		int state;
		if (null == userId) {
			state = -2;// 未登录
		} else {
			Integer videoId = getInteger(req, "videoId");
			Integer type = getInteger(req, "type");
			state = videoService.addVideoSugges(userId, videoId, type);
		}
		responeState(req, resp, state);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
