package com.xianwan.login.service;

import java.sql.SQLException;

import com.xianwan.login.dao.RegisterDao;

public class RegisterService {
	public String insertUser( String userAccount, String userPassword,String userName) throws SQLException {
		RegisterDao registerDao = new RegisterDao();
		return registerDao.insert(userAccount,userPassword,userName);
	}

	public String accountCheck(String account) throws SQLException {
		RegisterDao registerDao = new RegisterDao();
		return registerDao.accountCheck(account);
	}
}
