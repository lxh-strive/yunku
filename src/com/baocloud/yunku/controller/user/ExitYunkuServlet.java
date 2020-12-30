package com.baocloud.yunku.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baocloud.yunku.controller.BaseServlet;

/**
 * 退出系统
 * 
 * @author wzr
 *
 */
@WebServlet(name = "ExitYunkuServlet", value = "/user/exit")
public class ExitYunkuServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public ExitYunkuServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate();// 手动杀死Session
		response.sendRedirect(getBasePath(request) + "login.jsp");// 重定向到登录页面
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.sendError(405);
	}

}
