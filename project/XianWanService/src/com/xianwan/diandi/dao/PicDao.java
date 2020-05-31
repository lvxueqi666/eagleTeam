package com.xianwan.diandi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.xianwan.util.DBUtil;

public class PicDao {
	
	public void addUrl(String url){
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "insert";
		conn = DBUtil.getConn();
	}
}
