package com.xianwan.diandi.service;

import java.sql.SQLException;

import com.xianwan.diandi.dao.PicDao;

public class PicService {
	
	PicDao picDao = new PicDao();
	
	public void addUrl(String url,String userId,String time) throws SQLException{
		picDao.addUrl(url,userId,time);
	}
}
