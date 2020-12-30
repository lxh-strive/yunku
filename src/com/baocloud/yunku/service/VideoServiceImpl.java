package com.baocloud.yunku.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.baocloud.yunku.dao.UserDao;
import com.baocloud.yunku.dao.UserDaoImpl;
import com.baocloud.yunku.dao.VideoDao;
import com.baocloud.yunku.dao.VideoDaoImpl;
import com.baocloud.yunku.pojo.Video;
import com.baocloud.yunku.pojo.VideoColl;
import com.baocloud.yunku.pojo.VideoParentType;
import com.baocloud.yunku.pojo.VideoSubType;
import com.baocloud.yunku.utils.StringUtils;

public class VideoServiceImpl implements VideoService {

	private VideoDao videoDao;

	public VideoServiceImpl() {
		super();
		videoDao = new VideoDaoImpl();
	}

	@Override
	public Video getVideo(Integer videoId) {
		return videoDao.getVideo(videoId);
	}

	public void setVideoDao(VideoDao videoDao) {
		this.videoDao = videoDao;
	}

	@Override
	public List<Video> queryVideos(String keyword) {
		keyword = StringUtils.isEmptyStr(keyword) ? "" : StringUtils.filterAllBlank(keyword);
		List<Video> videos = videoDao.queryVideos(keyword);
		if (null != videos && !videos.isEmpty()) {
			UserDao userDao = new UserDaoImpl();
			for (Video video : videos) {
				video.setUser(userDao.getUser(video.getUserId()));
			}
		}
		return videos;
	}

	@Override
	public List<Video> queryVideos(Integer parentId) {
		List<Video> videos = videoDao.queryVideos(parentId);
		if (null != videos && !videos.isEmpty()) {
			UserDao userDao = new UserDaoImpl();
			for (Video video : videos) {
				video.setUser(userDao.getUser(video.getUserId()));
			}
		}
		return videos;
	}

	@Override
	public List<Video> queryVideos(Integer parentId, Integer subId) {
		List<Video> videos = videoDao.queryVideos(parentId, subId);
		if (null != videos && !videos.isEmpty()) {
			UserDao userDao = new UserDaoImpl();
			for (Video video : videos) {
				video.setUser(userDao.getUser(video.getUserId()));
			}
		}
		return videos;
	}

	@Override
	public int addVideo(Video video) {
		return videoDao.addVideo(video);
	}

