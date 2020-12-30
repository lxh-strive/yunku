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
 * 修改密码
 * 
 * @author wzr
 *
 */
@WebServlet(name = "UpdatePwdServlet", value = "/user/update_pwd")
public class UpdatePwdServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService;

	public UpdatePwdServlet() {
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
		Integer userId = getUserId(request);
		int state = userService.updatePwd(userId, newPwd);
		if (1 == state) {
			// 密码修改成功,重定向到登录页面
			request.getSession().invalidate();// 杀死session
			response.sendRedirect(getBasePath(request) + "login.jsp?state=1");
		} else {
			request.setAttribute("state", state);
			request.getRequestDispatcher("/user/user_update_pwd.jsp").forward(
					request, response);
		}
	}
}
