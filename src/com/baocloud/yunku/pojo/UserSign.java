package com.baocloud.yunku.pojo;

import java.sql.Date;

/**
 * 用户签到
 * 
 * @author wzr
 *
 */
public class UserSign {
	private Integer signId;// 签到ID
	private Integer userId;// 用户ID
	private Date signDate;// 签到日期
	private Integer days;// 当前连续签到天数

	public UserSign() {
		super();
	}

	public Integer getSignId() {
		return signId;
	}

	public void setSignId(Integer signId) {
		this.signId = signId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

}
