package com.xianwan.home.service;

import java.util.List;

import com.xianwan.home.dao.DetailImageDao;

public class DetailImageService {
	public List<String> queryImageUrl(String userAccount,String firstUrl) {
		return new DetailImageDao().queryImageUrl(userAccount, firstUrl);
	}
}
