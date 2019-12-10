package com.xianwan.me.service;

import java.util.List;

import com.xianwan.home.dao.HomeDao;
import com.xianwan.home.entity.Commodity;
import com.xianwan.me.dao.FabuDao;

public class FabuService {
	public List<Commodity> queryFabu(String attr) {
		return new FabuDao().queryFabu(attr);
	}
}
