package com.baocloud.yunku.controller.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.baocloud.yunku.service.UserService;
import com.baocloud.yunku.service.UserServiceImpl;

/**
 * 重置密码
 * 
 * @author wzr
 *
 */
@WebServlet(name = "ResetPwdServlet", value = "/user/reset_pwd")
public class ResetPwdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService;

	public ResetPwdServlet() {
		super();
		userService = new UserServiceImpl();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setStatus(405);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String newPwd = request.getParameter("newpwd");
		Integer userId = Integer.valueOf(request.getParameter("userid"));
		int state = userService.resetPwd(userId, newPwd);
		request.setAttribute("state", state);
		request.getRequestDispatcher("/reset_pwd_result.jsp").forward(request,
				response);
	}
}
