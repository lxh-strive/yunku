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
 * 获取当日签到状态
 * 
 * @author wzr
 *
 */
@WebServlet(name = "GetSignStateServlet", value = "/user/get_sign_state")
public class GetSignStateServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService;

	public GetSignStateServlet() {
		super();
		userService = new UserServiceImpl();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Integer userId = getUserId(request);
		int state = userService.getSignState(userId);
		request.setAttribute("state", state);
		request.getRequestDispatcher("/user/user_sign.jsp").forward(request,
				response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
