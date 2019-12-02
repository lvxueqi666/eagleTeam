package com.example.qiqi.xianwan.entity;

public class Message {
    private String userName;
    private String rules;
    private String date;
    private int userImgId;
    private int productImgId;

    public String getUserName() {
        return userName;
    }
    public String getRules() {
        return rules;
    }
    public String getDate() {
        return date;
    }
    public int getUserImgId() {
        return userImgId;
    }
    public int getProductImgId() {
        return productImgId;
    }

    public Message(String userName,String rules,String date,int userImgId,int productImgId){
        this.userName = userName;
        this.rules = rules;
        this.date = date;
        this.userImgId = userImgId;
        this.productImgId = productImgId;

    }
}
