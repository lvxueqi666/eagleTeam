package com.xianwan.login.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.xianwan.util.DBUtil;

public class LoginDao {

	public String checkUser(String userAccount, String userPassword) throws SQLException {
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		int count = 0;
		String sql = "select * from user where userAccount = ? and userPassword = ?";
		conn = DBUtil.getConn();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, userAccount);
			psmt.setString(2, userPassword);
			rs = psmt.executeQuery();
			if(rs.next()) {
				return "true";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
		return "false";
	}

}
