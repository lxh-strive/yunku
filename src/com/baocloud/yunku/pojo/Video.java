package com.baocloud.yunku.pojo;

import java.sql.Timestamp;

/**
 * 视频
 * 
 * @author wzr
 *
 */
public class Video {
	private Integer videoId;
	private String name;
	private String src;
	private Integer parentId;
	private Integer subId;
	private Integer userId;
	private Timestamp pubTime;
	private String videoDesc;
	private String pic;
	private String minPic;
	private Integer playNumber;
	private Integer downNumber;
	private Integer downPoints;
	private String mark;
	private Integer size;
	private String state;
	private User user;// 发布者
	/** 以下为统计数据 **/
	private Integer commCount;// 评论数量
	private Integer collCount;// 收藏数量
	private Integer suppCount;// 点赞数量
	private Integer unSuppCount;// 被踩数量
	private Integer compCount;// 投诉数量

	public Video() {
		super();
	}

	public Integer getCommCount() {
		return commCount;
	}

	public void setCommCount(Integer commCount) {
		this.commCount = null != commCount ? commCount : 0;
	}

	public Integer getCollCount() {
		return collCount;
	}

	public void setCollCount(Integer collCount) {
		this.collCount = null != collCount ? collCount : 0;
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

	public Integer getVideoId() {
		return videoId;
	}

	public void setVideoId(Integer videoId) {
		this.videoId = videoId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getSubId() {
		return subId;
	}

	public void setSubId(Integer subId) {
		this.subId = subId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Timestamp getPubTime() {
		return pubTime;
	}

	public void setPubTime(Timestamp pubTime) {
		this.pubTime = pubTime;
	}

	public String getVideoDesc() {
		return videoDesc;
	}

	public void setVideoDesc(String videoDesc) {
		this.videoDesc = videoDesc;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public Integer getPlayNumber() {
		return playNumber;
	}

	public void setPlayNumber(Integer playNumber) {
		this.playNumber = playNumber;
	}

	public Integer getDownNumber() {
		return downNumber;
	}

	public void setDownNumber(Integer downNumber) {
		this.downNumber = downNumber;
	}

	public Integer getDownPoints() {
		return downPoints;
	}

	public void setDownPoints(Integer downPoints) {
		this.downPoints = downPoints;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getMinPic() {
		minPic = pic.replace("600450", "200150");
		return minPic;
	}

	public void setMinPic(String minPic) {
		this.minPic = minPic;
	}

}
