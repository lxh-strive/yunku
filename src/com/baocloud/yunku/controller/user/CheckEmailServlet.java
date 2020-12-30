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
 * 验证电子邮件
 * 
 * @author wzr
 *
 */
@WebServlet(name = "CheckEmailServlet", value = "/user/check_email")
public class CheckEmailServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3789242849751148553L;

	private UserService userService;

	public CheckEmailServlet() {
		super();
		userService = new UserServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		int state = userService.checkEmail(email);
		resp.setContentType("text/html,charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(state);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
