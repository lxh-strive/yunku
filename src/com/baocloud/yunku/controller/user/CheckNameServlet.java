package com.baocloud.yunku.controller.user;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.baocloud.yunku.service.UserService;
import com.baocloud.yunku.service.UserServiceImpl;

/**
 * 检验用户名
 * 
 * @author wzr
 *
 */
@WebServlet(name = "CheckNameServlet", value = "/user/check_user_name")
public class CheckNameServlet extends HttpServlet {
	private static final long serialVersionUID = -4331397541734078735L;
	private UserService userService;

	public CheckNameServlet() {
		super();
		userService = new UserServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String userName = req.getParameter("username");
		int state = userService.isNameExist(userName);
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		out.print(state);
	}

}
