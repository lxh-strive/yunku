package com.baocloud.yunku.controller.upload;

import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.baocloud.yunku.utils.StringUtils;

@WebServlet(name = "ProgressServlet", value = "/upload/get_progress")
public class ProgressServlet extends HttpServlet {
	private static final long serialVersionUID = 2689369523440077855L;
	public final static String PK = "progress_key";

	public ProgressServlet() {
		super();
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		StringBuffer jsonBuf = new StringBuffer();
		HttpSession session = request.getSession();
		FileUploadStatus status = (FileUploadStatus) session.getAttribute(PK);
		try {
			out = response.getWriter();
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			if (null != status) {
				long pBytesRead = status.getPBytesRead();
				long pContentLength = status.getPContentLength();
				long state = status.getState();
				jsonBuf.append('{');
				jsonBuf.append("\"pBytesRead\":");
				jsonBuf.append(pBytesRead);
				jsonBuf.append(',');
				jsonBuf.append("\"pContentLength\":");
				jsonBuf.append(pContentLength);
				jsonBuf.append(',');
				jsonBuf.append("\"state\":");
				jsonBuf.append(state);
				jsonBuf.append(',');
				jsonBuf.append("\"path\":");
				jsonBuf.append('\"');
				String path = status.getPath();
				jsonBuf.append(path);
				jsonBuf.append('\"');
				jsonBuf.append('}');
				if (((pBytesRead == pContentLength) && !StringUtils
						.isEmptyStr(path)) || 5000 == state) {
					session.removeAttribute(PK);
					System.out.println("成功上传文件:" + getWebappsPath(request)+ path.replaceAll("[/]", "\\\\"));
				}
			} else {
				jsonBuf.append('{');
				jsonBuf.append("\"pBytesRead\":0");
				jsonBuf.append(',');
				jsonBuf.append("\"pContentLength\":0");
				jsonBuf.append(',');
				jsonBuf.append("\"state\":4000");
				jsonBuf.append(',');
				jsonBuf.append("\"path\":\"\"");
				jsonBuf.append('}');
				Thread.sleep(5);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonBuf.delete(0, jsonBuf.length());
			jsonBuf.append('{');
			jsonBuf.append("\"pBytesRead\":0");
			jsonBuf.append(',');
			jsonBuf.append("\"pContentLength\":0");
			jsonBuf.append(',');
			jsonBuf.append("\"state\":5005");
			jsonBuf.append(',');
			jsonBuf.append("\"path\":\"\"");
			jsonBuf.append('}');
		}
		if (null != out) {
			out.write(jsonBuf.toString());
			out.flush();
		}
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) {
		doPost(request, response);

	}

	/**
	 * 获取tomcat发布根目录
	 * 
	 * @param request
	 * @return
	 */
	private String getWebappsPath(HttpServletRequest request) {
		String webRootPath = request.getSession().getServletContext()
				.getRealPath("\\");
		String path = request.getContextPath();
		path = path.replace("/", "");
		String webappsPath = webRootPath.replace(path, "");
		webappsPath = webappsPath.replaceAll("\\\\\\\\", "\\\\");
		return webappsPath;
	}
}
