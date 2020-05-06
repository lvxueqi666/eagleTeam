package com.xianwan.ketang.service;

import java.util.List;

import com.xianwan.ketang.dao.GoodDao;
import com.xianwan.ketang.entity.GoodsEntity;



public class GoodService {
	public List<GoodsEntity> queryGoodbyType1(int type1) {
		System.out.println("service");
		return new GoodDao().queryGoodbyType1(type1);
	}

	public List<GoodsEntity> searchIntroduce(String introduce) {
		
		return new GoodDao().searchIntroduce(introduce);
	}
}
