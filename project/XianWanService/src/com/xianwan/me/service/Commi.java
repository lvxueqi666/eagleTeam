package com.xianwan.me.service;

import java.sql.SQLException;

import com.xianwan.me.dao.CommiDao;

public class Commi {
	public void addCommi(String address,String introduce,String price,String attr,String userAccount,String userName) {
		new CommiDao().insertCommidity(address,introduce,price,attr,userAccount,userName);
	}

	public String findAddressByAccount(String userAccount) throws SQLException {
		return new CommiDao().findAddress(userAccount);
	}
}
