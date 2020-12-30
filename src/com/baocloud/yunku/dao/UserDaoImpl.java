package com.baocloud.yunku.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.baocloud.yunku.pojo.UserInte;
import com.baocloud.yunku.pojo.User;

public class UserDaoImpl extends BaseDao implements UserDao {

	@Override
	public int userReg(String email, String password) {
		Connection conn = getConn();
		int state = -1;
		if (null != conn) {
			PreparedStatement ps = null;
			String sql = "insert into User(email,password) values(?,?)";
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, email);
				ps.setString(2, password);
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
	public int checkEmail(String email) {
		Connection conn = getConn();
		int state = -1;
		if (null != conn) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			String sql = "select count(*) from User where LOWER(email)=LOWER(?)";
			try {
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, email);
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
	public int checkMobile(String mobile, Integer userId) {
		Connection conn = getConn();
		int state = -1;
		if (null != conn) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			String sql = "select count(*) from User where  mobile=? and user_id<>?";
			try {
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, mobile);
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
	public int updateUser(Integer userId, int s) {
		Connection conn = getConn();
		int state = -1;
		if (null != conn) {
			PreparedStatement ps = null;
			String sql = "update User set login_state=? where user_id=?";
			try {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, s);
				ps.setInt(2, userId);
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
	public User getUser(String email, String password) {
		Connection conn = getConn();
		User user = null;
		if (null != conn) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			String sql = "select user_id,username,email,login_state,pic,mobile from User where (email=? or username=?) and password=?";
			try {
				user = new User();
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, email);
				ps.setString(2, email);
				ps.setString(3, password);
				rs = ps.executeQuery();
				if (rs.next()) {
					user.setUserId(rs.getInt("user_id"));
					user.setUsername(rs.getString("username"));
					user.setEmail(rs.getString("email"));
					user.setLoginState(rs.getString("login_state"));
					user.setPic(rs.getString("pic"));
					user.setMobile(rs.getString("mobile"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
				user = null;
			} catch (Exception e) {
				e.printStackTrace();
				user = null;
			}
			close(conn, ps, rs);
		}
		return user;
	}

	@Override
	public User getUser(Integer userId) {
		Connection conn = getConn();
		User user = null;
		if (null != conn) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			String sql = "select user_id,username,email,pic,mobile from User where user_id=?";
			try {
				user = new User();
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setInt(1, userId);
				rs = ps.executeQuery();
				if (rs.next()) {
					user.setUserId(rs.getInt("user_id"));
					user.setUsername(rs.getString("username"));
					user.setEmail(rs.getString("email"));
					user.setPic(rs.getString("pic"));
					user.setMobile(rs.getString("mobile"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
				user = null;
			} catch (Exception e) {
				e.printStackTrace();
				user = null;
			}
			close(conn, ps, rs);
		}
		return user;
	}

	@Override
	public Integer getUserId(String email) {
		Connection conn = getConn();
		Integer userId = null;
		if (null != conn) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			String sql = "select user_id from User where email=?";
			try {
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, email);
				rs = ps.executeQuery();
				if (rs.next()) {
					userId = rs.getInt("user_id");
				} else {
					userId = -1;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			close(conn, ps, rs);
		}
		return userId;
	}

	@Override
	public int getPwdResetSate(Integer userId) {
		Connection conn = getConn();
		int state = -1;
		if (null != conn) {
			Statement stmt = null;
			ResultSet rs = null;
			try {
				String sql = "select state from User where user_id=" + userId;
				stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = stmt.executeQuery(sql);
				if (rs.next()) {
					state = rs.getInt(1);
				} else {
					state = -2;// 该用户已删除或参数错误
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			close(conn, stmt, rs);
		}
		return state;
	}

	@Override
	public int updatePwd(Integer userId, String newPwd) {
		Connection conn = getConn();
		int state = -1;
		if (null != conn) {
			PreparedStatement ps = null;
			String sql = "update User set password=? where user_id=?";
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, newPwd);
				ps.setInt(2, userId);
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
	public int updateResetSate(Integer userId, int pState) {
		Connection conn = getConn();
		int state = -1;
		if (null != conn) {
			PreparedStatement ps = null;
			String sql = "update User set state=? where user_id=?";
			try {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, pState);
				ps.setInt(2, userId);
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
	public int lookSign(Integer userId, int yyyyMMdd) {
		int state = -1;
		Connection conn = getConn();
		if (null != conn) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				String sql = "select count(*) from usersign where user_id=? and date_format(sign_date,'%Y%m%d')=?";
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setInt(1, userId);
				ps.setInt(2, yyyyMMdd);
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
	public int getSignDays(Integer userId, int yyyyMMdd) {
		int days = -1;
		Connection conn = getConn();
		if (null != conn) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				String sql = "select days from usersign where user_id=? and date_format(sign_date,'%Y%m%d')=?";
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setInt(1, userId);
				ps.setInt(2, yyyyMMdd);
				rs = ps.executeQuery();
				if (rs.next()) {
					days = rs.getInt(1);
				} else {
					days = 0;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			close(conn, ps, rs);
		}
		return days;
	}

	@Override
	public int addSign(Integer userId, int days) {
		int state = -1;
		Connection conn = getConn();
		if (null != conn) {
			PreparedStatement ps = null;
			try {
				String sql = "insert into  usersign(user_id,sign_date,days) values(?,now(),?);";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, userId);
				ps.setInt(2, days);
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
	public List<UserInte> queryInte(Integer userId) {
		List<UserInte> inteList = null;
		Connection conn = getConn();
		if (null != conn) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			String sql = "select inte_Id,user_id,inte,inte_desc,time,bal from userinte where user_Id=? order by time desc";
			try {
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setInt(1, userId);
				rs = ps.executeQuery();
				inteList = new ArrayList<UserInte>();
				while (rs.next()) {
					UserInte inte = new UserInte();
					inte.setInte(rs.getInt("inte"));
					inte.setInteDesc(rs.getString("inte_desc"));
					inte.setTime(rs.getTimestamp("time"));
					inte.setBal(rs.getInt("bal"));
					inteList.add(inte);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				inteList = null;
			} catch (Exception e) {
				e.printStackTrace();
				inteList = null;
			}
			close(conn, ps, rs);
		}
		return inteList;
	}

	@Override
	public int isNameExist(String userName) {
		Connection conn = getConn();
		int state = -1;
		if (null != conn) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			String sql = "select count(*) from user where LOWER(userName)=LOWER(?)";
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, userName);
				rs = ps.executeQuery();
				rs.next();
				state = rs.getInt(1);
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
	public int setName(String userName, Integer userId) {
		Connection conn = getConn();
		int state = -1;
		if (null != conn) {
			PreparedStatement ps = null;
			String sql = "update user set userName=? where user_Id=?";
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, userName);
				ps.setInt(2, userId);
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
	public int setMobile(String mobile, Integer userId) {
		Connection conn = getConn();
		int state = -1;
		if (null != conn) {
			PreparedStatement ps = null;
			String sql = "update user set mobile=? where user_Id=?";
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, mobile);
				ps.setInt(2, userId);
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
	public int checkPwd(Integer userId, String password) {
		Connection conn = getConn();
		int state = -1;
		if (null != conn) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			String sql = "select count(*) from User where password=? and user_id=?";
			try {
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, password);
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
	public int updatePic(Integer userId, String pic) {
		Connection conn = getConn();
		int state = -1;
		if (null != conn) {
			PreparedStatement ps = null;
			String sql = "update user set pic=? where user_Id=?";
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, pic);
				ps.setInt(2, userId);
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
