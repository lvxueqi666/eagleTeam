package com.xianwan.diandi.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Album {
	private String userAccount;
	private String time;
	private List<String> url = new ArrayList<String>();;
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String string) {
		this.time = string;
	}
	public List<String> getUrl() {
		return url;
	}
	public void setUrl(List<String> url) {
		this.url = url;
	}
	
	
}
