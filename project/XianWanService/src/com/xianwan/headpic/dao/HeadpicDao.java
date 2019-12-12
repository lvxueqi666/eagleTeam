package com.xianwan.headpic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.HEAD;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.xianwan.headpic.entity.Headpic;
import com.xianwan.util.DBUtil;

public class HeadpicDao {
	private List<Headpic> list = new ArrayList<Headpic>();
	public List<Headpic> querytoHeadpic() throws SQLException{
		Connection con = DBUtil.getConn();
		String sql = "select * from headpic";
		PreparedStatement pstm = con.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			Headpic headpic = new Headpic();
			headpic.setUserAccount(rs.getString(3));
			headpic.setAddress(rs.getString(2));
			list.add(headpic);
		}
		return list;
	}
}
