package com.example.qiqi.xianwan.entity;

public class Commodity {
    private long id;
    private String image;
    private String introduce;
    private String price;
    private String userAccount;
    private String icon;
    private String attr;
    private String showLike;
    private String tag;
    private String userName;

    public Commodity(long id, String image, String introduce, String price, String tag, String userAccount, String icon, String userName, String attr, String showLike) {
        this.id = id;
        this.image = image;
        this.introduce = introduce;
        this.price = price;
        this.userAccount = userAccount;
        this.icon = icon;
        this.attr = attr;
        this.showLike = showLike;
        this.tag = tag;
        this.userName = userName;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public void setShowLike(String showLike) {
        this.showLike = showLike;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getIntroduce() {
        return introduce;
    }

    public String getPrice() {
        return price;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public String getIcon() {
        return icon;
    }

    public String getAttr() {
        return attr;
    }

    public String getShowLike() {
        return showLike;
    }

    public String getTag() {
        return tag;
    }

    public String getUserName() {
        return userName;
    }
}
