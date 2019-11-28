package com.example.qiqi.xianwan.entity;

public class Notice {
    private String noticeDate;
    private String noticeTitle;
    private String noticeDetails;
    private int noticeImg;

    public String getNoticeDate() {
        return noticeDate;
    }
    public String getNoticeTitle() {
        return noticeTitle;
    }
    public String getNoticeDetails() {
        return noticeDetails;
    }
    public int getNoticeImg() {
        return noticeImg;
    }

    public Notice(String noticeDate,String noticeTitle,String noticeDetails,int noticeImg){
        this.noticeDate = noticeDate;
        this.noticeTitle = noticeTitle;
        this.noticeDetails = noticeDetails;
        this.noticeImg = noticeImg;
    }
}
