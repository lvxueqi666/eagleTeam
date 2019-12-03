package com.xianwan.login.service;

import java.sql.SQLException;

import com.xianwan.login.dao.RegisterDao;

public class RegisterService {

	public String insertUser(String userName, String userAccount, String userPassword) throws SQLException {
		RegisterDao registerDao = new RegisterDao();
		return registerDao.insert(userName,userAccount,userPassword);
	}

}
