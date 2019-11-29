package com.xianwan.home.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.xianwan.util.DBUtil;
import com.xianwan.util.SnowflakeIdWorker;
import com.xianwan.home.entity.Commodity;

public class HomeDao {
	
	public List<Commodity> queryCommodity(String attr) {
		List<Commodity> coms = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "select * from commodity where id >= (select floor(RAND() * (select MAX(id) from commodity))) order by id limit 10";
		conn = DBUtil.getConn();
		try {
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()) {
				Commodity com = new Commodity();
				com.setId(rs.getInt(1));
				com.setImage(rs.getString(2));
				com.setIntroduce(rs.getString(3));
				com.setPrice(rs.getString(4));
				com.setTag(rs.getString(5));
				com.setUserId(rs.getInt(6));
				com.setIcon(rs.getString(7));
				com.setUserName(rs.getString(8));
				com.setAttr(rs.getString(9));
				com.setShowLike(rs.getString(10));
				coms.add(com);
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return coms;
	}
	
	public void insertCommodity(String image,String introduce,String price,String tag,int userId) {
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql = "insert into commodity values(?,?,?,?,?,?)";
		conn = DBUtil.getConn();
		SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
		long id = idWorker.nextId();
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setLong(1, id);
			pstm.setString(2, image);
			pstm.setString(3, introduce);
			pstm.setString(4, price);
			pstm.setString(5, tag);
			pstm.setInt(6, userId);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}


















