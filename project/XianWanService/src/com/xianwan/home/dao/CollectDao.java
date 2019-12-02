package com.xianwan.home.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.xianwan.util.DBUtil;

public class CollectDao {
	public List<Integer> queryCommodityIdByUserId(String userId) {
		List<Integer> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "select * from collect where userId = '" + userId + "'";
		conn = DBUtil.getConn();
		
		try {
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()) {
				int id = rs.getInt(1);
				list.add(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public boolean adjustIfExistCollection(int commodityId,int userId) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "select * from collect where commodityId = '" + commodityId + "' and userId = '" + userId + "'";
		conn = DBUtil.getConn();
		try {
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
	public void addCollection(int commodityId,int userId) {
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql = "insert into collect values(?,?)";
		
		conn = DBUtil.getConn();
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, commodityId);
			pstm.setInt(2, userId);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	public void cancelCollection(int commodityId,int userId) {
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql = "delete from collect where commodityId = '" + commodityId + "' and userId = '" + userId + "'";
		
		conn = DBUtil.getConn();
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
