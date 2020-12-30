package com.baocloud.yunku.controller.upload;

public class UploadException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6903363740345286539L;
	/**
	 * 5404:there is no upload file 5200:upload file count greater than 1
	 * 5401:no login 5110: repeat upload 5100:error file style 5120:unknowing
	 * file style
	 */
	private int code;

	public UploadException() {
		super();
	}

	public UploadException(String message) {
		super(message);
	}

	public UploadException(String message, int code) {
		super(message);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
