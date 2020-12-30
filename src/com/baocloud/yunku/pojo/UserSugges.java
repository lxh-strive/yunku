package com.baocloud.yunku.pojo;

import java.security.Timestamp;

/**
 * 用户投诉建议
 * 
 * @author wzr
 *  *
 */
public class UserSugges {

	private Integer suggesId;
	private Integer videoId;
	private Integer comId;
	private Integer repId;
	private Integer userId;
	private Integer type;
	private String remark;
	private Timestamp operTime;

	public UserSugges() {
		super();
	}

	public Integer getSuggesId() {
		return suggesId;
	}

	public void setSuggesId(Integer suggesId) {
		this.suggesId = suggesId;
	}

	public Integer getVideoId() {
		return videoId;
	}

	public void setVideoId(Integer videoId) {
		this.videoId = videoId;
	}

	public Integer getComId() {
		return comId;
	}

	public void setComId(Integer comId) {
		this.comId = comId;
	}

	public Integer getRepId() {
		return repId;
	}

	public void setRepId(Integer repId) {
		this.repId = repId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Timestamp getOperTime() {
		return operTime;
	}

	public void setOperTime(Timestamp operTime) {
		this.operTime = operTime;
	}

}
