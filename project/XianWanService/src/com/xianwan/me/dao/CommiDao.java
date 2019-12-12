package com.xianwan.me.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;



import com.xianwan.util.DBUtil;
import com.xianwan.util.SnowflakeIdWorker;

public class CommiDao {
	public void insertCommidity(String address,String introduce,String price,String attr,String userAccount,String userName) {
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql = "insert into commodity values (?,?,?,?,?,?,?,?,?,?)";
		String id = new SnowflakeIdWorker(0L,0L).nextId() + "";
		//int newId = Integer.parseInt(id);
		conn = DBUtil.getConn();
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, id);
			pstm.setString(2, address);
			pstm.setString(3, introduce);
			pstm.setString(4, price);
			pstm.setString(5, null);
			pstm.setString(6,userAccount);
			pstm.setString(7, null);
			pstm.setString(8, userName);
			pstm.setString(9, attr);
			pstm.setString(10, null);
			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("插入完成");
	}

	
	/*
	public String findAddress(String userAccount) throws SQLException {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "select * from fabupic where userId = ?";
		conn = DBUtil.getConn();
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, userAccount);
			rs = pstm.executeQuery();
			if(rs.next()) {
				return rs.getString(3);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
		return null;
	}
	*/
	
}