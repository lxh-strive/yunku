package com.baocloud.yunku.controller.comment;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.baocloud.yunku.controller.BaseServlet;
import com.baocloud.yunku.service.CommentService;
import com.baocloud.yunku.service.CommentServiceImpl;

/**
 * 添加视屏评论
 * 
 * @author wzr
 *
 */
@WebServlet(name = "AddVideoCommServlet", value = "/comment/add_comm")
public class AddVideoCommServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CommentService commentService;

	public AddVideoCommServlet() {
		super();
		commentService = new CommentServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		Integer userId = getUserId(req);
		Integer videoId = getInteger(req, "videoId");
		String comcon = req.getParameter("comcon");
		int state = commentService.addVideoComm(comcon, userId, videoId);
		StringBuffer urlBuf = new StringBuffer();
		urlBuf.append(getBasePath(req));
		urlBuf.append("comment/query_comm");
		urlBuf.append('?');
		urlBuf.append("state=" + state);
		urlBuf.append('&');
		urlBuf.append("videoId=" + videoId);
		resp.sendRedirect(urlBuf.toString());
	}
}