	@Override
	public String getVideoTypeXMLData() {
		int state = -500;
		StringBuffer xmlBuf = new StringBuffer();
		xmlBuf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xmlBuf.append("<video-types>");
		try {
			List<VideoParentType> videoTypeList = videoDao.queryParentType();
			if (null != videoTypeList) {
				for (VideoParentType videoType : videoTypeList) {
					Integer id = videoType.getParentId();
					String name = videoType.getName();
					xmlBuf.append("<video-type>");
					xmlBuf.append("<id>");
					xmlBuf.append(id);
					xmlBuf.append("</id>");
					xmlBuf.append("<name>");
					xmlBuf.append(name);
					xmlBuf.append("</name>");
					xmlBuf.append("</video-type>");
				}
				state = videoTypeList.size();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		xmlBuf.append("<state>");
		xmlBuf.append(state);
		xmlBuf.append("</state>");
		xmlBuf.append("</video-types>");
		return xmlBuf.toString();
	}

	@Override
	public String getVideoTypeXMLData(Integer parentId) {
		int state = -500;
		StringBuffer xmlBuf = new StringBuffer();
		xmlBuf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xmlBuf.append("<video-types>");
		try {
			List<VideoSubType> videoTypeList = videoDao.querySubType(parentId);
			if (null != videoTypeList) {
				for (VideoSubType videoType : videoTypeList) {
					Integer id = videoType.getSubId();
					String name = videoType.getName();
					xmlBuf.append("<video-type>");
					xmlBuf.append("<id>");
					xmlBuf.append(id);
					xmlBuf.append("</id>");
					xmlBuf.append("<name>");
					xmlBuf.append(name);
					xmlBuf.append("</name>");
					xmlBuf.append("</video-type>");
				}
				state = videoTypeList.size();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		xmlBuf.append("<state>");
		xmlBuf.append(state);
		xmlBuf.append("</state>");
		xmlBuf.append("</video-types>");
		return xmlBuf.toString();
	}

	@Override
	public int addVideoColl(Integer videoId, Integer userId) {
		int state = videoDao.checkVideoColl(videoId, userId);
		if (0 == state) {
			long times = new Date().getTime();
			Timestamp colTime = new Timestamp(times);
			state = videoDao.addVideoColl(videoId, userId, colTime);
		} else {
			state = state > 0 ? 2 : state;
		}
		return state;
	}

	@Override
	public int deleteVideoColl(Integer collId) {
		return videoDao.deleteVideoColl(collId);
	}

	@Override
	public List<VideoColl> queryVideoColl(Integer userId) {
		List<VideoColl> collList = videoDao.queryVideoColl(userId);
		if (null != collList) {
			for (VideoColl coll : collList) {
				Integer videoId = coll.getVideoId();
				Video video = videoDao.getVideo(videoId);
				coll.setVideo(video);// 设置收藏对象中视频属性
			}
		}
		return collList;
	}

	@Override
	public int checkVideoColl(Integer videoId, Integer userId) {
		return videoDao.checkVideoColl(videoId, userId);
	}

	@Override
	public Video getVideo(Integer videoId, boolean isCount) {
		Video video = videoDao.getVideo(videoId);
		if (null != video && null != video.getVideoId()) {
			if (isCount) {
				video.setCommCount(videoDao.getCommCount(videoId));
				video.setCollCount(videoDao.getCollCount(videoId));
				video.setSuppCount(videoDao.getSuggesCount(videoId, 1));
				video.setUnSuppCount(videoDao.getSuggesCount(videoId, 2));
				video.setCompCount(videoDao.getSuggesCount(videoId, 3));
			}
			Integer userId = video.getUserId();
			UserDao userDao = new UserDaoImpl();
			video.setUser(userDao.getUser(userId));
		}
		return video;
	}

	@Override
	public List<VideoParentType> queryParentType(boolean isCount) {
		List<VideoParentType> parentTypes = videoDao.queryParentType();
		if (isCount && null != parentTypes && parentTypes.size() > 0) {
			// 获取所有父视频分组统计数量Map
			Map<Integer, Integer> groupMap = videoDao.queryVideoGroups();
			for (VideoParentType videoType : parentTypes) {
				Integer parentId = videoType.getParentId();
				videoType.setCounts(groupMap.get(parentId));
				videoType.setSubTypes(querySubType(parentId, true));
			}
		}
		return parentTypes;
	}

	@Override
	public List<VideoSubType> querySubType(Integer parentId, boolean isCount) {
		List<VideoSubType> subTypes = videoDao.querySubType(parentId);
		if (isCount && null != subTypes && subTypes.size() > 0) {
			// 获取所有子视频分组统计数量Map
			Map<Integer, Integer> groupMap = videoDao.queryVideoGroups(parentId);
			for (VideoSubType videoType : subTypes) {
				Integer subId = videoType.getSubId();
				videoType.setCounts(groupMap.get(subId));
			}
		}
		return subTypes;
	}

	@Override
	public List<Video> selectUserVideos(Integer userId) {
		return videoDao.queryUserVideos(userId);
	}

	@Override
	public int updatePlayNumber(Integer videoId) {
		return videoDao.updatePlayNumber(videoId);
	}

	@Override
	public int updateDownNumber(Integer videoId) {
		return videoDao.updateDownNumber(videoId);
	}

	@Override
	public int addVideoSugges(Integer userId, Integer videoId, int type) {
		int state = videoDao.getSuggesState(userId, videoId, type);
		if (0 == state) {
			state = videoDao.addVideoSugges(userId, videoId, type);
		} else {
			state = 1 == state ? 2 : state;
		}
		return state;
	}

	@Override
	public VideoParentType getParentType(Integer parentId) {
		VideoParentType videoType = videoDao.getParentType(parentId);
		videoType.setSubTypes(querySubType(parentId, true));
		videoType.setCounts(videoDao.countVideos(parentId));
		return videoType;
	}

	@Override
	public VideoSubType getSubType(Integer subId) {
		VideoSubType subType = videoDao.getSubType(subId);
		Integer parentId = subType.getParentId();
		VideoParentType parentType = videoDao.getParentType(parentId);
		subType.setCounts(videoDao.countVideos(parentId, subId));
		parentType.setCounts(videoDao.countVideos(parentId));
		subType.setParentType(parentType);
		return subType;
	}

}
