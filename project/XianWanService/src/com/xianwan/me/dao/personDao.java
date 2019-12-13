package com.xianwan.me.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.xianwan.me.entity.UserDetail;
import com.xianwan.util.DBUtil;

public class personDao {

	public void insertPerson(String userAccount, String userName, String userSex, String userBirth, String userLocaton,
			String userJianjie, String userJob, String userJobName) {
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql="REPLACE INTO userdetail (userAccount,userName,userSex,userBirth,userLocation,userJianjie,userJob,userJobName) VALUES(?,?,?,?,?,?,?,?)"; 
		
		 //String sql = "update userdetail set userSex= ? , userBirth =? , userLocation= ? , userJianjie= ? , userJob = ? , userJobName = ? where userAccount =?";
		
		conn = DBUtil.getConn();
		try {
			pstm = conn.prepareStatement(sql);
			
			pstm.setString(2, userName);
			pstm.setString(3, userSex);
			pstm.setString(4, userBirth);
			pstm.setString(5, userLocaton);
			pstm.setString(6,userJianjie);
			pstm.setString(7, userJob);
			pstm.setString(8, userJobName);
			pstm.setString(1, userAccount);
			System.out.println("gengxin"+userJobName);
			pstm.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("更新完成");
	}
		
		
	}


