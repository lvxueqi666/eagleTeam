package com.xianwan.me.dao;

import java.sql.Connection;
import java.sql.Date;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.xianwan.util.DBUtil;



public class FabuPicDao {
	public void addPicToSQL(String id,String userId,String address,Date uploadTime,int frequency,String firstUrl) {
		Connection conn = null;
		PreparedStatement pstm = null;
	
		String sql = "insert fabupic values(?,?,?,?,?,?)";
		
		try {
			conn = DBUtil.getConn();
			pstm = conn.prepareStatement(sql);
			
			pstm.setString(2, id);
		
			pstm.setString(1, id);
			pstm.setString(2, userId);
			pstm.setString(3, address);
			pstm.setDate(4, uploadTime);
			pstm.setInt(5, frequency);
			pstm.setString(6, firstUrl);
			pstm.executeUpdate();
			
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		
	}
	
	/*
	public String queryPicGetFirst(String userId) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "select address from fabupic where userId = ?";
		int count = queryRrequency(userId);
		try {
			conn = DBUtil.getConn();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, userId);
			rs = pstm.executeQuery();
			if(rs.next()) {
				if(rs.getString(1).contains("http://49.233.142.163:8080/images/0FD"+userId+"N"+count+".jpg")) {
					return rs.getString(1);
				}	
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return "";
	}
	*/

	public String getFakeName(String userId) {
		return "http://49.233.142.163:8080/images/0FD"+userId+"N"+queryRrequency(userId)+".jpg";
	}
	
	public int queryRrequency(String userId) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int m = 0;
		String sql = "select frequency from fabupic where userAccount = ?";
		try {
			conn = DBUtil.getConn();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, userId);
			rs = pstm.executeQuery();
			while(rs.next()) {
				int n = rs.getInt(1);
				if(m<=n) {
					m = n;
				}
				
			}
			conn.close();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		System.out.println("次数："+(++m));
		return m++;
		
	}
	

}
