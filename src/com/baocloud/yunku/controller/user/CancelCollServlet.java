package com.baocloud.yunku.controller.user;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.baocloud.yunku.service.VideoService;
import com.baocloud.yunku.service.VideoServiceImpl;

/**
 * 取消收藏
 * 
 * @author wzr
 *
 */
@WebServlet(name = "CancelCollServlet", value = "/user/canle_videocoll")
public class CancelCollServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8579005729567709983L;
	private VideoService videoService;

	public CancelCollServlet() {
		super();
		videoService = new VideoServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer collId = Integer.valueOf(req.getParameter("collId"));
		int state = videoService.deleteVideoColl(collId);
		resp.setContentType("text/html;charset=utf-8");
		resp.setHeader("Pragma", "no-cache");
		resp.addHeader("Cache-Control", "must-revalidate");
		resp.addHeader("Cache-Control", "no-cache");
		resp.addHeader("Cache-Control", "no-store");
		resp.setDateHeader("Expires", 0);
		PrintWriter out = resp.getWriter();
		out.println(state);
	}

}
