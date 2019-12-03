package com.xianwan.login.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import com.xianwan.util.DBUtil;
import com.xianwan.util.SnowflakeIdWorker;

public class RegisterDao {

	public String insert(String userName, String userAccount, String userPassword) throws SQLException {
		Connection conn = null;
		PreparedStatement psmt = null;
		String sql = "insert into user values(?,?,?,?)";
		long id = new SnowflakeIdWorker(0L,0L).nextId();
		conn = DBUtil.getConn();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setLong(1, id);
			psmt.setString(2, userName);
			psmt.setString(3, userPassword);
			psmt.setString(4, userAccount);
			psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
		return "true";
	}

}
