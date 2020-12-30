package com.baocloud.yunku.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.baocloud.yunku.pojo.CommReply;
import com.baocloud.yunku.pojo.VideoComm;
import java.sql.PreparedStatement;

public class CommentDaoImpl extends BaseDao implements CommentDao {

	public CommentDaoImpl() {
		super();
	}

	@Override
	public List<VideoComm> queryVideoComms(Integer videoId) {
		Connection conn = getConn();
		List<VideoComm> videoComms = null;
		if (null != conn) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			String sql = "select com_id,comcon,comtime,user_id from videocomm  where video_id=? order by comtime desc";
			try {
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setInt(1, videoId);
				rs = ps.executeQuery();
				videoComms = new ArrayList<VideoComm>();
				while (rs.next()) {
					VideoComm videoComm = new VideoComm();
					videoComm.setComId(rs.getInt("com_id"));
					videoComm.setComTime(rs.getTimestamp("comtime"));
					videoComm.setUserId(rs.getInt("user_id"));
					videoComm.setComCon(rs.getString("comcon"));
					videoComms.add(videoComm);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				videoComms = null;
			} catch (Exception e) {
				e.printStackTrace();
				videoComms = null;
			}
			close(conn, ps, rs);

		}
		return videoComms;
	}

	@Override
	public List<CommReply> queryCommReplys(Integer comId) {
		Connection conn = getConn();
		List<CommReply> commReplys = null;
		if (null != conn) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			String sql = "select rep_id,repcon,user_id,reptime from CommReply where com_id=? order by  reptime desc";
			try {
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setInt(1, comId);
				rs = ps.executeQuery();
				commReplys = new ArrayList<CommReply>();
				while (rs.next()) {
					CommReply commReply = new CommReply();
					commReply.setReplyId(rs.getInt("rep_id"));
					commReply.setRepcon(rs.getString("repcon"));
					commReply.setReptime(rs.getTimestamp("reptime"));
					commReply.setUserId(rs.getInt("user_id"));
					commReplys.add(commReply);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				commReplys = null;
			} catch (Exception e) {
				e.printStackTrace();
				commReplys = null;
			}
			close(conn, ps, rs);
		}
		return commReplys;
	}

	@Override
	public int addVideoComm(String comcon, Integer userId, Integer videoId) {
		Connection conn = getConn();
		int state = -1;
		if (null != conn) {
			PreparedStatement pStmt = null;
			String sql = "insert into videocomm (comcon,user_Id,video_id) values(?,?,?)";
			try {
				pStmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				pStmt.setString(1, comcon);
				pStmt.setInt(2, userId);
				pStmt.setInt(3, videoId);
				state = pStmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			close(conn, pStmt, null);
		}
		return state;
	}

	@Override
	public int addCommReply(String repcon, Integer userId, Integer comId) {
		Connection conn = getConn();
		int state = -1;
		if (null != conn) {
			PreparedStatement pStmt = null;
			String sql = "insert into CommReply (repcon,user_Id,com_id) values(?,?,?)";
			try {
				pStmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				pStmt.setString(1, repcon);
				pStmt.setInt(2, userId);
				pStmt.setInt(3, comId);
				state = pStmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			close(conn, pStmt, null);
		}
		return state;
	}

	@Override
	public int getReplyCount(Integer comId) {
		Connection conn = getConn();
		int state = -1;
		if (null != conn) {
			PreparedStatement pStmt = null;
			ResultSet rs = null;
			String sql = "select count(*) from CommReply where com_id=? ";
			try {
				pStmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				pStmt.setInt(1, comId);
				rs = pStmt.executeQuery();
				rs.next();
				state = rs.getInt(1);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			close(conn, pStmt, rs);
		}
		return state;
	}

	@Override
	public int getCommState(Integer comId, Integer userId, int type) {
		int state = -1;
		Connection conn = getConn();
		if (null != conn) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				String sql = "select count(*) from UserSugges where  user_id=? and com_id=? and type=?";
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setInt(1, userId);
				ps.setInt(2, comId);
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
	public int getReplyState(Integer repId, Integer userId, int type) {
		int state = -1;
		Connection conn = getConn();
		if (null != conn) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				String sql = "select count(*) from UserSugges where  user_id=? and rep_id=? and type=?";
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setInt(1, userId);
				ps.setInt(2, repId);
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
	public int countCommSugges(Integer comId, int type) {
		int state = -1;
		Connection conn = getConn();
		if (null != conn) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				String sql = "select count(*) from UserSugges where  com_id=? and type=?";
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setInt(1, comId);
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
	public int countReplySugges(Integer repId, int type) {
		int state = -1;
		Connection conn = getConn();
		if (null != conn) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				String sql = "select count(*) from UserSugges where  rep_id=? and type=?";
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setInt(1, repId);
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
	public int addCommSugges(Integer userId, Integer comId, int type) {
		int state = -1;
		Connection conn = getConn();
		if (null != conn) {
			PreparedStatement ps = null;
			try {
				String sql = "insert into UserSugges (com_id,user_id,type,opertime)values(?,?,?,now())";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, comId);
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
	public int addReplySugges(Integer userId, Integer repId, int type) {
		int state = -1;
		Connection conn = getConn();
		if (null != conn) {
			PreparedStatement ps = null;
			try {
				String sql = "insert into UserSugges (rep_id,user_id,type,opertime)values(?,?,?,now())";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, repId);
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

}
