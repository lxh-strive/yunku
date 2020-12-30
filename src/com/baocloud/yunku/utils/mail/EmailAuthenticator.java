package com.baocloud.yunku.utils.mail;

import javax.mail.PasswordAuthentication;

/**
 * 
 * @author baocloud
 *
 */
public final class EmailAuthenticator extends javax.mail.Authenticator {
	private String userName;
	private String passWord;

	public EmailAuthenticator(String userName, String passWord) {
		this.userName = userName;
		this.passWord = passWord;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, passWord);
	}

}
