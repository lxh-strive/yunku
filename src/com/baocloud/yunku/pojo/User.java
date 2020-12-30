package com.baocloud.yunku.pojo;

import java.sql.Timestamp;

import com.baocloud.yunku.utils.StringUtils;

/**
 * 用户类
 * 
 * @author wzr
 *
 */
public class User {

	private Integer userId;// 用户ID
	private String username;//
	private String password;
	private String email;
	private String pic;
	private String mobile;
	private Integer inte;
	private Timestamp regTime;
	private Timestamp upTime;
	private String loginState;
	private Integer grade;
	private Integer state;
	private String nickname;// 昵称

	public User() {
		super();
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getInte() {
		return inte;
	}

	public void setInte(Integer inte) {
		this.inte = inte;
	}

	public Timestamp getRegTime() {
		return regTime;
	}

	public void setRegTime(Timestamp regTime) {
		this.regTime = regTime;
	}

	public Timestamp getUpTime() {
		return upTime;
	}

	public void setUpTime(Timestamp upTime) {
		this.upTime = upTime;
	}

	public String getLoginState() {
		return loginState;
	}

	public void setLoginState(String loginState) {
		this.loginState = loginState;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getNickname() {
		nickname = StringUtils.isEmptyStr(username) ? email : username;
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}
