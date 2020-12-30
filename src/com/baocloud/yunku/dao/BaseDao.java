package com.baocloud.yunku.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public abstract class BaseDao {

	/**
	 * 获取数据库连接
	 * 
	 * @return
	 */
	protected Connection getConn() {
		Connection conn = null;
		try {
			ResourceBundle rb = ResourceBundle.getBundle("com.baocloud.yunku.dao.config.db");
			String driverName = rb.getString("mysql.driver");
			String user = rb.getString("mysql.user");
			String password = rb.getString("mysql.password");
			String url = rb.getString("mysql.url");
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 关闭资源
	 */
	protected void close(Connection conn, Statement stmt, ResultSet rs) {
		if (null != rs) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (null != stmt) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (null != conn) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 回滚事务
	 * 
	 * @param conn
	 */
	protected void rollback(Connection conn) {
		if (null != conn) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
