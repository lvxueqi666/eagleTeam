package com.xianwan.me.dao;

import java.sql.Connection;
import java.sql.Date;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.xianwan.util.DBUtil;



public class FabuPicDao {
	public void addPicToSQL(String id,String userId,String address,Date uploadTime,int frequency) {
		Connection conn = null;
		PreparedStatement pstm = null;
	
		String sql = "insert fabupic values(?,?,?,?,?)";
		
		try {
			conn = DBUtil.getConn();
			pstm = conn.prepareStatement(sql);
			
			pstm.setString(2, id);
		
			pstm.setString(1, id);
			pstm.setString(2, userId);
			pstm.setString(3, address);
			pstm.setDate(4, uploadTime);
			pstm.setInt(5, frequency);
			pstm.executeUpdate();
			
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		
	}
	
	public String queryPicGetFirst(String userId) {
		System.out.println("xxxxxxxxxxxx");
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		String sql = "select * from fabupic where userId = ?";
		int count = queryRrequency(userId);
		System.out.println("yyyyyyyyyyyyyyy"+count);
		try {
			conn = DBUtil.getConn();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, userId);
			rs = pstm.executeQuery();
			int number0 = 10;//最多上传9张图片
			if(rs.next()) {
				return rs.getString(3);
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
	}
	public int queryRrequency(String userId) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String address = null;
		int m = 0;
		String sql = "select frequency from fabupic where userId = ?";
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
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		System.out.println("次数："+(++m));
		return m++;
		
	}
	

}
