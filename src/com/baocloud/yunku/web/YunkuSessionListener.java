package com.baocloud.yunku.web;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import com.baocloud.yunku.pojo.User;
import com.baocloud.yunku.service.UserService;
import com.baocloud.yunku.service.UserServiceImpl;

@WebListener
public class YunkuSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent e) {

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent e) {
		// 当回话是失效时,修改登录状态为0
		HttpSession session = e.getSession();
		User user = (User) session.getAttribute("user");
		if (null != user) {
			UserService userService = new UserServiceImpl();
			userService.updateUser(user.getUserId(), 0);
			System.out.println("修改回话ID为:" + session.getId() + "登录状态为0");
		}
	}

}
