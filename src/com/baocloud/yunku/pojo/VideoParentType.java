package com.baocloud.yunku.pojo;

import java.util.List;

/**
 * 视频父类分(一级)
 * 
 * @author wzr
 *
 */
public class VideoParentType {
	private Integer parentId;
	private String name;
	private Integer state;
	private String logo;
	private Integer counts;// 该分类下视频的总数量
	private List<VideoSubType> subTypes;

	public VideoParentType() {
		super();
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Integer getCounts() {
		return counts;
	}

	public void setCounts(Integer counts) {
		this.counts = null != counts ? counts : 0;
	}

	public List<VideoSubType> getSubTypes() {
		return subTypes;
	}

	public void setSubTypes(List<VideoSubType> subTypes) {
		this.subTypes = subTypes;
	}

}
