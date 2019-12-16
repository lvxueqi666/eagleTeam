package com.example.qiqi.xianwan.entity;

public class User {
    private String userAccount;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public String getUserAccount() {

        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public User(String userAccount, String userName) {
        this.userAccount = userAccount;
        this.userName = userName;
    }
}
