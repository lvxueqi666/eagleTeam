package com.xianwan.home.service;

import java.util.List;

import com.xianwan.home.dao.SearchDao;
import com.xianwan.home.entity.Commodity;

public class SearchService {
	public List<Commodity> queryCommodityForSearch(String tag){
		return new SearchDao().queryCommodityForSearch(tag);
	}
}
