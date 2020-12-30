package com.baocloud.yunku.controller.upload;

import org.apache.commons.fileupload.ProgressListener;

public class UpLoadProgressListener implements ProgressListener {
	private FileUploadStatus status;

	public UpLoadProgressListener(FileUploadStatus status) {
		this.status = status;
	}

	public void update(long pBytesRead, long pContentLength, int pItems) {
		status.setPBytesRead(pBytesRead);
		status.setPContentLength(pContentLength);
		status.setPItems(pItems);
	}
}
