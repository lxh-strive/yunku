package com.baocloud.yunku.controller.ul;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.baocloud.yunku.controller.BaseServlet;
import com.baocloud.yunku.service.VideoService;
import com.baocloud.yunku.service.VideoServiceImpl;

/**
 * 加载所有视频分类数据,统计每个分类视频总数量
 * 
 * @author wzr
 *
 */
@WebServlet(name = "LoadVideoDataServlet", value = "/ui/index")
public class LoadVideoDataServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3015922268876800313L;
	private VideoService videoService;

	public LoadVideoDataServlet() {
		super();
		videoService = new VideoServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		setRequestAttr(req, "videoTypes", videoService.queryParentType(true));
		req.getRequestDispatcher("/ui/main.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
