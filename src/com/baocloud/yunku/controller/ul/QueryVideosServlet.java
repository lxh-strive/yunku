package com.baocloud.yunku.controller.ul;

import com.baocloud.yunku.controller.BaseServlet;
import com.baocloud.yunku.pojo.Video;
import com.baocloud.yunku.pojo.VideoSubType;
import com.baocloud.yunku.service.VideoService;
import com.baocloud.yunku.service.VideoServiceImpl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 检索视屏 1.分类检索 2.关键字检索
 * 
 * @author wzr
 *
 */
@WebServlet(name = "QueryVideosServlet", urlPatterns = {
		"/videos/search_parent", "/videos/search_kw", "/videos/search_sub" })
public class QueryVideosServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private VideoService videoService;

	public QueryVideosServlet() {
		super();
		videoService = new VideoServiceImpl();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String url = request.getRequestURI();
		if (url.endsWith("search_parent")) {
			searchParent(request, response);
		} else if (url.endsWith("search_sub")) {
			searchSub(request, response);
		} else {
			searchKeyword(request, response);
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * 根据视频分类类型检索
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void searchParent(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Integer parentId = getInteger(request, "parent_id");
		setRequestAttr(request, "videoParentType",
				videoService.getParentType(parentId));
		setRequestAttr(request, "videos", videoService.queryVideos(parentId));
		request.getRequestDispatcher("/ui/parent_videos.jsp").forward(request,
				response);
	}

	/**
	 * 根据视频分类类型检索
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void searchSub(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Integer subId = getInteger(request, "sub_id");
		VideoSubType videoSubType = videoService.getSubType(subId);
		List<Video> videos = videoService.queryVideos(
				videoSubType.getParentId(), subId);
		setRequestAttr(request, "videoSubType", videoSubType);
		setRequestAttr(request, "videos", videos);
		request.getRequestDispatcher("/ui/sub_videos.jsp").forward(request,
				response);
	}

	/**
	 * 根据关键字类型检索
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void searchKeyword(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String keyword = getString(request, "keyword");
		setRequestAttr(request, "keyword", keyword);
		setRequestAttr(request, "videos", videoService.queryVideos(keyword));
		request.getRequestDispatcher("/ui/kd_videos.jsp").forward(request,
				response);
	}
}
