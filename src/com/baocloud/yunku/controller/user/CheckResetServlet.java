package com.baocloud.yunku.controller.user;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.baocloud.yunku.service.UserService;
import com.baocloud.yunku.service.UserServiceImpl;

/**
 * 重置用户密码
 * 
 * @author wzr
 *
 */
@WebServlet(name = "CheckResetServlet", value = "/user/pwd_reset_check")
public class CheckResetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService;

	public CheckResetServlet() {
		super();
		userService = new UserServiceImpl();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("user_id", request.getParameter("user_id"));
		paramMap.put("nonce_str", request.getParameter("nonce_str"));
		paramMap.put("expire", request.getParameter("expire"));
		paramMap.put("sign", request.getParameter("sign"));
		int state = userService.getResetPwdSate(paramMap);
		request.setAttribute("state", state);
		if (1 == state) {
			request.setAttribute("userid", request.getParameter("user_id"));
		}
		request.getRequestDispatcher("/reset_pwd.jsp")
				.forward(request, response);

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 禁止get提交
		response.setStatus(405);
	}

}
