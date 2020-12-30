package com.baocloud.yunku.dao;

import java.util.List;
import com.baocloud.yunku.pojo.CommReply;
import com.baocloud.yunku.pojo.VideoComm;

/**
 * 评论dao接口
 * 
 * @author wzr
 *
 */
public interface CommentDao {
	/**
	 * 查询视频所有评论
	 * 
	 * @param videoId
	 * @return
	 */
	public List<VideoComm> queryVideoComms(Integer videoId);

	/**
	 * 查询评论所有回复
	 * 
	 * @param comId
	 * @return
	 */
	public List<CommReply> queryCommReplys(Integer comId);

	/**
	 * 添加视频评论
	 * 
	 * @param userId
	 * @param videoId
	 * @return
	 */
	public int addVideoComm(String comcon, Integer userId, Integer videoId);

	/**
	 * 添加评论回复
	 * 
	 * @param userId
	 * @param comId
	 * @return
	 */
	public int addCommReply(String repcon, Integer userId, Integer comId);

	/**
	 * 获取评论回复数量
	 * 
	 * @param comId
	 * @return
	 */
	public int getReplyCount(Integer comId);

	/**
	 * 获取当前用户对该评论进行点赞、踩、投诉状态 0:未进行相关操作 1:已进行相关操作
	 * 
	 * @param comId
	 * @param userId
	 * @param type
	 * @return
	 */
	public int getCommState(Integer comId, Integer userId, int type);

	/**
	 * 获取当前用户对该回复进行点赞、踩、投诉状态 0:未进行相关操作 1:已进行相关操作
	 * 
	 * @param repId
	 * @param userId
	 * @param type
	 * @return
	 */
	public int getReplyState(Integer repId, Integer userId, int type);

	/**
	 * 统计评论点赞、被踩、投诉数量 1:点赞 2:被踩 3:投诉 其它:参数错误
	 * 
	 * @param videoId
	 * @return
	 */
	public int countCommSugges(Integer comId, int type);

	/**
	 * 统计回复点赞、被踩、投诉数量 1:点赞 2:被踩 3:投诉 其它:参数错误
	 * 
	 * @param repId
	 * @param type
	 * @return
	 */
	public int countReplySugges(Integer repId, int type);

	/**
	 * 添加评论的点赞、踩、投诉记录
	 * 
	 * @param userId
	 * @param comId
	 * @param type
	 * @return
	 */
	public int addCommSugges(Integer userId, Integer comId, int type);

	/**
	 * 添加回复的点赞、踩、投诉记录
	 * 
	 * @param userId
	 * @param comId
	 * @param type
	 * @return
	 */
	public int addReplySugges(Integer userId, Integer repId, int type);
}
