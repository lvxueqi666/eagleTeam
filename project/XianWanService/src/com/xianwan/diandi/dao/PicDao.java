package com.xianwan.diandi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.xianwan.util.DBUtil;

public class PicDao {
	
	public void addUrl(String url,String userId,String time) throws SQLException{
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "insert into diandi(userAccount,time,url) value(?,?,?)";
		conn = DBUtil.getConn();
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, userId);
		pstm.setString(2, time);
		pstm.setString(3, url);
		pstm.executeUpdate();
	}
}
