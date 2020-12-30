package com.baocloud.yunku.controller.upload;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import com.baocloud.yunku.utils.FileUtils;

import com.baocloud.yunku.utils.StringUtils;

@WebServlet(name = "UploadServlet", urlPatterns = { "/upload/video_img",
		"/upload/user_head", "/upload/video_src" })
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 9186051128246056293L;
	public static final String RAW_IMG_DIR = "raw";// 原始图片目录名称
	public static final String V_MIN_DIR = "200150";// 视屏小图片目录名称
	public static final String V_MAX_DIR = "600450";// 视频大图片目录名称
	public static final int V_MIN_WIDTH = 200;// 视频小图片宽
	public static final int V_MIN_HIGHT = 150;// 视频小图片高
	public static final int V_MAX_WIDTH = 600;// 视频大图片宽
	public static final int V_MAX_HIGHT = 450;// 视频大图片高
	// 用户头像
	public static final String U_MIN_DIR = "4040";// 用户头像小图片目录名称
	public static final String U_MAX_DIR = "100100";// 用户头像大图片目录名称
	public static final int U_MIN_WIDTH = 40;// 用户头像小图片宽
	public static final int U_MIN_HIGHT = 40;// 用户头像小图片高
	public static final int U_MAX_WIDTH = 100;// 用户头像大图片宽
	public static final int U_MAX_HIGHT = 100;// 用户头像大图片高

	public UploadServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI().toLowerCase();
		if (uri.endsWith("video_img")) {
			uploadVideoImg(request, response);
		} else if (uri.endsWith("video_src")) {
			uploadVideoSrc(request, response);
		} else {
			uploadUserHeadImg(request, response);
		}
	}

	private void uploadVideoImg(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		try {
			String bundlePath = "com.baocloud.yunku.controller.upload.config.uploadconfig";
			ResourceBundle rb = ResourceBundle.getBundle(bundlePath);
			String tempDirPath = rb.getString("upload.temp.dir");
			File tempDir = FileUtils.createFolder(tempDirPath);
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setRepository(tempDir);
			int size = StringUtils.converToInt(rb
					.getString("upload.threshold.size"));
			factory.setSizeThreshold(size);
			ServletFileUpload upload = new ServletFileUpload(factory);
			int maxSize = StringUtils.converToInt(rb
					.getString("upload.file.maxsize"));
			upload.setSizeMax(maxSize * 1024 * 1024);
			String encoding = StringUtils.filterAllBlank(rb
					.getString("upload.header.encoding"));
			upload.setHeaderEncoding(encoding);
			FileUploadStatus status = new FileUploadStatus();
			session.setAttribute(ProgressServlet.PK, status);
			UpLoadProgressListener getBarListener = new UpLoadProgressListener(
					status);
			upload.setProgressListener(getBarListener);
			List<FileItem> formList = upload.parseRequest(request);
			Iterator<FileItem> formItem = formList.iterator();
			int fileCount = 0;
			FileItem item = null;
			FileItem fileItem = null;
			/** get upload files count */
			while (formItem.hasNext()) {
				item = (FileItem) formItem.next();
				if (item.isFormField()) {
					continue;
				} else {
					fileCount++;
					fileItem = item;
				}
			}
			if (0 == fileCount) {
				String message = "there is no upload file";
				throw new UploadException(message, 0);
			}
			if (fileCount > 1) {
				String message = "upload file count can not greater than 1";
				throw new UploadException(message, 2);
			}
			/** 原始上传图片文件名称 */
			String uploadFileName = fileItem.getName();
			String fileName = String.valueOf(System.currentTimeMillis())
					+ uploadFileName.substring(uploadFileName.lastIndexOf("."));
			if (!FileUtils.checkImgStyle(fileName)) {
				String message = "error img format";
				throw new UploadException(message, -1);
			}
			String baseCopyDirPath = StringUtils.filterAllBlank(rb
					.getString("upload.copy.img.dir")) + "\\video";
			/** 原始图片路径 */
			String copyRawImgDirPath = StringUtils
					.filterAllBlank(baseCopyDirPath + "\\" + RAW_IMG_DIR);
			FileUtils.createFolder(copyRawImgDirPath);
			String copyRawImgPath = copyRawImgDirPath + "\\" + fileName;
			/** 写入原始上传图片 */
			File copyRawImg = new File(copyRawImgPath);
			fileItem.write(copyRawImg);
			copyRawImg = new File(copyRawImgPath);
			BufferedImage rawImage = ImageIO.read(copyRawImg);
			/** 写入200*150图片 */
			String copyMinImgDirPath = StringUtils
					.filterAllBlank(baseCopyDirPath + "\\" + V_MIN_DIR + "\\");
			FileUtils.createFolder(copyMinImgDirPath);
			String copyMinImgPath = copyMinImgDirPath + "\\" + fileName;
			File copyMinImg = new File(copyMinImgPath);
			FileUtils.zoomImage(rawImage, copyMinImg, V_MIN_WIDTH, V_MIN_HIGHT);
			/** 写入600*450图片 */
			String copyNormImgDirPath = StringUtils
					.filterAllBlank(baseCopyDirPath + "\\" + V_MAX_DIR);
			FileUtils.createFolder(copyNormImgDirPath);
			String copyNormImgPath = copyNormImgDirPath + "\\" + fileName;
			File copyNormImg = new File(copyNormImgPath);
			FileUtils
					.zoomImage(rawImage, copyNormImg, V_MAX_WIDTH, V_MAX_HIGHT);
			/** set download file */
			String webRelaPath = StringUtils.filterAllBlank(rb
					.getString("upload.web.img.dir")) + "\\video";
			String webRootPath = getWebappsPath(request);
			String baseWebDirPath = webRootPath + "\\" + webRelaPath;
			/** 将路径中两个斜杠替换成一个斜杠 */
			baseWebDirPath = baseWebDirPath.replaceAll("\\\\\\\\", "\\\\");
			FileUtils.createFolder(baseWebDirPath);
			/** 将图片备份复制到发布目录 */
			FileUtils.copyFile(copyRawImg, baseWebDirPath + "\\" + RAW_IMG_DIR
					+ "\\" + fileName);
			FileUtils.copyFile(copyMinImg, baseWebDirPath + "\\" + V_MIN_DIR
					+ "\\" + fileName);
			FileUtils.copyFile(copyNormImg, baseWebDirPath + "\\" + V_MAX_DIR
					+ "\\" + fileName);
			String path = webRelaPath + "\\" + V_MAX_DIR + "\\" + fileName;
			path = path.replaceAll("\\\\", "/");
			status.setPath(path.substring(1));
		} catch (Exception e) {
			e.printStackTrace();
			FileUploadStatus status = (FileUploadStatus) session
					.getAttribute(ProgressServlet.PK);
			status = null == status ? new FileUploadStatus() : status;
			status.setState(5000);
			session.setAttribute(ProgressServlet.PK, status);
		}
	}

	/**
	 * 上传视频
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void uploadVideoSrc(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		try {
			String bundlePath = "com.baocloud.yunku.controller.upload.config.uploadconfig";
			ResourceBundle rb = ResourceBundle.getBundle(bundlePath);
			String tempDirPath = rb.getString("upload.temp.dir");
			File tempDir = FileUtils.createFolder(tempDirPath);
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setRepository(tempDir);
			int size = StringUtils.converToInt(rb
					.getString("upload.threshold.size"));
			factory.setSizeThreshold(size);
			ServletFileUpload upload = new ServletFileUpload(factory);
			int maxSize = StringUtils.converToInt(rb
					.getString("upload.file.maxsize"));
			upload.setSizeMax(maxSize * 1024 * 1024);
			String encoding = StringUtils.filterAllBlank(rb
					.getString("upload.header.encoding"));
			upload.setHeaderEncoding(encoding);
			FileUploadStatus status = new FileUploadStatus();
			session.setAttribute(ProgressServlet.PK, status);
			UpLoadProgressListener getBarListener = new UpLoadProgressListener(
					status);
			upload.setProgressListener(getBarListener);
			List<FileItem> formList = upload.parseRequest(request);
			Iterator<FileItem> formItem = formList.iterator();
			int fileCount = 0;
			FileItem item = null;
			FileItem fileItem = null;
			/** get upload files count */
			while (formItem.hasNext()) {
				item = (FileItem) formItem.next();
				if (item.isFormField()) {
					continue;
				} else {
					fileCount++;
					fileItem = item;
				}
			}
			if (0 == fileCount) {
				String message = "there is no upload file";
				throw new UploadException(message, 0);
			}
			if (fileCount > 1) {
				String message = "upload file count can not greater than 1";
				throw new UploadException(message, 2);
			}
			/** 原始上传视频文件名称 */
			String uploadFileName = fileItem.getName();
			String fileName = "v" + StringUtils.getUUID32()
					+ uploadFileName.substring(uploadFileName.lastIndexOf("."));
			if (!FileUtils.checkVideoStyle(fileName)) {
				String message = "error video format";
				throw new UploadException(message, -1);
			}
			String copyVideoDirPath = StringUtils.filterAllBlank(rb
					.getString("upload.copy.video.dir")) + "\\";
			copyVideoDirPath = StringUtils.filterAllBlank(copyVideoDirPath);
			FileUtils.createFolder(copyVideoDirPath);
			String copyVideoFilePath = copyVideoDirPath + "\\" + fileName;
			/** 写入视屏备份 */
			File copyVideo = new File(copyVideoFilePath);
			fileItem.write(copyVideo);
			copyVideo = new File(copyVideoFilePath);
			String videoRelaPath = StringUtils.filterAllBlank(rb
					.getString("upload.web.video.dir"));
			String webRootPath = getWebappsPath(request);
			String webVideoDirPath = webRootPath + "\\" + videoRelaPath;
			/** 将路径中两个斜杠替换成一个斜杠 */
			webVideoDirPath = webVideoDirPath.replaceAll("\\\\\\\\", "\\\\");
			FileUtils.createFolder(webVideoDirPath);
			String webVideoFilePath = webVideoDirPath + "\\" + fileName;
			/** 将视屏复制到发布目录 */
			FileUtils.copyFile(copyVideo, webVideoFilePath);
			String path = videoRelaPath + "\\" + fileName;
			path = path.replaceAll("\\\\", "/");
			status.setPath(path.substring(1));
		} catch (Exception e) {
			e.printStackTrace();
			FileUploadStatus status = (FileUploadStatus) session
					.getAttribute(ProgressServlet.PK);
			status = null == status ? new FileUploadStatus() : status;
			status.setState(5000);
			session.setAttribute(ProgressServlet.PK, status);
		}
	}

	/**
	 * 上传用户头像
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void uploadUserHeadImg(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		try {
			String bundlePath = "com.baocloud.yunku.controller.upload.config.uploadconfig";
			ResourceBundle rb = ResourceBundle.getBundle(bundlePath);
			String tempDirPath = rb.getString("upload.temp.dir");
			File tempDir = FileUtils.createFolder(tempDirPath);
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setRepository(tempDir);
			int size = StringUtils.converToInt(rb
					.getString("upload.threshold.size"));
			factory.setSizeThreshold(size);
			ServletFileUpload upload = new ServletFileUpload(factory);
			int maxSize = StringUtils.converToInt(rb
					.getString("upload.file.maxsize"));
			upload.setSizeMax(maxSize * 1024 * 1024);
			String encoding = StringUtils.filterAllBlank(rb
					.getString("upload.header.encoding"));
			upload.setHeaderEncoding(encoding);
			FileUploadStatus status = new FileUploadStatus();
			session.setAttribute(ProgressServlet.PK, status);
			UpLoadProgressListener getBarListener = new UpLoadProgressListener(
					status);
			upload.setProgressListener(getBarListener);
			List<FileItem> formList = upload.parseRequest(request);
			Iterator<FileItem> formItem = formList.iterator();
			int fileCount = 0;
			FileItem item = null;
			FileItem fileItem = null;
			/** get upload files count */
			while (formItem.hasNext()) {
				item = (FileItem) formItem.next();
				if (item.isFormField()) {
					continue;
				} else {
					fileCount++;
					fileItem = item;
				}
			}
			if (0 == fileCount) {
				String message = "there is no upload file";
				throw new UploadException(message, 0);
			}
			if (fileCount > 1) {
				String message = "upload file count can not greater than 1";
				throw new UploadException(message, 2);
			}
			/** 原始上传图片文件名称 */
			String uploadFileName = fileItem.getName();
			String fileName = String.valueOf(System.currentTimeMillis())
					+ uploadFileName.substring(uploadFileName.lastIndexOf("."));
			if (!FileUtils.checkImgStyle(fileName)) {
				String message = "error img format";
				throw new UploadException(message, -1);
			}
			String baseCopyDirPath = StringUtils.filterAllBlank(rb
					.getString("upload.copy.img.dir")) + "\\user";
			/** 原始图片路径 */
			String copyRawImgDirPath = StringUtils
					.filterAllBlank(baseCopyDirPath + "\\" + RAW_IMG_DIR);
			FileUtils.createFolder(copyRawImgDirPath);
			String copyRawImgPath = copyRawImgDirPath + "\\" + fileName;
			/** 写入原始上传图片 */
			File copyRawImg = new File(copyRawImgPath);
			fileItem.write(copyRawImg);
			copyRawImg = new File(copyRawImgPath);
			BufferedImage rawImage = ImageIO.read(copyRawImg);
			/** 写入200*150图片 */
			String copyMinImgDirPath = StringUtils
					.filterAllBlank(baseCopyDirPath + "\\" + U_MIN_DIR + "\\");
			FileUtils.createFolder(copyMinImgDirPath);
			String copyMinImgPath = copyMinImgDirPath + "\\" + fileName;
			File copyMinImg = new File(copyMinImgPath);
			FileUtils.zoomImage(rawImage, copyMinImg, U_MIN_WIDTH, U_MIN_HIGHT);
			/** 写入600*450图片 */
			String copyNormImgDirPath = StringUtils
					.filterAllBlank(baseCopyDirPath + "\\" + U_MAX_DIR);
			FileUtils.createFolder(copyNormImgDirPath);
			String copyNormImgPath = copyNormImgDirPath + "\\" + fileName;
			File copyNormImg = new File(copyNormImgPath);
			FileUtils
					.zoomImage(rawImage, copyNormImg, U_MAX_WIDTH, U_MAX_HIGHT);
			/** set download file */
			String webRelaPath = StringUtils.filterAllBlank(rb
					.getString("upload.web.img.dir")) + "\\user";
			String webRootPath = getWebappsPath(request);
			String baseWebDirPath = webRootPath + "\\" + webRelaPath;
			/** 将路径中两个斜杠替换成一个斜杠 */
			baseWebDirPath = baseWebDirPath.replaceAll("\\\\\\\\", "\\\\");
			FileUtils.createFolder(baseWebDirPath);
			/** 将图片备份复制到发布目录 */
			FileUtils.copyFile(copyRawImg, baseWebDirPath + "\\" + RAW_IMG_DIR
					+ "\\" + fileName);
			FileUtils.copyFile(copyMinImg, baseWebDirPath + "\\" + U_MIN_DIR
					+ "\\" + fileName);
			FileUtils.copyFile(copyNormImg, baseWebDirPath + "\\" + U_MAX_DIR
					+ "\\" + fileName);
			String path = webRelaPath + "\\" + U_MAX_DIR + "\\" + fileName;
			path = path.replaceAll("\\\\", "/");
			status.setPath(path.substring(1));
		} catch (Exception e) {
			e.printStackTrace();
			FileUploadStatus status = (FileUploadStatus) session
					.getAttribute(ProgressServlet.PK);
			status = null == status ? new FileUploadStatus() : status;
			status.setState(5000);
			session.setAttribute(ProgressServlet.PK, status);
		}
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

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	public void destroy() {
		super.destroy();
	}
}
