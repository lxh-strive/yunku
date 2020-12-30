package com.baocloud.yunku.pojo;

import java.sql.Timestamp;

import com.baocloud.yunku.utils.DateTimeUtil;

/**
 * 评论回复
 * 
 * @author wzr
 *
 */
public class CommReply {
	private Integer replyId;
	private Integer comId;
	private Integer userId;
	private String repcon;
	private Timestamp reptime;
	private Integer state;
	private User user;// 评论者
	/** 以下为统计数据 **/
	private Integer suppCount;// 点赞数量
	private Integer unSuppCount;// 被踩数量
	private Integer compCount;// 投诉数量

	public CommReply() {
		super();
	}

	public Integer getComId() {
		return comId;
	}

	public void setComId(Integer comId) {
		this.comId = comId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getRepcon() {
		return repcon;
	}

	public void setRepcon(String repcon) {
		this.repcon = repcon;
	}

	public Timestamp getReptime() {
		return reptime;
	}

	public void setReptime(Timestamp reptime) {
		this.reptime = reptime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getReplyId() {
		return replyId;
	}

	public void setReplyId(Integer replyId) {
		this.replyId = replyId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getSuppCount() {
		return suppCount;
	}

	public void setSuppCount(Integer suppCount) {
		this.suppCount = null != suppCount ? suppCount : 0;
	}

	public Integer getUnSuppCount() {
		return unSuppCount;
	}

	public void setUnSuppCount(Integer unSuppCount) {
		this.unSuppCount = null != unSuppCount ? unSuppCount : 0;
	}

	public Integer getCompCount() {
		return compCount;
	}

	public void setCompCount(Integer compCount) {
		this.compCount = null != compCount ? compCount : 0;
	}

	public String getTimeDesc() {
		return DateTimeUtil.getTimeDesc(this.reptime);
	}

}
