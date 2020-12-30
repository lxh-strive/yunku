package com.baocloud.yunku.pojo;

import java.sql.Timestamp;
import java.util.List;
import com.baocloud.yunku.utils.DateTimeUtil;

/**
 * 视频评论
 * 
 * @author wzr
 *
 */
public class VideoComm {

	private Integer comId;
	private Integer videoId;
	private Integer userId;
	private String comCon;
	private Timestamp comTime;
	private Integer state;
	private User user;// 评论者
	/** 以下为统计数据 **/
	private Integer suppCount;// 点赞数量
	private Integer unSuppCount;// 被踩数量
	private Integer compCount;// 投诉数量
	private Integer repCount;// 回复数量
	private List<CommReply> commReplies;// 评论回复列表

	public VideoComm() {
		super();
	}

	public Integer getComId() {
		return comId;
	}

	public void setComId(Integer comId) {
		this.comId = comId;
	}

	public Integer getVideoId() {
		return videoId;
	}

	public void setVideoId(Integer videoId) {
		this.videoId = videoId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getComCon() {
		return comCon;
	}

	public void setComCon(String comCon) {
		this.comCon = comCon;
	}

	public Timestamp getComTime() {
		return comTime;
	}

	public void setComTime(Timestamp comTime) {
		this.comTime = comTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
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

	public Integer getRepCount() {
		repCount = null != commReplies ? commReplies.size() : 0;
		return repCount;
	}

	public void setRepCount(Integer repCount) {
		this.repCount = repCount;
	}

	public List<CommReply> getCommReplies() {
		return commReplies;
	}

	public void setCommReplies(List<CommReply> commReplies) {
		this.commReplies = commReplies;
	}

	public String getTimeDesc() {
		return DateTimeUtil.getTimeDesc(this.comTime);
	}
}
