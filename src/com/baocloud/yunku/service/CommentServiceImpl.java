package com.baocloud.yunku.service;

import java.util.List;
import com.baocloud.yunku.dao.CommentDao;
import com.baocloud.yunku.dao.CommentDaoImpl;
import com.baocloud.yunku.dao.UserDao;
import com.baocloud.yunku.dao.UserDaoImpl;
import com.baocloud.yunku.pojo.CommReply;
import com.baocloud.yunku.pojo.VideoComm;

public class CommentServiceImpl implements CommentService {
	private CommentDao commentDao;

	public CommentServiceImpl() {
		super();
		commentDao = new CommentDaoImpl();
	}

	@Override
	public List<VideoComm> queryVideoComms(Integer videoId) {
		List<VideoComm> videoComms = commentDao.queryVideoComms(videoId);
		if (null != videoComms && videoComms.size() > 0) {
			UserDao userDao = new UserDaoImpl();
			for (VideoComm videoComm : videoComms) {
				Integer userId = videoComm.getUserId();
				Integer comId = videoComm.getComId();
				videoComm.setUser(userDao.getUser(userId));
				videoComm.setSuppCount(commentDao.countCommSugges(comId, 1));
				videoComm.setUnSuppCount(commentDao.countCommSugges(comId,2));
				videoComm.setCompCount(commentDao.countCommSugges(comId, 3));
				videoComm.setCommReplies(queryCommReplys(comId));
			}
		}
		return videoComms;
	}

	@Override
	public List<CommReply> queryCommReplys(Integer comId) {
		List<CommReply> commReplies = commentDao.queryCommReplys(comId);
		if (null != commReplies && commReplies.size() > 0) {
			UserDao userDao = new UserDaoImpl();
			for (CommReply commReply : commReplies) {
				Integer userId = commReply.getUserId();
				Integer repId = commReply.getReplyId();
				commReply.setUser(userDao.getUser(userId));
				commReply.setSuppCount(commentDao.countReplySugges(repId, 1));
				commReply
						.setUnSuppCount(commentDao.countReplySugges(repId, -1));
				commReply.setCompCount(commentDao.countReplySugges(repId, -2));
			}
		}
		return commReplies;
	}

	@Override
	public int addVideoComm(String comcon, Integer userId, Integer videoId) {
		return commentDao.addVideoComm(comcon, userId, videoId);
	}

	@Override
	public int addCommReply(String repcon, Integer userId, Integer comId) {
		return commentDao.addCommReply(repcon, userId, comId);
	}

	@Override
	public int getReplyCount(Integer comId) {
		return commentDao.getReplyCount(comId);
	}

	@Override
	public int getCommState(Integer comId, Integer userId, int type) {
		return commentDao.getCommState(comId, userId, type);
	}

	@Override
	public int getReplyState(Integer repId, Integer userId, int type) {
		return commentDao.getReplyState(repId, userId, type);
	}

	@Override
	public int countCommSugges(Integer comId, int type) {
		return commentDao.countCommSugges(comId, type);
	}

	@Override
	public int countReplySugges(Integer repId, int type) {
		return commentDao.countReplySugges(repId, type);
	}

	@Override
	public int addCommSugges(Integer userId, Integer comId, int type) {
		int state = commentDao.getCommState(comId, userId, type);
		if (0 == state) {
			state = commentDao.addCommSugges(userId, comId, type);
		} else {
			state = 1 == state ? 2 : state;
		}
		return state;
	}

	@Override
	public int addReplySugges(Integer userId, Integer repId, int type) {
		int state = commentDao.getReplyState(repId, userId, type);
		if (0 == state) {
			state = commentDao.addReplySugges(userId, repId, type);
		} else {
			state = 1 == state ? 2 : state;
		}
		return state;
	}

}
