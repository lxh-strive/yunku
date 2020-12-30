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
 * 回复或评论添加点赞、踩、投诉记录
 * 
 * @author wzr
 *
 */
@WebServlet(name = "AddCommentSuggesServlet", value = "/comment/add_sugges")
public class AddSuggesServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5797139592290287206L;
	private CommentService commentService;

	public AddSuggesServlet() {
		super();
		commentService = new CommentServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Integer userId = getUserId(req);
		int state;
		if (null == userId) {
			state = -2;// 未登录
		} else {
			int contentType = getInteger(req, "content_type");// 1:评论:2:回复
			Integer type = getInteger(req, "type");
			if (1 == contentType) {
				Integer comId = getInteger(req, "comId");
				state = commentService.addCommSugges(userId, comId, type);
			} else if (2 == contentType) {
				Integer repId = getInteger(req, "repId");
				state = commentService.addReplySugges(userId, repId, type);
			} else {
				state = -3;// 客户端参数错误
			}
		}
		responeState(req, resp, state);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
