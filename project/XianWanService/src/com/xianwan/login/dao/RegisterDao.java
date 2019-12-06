package com.xianwan.login.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.xianwan.util.DBUtil;

public class RegisterDao {
	public String insert(String userAccount, String userPassword,String userName) throws SQLException {
		Connection conn = null;
		PreparedStatement psmt = null;
		String sql = "insert into user values(?,?,?)";
		conn = DBUtil.getConn();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, userAccount);
			psmt.setString(2, userPassword);
			psmt.setString(3, userName);
			psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
		return "true";
	}

	public String accountCheck(String account) throws SQLException {
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		String sql = "select * from user where userAccount = ?";
		conn = DBUtil.getConn();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, account);
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
