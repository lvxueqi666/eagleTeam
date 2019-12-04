package com.xianwan.home.service;

import java.util.List;

import com.xianwan.home.dao.CollectDao;

public class CollectService {
	public List<Integer> queryCommodityIdByUserId(String userId){
		return new CollectDao().queryCommodityIdByUserId(userId);
	}
	
	public void addCollection(int commodityId,int userId) {
		new CollectDao().addCollection(commodityId, userId);
	}
	
	public void cancelCollection(int commodityId,int userId) {
		new CollectDao().cancelCollection(commodityId, userId);
	}
	
	public boolean adjustIfExistCollection(int commodityId,int userId) {
		return new CollectDao().adjustIfExistCollection(commodityId, userId);
	}
}
