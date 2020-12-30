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
 * 用户注册Controller
 * 
 * @author wzr
 *
 */
@WebServlet(name = "RegServlet", value = "/user/reg")
public class RegServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
		// resp.setStatus(405);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		System.out.println(email);
		String password = req.getParameter("password");
		UserService userService=new UserServiceImpl();
		userService.userReg(email, password);
		//返回页面提示注册成功或失败
	}

}
