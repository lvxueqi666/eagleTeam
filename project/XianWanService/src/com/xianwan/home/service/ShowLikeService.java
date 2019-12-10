package com.xianwan.home.service;

import com.xianwan.home.dao.ShowLikeDao;

public class ShowLikeService {
	public void modifyLikeCount(String commodityId,String operate) {
		new ShowLikeDao().modifyLikeCount(commodityId,operate);
	}
	
	public void addShowLike(int commodityId,String userAccount) {
		new ShowLikeDao().addShowLike(commodityId, userAccount);
	}
	
	public void cancelShowLike(int commodityId,String userAccount) {
		new ShowLikeDao().cancelShowLike(commodityId, userAccount);
	}
	
	public boolean adjustIfExistShowLike(int commodityId,String userAccount) {
		return new ShowLikeDao().adjustIfExistShowLike(commodityId, userAccount);
	}
}
