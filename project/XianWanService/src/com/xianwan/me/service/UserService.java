package com.xianwan.me.service;

import java.util.List;

import com.xianwan.me.dao.SearchDao;
import com.xianwan.me.dao.userDao;
import com.xianwan.me.entity.User;
import com.xianwan.me.entity.UserDetail;

public class UserService {
	public List<User> queryUser(String userAccount) {
		return new userDao().queryUser(userAccount);
	}
}
