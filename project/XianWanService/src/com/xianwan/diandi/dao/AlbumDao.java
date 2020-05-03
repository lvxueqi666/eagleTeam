package com.xianwan.diandi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.xianwan.diandi.entity.Album;
import com.xianwan.util.DBUtil;

public class AlbumDao {
	
	public List<List<Album>> queryAlbumByUserAccount(String userAccount) {
		List<List<Album>> albums = new ArrayList<List<Album>>();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "select * from diandi where userAccount = ? order by time desc";
		conn = DBUtil.getConn();
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, userAccount);
			rs = pstm.executeQuery();
			String time = null;
			if(rs.next()) {
				time = rs.getString(2);
				rs.previous();
			}
			List<Album> album = new ArrayList<Album>();
			Album al = new Album();
			while(rs.next()) {
				if(rs.getString(2).equals(time)) {
					System.out.println("匹配+"+rs.getString(2));
					al.getUrl().add(rs.getString(3));
				}else {
					al.setTime(rs.getString(2));
					album.add(al);
					System.out.println("不匹配+"+rs.getString(2));
					albums.add(album);
					album = new ArrayList<Album>();
					time = rs.getString(2);
					al = new Album();
					rs.previous();
				}
			}
//			while(rs.next()) {
//				if(rs.getString(2).equals(time)) {
//					System.out.println("匹配+"+rs.getString(2));
//					Album al = new Album();
//					al.setTime(rs.getString(2));
//					al.setUrl(rs.getString(3));
//					album.add(al);
//				}else {
//					System.out.println("不匹配+"+rs.getString(2));
//					albums.add(album);
//					album = new ArrayList<Album>();
//					time = rs.getString(2);
//					rs.previous();
//				}
//			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return albums;
	}
}
