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
 * 添加评论回复
 * 
 * @author wzr
 *
 */
@WebServlet(name = "AddCommReplyServlet", value = "/comment/add_reply")
public class AddCommReplyServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private CommentService commentService;

	public AddCommReplyServlet() {
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
		Integer userId = getUserId(req);
		Integer comId = getInteger(req, "comId");
		String repcon = req.getParameter("repcon");
		int state = commentService.addCommReply(repcon, userId, comId);
		responeState(req, resp, state);
	}
}
