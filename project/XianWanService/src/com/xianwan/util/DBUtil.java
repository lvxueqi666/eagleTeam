package com.xianwan.util;


import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	
	private static Connection conn;
	
	
	public static Connection getConn() {
		try {
			if(null == conn || conn.isClosed()) {
				try {
					Class.forName("com.mysql.jdbc.Driver");
					conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lizi" + 
					"?useUnicode=true&characterEncoding=utf-8","root","");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return conn;
		
		
	}
	
	public void closeCon(Connection con){
		try {
			con.close();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

}

