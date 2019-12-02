package com.xianwan.home.service;

import com.xianwan.home.dao.ShowLikeDao;

public class ShowLikeService {
	public void modifyLikeCount(String commodityId,String operate) {
		new ShowLikeDao().modifyLikeCount(commodityId,operate);
	}
	
	public void addShowLike(int commodityId,int userId) {
		new ShowLikeDao().addShowLike(commodityId, userId);
	}
	
	public void cancelShowLike(int commodityId,int userId) {
		new ShowLikeDao().cancelShowLike(commodityId, userId);
	}
	
	public boolean adjustIfExistShowLike(int commodityId,int userId) {
		return new ShowLikeDao().adjustIfExistShowLike(commodityId, userId);
	}
}
