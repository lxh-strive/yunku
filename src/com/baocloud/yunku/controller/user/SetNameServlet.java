package com.baocloud.yunku.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baocloud.yunku.controller.BaseServlet;
import com.baocloud.yunku.service.UserService;
import com.baocloud.yunku.service.UserServiceImpl;

@WebServlet(name = "SetNameServlet", value = "/user/user_set_name")
public class SetNameServlet extends BaseServlet {
	private static final long serialVersionUID = -4331397541734078735L;
	private UserService userService;
	int state;

	public SetNameServlet() {
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
		Integer userId = getUserId(req);
		String userName = req.getParameter("username");
		int state = userService.setName(userName, userId);
		req.setAttribute("state", state);
		if (1 == state) {
			setUserName(req, userName);
		}
		req.getRequestDispatcher("/user/user_set_name.jsp").forward(req, resp);
	}
}
