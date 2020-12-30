package com.baocloud.yunku.controller.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 检查验证码是否正确
 * 
 * @author wzr
 *
 */
@WebServlet(name = "CheckCodesServlet", value = "/user/check_codes")
public class CheckCodesServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CheckCodesServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pCodes = req.getParameter("codes");
		// 获取HttpSession中的验证码
		HttpSession session = req.getSession();
		String codes = (String) session.getAttribute("codes");
		// 返回状态:1表示验证码正确;0表示验证码错误;-1表示回话过期;-2表示客户端请求未传验证码
		int state;
		if (null == codes) {
			state = -1;
		} else {
			if (null == pCodes) {
				state = -2;
			} else {
				state = codes.equalsIgnoreCase(pCodes) ? 1 : -1;
			}
		}
		resp.setContentType("text/html,charset=UTF-8");
		PrintWriter out = resp.getWriter();
		out.print(state);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
