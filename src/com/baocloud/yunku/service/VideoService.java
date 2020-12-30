package com.baocloud.yunku.service;

import java.util.List;
import com.baocloud.yunku.pojo.Video;
import com.baocloud.yunku.pojo.VideoColl;
import com.baocloud.yunku.pojo.VideoParentType;
import com.baocloud.yunku.pojo.VideoSubType;

/**
 * 视频Service接口
 * 
 * @author wzr
 * 
 */
public interface VideoService {
	/**
	 * 查询视频信息
	 * 
	 * @param videoId
	 * @return
	 */
	public Video getVideo(Integer videoId);

	/**
	 * 查询视频一级分类
	 * 
	 * @param containSub
	 *            是否包含二级分类
	 * @return
	 */
	public List<VideoParentType> queryParentType(boolean containSub);

	/**
	 * 根据关键字查询视频
	 * 
	 * @param keyword
	 * @return
	 */
	public List<Video> queryVideos(String keyword);

	/**
	 * 根据视频父分类ID查询视频
	 * 
	 * @param parentId
	 * @return
	 */
	public List<Video> queryVideos(Integer parentId);

	/**
	 * 根据视频子分类ID查询视频
	 * 
	 * @param parentId
	 * @return
	 */
	public List<Video> queryVideos(Integer parentId, Integer subId);

	/**
	 * 获取视频一级分类XML数据
	 * 
	 * @param parentId
	 * @return
	 */
	public String getVideoTypeXMLData();

	/**
	 * 获取视频二级分类XML数据
	 * 
	 * @param parentId
	 * @return
	 */
	public String getVideoTypeXMLData(Integer parentId);

	/**
	 * 添加视频搜藏
	 * 
	 * @param videoId
	 * @param userId
	 * @return
	 */
	public int addVideoColl(Integer videoId, Integer userId);

	/**
	 * 添加视频
	 * 
	 * @param video
	 * @return
	 */
	public int addVideo(Video video);

	/**
	 * 删除视频搜藏
	 * 
	 * @param collId
	 * @return
	 */
	public int deleteVideoColl(Integer collId);

	/**
	 * 查询用户视频搜藏
	 * 
	 * @param userId
	 * @return
	 */
	public List<VideoColl> queryVideoColl(Integer userId);

	/**
	 * 检查该用户是否收藏该视频
	 * 
	 * @param videoId
	 * @param userId
	 * @return
	 */
	public int checkVideoColl(Integer videoId, Integer userId);
    /**
     * 获取视频
     * @param videoId
     * @param isCount
     * @return
     */
	public Video getVideo(Integer videoId, boolean isCount);

	/**
	 * 查询视频二级分类
	 * @param parentId
	 * @param isCount
	 * @return
	 */
	public List<VideoSubType> querySubType(Integer parentId, boolean isCount);

	/**
	 * 查询用户发表的视频
	 * 
	 * @param userId
	 * @return
	 */
	public List<Video> selectUserVideos(Integer userId);

	/**
	 * 修改视频播放次数
	 * 
	 * @param videoId
	 * @return
	 */
	public int updatePlayNumber(Integer videoId);

	/**
	 * 修改视频下载量
	 * 
	 * @param videoId
	 * @return
	 */
	public int updateDownNumber(Integer videoId);

	/**
	 * 添加视频点赞或踩的数量
	 * 
	 * @param subId
	 * @return
	 */
	public int addVideoSugges(Integer userId, Integer videoId, int type);

	/**
	 * 获取视频一级分类
	 * 
	 * @param subId
	 * @return
	 */
	public VideoParentType getParentType(Integer parentId);

	/**
	 * 获取视频二级分类
	 * 
	 * @param subId
	 * @return
	 */
	public VideoSubType getSubType(Integer subId);

}
