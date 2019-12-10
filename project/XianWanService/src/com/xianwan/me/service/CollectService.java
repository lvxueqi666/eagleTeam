package com.xianwan.me.service;
import java.util.List;

import com.xianwan.home.entity.Commodity;
import com.xianwan.me.dao.CollectDao;
public class CollectService {
	public List<Commodity> queryCommodityIdByUserAccount(String userAccount) {
		return new CollectDao().queryCommodityIdByUserAccount(userAccount);
	}
}
