package com.baocloud.yunku.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baocloud.yunku.controller.BaseServlet;
import com.baocloud.yunku.dao.UserDao;
import com.baocloud.yunku.dao.UserDaoImpl;

/**
 * 查询用户积分
 * @author wzr
 *
 */
@WebServlet(name = "InteServlet", value = "/user/query_inte")
public class InteServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7588922357188284120L;

	public InteServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Integer userId = getUserId(req);
		UserDao userDao = new UserDaoImpl();
		req.setAttribute("inteList", userDao.queryInte(userId));
		req.getRequestDispatcher("/user/user_inte.jsp").forward(req, resp);
	}

}
