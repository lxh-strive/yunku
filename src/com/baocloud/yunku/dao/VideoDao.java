package com.baocloud.yunku.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.baocloud.yunku.pojo.Video;
import com.baocloud.yunku.pojo.VideoColl;
import com.baocloud.yunku.pojo.VideoParentType;
import com.baocloud.yunku.pojo.VideoSubType;

/**
 * 视频DAO接口
 * 
 * @author DELL
 * 
 */
public interface VideoDao {
	/**
	 * 查询视频二级或子分类
	 * 
	 * @param parentId
	 * @return
	 */
	public List<VideoSubType> querySubType(Integer parentId);

	/**
	 * 添加视频信息
	 * 
	 * @param video
	 * @return
	 */
	public int addVideo(Video video);

	/**
	 * 查询视频一级或父分类
	 * 
	 * @return
	 */
	public List<VideoParentType> queryParentType();

	/**
	 * 添加视频收藏信息
	 * 
	 * @param videoId
	 * @param userId
	 * @param colTime
	 * @return
	 */
	public int addVideoColl(Integer videoId, Integer userId, Timestamp colTime);

	/**
	 * 删除视频收藏信息
	 * 
	 * @param collId
	 * @return
	 */
	public int deleteVideoColl(Integer collId);

	/**
	 * 查询视频收藏信息
	 * 
	 * @param userId
	 * @return
	 */
	public List<VideoColl> queryVideoColl(Integer userId);

	/**
	 * 检测当前用户是否收藏该视频
	 * 
	 * @param videoId
	 * @param userId
	 * @return
	 */
	public int checkVideoColl(Integer videoId, Integer userId);

	/**
	 * 获取视频信息
	 * 
	 * @param videoId
	 * @return
	 */
	public Video getVideo(Integer videoId);

	/**
	 * 查询视频
	 * 
	 * @param keyword
	 * @return
	 */
	public List<Video> queryVideos(String keyword);

	/**
	 * 根据一级视频分类ID查询二级视频
	 * 
	 * @param parentId
	 * @return
	 */
	public List<Video> queryVideos(Integer parentId);

	/**
	 * 查询视频
	 * 
	 * @param parentId
	 * @param subId
	 * @return
	 */
	public List<Video> queryVideos(Integer parentId, Integer subId);

	/**
	 * 查询一级视频分组
	 * 
	 * @return
	 */
	public Map<Integer, Integer> queryVideoGroups();

	/**
	 * 查询二级视频分组
	 * 
	 * @return
	 */
	public Map<Integer, Integer> queryVideoGroups(Integer parentId);

	/**
	 * 查询用户视频
	 * 
	 * @return
	 */
	public List<Video> queryUserVideos(Integer userId);

	/**
	 * 查询播放次数
	 * 
	 * @return
	 */
	public int updatePlayNumber(Integer videoId);

	/**
	 * 查询下载次数
	 * 
	 * @return
	 */
	public int updateDownNumber(Integer videoId);

	/**
	 * 查询评论数量
	 * 
	 * @param videoId
	 * @return
	 */
	public int getCommCount(Integer videoId);

	/**
	 * 查询收藏数量
	 * 
	 * @param videoId
	 * @return
	 */
	public int getCollCount(Integer videoId);

	/**
	 * 查询点赞或踩的数量
	 * 
	 * @param videoId
	 * @param type
	 * @return
	 */
	public int getSuggesCount(Integer videoId, int type);

	/**
	 * 查询点赞或踩的状态
	 * 
	 * @param userId
	 * @param videoId
	 * @param type
	 * @return
	 */
	public int getSuggesState(Integer userId, Integer videoId, int type);

	/**
	 * 获取视频点赞或踩的数量
	 * 
	 * @param userId
	 * @param videoId
	 * @param type
	 * @return
	 */
	public int addVideoSugges(Integer userId, Integer videoId, int type);

	/**
	 * 查询父类视频分类信息
	 * 
	 * @param parentId
	 * @return
	 */
	public VideoParentType getParentType(Integer parentId);

	/**
	 * 查询子类视频分类信息
	 * 
	 * @param subId
	 * @return
	 */
	public VideoSubType getSubType(Integer subId);

	/**
	 * 根据视频一级分类统计视频数量
	 * @param parentId
	 * @return
	 */
	public int countVideos(Integer parentId);
    /**
     * 根据视频二级分类统计视频数量
     * @param parentId
     * @param subId
     * @return
     */
	public int countVideos(Integer parentId, Integer subId);

}