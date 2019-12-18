package com.xianwan.home.service;

import java.util.List;

import com.xianwan.home.dao.CollectDao;

public class CollectService {
	public List<Integer> queryCommodityIdByUserAccount(String userAccount){
		return new CollectDao().queryCommodityIdByUserAccount(userAccount);
	}
	
	public void addCollection(String commodityId,String userAccount) {
		new CollectDao().addCollection(commodityId, userAccount);
	}
	
	public void cancelCollection(String commodityId,String userAccount) {
		new CollectDao().cancelCollection(commodityId, userAccount);
	}
	
	public boolean adjustIfExistCollection(String commodityId,String userId) {
		return new CollectDao().adjustIfExistCollection(commodityId, userId);
	}
}
