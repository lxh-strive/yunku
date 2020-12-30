package com.baocloud.yunku.controller.video;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baocloud.yunku.controller.BaseServlet;
import com.baocloud.yunku.service.VideoService;
import com.baocloud.yunku.service.VideoServiceImpl;

/**
 * 获取视频分类xml格式数据
 * 
 * @author luliujun
 *
 */
@WebServlet(name = "GetViedoTypeDataServlet", value = "/video/get_type_data")
public class GetVideoTypeDataServlet extends BaseServlet {

	/**  
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private VideoService videoService;

	public GetVideoTypeDataServlet() {
		super();
		videoService = new VideoServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Integer parentId = getInteger(req, "parentId");
		String xmlData;
		if (null == parentId) {
			xmlData = videoService.getVideoTypeXMLData();
		} else {
			xmlData = videoService.getVideoTypeXMLData(parentId);
		}
		resp.setContentType("text/xml;charset=utf-8");
		PrintWriter out = resp.getWriter();
		resp.setHeader("Pragma", "no-cache");
		resp.addHeader("Cache-Control", "must-revalidate");
		resp.addHeader("Cache-Control", "no-cache");
		resp.addHeader("Cache-Control", "no-store");
		resp.setDateHeader("Expires", 0);
		out.print(xmlData);
		out.flush();
	}  

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
