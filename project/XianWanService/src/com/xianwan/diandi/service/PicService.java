package com.xianwan.diandi.service;

import com.xianwan.diandi.dao.PicDao;

public class PicService {
	
	PicDao picDao = new PicDao();
	
	public void addUrl(String url){
		picDao.addUrl(url);
	}
}
