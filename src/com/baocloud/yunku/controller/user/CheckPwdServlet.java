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
import com.baocloud.yunku.utils.StringUtils;

/**
 * 验证用户密码 1:密码正确 0:密码不正确 -1:异常 -2:未登录 110:缺少参数
 * 
 * @author wzr
 *
 */
@WebServlet(name = "CheckPwdServlet", value = "/user/user_check_pwd")
public class CheckPwdServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService;

	public CheckPwdServlet() {
		super();
		userService = new UserServiceImpl();
	}
 
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.sendError(405);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int state;
		Integer userId = getUserId(request);
		if (null == userId) {
			state = -2;// 未登录
		} else {
			String password = request.getParameter("password");
			state = !StringUtils.isEmptyStr(password) ? userService.checkPwd(
					userId, password) : 110;
		}
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(state);
	}
}
