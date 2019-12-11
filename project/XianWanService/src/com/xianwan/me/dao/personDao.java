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
		String sql = "insert into userdetail values (?,?,?,?,?,?,?,?)";
		
		conn = DBUtil.getConn();
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, userAccount);
			pstm.setString(2, userName);
			pstm.setString(3, userSex);
			pstm.setString(4, userBirth);
			pstm.setString(5, userLocaton);
			pstm.setString(6,userJianjie);
			pstm.setString(7, userJob);
			pstm.setString(8, userJobName);
			
			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("插入完成");
	}
		
		
	}


