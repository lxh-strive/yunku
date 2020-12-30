package com.baocloud.yunku.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baocloud.yunku.controller.BaseServlet;
import com.baocloud.yunku.pojo.User;
import com.baocloud.yunku.service.UserService;
import com.baocloud.yunku.service.UserServiceImpl;

/**
 * 用户登录
 * 
 * @author wzr
 *
 */
@WebServlet(name = "LoginServlet", value = "/user/login")
public class LoginServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4331397541734078735L;
	private UserService userService;

	public LoginServlet() {
		super();
		userService = new UserServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setStatus(405);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		User user = userService.userLogin(email, password);
		// 1:成功;2:重复登录;-1:异常;0:邮件或密码错误
		int state;
		if (null != user) {
			Integer userId = user.getUserId();
			if (null == userId) {// 登录状态
				state = 0;
			} else {
				state = user.getLoginState().equals("0") ? 1 : 2;
			}
		} else {
			state = -1;
		}
		// state为1表示登录成功,登录成功需要将用户对象保存到HttpSession中去
		if (1 == state) {
			req.getSession().setAttribute("user", user);
			resp.sendRedirect(getBasePath(req) + "user/user_base_info.jsp?state=" + state);
		}else{
			resp.sendRedirect(getBasePath(req) + "login.jsp?state=" + state);
		}
	}

}
