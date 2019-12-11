package com.xianwan.me.service;

import java.sql.Date;

import com.xianwan.me.dao.FabuPicDao;



public class FabuPicService {
	public void addPicToSQL(String id,String userId,String address,Date uploadTime,int frequency,String firstUrl) {
		FabuPicDao fabuPicDao = new FabuPicDao();
		fabuPicDao.addPicToSQL(id, userId, address, uploadTime,frequency,firstUrl);
	}
	/*
	public String queryPicGetFirst(String userId) {
		FabuPicDao fabuPicDao = new FabuPicDao();
		return fabuPicDao.queryPicGetFirst(userId);
	}
	*/
	public int queryRrequency(String userId) {
		FabuPicDao fabuPicDao = new FabuPicDao();
		return fabuPicDao.queryRrequency(userId);
	}
}
