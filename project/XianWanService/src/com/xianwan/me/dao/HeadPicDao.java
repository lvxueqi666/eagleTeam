package com.xianwan.me.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.xianwan.util.DBUtil;



public class HeadPicDao {
	
	public String queryHeadPic(String userAccount) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "select address from headpic where userAccount = ?";
		String address = null;
		
		try {
			conn = DBUtil.getConn();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, userAccount);
			rs = pstm.executeQuery();
			while(rs.next()) {
				address = rs.getString(1);
			}
			conn.close();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return address;
		
	}
	
	/*
	 * 每个用户仅保留一张头像，之前的头像需要被删除
	 */
	public void saveHeadPic(String id,String address,String userAccount,Date uploadTime) {
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql = "insert headpic values(?,?,?,?)";
		String sql1 = "delete from headpic where userAccount = ?";
		try {
			conn = DBUtil.getConn();
			//删除
			pstm = conn.prepareStatement(sql1);
			pstm.setString(1, userAccount);
			pstm.executeUpdate();
			
			//添加
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, id);
			pstm.setString(2, address);
			pstm.setString(3,userAccount);
			pstm.setDate(4, uploadTime);
			pstm.executeUpdate();
		
			conn.close();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
		
		

}

