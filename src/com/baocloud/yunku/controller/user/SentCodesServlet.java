package com.baocloud.yunku.controller.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.baocloud.yunku.service.UserService;
import com.baocloud.yunku.service.UserServiceImpl;

/**
 * 发送邮件验证码
 * 
 * @author wzr
 *
 */
@WebServlet(name = "SentCodesServlet", value = "/user/sent_email_codes")
public class SentCodesServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 440459097453809995L;
	private UserService userService;

	public SentCodesServlet() {
		super();
		userService = new UserServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String codes = userService.sentEmailCodes(email);
		//将验证码放入HttpSession中去
		HttpSession session=req.getSession();
		session.setAttribute("codes", codes);
		int state = null != codes ? 1 : -1;
		resp.setContentType("text/html,charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(state);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
