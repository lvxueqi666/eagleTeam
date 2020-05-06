package com.example.qiqi.xianwan.ketang;

import java.io.Serializable;

class GoodsEntity implements Serializable {
    public int id;
    public int type;
    public String imgPath;
    public String introduce;
    public String actor;
    public String videoPath;
    public String content;
    public GoodsEntity() {
    }

    public GoodsEntity(int id, int type, String imgPath, String introduce, String actor, String videoPath, String content) {
        this.id = id;
        this.type = type;
        this.imgPath = imgPath;
        this.introduce = introduce;
        this.actor = actor;
        this.videoPath=videoPath;
        this.content=content;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "GoodsEntity{" +
                "imgPath='" + imgPath + '\'' +
                ", introduce='" + introduce + '\'' +
                ", actor='" + actor + '\'' +
                '}';
    }
}
