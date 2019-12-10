package com.xianwan.me.service;

import java.sql.Date;

import com.xianwan.me.dao.HeadPicDao;



public class HeadPicService {
	
	public String queryHeadPic(String userId) {
		HeadPicDao headPicDao = new HeadPicDao();
		return headPicDao.queryHeadPic(userId);
	}
	public void saveHeadPic(String id,String address,String userId,Date uploadTime) {
		HeadPicDao headPicDao = new HeadPicDao();
		headPicDao.saveHeadPic(id, address, userId, uploadTime);
	}
}
