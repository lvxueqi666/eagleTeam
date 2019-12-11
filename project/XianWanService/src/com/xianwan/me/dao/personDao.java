package com.xianwan.me.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.xianwan.util.DBUtil;
import com.xianwan.util.SnowflakeIdWorker;

public class personDao {

	public void insertPerson(String userAccount, String userName, String userSex, String userBirth, String userLocaton,
			String userJianjie, String userJob, String userJobName) {
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql = "update userdetail set userSex= ? , userBirth =? , userLocation= ? , userJianjie= ? , userJob = ? , userJobName = ? where userAccount =?";
		
		conn = DBUtil.getConn();
		try {
			pstm = conn.prepareStatement(sql);
			
			
			pstm.setString(1, userSex);
			pstm.setString(2, userBirth);
			pstm.setString(3, userLocaton);
			pstm.setString(4,userJianjie);
			pstm.setString(5, userJob);
			pstm.setString(6, userJobName);
			pstm.setString(7, userAccount);
			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("更新完成");
	}
		
		
	}


