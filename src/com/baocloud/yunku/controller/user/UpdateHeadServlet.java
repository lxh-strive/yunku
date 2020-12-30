package com.baocloud.yunku.controller.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baocloud.yunku.controller.BaseServlet;
import com.baocloud.yunku.service.UserService;
import com.baocloud.yunku.service.UserServiceImpl;

/**
 * 修改用户头像
 * 
 * @author wzr
 *
 */
@WebServlet(name = "UpdateHeadServlet", value = "/user/update_head")
public class UpdateHeadServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	private UserService userService;

	public UpdateHeadServlet() {
		super();
		userService = new UserServiceImpl();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String pic = request.getParameter("pic");
		Integer userId = getUserId(request);
		int state = userService.updatePic(userId, pic);
		if (1 == state) {
			setPic(request, pic);
		}
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out = response.getWriter();
		response.setHeader("Pragma", "no-cache");
		response.addHeader("Cache-Control", "must-revalidate");
		response.addHeader("Cache-Control", "no-cache");
		response.addHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);
		out.print(state);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
