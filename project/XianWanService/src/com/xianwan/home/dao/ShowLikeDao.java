package com.xianwan.home.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.xianwan.util.DBUtil;

public class ShowLikeDao {
	
	
	public void modifyLikeCount(String commodityId,String operate) {
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql = "update commodity set showLike = showLike + 1 where id = " + commodityId;
		String sql1 = "update commodity set showLike = showLike - 1 where id = " + commodityId;
		
		conn = DBUtil.getConn();
		try {
			if(operate.equals("add")) {
				pstm = conn.prepareStatement(sql);
				pstm.executeUpdate();
			}else {
				pstm = conn.prepareStatement(sql1);
				pstm.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addShowLike(String commodityId,String userAccount) {
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql = "insert into showlike values(?,?)";
		
		conn = DBUtil.getConn();
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, commodityId);
			pstm.setString(2, userAccount);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void cancelShowLike(String commodityId,String userAccount) {
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql = "delete from showlike where commodityId = '" + commodityId + "' and userAccount = '" + userAccount + "'";
		
		conn = DBUtil.getConn();
		
		try {
			System.out.println("取消了！！！");
			pstm = conn.prepareStatement(sql);
			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean adjustIfExistShowLike(String commodityId,String userAccount) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "select * from showlike where commodityId = '" + commodityId + "' and userAccount = '" + userAccount + "'";
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
}
