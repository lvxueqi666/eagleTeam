package com.xianwan.me.service;

import com.xianwan.me.dao.personDao;

public class personService {
	public void addPerson(String userAccount,String userName,String userSex ,String userBirth, String userLocaton, String userJianjie, String userJob, String userJobName) {
		new personDao().insertPerson(userAccount,userName,userSex,userBirth,userLocaton,userJianjie,userJob,userJobName);
	}
}
