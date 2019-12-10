package com.xianwan.me.entity;

import java.net.URL;
import java.sql.Date;

public class FabuPic {
	//复合唯一值id 由用户名图片作用类型及上传时间决定
	private String id;
	private String userId;
	private URL address;
	private Date uploadTime;
	private int frequency;
	
	public FabuPic() {
		super();
	}
	
	
	
	public int getFrequency() {
		return frequency;
	}



	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}



	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}



	public URL getAddress() {
		return address;
	}
	public void setAddress(URL address) {
		this.address = address;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	
	
}
