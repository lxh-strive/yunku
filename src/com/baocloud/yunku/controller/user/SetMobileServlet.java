package com.baocloud.yunku.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baocloud.yunku.controller.BaseServlet;
import com.baocloud.yunku.service.UserService;
import com.baocloud.yunku.service.UserServiceImpl;

/**
 * 修改手机号码
 * 
 * @author wzr
 *
 */
@WebServlet(name = "SetMobileServlet", value = "/user/set_mobile")
public class SetMobileServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4893155081421614772L;
	private UserService userService;

	public SetMobileServlet() {
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
		String mobile = req.getParameter("mobile");
		Integer userId = getUserId(req);
		int state = userService.setMobile(mobile, userId);
		if (1 == state) {
			setMobile(req, mobile);
		}
		req.getRequestDispatcher("/user/user_set_mobile.jsp").forward(req, resp);
	}

}
