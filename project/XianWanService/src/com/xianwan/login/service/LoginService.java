package com.xianwan.login.service;

import java.sql.SQLException;

import com.xianwan.login.dao.LoginDao;

public class LoginService {
	public String loginCheck(String userAccount, String userPassword) throws SQLException{
		return new LoginDao().checkUser(userAccount,userPassword);
	}

	public String insertAccountAndName(String userAccount, String result) throws SQLException {
		LoginDao loginDao = new LoginDao();
		return loginDao.insertAccountAndName(userAccount,result);
	}
}
