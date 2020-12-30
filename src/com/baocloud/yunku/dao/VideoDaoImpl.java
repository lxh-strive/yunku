package com.baocloud.yunku.dao;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baocloud.yunku.pojo.Video;
import com.baocloud.yunku.pojo.VideoColl;
import com.baocloud.yunku.pojo.VideoParentType;
import com.baocloud.yunku.pojo.VideoSubType;

public class VideoDaoImpl extends BaseDao implements VideoDao {

	public VideoDaoImpl() {
		super();
	}

	@Override
	public List<VideoSubType> querySubType(Integer parentId) {
		List<VideoSubType> videoTypeList = null;
		Connection conn = getConn();
		if (null != conn) {
			Statement s = null;
			ResultSet rs = null;
			try {
				String sql = "select sub_id,name from videosubtype  where parent_id=" + parentId;
				s = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = s.executeQuery(sql);
				videoTypeList = new ArrayList<VideoSubType>();
				while (rs.next()) {
					VideoSubType videoType = new VideoSubType();
					videoType.setName(rs.getString("name"));
					videoType.setSubId(rs.getInt("sub_id"));
					videoTypeList.add(videoType);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				videoTypeList = null;
			} catch (Exception e) {
				e.printStackTrace();
				videoTypeList = null;
			}
			close(conn, s, rs);
		}
		return videoTypeList;
	}

	@Override
	public int addVideo(Video video) {
		int state = -1;
		Connection conn = getConn();
		if (null != conn) {
			PreparedStatement ps = null;
			String sql = "insert into Video(name,src,parent_id,sub_id,user_id,pub_time,video_desc,pic,play_number,down_number,down_points,mark,size,state)values(?,?,?,?,?,?,?,?,0,0,?,?,?,1)";
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, video.getName());
				ps.setString(2, video.getSrc());
				if (null != video.getParentId()) {
					ps.setInt(3, video.getParentId());
				} else {
					ps.setNull(3, Types.INTEGER);
				}
				if (null != video.getSubId()) {
					ps.setInt(4, video.getSubId());
				} else {
					ps.setNull(4, Types.INTEGER);
				}
				if (null != video.getUserId()) {
					ps.setInt(5, video.getUserId());
				} else {
					ps.setNull(5, Types.INTEGER);
				}
				ps.setTimestamp(6, video.getPubTime());
				ps.setString(7, video.getVideoDesc());
				ps.setString(8, video.getPic());
				ps.setInt(9, video.getDownPoints());
				ps.setString(10, video.getMark());
				ps.setInt(11, video.getSize());
				state = ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return state;
	}

	@Override
	public List<VideoParentType> queryParentType() {
		List<VideoParentType> videoTypeList = null;
		Connection conn = getConn();
		if (null != conn) {
			Statement s = null;
			ResultSet rs = null;
			try {
				String sql = "select parent_id,name from videoparenttype";
				s = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = s.executeQuery(sql);
				videoTypeList = new ArrayList<VideoParentType>();
				while (rs.next()) {
					VideoParentType videoType = new VideoParentType();
					videoType.setName(rs.getString("name"));
					videoType.setParentId(rs.getInt("parent_id"));
					videoTypeList.add(videoType);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				videoTypeList = null;
			} catch (Exception e) {
				e.printStackTrace();
				videoTypeList = null;
			}
			close(conn, s, rs);
		}
		return videoTypeList;
	}

	@Override
	public int addVideoColl(Integer videoId, Integer userId, Timestamp colTime) {
		Connection conn = getConn();
		int state = -1;
		if (null != conn) {
			PreparedStatement ps = null;
			try {
				conn.setAutoCommit(false);
				String sql = "INSERT into videocoll(video_id,user_id,coll_time)VALUES(?,?,?)";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, videoId);
				ps.setInt(2, userId);
				ps.setTimestamp(3, colTime);
				state = ps.executeUpdate();
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			close(conn, ps, null);
		}
		return state;
	}

	@Override
	public int deleteVideoColl(Integer collId) {
		Connection conn = getConn();
		int state = -1;
		if (conn != null) {
			Statement stmt = null;
			String sql = "delete from videocoll where coll_id=" + collId;
			try {
				stmt = conn.createStatement();
				state = stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			close(conn, stmt, null);
		}
		return state;
	}

	@Override
	public List<VideoColl> queryVideoColl(Integer userId) {
		List<VideoColl> collList = null;
		Connection conn = getConn();
		if (null != conn) {
			Statement stmt = null;
			ResultSet rs = null;
			String sql = "select coll_id,video_id,coll_time from videocoll where user_id=" + userId
					+ " order by colTime desc";
			try {
				stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.TYPE_FORWARD_ONLY);
				rs = stmt.executeQuery(sql);
				collList = new ArrayList<VideoColl>();
				while (rs.next()) {
					VideoColl coll = new VideoColl();
					coll.setCollId(rs.getInt("coll_id"));
					coll.setVideoId(rs.getInt("video_id"));
					coll.setCollTime(rs.getTimestamp("coll_time"));
					collList.add(coll);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				collList = null;
			} catch (Exception e) {
				e.printStackTrace();
				collList = null;
			}
			close(conn, stmt, rs);
		}
		return collList;
	}

	@Override
	public int checkVideoColl(Integer videoId, Integer userId) {
		Connection conn = getConn();
		int state = -1;
		if (null != conn) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			String sql = "select count(*) from videocoll where video_id=? and user_id=?";
			try {
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setInt(1, videoId);
				ps.setInt(2, userId);
				rs = ps.executeQuery();
				rs.next();
				state = rs.getInt(1);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			close(conn, ps, rs);
		}
		return state;
	}

	@Override
	public Video getVideo(Integer videoId) {
		Connection conn = getConn();
		Video video = null;
		if (null != conn) {
			ResultSet rs = null;
			Statement stmt = null;
			String sql = "select user_id, video_id, name,src,pub_time,video_desc,pic,play_number,down_number,down_points,size,mark from video where video_id="
					+ videoId;
			try {
				stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = stmt.executeQuery(sql);
				video = new Video();
				if (rs.next()) {
					video.setUserId(rs.getInt("user_id"));
					video.setVideoId(rs.getInt("video_id"));
					video.setName(rs.getString("name"));
					video.setSrc(rs.getString("src"));
					video.setPubTime(rs.getTimestamp("pub_time"));
					video.setVideoDesc(rs.getString("video_desc"));
					video.setPic(rs.getString("pic"));
					video.setPlayNumber(rs.getInt("play_number"));
					video.setDownNumber(rs.getInt("down_number"));
					video.setDownPoints(rs.getInt("down_points"));
					video.setSize(rs.getInt("size"));
					video.setMark(rs.getString("mark"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
				video = null;
			} catch (Exception e) {
				e.printStackTrace();
				video = null;
			}
			close(conn, stmt, rs);
		}
		return video;
	}

	@Override
	/**
	 * 关键字查询
	 */
	public List<Video> queryVideos(String keyword) {
		Connection conn = getConn();
		List<Video> videos = null;
		if (null != conn) {
			String sql = "select user_id, video_id, name,src,pub_time,video_desc,pic,play_number,down_number,down_points,size,mark from video where mark like ?";
			PreparedStatement ps = null;
			ResultSet rs = null;
			videos = new ArrayList<Video>();
			try {
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, "%" + keyword + "%");
				rs = ps.executeQuery();
				while (rs.next()) {
					Video video = new Video();
					video.setUserId(rs.getInt("user_id"));
					video.setVideoId(rs.getInt("video_id"));
					video.setName(rs.getString("name"));
					video.setSrc(rs.getString("src"));
					video.setPubTime(rs.getTimestamp("pub_time"));
					video.setVideoDesc(rs.getString("video_desc"));
					video.setPic(rs.getString("pic"));
					video.setPlayNumber(rs.getInt("play_number"));
					video.setDownNumber(rs.getInt("down_number"));
					video.setDownPoints(rs.getInt("down_points"));
					video.setSize(rs.getInt("size"));
					video.setMark(rs.getString("mark"));
					videos.add(video);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			close(conn, ps, rs);
		}
		return videos;
	}

	/**
	 * 一级栏目查询
	 */
	@Override
	public List<Video> queryVideos(Integer parentId) {
		Connection conn = getConn();
		List<Video> videos = null;
		if (null != conn) {
			String sql = "select user_id, video_id, name,src,pub_time,video_desc,pic,play_number,down_number,down_points,size,mark from video where parent_id=?";
			PreparedStatement ps = null;
			ResultSet rs = null;
			videos = new ArrayList<Video>();
			try {
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setInt(1, parentId);
				rs = ps.executeQuery();
				while (rs.next()) {
					Video video = new Video();
					video.setUserId(rs.getInt("user_id"));
					video.setVideoId(rs.getInt("video_id"));
					video.setName(rs.getString("name"));
					video.setSrc(rs.getString("src"));
					video.setPubTime(rs.getTimestamp("pub_time"));
					video.setVideoDesc(rs.getString("video_desc"));
					video.setPic(rs.getString("pic"));
					video.setPlayNumber(rs.getInt("play_number"));
					video.setDownNumber(rs.getInt("down_number"));
					video.setDownPoints(rs.getInt("down_points"));
					video.setSize(rs.getInt("size"));
					video.setMark(rs.getString("mark"));
					videos.add(video);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			close(conn, ps, rs);
		}
		return videos;
	}

	/**
	 * 一级栏目下二级栏目查询
	 */
	@Override
	public List<Video> queryVideos(Integer parentId, Integer subId) {
		Connection conn = getConn();
		List<Video> videos = null;
		if (null != conn) {
			String sql = "select user_id, video_id, name,src,pub_time,video_desc,pic,play_number,down_number,down_points,size,mark from video where parent_id =? and   sub_id=?";
			PreparedStatement ps = null;
			ResultSet rs = null;
			videos = new ArrayList<Video>();
			try {
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setInt(1, parentId);
				ps.setInt(2, subId);
				rs = ps.executeQuery();
				while (rs.next()) {
					Video video = new Video();
					video.setUserId(rs.getInt("user_id"));
					video.setVideoId(rs.getInt("video_id"));
					video.setName(rs.getString("name"));
					video.setSrc(rs.getString("src"));
					video.setPubTime(rs.getTimestamp("pub_time"));
					video.setVideoDesc(rs.getString("video_desc"));
					video.setPic(rs.getString("pic"));
					video.setPlayNumber(rs.getInt("play_number"));
					video.setDownNumber(rs.getInt("down_number"));
					video.setDownPoints(rs.getInt("down_points"));
					video.setSize(rs.getInt("size"));
					video.setMark(rs.getString("mark"));
					videos.add(video);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			close(conn, ps, rs);
		}
		return videos;
	}

	@Override
	public Map<Integer, Integer> queryVideoGroups() {
		Connection conn = getConn();
		Map<Integer, Integer> groupMap = null;
		if (null != conn) {
			Statement s = null;
			ResultSet rs = null;
			String sql = "select parent_id,count(*)as counts from Video group by parent_id";
			try {
				s = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = s.executeQuery(sql);
				groupMap = new HashMap<Integer, Integer>();
				while (rs.next()) {
					Integer parentId = rs.getInt("parent_id");
					Integer counts = rs.getInt("counts");
					groupMap.put(parentId, counts);
				}
			} catch (SQLException e) {
				groupMap = null;
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				groupMap = null;
			}
			close(conn, s, rs);
		}
		return groupMap;
	}

	@Override
	public Map<Integer, Integer> queryVideoGroups(Integer parentId) {
		Connection conn = getConn();
		Map<Integer, Integer> groupMap = null;
		if (null != conn) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			String sql = "select sub_id,count(*)as counts from Video where parent_id=? group by sub_id";
			try {
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setInt(1, parentId);
				rs = ps.executeQuery();
				groupMap = new HashMap<Integer, Integer>();
				while (rs.next()) {
					Integer subId = rs.getInt("sub_id");
					Integer counts = rs.getInt("counts");
					groupMap.put(subId, counts);
				}
			} catch (SQLException e) {
				groupMap = null;
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				groupMap = null;
			}
			close(conn, ps, rs);
		}
		return groupMap;
	}

	@Override
	public List<Video> queryUserVideos(Integer userId) {
		Connection conn = getConn();
		List<Video> videos = null;
		if (null != conn) {
			String sql = "select video_id, name,src,pub_time,video_desc,pic,play_number,down_number,down_points,size,mark from video where user_id=?";
			PreparedStatement ps = null;
			ResultSet rs = null;
			videos = new ArrayList<Video>();
			try {
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setInt(1, userId);
				rs = ps.executeQuery();
				while (rs.next()) {
					Video video = new Video();
					video.setVideoId(rs.getInt("video_id"));
					video.setName(rs.getString("name"));
					video.setSrc(rs.getString("src"));
					video.setPubTime(rs.getTimestamp("pub_time"));
					video.setVideoDesc(rs.getString("video_desc"));
					video.setPic(rs.getString("pic"));
					video.setPlayNumber(rs.getInt("play_number"));
					video.setDownNumber(rs.getInt("down_number"));
					video.setDownPoints(rs.getInt("down_points"));
					video.setSize(rs.getInt("size"));
					video.setMark(rs.getString("mark"));
					videos.add(video);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			close(conn, ps, rs);
		}
		return videos;
	}

	@Override
	public int updatePlayNumber(Integer videoId) {
		Connection conn = getConn();
		int state = -1;
		if (null != conn) {
			PreparedStatement ps = null;
			String sql = "update video set play_number=play_number+1 where video_id=?";
			try {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, videoId);
				state = ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			close(conn, ps, null);
		}
		return state;
	}

	@Override
	public int updateDownNumber(Integer videoId) {
		Connection conn = getConn();
		int state = -1;
		if (null != conn) {
			PreparedStatement ps = null;
			String sql = "update video set play_number=down_number+1 where video_id=?";
			try {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, videoId);
				state = ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			close(conn, ps, null);
		}
		return state;
	}

	@Override
	public int getCommCount(Integer videoId) {
		int state = -1;
		Connection conn = getConn();
		if (null != conn) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				String sql = "select count(*) from VideoComm where video_id=?";
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setInt(1, videoId);
				rs = ps.executeQuery();
				rs.next();
				state = rs.getInt(1);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			close(conn, ps, rs);
		}

		return state;
	}

	@Override
	public int getCollCount(Integer videoId) {
		int state = -1;
		Connection conn = getConn();
		if (null != conn) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				String sql = "select count(*) from VideoColl where video_id=?";
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setInt(1, videoId);
				rs = ps.executeQuery();
				rs.next();
				state = rs.getInt(1);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			close(conn, ps, rs);
		}
		return state;
	}

	@Override
	public int getSuggesCount(Integer videoId, int type) {
		int state = -1;
		Connection conn = getConn();
		if (null != conn) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				String sql = "select count(*) from UserSugges where video_id=? and type=?";
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setInt(1, videoId);
				ps.setInt(2, type);
				rs = ps.executeQuery();
				rs.next();
				state = rs.getInt(1);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			close(conn, ps, rs);
		}
		return state;
	}

	@Override
	public int getSuggesState(Integer userId, Integer videoId, int type) {
		int state = -1;
		Connection conn = getConn();
		if (null != conn) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				String sql = "select count(*) from UserSugges where  user_id=? and video_id=? and type=?";
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setInt(1, userId);
				ps.setInt(2, videoId);
				ps.setInt(3, type);
				rs = ps.executeQuery();
				rs.next();
				state = rs.getInt(1);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			close(conn, ps, rs);
		}
		return state;
	}

	@Override
	public int addVideoSugges(Integer userId, Integer videoId, int type) {
		int state = -1;
		Connection conn = getConn();
		if (null != conn) {
			PreparedStatement ps = null;
			try {
				String sql = "insert into UserSugges (video_id,user_id,type,opertime)values(?,?,?,now())";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, videoId);
				ps.setInt(2, userId);
				ps.setInt(3, type);
				state = ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			close(conn, ps, null);
		}
		return state;
	}

	@Override
	public VideoParentType getParentType(Integer parentId) {
		VideoParentType videoType = null;
		Connection conn = getConn();
		if (null != conn) {
			Statement s = null;
			ResultSet rs = null;
			try {
				String sql = "select parent_id,name from videoparenttype where parent_id=" + parentId;
				s = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = s.executeQuery(sql);
				videoType = new VideoParentType();
				if (rs.next()) {
					videoType.setName(rs.getString("name"));
					videoType.setParentId(rs.getInt("parent_id"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			close(conn, s, rs);
		}
		return videoType;
	}

	@Override
	public VideoSubType getSubType(Integer subId) {
		VideoSubType videoType = null;
		Connection conn = getConn();
		if (null != conn) {
			Statement s = null;
			ResultSet rs = null;
			try {
				String sql = "select sub_id,parent_id,name from videosubtype  where sub_id=" + subId;
				s = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = s.executeQuery(sql);
				videoType = new VideoSubType();
				if (rs.next()) {
					videoType.setName(rs.getString("name"));
					videoType.setSubId(rs.getInt("sub_id"));
					videoType.setParentId(rs.getInt("parent_id"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			close(conn, s, rs);
		}
		return videoType;
	}

	@Override
	public int countVideos(Integer parentId) {
		int state = -1;
		Connection conn = getConn();
		if (null != conn) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				String sql = "select count(*) from  Video where parent_id=?";
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setInt(1, parentId);
				rs = ps.executeQuery();
				rs.next();
				state = rs.getInt(1);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			close(conn, ps, rs);
		}
		return state;
	}

	@Override
	public int countVideos(Integer parentId, Integer subId) {
		int state = -1;
		Connection conn = getConn();
		if (null != conn) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				String sql = "select count(*) from  Video where parent_id=? and sub_id=?";
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setInt(1, parentId);
				ps.setInt(2, subId);
				rs = ps.executeQuery();
				rs.next();
				state = rs.getInt(1);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			close(conn, ps, rs);
		}
		return state;
	}

}
