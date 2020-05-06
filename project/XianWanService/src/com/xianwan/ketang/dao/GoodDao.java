package com.xianwan.ketang.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.xianwan.ketang.entity.GoodsEntity;
import com.xianwan.util.DBUtil;




public class GoodDao {
	public List<GoodsEntity> queryGoodbyType1(int type1) {
		List<GoodsEntity> goodsEntities = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "select * from good where type= "+type1;
		
		conn = DBUtil.getConn();
		
		try {
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()) {
				System.out.println(sql);
				GoodsEntity goodsEntity = new GoodsEntity();
				goodsEntity.setImgPath(rs.getString(3));
				goodsEntity.setIntroduce(rs.getString(4));
				goodsEntity.setActor(rs.getString(5));
				goodsEntity.setVideoPath(rs.getString(6));
				goodsEntity.setContent(rs.getString(7));
				goodsEntities.add(goodsEntity);
				System.out.println(goodsEntities);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return goodsEntities;
	}

	public List<GoodsEntity> searchIntroduce(String introduce) {
		List<GoodsEntity> goodsEntities = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "select * from good where introduce like '%" + introduce + "%'";
		
		conn = DBUtil.getConn();
		
		try {
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()) {
				System.out.println(sql);
				GoodsEntity goodsEntity = new GoodsEntity();
				goodsEntity.setImgPath(rs.getString(3));
				goodsEntity.setIntroduce(rs.getString(4));
				goodsEntity.setActor(rs.getString(5));
				goodsEntity.setVideoPath(rs.getString(6));
				goodsEntity.setContent(rs.getString(7));
				goodsEntities.add(goodsEntity);
				System.out.println(goodsEntities);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return goodsEntities;
	}

}
