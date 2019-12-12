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
	
	public List<Commodity> queryCommodity(String type) {
		List<Commodity> coms = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		String sql = "select * from commodity as t1 join (select round(rand() * (select max(id) from commodity)) as id) as t2 where t1.id >= t2.id and attr = '" + type + "'order by t1.id limit 8";
		//String sql = "select * from commodity where attr = '" + type + "' and id >= (select floor(RAND() * (select MAX(id) from commodity))) order by id limit 10";
		String sql2 = "select address from headpic where userAccount = ?";
		conn = DBUtil.getConn();
		try {
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()) {
				pstm = conn.prepareStatement(sql2);
				pstm.setString(1, rs.getString(6));
				rs2 = pstm.executeQuery();
				Commodity com = new Commodity();
				com.setId(rs.getString(1));
				com.setImage(rs.getString(2));
				com.setIntroduce(rs.getString(3));
				com.setPrice(rs.getString(4));
				com.setTag(rs.getString(5));
				com.setUserAccount(rs.getString(6));
				if(rs2.next()) {
					com.setIcon(rs2.getString("address"));
				}
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
	
	public void insertCommodity(String image,String introduce,String price,String tag,String userAccount) {
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
			pstm.setString(6, userAccount);
			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
}


















