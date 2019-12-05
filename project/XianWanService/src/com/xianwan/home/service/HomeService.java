package com.xianwan.home.service;

import java.util.List;


import com.xianwan.home.dao.HomeDao;
import com.xianwan.home.entity.Commodity;

public class HomeService {
	public List<Commodity> queryCommodity(String attr) {
		return new HomeDao().queryCommodity(attr);
	}
	
	
}
