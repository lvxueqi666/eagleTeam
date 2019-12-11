package com.example.qiqi.xianwan.entity;

public class UserDetail {
    private String userAccount;
    private String userName;
    private String  userSex;
    private String userBirth;
    private String userLocation;
    private String userJianjie;
    private  String userJob;
    private String userJobName;
private String userPicture;

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }

    public UserDetail(String userAccount, String userName, String userSex, String userBirth, String userLocation, String userJianjie, String userJob, String userJobName, String userPicture) {
        this.userAccount = userAccount;
        this.userName = userName;
        this.userSex = userSex;
        this.userBirth =userBirth;
        this.userLocation = userLocation;
        this.userJianjie = userJianjie;
        this.userJob = userJob;
        this.userJobName = userJobName;
        this.userPicture = userPicture;
    }



    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public void setUserBirth(String userBirth) {
        this.userBirth = userBirth;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public void setUserJianjie(String userJianjie) {
        this.userJianjie = userJianjie;
    }

    public void setUserJob(String userJob) {
        this.userJob = userJob;
    }

    public void setUserJobName(String userJobName) {
        this.userJobName = userJobName;
    }


    public String getUserName() {
        return userName;
    }

    public String getUserSex() {
        return userSex;
    }

    public String getUserBirth() {
        return userBirth;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public String getUserJianjie() {
        return userJianjie;
    }

    public String getUserJob() {
        return userJob;
    }

    public String getUserJobName() {
        return userJobName;
    }


}
