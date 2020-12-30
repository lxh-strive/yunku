package com.baocloud.yunku.pojo;

import java.sql.Timestamp;

/**
 * 用户积分
 * 
 * @author wzr
 *
 */
public class UserInte {
	private Integer inteId;// 积分ID
	private Integer userId;// 用户ID
	private Integer inte;// 积分
	private String inteDesc;// 积分说明
	private Timestamp time;// 时间
	private Integer bal;// 当前积分余额

	public UserInte() {
		super();
	}

	public Integer getInteId() {
		return inteId;
	}

	public void setInteId(Integer inteId) {
		this.inteId = inteId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getInte() {
		return inte;
	}

	public void setInte(Integer inte) {
		this.inte = inte;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getInteDesc() {
		return inteDesc;
	}

	public void setInteDesc(String inteDesc) {
		this.inteDesc = inteDesc;
	}

	public Integer getBal() {
		return bal;
	}

	public void setBal(Integer bal) {
		this.bal = bal;
	}
}
