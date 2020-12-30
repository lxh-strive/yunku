package com.baocloud.yunku.controller.upload;

import java.io.Serializable;
import com.baocloud.yunku.utils.StringUtils;

public class FileUploadStatus implements Serializable {

	private static final long serialVersionUID = 9194905623837027794L;
	private long pBytesRead;
	private long pContentLength;
	private long pItems;
	/**
	 * 0:正常 4000:未获取上传进度 5000:上传文件服务器运行异常 5000:上传文件服务器运行异常 6000：获取上传进度服务器运行异常
	 */
	private int state = 0;
	private String path;

	public FileUploadStatus() {
		super();

	}

	public long getPBytesRead() {
		return pBytesRead;
	}

	public void setPBytesRead(long bytesRead) {
		pBytesRead = bytesRead;
	}

	public long getPContentLength() {
		return pContentLength;
	}

	public void setPContentLength(long contentLength) {
		pContentLength = contentLength;
	}

	public long getPItems() {
		return pItems;
	}

	public void setPItems(long items) {
		pItems = items;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getPath() {
		return null == path ? StringUtils.getEmptyStr() : path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
