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
		String sql = "select * from user where userAccount = ? and userPassword = ?";
		conn = DBUtil.getConn();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, userAccount);
			psmt.setString(2, userPassword);
			rs = psmt.executeQuery();
			if(rs.next()) {
				return rs.getString(3);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
		return "";
	}

	public void insertAccountAndName(String userAccount, String result) throws SQLException {
		Connection conn = null;
		PreparedStatement psmt = null;
		String sql = "insert into userdetail values(?,?,?,?,?,?,?,?)";
		conn = DBUtil.getConn();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, userAccount);
			psmt.setString(2, result);
			psmt.setString(3, "");
			psmt.setString(4, "");
			psmt.setString(5, "");
			psmt.setString(6, "");
			psmt.setString(7, "");
			psmt.setString(8, "");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
	}

}
