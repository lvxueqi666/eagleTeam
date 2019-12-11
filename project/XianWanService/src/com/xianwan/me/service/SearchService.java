package com.xianwan.me.service;

import java.util.List;

import com.xianwan.home.entity.Commodity;
import com.xianwan.me.dao.FabuDao;
import com.xianwan.me.dao.SearchDao;
import com.xianwan.me.entity.UserDetail;

public class SearchService {
	public List<UserDetail> queryUserDetail(String detail) {
		return new SearchDao().queryUserDetail(detail);
	}
}
