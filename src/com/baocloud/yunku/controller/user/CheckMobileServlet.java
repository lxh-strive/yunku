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
 * 验证手机
 * 
 * @author wzr
 *
 */
@WebServlet(name = "CheckMobileServlet", value = "/user/check_mobile")
public class CheckMobileServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3789242849751148553L;

	private UserService userService;

	public CheckMobileServlet() {
		super();
		userService = new UserServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String mobile = req.getParameter("mobile");
		Integer userId = getUserId(req);
		int state = userService.checkMobile(mobile, userId);
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(state);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
