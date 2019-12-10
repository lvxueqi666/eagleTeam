package com.xianwan.me.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.xianwan.home.entity.Commodity;
import com.xianwan.me.entity.UserDetail;
import com.xianwan.util.DBUtil;

public class SearchDao {
	public List<UserDetail> queryUserDetail(String detail) {
		List<UserDetail> details = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "select * from userdetail where userAccount = ?";
		conn = DBUtil.getConn();
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, detail);
			rs = pstm.executeQuery();
			while(rs.next()) {
				UserDetail userDetail=new UserDetail();
				userDetail.setUserAccount(rs.getString(1));
				userDetail.setUserName(rs.getString(2));
				userDetail.setUserSex(rs.getString(3));
				userDetail.setUserBirth(rs.getString(4));
				userDetail.setUserLocation(rs.getString(5));
				userDetail.setUserJianjie(rs.getString(6));
				userDetail.setUserJob(rs.getString(7));
				userDetail.setUserJobName(rs.getString(8));
				
				details.add(userDetail);
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return details;
	}
}
