package com.baocloud.yunku.pojo;

/**
 * 视频子分类
 * 
 * @author wzr
 *
 */
public class VideoSubType {

	private Integer subId;
	private Integer parentId;
	private String name;
	private String logo;
	private Integer counts;// 该分类下视频的总数量
	private VideoParentType parentType;// 所属视频分类

	public Integer getSubId() {
		return subId;
	}

	public void setSubId(Integer subId) {
		this.subId = subId;
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

	public VideoParentType getParentType() {
		return parentType;
	}

	public void setParentType(VideoParentType parentType) {
		this.parentType = parentType;
	}
}
