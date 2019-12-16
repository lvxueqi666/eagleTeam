package com.xianwan.me.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.xianwan.me.entity.User;
import com.xianwan.me.entity.UserDetail;
import com.xianwan.util.DBUtil;

public class userDao {
	public List<User> queryUser(String userAccount) {
		List<User> user = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstm = null;
		
		ResultSet rs = null;
		String sql = "select * from user where userAccount = ?";
		
		conn = DBUtil.getConn();
		try {
			pstm = conn.prepareStatement(sql);
		
			pstm.setString(1, userAccount);
			rs = pstm.executeQuery();
			while(rs.next()) {
				User user2=new User();
				user2.setUserAccount(rs.getString(1));
				user2.setUserName(rs.getString(3));
				user.add(user2);
			
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
		
	}
}
