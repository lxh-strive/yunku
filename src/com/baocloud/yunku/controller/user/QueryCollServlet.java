package com.baocloud.yunku.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baocloud.yunku.controller.BaseServlet;
import com.baocloud.yunku.service.VideoService;
import com.baocloud.yunku.service.VideoServiceImpl;

@WebServlet(name = "QueryCollServlet", value = "/user/query_videocoll")
public class QueryCollServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5983765230888582689L;
	private VideoService videoService;

	public QueryCollServlet() {
		super();
		videoService = new VideoServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer userId = getUserId(req);
		req.setAttribute("collList", videoService.queryVideoColl(userId));
		req.getRequestDispatcher("/user/user_coll.jsp").forward(req, resp);
	}

}
