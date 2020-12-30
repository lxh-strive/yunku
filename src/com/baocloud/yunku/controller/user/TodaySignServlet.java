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
 * 每日签到
 * 
 * @author wzr
 *
 */
@WebServlet(name = "TodaySignServlet", value = "/user/day_sign")
public class TodaySignServlet extends BaseServlet {

	private static final long serialVersionUID = -4001577854375692064L;
	private UserService userService;

	public TodaySignServlet() {
		super();
		userService = new UserServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Integer userId = getUserId(req);
		int state = userService.addSign(userId);
		if (1 == state) {
			// 签到成功
			req.getRequestDispatcher("/user/get_sign_state").forward(req, resp);
		} else {
			// 签到失败,跳转到签到失败页面
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
