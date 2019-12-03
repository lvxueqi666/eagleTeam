package com.xianwan.home.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
}
