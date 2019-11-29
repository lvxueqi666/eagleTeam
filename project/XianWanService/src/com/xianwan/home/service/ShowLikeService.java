package com.xianwan.home.service;

import com.xianwan.home.dao.ShowLikeDao;

public class ShowLikeService {
	public void modifyLikeCount(String commodityId,String operate) {
		new ShowLikeDao().modifyLikeCount(commodityId,operate);
	}
}
