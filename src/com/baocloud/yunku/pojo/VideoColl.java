package com.baocloud.yunku.pojo;

import java.sql.Timestamp;

/**
 * 视频收藏
 * 
 * @author wzr
 *
 */
public class VideoColl {

	private Integer collId;
	private Integer videoId;
	private Integer userId;
	private Timestamp collTime;
	private Video video;

	public VideoColl() {
		super();
	}

	public Integer getCollId() {
		return collId;
	}

	public void setCollId(Integer collId) {
		this.collId = collId;
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

	public Timestamp getCollTime() {
		return collTime;
	}

	public void setCollTime(Timestamp collTime) {
		this.collTime = collTime;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	
}
