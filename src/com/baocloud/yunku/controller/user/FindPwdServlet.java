package com.baocloud.yunku.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baocloud.yunku.controller.BaseServlet;
import com.baocloud.yunku.service.UserService;
import com.baocloud.yunku.service.UserServiceImpl;

@WebServlet(name = "FindPwdServlet", value = "/user/find_pwd")
public class FindPwdServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public FindPwdServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		UserService userService = new UserServiceImpl();
		int state = userService.sendRestPwdURL(email, getResetPwdUrl(request));
		response.sendRedirect(getBasePath(request)
				+ "find_pwd_result.jsp?state=" + state + "&email=" + email);

	}

	/**
	 * 获取重置密码的url
	 */
	private String getResetPwdUrl(HttpServletRequest request) {
		String url = getBasePath(request) + "user/pwd_reset_check";
		return url;
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
