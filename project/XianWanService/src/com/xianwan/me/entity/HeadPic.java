package com.xianwan.me.entity;

import java.net.URL;
import java.sql.Date;

public class HeadPic {
	//复合唯一值id 由用户名图片作用类型及上传时间决定
	private String id;
	private URL address;
	private String userId;
	private Date uploadTime;
	
	public HeadPic() {
		super();
	}
	
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public URL getAddress() {
		return address;
	}
	public void setAddress(URL address) {
		this.address = address;
	}
	
	
	
	 

}
