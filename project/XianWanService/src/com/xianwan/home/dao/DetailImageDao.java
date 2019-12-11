package com.xianwan.home.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.xianwan.util.DBUtil;

public class DetailImageDao {
	public List<String> queryImageUrl(String userAccount,String firstUrl) {
		List<String> images = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "select imageUrl from detailimage where userAccount = ? and firstUrl = ?";
		conn = DBUtil.getConn();
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, userAccount);
			pstm.setString(2, firstUrl);
			rs = pstm.executeQuery();
			while(rs.next()) {
				String img = rs.getString("imageUrl");
				images.add(img);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(images);
		return images;
	}
}
